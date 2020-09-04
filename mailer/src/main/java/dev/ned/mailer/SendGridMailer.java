package dev.ned.mailer;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import dev.ned.config.SendGridConfiguration;
import dev.ned.helpers.EmailTypeIdentifierEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class SendGridMailer implements MailerInterface {

    @Autowired
    SendGridConfiguration sendGridConfiguration;

    @Override
    public Integer sendPersonalizedEmail(String email, Map<String, String> data, EmailTypeIdentifierEnum identifier) throws IOException {
        Email from = new Email(sendGridConfiguration.getFrom());
        Email to = new Email(email);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(sendGridConfiguration.getTemplates().get(identifier.toString()));
        Personalization personalization = new Personalization();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }
        personalization.addTo(to);
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid(sendGridConfiguration.getApiKey());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
