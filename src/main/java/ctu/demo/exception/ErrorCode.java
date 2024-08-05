/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.exception;

/**
 *
 * @author ADMIN
 */
public enum ErrorCode {
    EXITED_ERROR(400,"EXISTED"),
    NOTFOUND_ERROR(400,"Not Found"),
    SERVER_ERROR(500,"Internal Server Error"),
    UNAUTHENTICATED(401,"UNAUTHENTICATED");
    
    ErrorCode(int code, String message){
        this.code=code;
        this.message=message;
}
    int code;
    String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}
