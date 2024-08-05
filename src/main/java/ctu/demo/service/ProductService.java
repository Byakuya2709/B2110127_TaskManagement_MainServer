/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.exception.AppException;
import static ctu.demo.exception.ErrorCode.NOTFOUND_ERROR;
import ctu.demo.model.Product;
import ctu.demo.repository.ProductRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
 
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new AppException(NOTFOUND_ERROR,id));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
  
}
