package ru.hh.checkly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.hh.checkly.service.MailSenderService;
import ru.hh.nab.common.properties.FileSettings;

import javax.inject.Named;
import java.util.Properties;

@Import({
    MailSenderService.class,
})
@Configuration
public class MailConfig {
  @Bean
  public Properties mailProperties(FileSettings fileSettings) {
    return fileSettings.getSubProperties("mail");
  }

  @Bean
  public JavaMailSender getJavaMailSender(@Named("mailProperties") Properties mailProperties) {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(mailProperties.getProperty("host"));
    mailSender.setPort(Integer.parseInt(mailProperties.getProperty("port")));

    mailSender.setUsername(mailProperties.getProperty("username"));
    mailSender.setPassword(mailProperties.getProperty("password"));

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", mailProperties.getProperty("transport.protocol"));
    props.put("mail.smtp.auth", mailProperties.getProperty("smtp.auth"));
    props.put("mail.smtp.starttls.enable", mailProperties.getProperty("smtp.starttls.enable"));
    props.put("mail.debug", mailProperties.getProperty("debug"));
    props.put("mail.smtp.connectiontimeout", mailProperties.getProperty("smtp.connectiontimeout"));
    props.put("mail.smtp.timeout", mailProperties.getProperty("smtp.timeout"));
    props.put("mail.smtp.writetimeout", mailProperties.getProperty("smtp.writetimeout"));

    return mailSender;
  }
}
