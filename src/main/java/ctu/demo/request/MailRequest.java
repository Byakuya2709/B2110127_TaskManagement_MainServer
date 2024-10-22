/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.request;

/**
 *
 * @author ADMIN
 */
public class MailRequest {
    String email;
    String code;
    String type;

    public String getEmail() {
        return email;
    }

    public String getTypeOtp() {
        return type;
    }

    public void setTypeOtp(String typeOtp) {
        this.type = typeOtp;
    }

    public MailRequest(String email, String code, String typeOtp) {
        this.email = email;
        this.code = code;
        this.type = typeOtp;
    }

    public MailRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
