/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.respone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

/**
 *
 * @author ADMIN
 */
public class ResponseHandler {
     public static ResponseEntity<Object> resBuilder(String mes, HttpStatus statusCode,Object res){
        Map<String,Object> response = new HashMap<>();
        response.put("message",mes);
        response.put("status",statusCode);
        response.put("data",res);

        return new ResponseEntity<>(response,statusCode);
    }
      public static ResponseEntity<Object> resBuilder(APIResponse res){
        Map<String,Object> response = new HashMap<>();
        response.put("message",res.getMessage());
        response.put("status",res.getCode());
        response.put("data",res.getResult());

        return new ResponseEntity<>(response,HttpStatus.valueOf(res.getCode()));
    }
     
     public static ResponseEntity<Object> resBuilder(String message, HttpStatus status, List<ObjectError> errors) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(message, errors);
        return new ResponseEntity<>(errorResponse, status);
    }
}
