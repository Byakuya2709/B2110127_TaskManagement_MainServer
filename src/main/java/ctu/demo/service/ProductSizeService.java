/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.model.ProductSize;
import ctu.demo.repository.ProductSizeRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ProductSizeService {
       @Autowired
       ProductSizeRepository productSizeRepository;
     

    public List<ProductSize> getAllProductSizes() {
        return productSizeRepository.findAll();
    }

    public ProductSize getProductSizeById(Long id) {
        return productSizeRepository.findById(id).orElse(null);
    }

    public ProductSize saveProductSize(ProductSize productSize) {
        return productSizeRepository.save(productSize);
    }

    public void deleteProductSize(Long id) {
        productSizeRepository.deleteById(id);
    }
}
