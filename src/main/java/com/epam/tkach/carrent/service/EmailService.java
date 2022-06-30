package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class EmailService {
    private static final Logger logger = LogManager.getLogger(EmailService.class);
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            logger.error(exception);
        }
    }

    //public
    public void notifyAboutTopUp(User user, String locale, double amount){
        if (user.isReceiveNotifications()){
            String formattedDouble = new DecimalFormat("#0.00").format(amount);
            sendSimpleMessage(user.getEmail(),
                    "Your balance was topped up",
                    String.format("Your account has been topped up. Amount %s UAH.",formattedDouble));
        }
    }
}
