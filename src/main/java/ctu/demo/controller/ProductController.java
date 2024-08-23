/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.utils.FileUtil;
import ctu.demo.respone.ResponseHandler;
import ctu.demo.dto.ProductMapper;
import ctu.demo.dto.ProductRequest;
import ctu.demo.model.Image;
import ctu.demo.model.Product;
import ctu.demo.model.ProductSize;
import ctu.demo.model.ProductType;
import ctu.demo.service.ImageService;
import ctu.demo.service.ProductService;
import ctu.demo.service.ProductTypeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ImageService imageService;
    @Autowired
    ProductTypeService productTypeService;
    
     @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProductById(id);
        if (product==null) return ResponseHandler.resBuilder("NOTFOUND", HttpStatus.NOT_FOUND, null);               
        return ResponseHandler.resBuilder("ok", HttpStatus.OK, ProductMapper.toDTO(product));
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<Object> getImage(@PathVariable("id") Long id){
        Image image = imageService.getimageById(id);
        if (image==null) return ResponseHandler.resBuilder("NOTFOUND", HttpStatus.NOT_FOUND, null);
        

        
        return ResponseHandler.resBuilder("ok", HttpStatus.OK, image);
    }
    @GetMapping("/types")
    public ResponseEntity<Object> getAllProductTypes1(){
        List<ProductType> listProduct = productTypeService.getAllProductTypes();   
        return ResponseHandler.resBuilder("ok", HttpStatus.OK, listProduct);
    }
    @PostMapping("/new")
    public ResponseEntity<Object> uploadProduct(
        @RequestParam("productName") String productName,
        @RequestParam("productPrice") Double productPrice,
        @RequestParam("productType") String productType,
        @RequestParam("productDescription") String productDescription,
        @RequestParam("productSizes") String[] productSizes,
        @RequestParam("quantities") Integer[] quantities,
        @RequestParam("image") MultipartFile image) {
        System.out.println(productType);
        try {
            byte[] data = image.getBytes();
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            Image img = new Image();
            img.setFileName(fileName);
            img.setFilePath(FileUtil.saveImage(image, productName)); 
            img.setData(data);
            

            // Save the product
             Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(productPrice.intValue());
            product.setProductDescription(productDescription);
            img.setProduct(product);
             // Find the existing ProductType
            ProductType existingProductType = productTypeService.getProductTypeById(productType);
            if (existingProductType == null) {
            return ResponseHandler.resBuilder("ProductType is not existing", HttpStatus.NOT_FOUND, new ProductType());
        }
           product.setProductType(existingProductType);
   
           product.getImages().add(img); 

           List<ProductSize> productSizeList = new ArrayList<>();
            for (int i = 0; i < productSizes.length; i++) {
                ProductSize productSize = new ProductSize();
                productSize.setSizeName(productSizes[i]);
                productSize.setQuantity(quantities[i]);
                productSize.setProduct(product);
                productSizeList.add(productSize);
            }
            product.setProductSizes(productSizeList);

            productService.saveProduct(product);

            return ResponseHandler.resBuilder("Product uploaded successfully!", HttpStatus.OK,ProductMapper.toDTO(product));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
//    @PostMapping("/new")
//    public ResponseEntity<Object> uploadProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseHandler.resBuilder("Validation errors", HttpStatus.BAD_REQUEST, bindingResult.getAllErrors());
//        }
//
//        try {
//            byte[] data = productRequest.getImage().getBytes();
//            String fileName = StringUtils.cleanPath(productRequest.getImage().getOriginalFilename());
//            Image img = new Image();
//            img.setFileName(fileName);
//            img.setFilePath(FileUtil.saveImage(productRequest.getImage(), productRequest.getProductName())); 
//            img.setData(data);
//            
//            // Save the product
//            Product product = new Product();
//            product.setProductName(productRequest.getProductName());
//            product.setProductPrice(productRequest.getProductPrice().intValue());
//            product.setProductDescription(productRequest.getProductDescription());
//            img.setProduct(product);
//
//            // Find the existing ProductType
//            ProductType existingProductType = productTypeService.getProductTypeById(productRequest.getProductType());
//            if (existingProductType == null) {
//                return ResponseHandler.resBuilder("ProductType is not existing", HttpStatus.NOT_FOUND, new ProductType());
//            }
//            product.setProductType(existingProductType);
//
//            product.getImages().add(img);
//
//            List<ProductSize> productSizeList = new ArrayList<>();
//            for (int i = 0; i < productRequest.getProductSizes().length; i++) {
//                ProductSize productSize = new ProductSize();
//                productSize.setSizeName(productRequest.getProductSizes()[i]);
//                productSize.setQuantity(productRequest.getQuantities()[i]);
//                productSize.setProduct(product);
//                productSizeList.add(productSize);
//            }
//            product.setProductSizes(productSizeList);
//
//            productService.saveProduct(product);
//
//            return ResponseHandler.resBuilder("Product uploaded successfully!", HttpStatus.OK, ProductMapper.toDTO(product));
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
//        }
//    }
}
