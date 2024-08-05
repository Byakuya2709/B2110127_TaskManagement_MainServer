/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.respone;

/**
 *
 * @author ADMIN
 * @param <T>
 */
public class APIResponse <T> {
    int code;
    String message;
    T result;

    public APIResponse() {
    }

    public APIResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
    
}
