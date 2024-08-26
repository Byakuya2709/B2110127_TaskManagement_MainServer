/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.exception;

import ctu.demo.respone.APIResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 *
 * @author ADMIN
 */
@ControllerAdvice
public class globalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("timestamp", LocalDateTime.now()); // Thêm thời gian lỗi xảy ra
        errorResponse.put("path", request.getDescription(false)); // Thêm đường dẫn yêu cầu

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<Map<String, Object>> handleClientAbortException(ClientAbortException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Client aborted the connection.");
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("path", request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundExceptions(UsernameNotFoundException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Model model) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = RuntimeException.class)
//    public String handleRuntimeException(Model model, RuntimeException ex) {
//
//        APIResponse res = new APIResponse();
//        res.setCode(500);
//        res.setMessage(ex.getMessage());
//        res.setResult(ex.getClass()); 
//        model.addAttribute("res", res);
//        return "errorPage";
//    }
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, Model model) {
//        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
//            model.addAttribute("errorMessage", "Tên sản phẩm đã tồn tại.");
//            return "uploadProduct"; 
//        }
//        model.addAttribute("errorMessage", "Đã xảy ra lỗi khi lưu sản phẩm.");
//        return "uploadProduct"; 
//    }
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<?> handlingAppException(Model model, AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        APIResponse res = new APIResponse();
        res.setCode(errorCode.getCode());
        res.setMessage(errorCode.getMessage());
        res.setResult(ex.getResult());

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleConstraintViolationException(ConstraintViolationException ex, Model model) {
//        model.addAttribute("errorMessage", "Tên sản phẩm đã tồn tại.");
//        return "uploadProduct"; 
//    }
//         @ExceptionHandler(value = AppException.class)
//         public String handlingAppException(Model model,ResponseEntity<Object> res) {
//            model.addAttribute("res", res);
//            return "errorPage";
//        }
//    @ExceptionHandler(FileStorageException.class)
//    String  handleFileStorageException(FileStorageException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "errorPage";
//    }
    // tra ve error dua vao message tra ve
    @ExceptionHandler(value = ArithmeticException.class)
    ResponseEntity<APIResponse> handlingArithmeticException(ArithmeticException ex) {
        String message = ex.getMessage();

        ErrorCode errorCode = ErrorCode.valueOf(message);

        APIResponse res = new APIResponse();

        res.setCode(errorCode.getCode());
        res.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(res);

    }
}
