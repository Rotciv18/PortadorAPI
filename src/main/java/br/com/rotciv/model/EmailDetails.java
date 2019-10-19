package br.com.rotciv.model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailDetails {

    private String emailTo;
    private String emailSubject;
    private String emailCC;
    private String emailBody;

    public EmailDetails(){

    }

    public EmailDetails(String emailTo, String emailSubject, String emailCC, String emailBody) {
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailCC = emailCC;
        this.emailBody = emailBody;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
