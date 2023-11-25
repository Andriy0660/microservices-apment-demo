package com.andrii.smtpclient.email;

import com.andrii.email.EmailRequest;
import com.andrii.email.SuccessRegistrationInfo;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder {
    public static String buildMessage(EmailRequest request) {

        String messageBuilder = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;background-color:#f6f8fa;padding:20px\">\n" +
                "\n" +
                "  <div style=\"font-size:28px;font-weight:bold;color:#ffffff;text-align:center;margin-bottom:10px;background-color:#000000;height:50px;line-height:50px\">\n" +
                "    <span style=\"color:#ffffff;\">Greeting!</span>\n" +
                "  </div>\n" +
                "\n" +
                "  <div style=\"font-size:19px;line-height:25px;color:#0b0c0c;margin-bottom:20px\">\n" +
                "    Hi, " + request.getFirstName() + ".<br><br>\n" +
                "    Thank you for being registered. Your id is " + request.getId() + " <br><br>\n" +
                "<br>" +
                "    See you soon!\n" +
                "  </div>\n" +
                "\n" +
                "</div>";

        return messageBuilder;
    }
    public static String buildMessage(SuccessRegistrationInfo info) {

        String messageBuilder = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c;background-color:#f6f8fa;padding:20px\">\n" +
                "\n" +
                "  <div style=\"font-size:28px;font-weight:bold;color:#ffffff;text-align:center;margin-bottom:10px;background-color:#000000;height:50px;line-height:50px\">\n" +
                "    <span style=\"color:#ffffff;\">You have been successfully registered to our service</span>\n" +
                "  </div>\n" +
                "</div>";

        return messageBuilder;
    }
}
