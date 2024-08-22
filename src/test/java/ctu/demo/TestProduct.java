/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo;

import ctu.demo.model.Image;
import ctu.demo.model.Product;
import ctu.demo.model.ProductSize;
import ctu.demo.model.ProductType;

import ctu.demo.repository.ProductRepository;
import ctu.demo.repository.ProductTypeRepository;

import ctu.demo.service.ProductService;
import ctu.demo.service.ProductTypeService;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 *
 * @author ADMIN
 */
@SpringBootTest
public class TestProduct {
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

//    @Test
//    public void testSaveProductType() {
//        // Given
//        ProductType productType = new ProductType("AO", "Loại2");
//
//        // When
//        ProductType savedProductType = productTypeService.saveProductType(productType);
//
//        // Then
//        assertNotNull(savedProductType);
//        
//        assertEquals(productType.getType_id(), savedProductType.getType_id());
//    }
    @Test
    public void testSaveProductWithSizes() {
//        // Given
//        ProductType productType = productTypeService.getProductTypeById("3");
        //
//        Product product = new Product();
//        product.setProductName("Product 6");
//        product.setProductPrice(100);
//        product.setProductType(productType);
//
//        Set<ProductSize> productSizes = new HashSet<>();
//        productSizes.add(new ProductSize("S", 10, product));
//        productSizes.add(new ProductSize("M", 20, product));
//        productSizes.add(new ProductSize("XL", 15, product));
//
//        product.setProductSizes(productSizes);
//
//        // When
//        long id= (long)4;
//        Product savedProduct = productService.getProductById(id);
        
//        System.out.println(savedProduct.toString());
       
        // Then
//        assertEquals(100,savedProduct.getProductPrice());
      

//        
//       ProductType productType1 =productTypeService.getProductTypeById("3");
//       assertEquals("quandai",productType1.getType_name());
//       
//       
//
//    
//        Product fetchedProduct = productRepository.findById(savedProduct.getProductId()).orElse(null);
//        
//        
//        assertNotNull(fetchedProduct);
//        assertEquals(2, fetchedProduct.getProductSizes().size());
//        assertEquals(3, savedProduct.getProductSizes().size());
    }
     

}
