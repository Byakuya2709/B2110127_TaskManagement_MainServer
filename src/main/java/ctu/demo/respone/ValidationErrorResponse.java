/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.respone;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

/**
 *
 * @author ADMIN
 */
class ValidationErrorResponse extends APIResponse{
     private List<ObjectError> errors;

        public ValidationErrorResponse(String message, List<ObjectError> errors) {
            super(400,message, errors);
            this.errors = errors;
        }
}
