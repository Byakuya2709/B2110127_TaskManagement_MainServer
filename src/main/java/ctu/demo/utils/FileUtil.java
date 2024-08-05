/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.utils;
import ctu.demo.exception.FileStorageException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ctu.demo.model.Product;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ADMIN
 */
public class FileUtil {
     public static String saveImage(MultipartFile imageFile,String product){
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String uploadDir = "data/img/" + product;
        String finalPath=uploadDir+"/"+fileName;
//         try {
//            saveFile(uploadDir,fileName,imageFile);
//         } catch (IOException ex) { 
//            throw new FileStorageException("Failed to save image file: " + fileName, ex);
//         }
//                
        return finalPath;
    }
     
    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        
        
        
        String upload = "src/main/resources/static/" + uploadDir;
        Path uploadPath = Paths.get(upload);
        
         //tao thu muc
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new FileStorageException("Could not save image file: " + fileName, ioe);
        
        }     
    }
    
    public static String convertImageToBase64(byte[] imagebyte){
        return Base64.getEncoder().encodeToString(imagebyte);
    }
}
