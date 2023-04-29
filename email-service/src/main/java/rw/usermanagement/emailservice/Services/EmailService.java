package rw.usermanagement.emailservice.Services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import rw.usermanagement.emailservice.Dto.EmailDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    @KafkaListener(topics = "user-signup-topic", groupId = "send-notification-group")
    public void receiveMessage(EmailDto email) {
       System.out.println("Received message: " + email.getBody());
        LOGGER.info(String.format("Order event received in email service => %s", email.getBody()));

        try {
            // Send email using retry template
            //sendEmail(email);
        } catch (Exception e) {
            System.out.println("Failed to send email to "+ email.getRecipient() + ": " + e.getMessage());
        }
    }



    public void sendEmail(EmailDto emailDto) {
        log.info("sending email to {}" , emailDto.getRecipient());
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("rogerndutiye@gmail.com","User Management Application");
            mimeMessageHelper.setTo(emailDto.getRecipient());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody(), true);

        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("email has sent!!");
        }catch (MailException exception) {
            log.error("Exception occurred when sending mail {}",exception.getMessage());
        }
    }
}
