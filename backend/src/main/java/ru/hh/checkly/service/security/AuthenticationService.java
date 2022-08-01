package ru.hh.checkly.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dto.Credentials;
import ru.hh.checkly.dto.LoginResponseDto;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.entity.Recruiter;
import ru.hh.checkly.exception.RestErrorResponseCode;
import ru.hh.checkly.exception.RestException;
import ru.hh.nab.common.properties.FileSettings;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static ru.hh.checkly.exception.RestErrorResponseCode.AUTHORIZATION_TOKEN_IS_NOT_VALID;
import static ru.hh.checkly.service.mapping.RecruiterMapper.toRecruiterDto;

public class AuthenticationService {

  private final String authenticationScheme;
  private final String secretKey;
  private final int tokenExpirationTimeHours;

  private final RecruiterDao recruiterDao;

  @Inject
  public AuthenticationService(RecruiterDao recruiterDao, FileSettings fileSettings) {
    this.recruiterDao = recruiterDao;
    this.secretKey = fileSettings.getString("login.secretKey");
    this.tokenExpirationTimeHours = fileSettings.getInteger("login.tokenExpirationTimeInHours");
    this.authenticationScheme = fileSettings.getString("login.authenticationScheme");
  }

  @Transactional
  public LoginResponseDto getLoginResponse(Credentials credentials) {
    Recruiter recruiter = recruiterDao.getRecruiterByEmailAndPassword(credentials.getEmail(), credentials.getPassword())
      .orElseThrow(() -> new RestException(RestErrorResponseCode.INVALID_USER_OR_PASSWORD));
    return new LoginResponseDto(issueToken(recruiter.getId()), toRecruiterDto(recruiter));
  }

  @Transactional
  public RecruiterDto getUserInfo(String authorizationHeader) {
    String token = getTokenFromHeader(authorizationHeader);
    Long id = getId(token);
    Recruiter recruiter = recruiterDao.getRecruiterById(id)
      .orElseThrow(() -> new RestException(RestErrorResponseCode.RECRUITER_NOT_FOUND));
    return toRecruiterDto(recruiter);
  }

  void authenticate(String authorizationHeader) {
    validateToken(getTokenFromHeader(authorizationHeader));
  }

  public String issueToken(Long id) {
    return authenticationScheme + " " + Jwts.builder()
      .setId(String.valueOf(id))
      .setIssuedAt(Date.from(Instant.now()))
      .setExpiration(Date.from(Instant.now().plus(tokenExpirationTimeHours, ChronoUnit.HOURS)))
      .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
      .compact();
  }

  private void validateToken(String token) {
    if (getClaims(token).getExpiration().before(Date.from(Instant.now()))) {
      throw new RestException(AUTHORIZATION_TOKEN_IS_NOT_VALID);
    }
  }

  private String getTokenFromHeader(String authorizationHeader) {
    if (StringUtils.isEmpty(authorizationHeader) ||
      !authorizationHeader
        .toLowerCase()
        .startsWith(authenticationScheme.toLowerCase() + " ")) {
      throw new RestException(AUTHORIZATION_TOKEN_IS_NOT_VALID);
    }
    return authorizationHeader.substring(authenticationScheme.length()).trim();
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token)
        .getBody();
    } catch (JwtException e) {
      throw new RestException(AUTHORIZATION_TOKEN_IS_NOT_VALID);
    }
  }

  private Long getId(String token) {
    return Long.valueOf(getClaims(token).getId());
  }

}
