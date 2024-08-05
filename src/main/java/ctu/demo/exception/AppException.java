/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.exception;

/**
 *
 * @author ADMIN
 */
public class AppException extends RuntimeException{
    ErrorCode errorCode;
    Object result;

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    
    
    public AppException(ErrorCode errorCode,Object result ) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.result=result;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    
}
