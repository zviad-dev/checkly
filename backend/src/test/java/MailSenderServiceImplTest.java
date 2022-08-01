import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.checkly.dto.MailMessage;
import ru.hh.checkly.service.MailSenderService;
//import ru.hh.nab.testbase.NabTestBase;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;

/**
 * Тестирование отправки разных типов сообщений на почтовые ящики
 * <p>
 * Не включать в Test-suite
 *
 * @author strelchm
 */
@ContextConfiguration(classes = {AppTestConfig.class})
public class MailSenderServiceImplTest { // extends NabTestBase { TODO {strelchm} - moving to TestContainers

  private static final String FIRST_TEST_EMAIL_ADDRESS = "checklyhh@gmail.com";

  private static final String SECOND_TEST_EMAIL_ADDRESS = "strelchm@yandex.ru";

  @Inject
  private MailSenderService mailService;

  /**
   * Тестирование отправки простого текстового сообщения
   *
   * @throws MessagingException - разные варианты ошибок отправки сообщения
   */
  @Test
  @Ignore
  public void sendSimpleTextEmail() throws MessagingException {
    MailMessage mailMessage = new MailMessage(
        "Тестовое сообщение",
        "Привет, мир!",
        FIRST_TEST_EMAIL_ADDRESS,
        SECOND_TEST_EMAIL_ADDRESS
    );

    mailService.sendEmailWithAttachment(mailMessage);
  }

  /**
   * Тестирование отправки сообщения с html
   *
   * @throws MessagingException - разные варианты ошибок отправки сообщения
   */
  @Test
  @Ignore
  public void sendSimpleHtmlEmail() throws MessagingException {
    MailMessage mailMessage = new MailMessage(
        "Тестовое сообщение",
        "<h1>Hello, world!</h1><div style=\"background-color: red\">This is checkly!</div>",
        true,
        FIRST_TEST_EMAIL_ADDRESS,
        SECOND_TEST_EMAIL_ADDRESS
    );

    mailService.sendEmailWithAttachment(mailMessage);
  }

  /**
   * Тестирование отправки сообщения с одним файлом
   *
   * @throws MessagingException - разные варианты ошибок отправки сообщения
   */
  @Test
  @Ignore
  public void sendEmailWithAttachmentFile() throws MessagingException, IOException {
    MailMessage mailMessage = new MailMessage(
        "Тестовое сообщение c файлом",
        "Привет, тебе файл передали!",
        FIRST_TEST_EMAIL_ADDRESS,
        SECOND_TEST_EMAIL_ADDRESS
    );

    putAttachmentFile(mailMessage, "111.txt");

    mailService.sendEmailWithAttachment(mailMessage);
  }

  /**
   * Тестирование отправки сообщения с несколькими файлом
   *
   * @throws MessagingException - разные варианты ошибок отправки сообщения
   */
  @Test
  @Ignore
  public void sendEmailWithAttachmentFiles() throws MessagingException, IOException {
    MailMessage mailMessage = new MailMessage(
        "Тестовое сообщение c файлами",
        "Привет, тебе файл передали!",
        FIRST_TEST_EMAIL_ADDRESS,
        SECOND_TEST_EMAIL_ADDRESS
    );

    putAttachmentFile(mailMessage, "222.txt");
    putAttachmentFile(mailMessage, "333.txt");
    putAttachmentFile(mailMessage, "444.txt");
    putAttachmentFile(mailMessage, "555.txt");

    mailService.sendEmailWithAttachment(mailMessage);
  }

  /**
   * @param destMessage - объект сообщения
   * @param fileName    - имя файла c расширением
   * @throws IOException - io
   */
  private void putAttachmentFile(MailMessage destMessage, String fileName) throws IOException {
    File file = createRandomContentFile(fileName);
    assertTrue(file.length() > 0);
    destMessage.putFile(fileName, file);
  }

  /**
   * Генерация temp-файла. После завершения программы файл будет удален
   *
   * @param fileName - имя файла. Минимум 3 символа
   * @throws IOException - в случае закрытия OutputStream
   */
  private File createRandomContentFile(String fileName) throws IOException {
    File tempFile = new File(fileName);

    assertTrue(tempFile.createNewFile());

    tempFile.deleteOnExit();

    PrintWriter out = new PrintWriter(new FileWriter(tempFile));

    int i = 0;
    while (i < 100) {
      out.write(i + "\n");
      i++;
    }

    out.flush();
    out.close();

    return tempFile;
  }
}
