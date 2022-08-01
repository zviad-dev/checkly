package ru.hh.checkly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.hh.checkly.dto.MailMessage;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * Сервис отправки mail-сообщений
 *
 * @author strelchm
 */
@EnableAsync
public class MailSenderService {

  private final JavaMailSender mailSender;
  private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);

  @Inject
  public MailSenderService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * Отправка сообщения (с файлами-вложениями)
   *
   * @param message - pojo письма
   */
  @Async
  public void sendEmailWithAttachment(MailMessage message) {
    try {
      MimeMessage msg = mailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

      helper.setTo(message.getReceiverAddresses());
      helper.setSubject(message.getSubject());
      helper.setText(message.getBody(), message.isHtmlBody());

      for (Map.Entry<String, File> entry : message.getFiles().entrySet()) {
        helper.addAttachment(entry.getKey(), entry.getValue());
      }

      mailSender.send(msg);
    } catch (MessagingException ex) {
      LOGGER.error(String.format("Error during message %s sending", message), ex);
    }
  }
}
