/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
public class ProductRequest {
     @NotEmpty(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    private Double productPrice;

    @NotEmpty(message = "Product type is required")
    private String productType;

    @NotEmpty(message = "Product description is required")
    private String productDescription;

    @NotEmpty(message = "Product sizes are required")
    private List<ProductSizeRequest> productSizes;

    @NotNull(message = "Quantities are required")
    private Integer[] quantities;

    @NotNull(message = "Product image is required")
    private MultipartFile image;

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public List<ProductSizeRequest> getProductSizes() {
        return productSizes;
    }

  

    public Integer[] getQuantities() {
        return quantities;
    }

    public MultipartFile getImage() {
        return image;
    }
    public class ProductSizeRequest {
    @NotEmpty(message = "Size name is required")
    private String sizeName;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;

    // Getters and setters
}
}
