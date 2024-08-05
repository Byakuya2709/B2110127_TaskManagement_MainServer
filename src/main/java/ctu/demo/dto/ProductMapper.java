/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.dto;

import ctu.demo.dto.DTO.*;
import ctu.demo.model.Product;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ADMIN
 */
public class ProductMapper {
     public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        
        dto.setProductDescription(product.getProductDescription());

        ProductTypeDTO typeDTO = new ProductTypeDTO();
        typeDTO.setType_id(product.getProductType().getType_id());
        typeDTO.setType_name(product.getProductType().getType_name());
        
        dto.setProductType(typeDTO);

        List<SizeDTO> sizeDTOs = product.getProductSizes().stream()
                .map(size -> {
                    SizeDTO sizeDTO = new SizeDTO();
                    sizeDTO.setSizeId(size.getSizeId());
                    sizeDTO.setSizeName(size.getSizeName());
                    sizeDTO.setQuantity(size.getQuantity());
                    return sizeDTO;
                })
                .collect(Collectors.toList());
        dto.setSizes(sizeDTOs);

        List<ImageDTO> imageDTOs = product.getImages().stream()
                .map(image -> {
                    ImageDTO imgDTO = new ImageDTO();
                    imgDTO.setImageId(image.getImageId());
                    imgDTO.setFileName(image.getFileName());
                    imgDTO.setFilePath(image.getFilePath());
                    imgDTO.setBase64Image(image.getBase64Image());
                    return imgDTO;
                })
                .collect(Collectors.toList());
        dto.setImages(imageDTOs);

        return dto;
    }
}
