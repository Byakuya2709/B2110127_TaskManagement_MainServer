package ctu.demo.dto;

import java.util.List;

/**
 * Data Transfer Objects for the application.
 */
public class DTO {

    public static class ProductDTO {
        private Long productId;
        private String productName;
        private int productPrice;
        private String productDescription;
        private ProductTypeDTO productType;
        private List<SizeDTO> sizes;
        private List<ImageDTO> images;

        // Getters and Setters
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public ProductTypeDTO getProductType() {
            return productType;
        }

        public void setProductType(ProductTypeDTO productType) {
            this.productType = productType;
        }

        public List<SizeDTO> getSizes() {
            return sizes;
        }

        public void setSizes(List<SizeDTO> sizes) {
            this.sizes = sizes;
        }

        public List<ImageDTO> getImages() {
            return images;
        }

        public void setImages(List<ImageDTO> images) {
            this.images = images;
        }
    }

    public static class ProductTypeDTO {
        private String type_id;
        private String type_name;

        // Getters and Setters
        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }

    public static class ImageDTO {
        private Long imageId;
        private String fileName;
        private String filePath;
        private String base64Image;

        // Getters and Setters
        public Long getImageId() {
            return imageId;
        }

        public void setImageId(Long imageId) {
            this.imageId = imageId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getBase64Image() {
            return base64Image;
        }

        public void setBase64Image(String base64Image) {
            this.base64Image = base64Image;
        }
    }

    public static class SizeDTO {
        private Long sizeId;
        private String sizeName;
        private int quantity;

        // Getters and Setters
        public Long getSizeId() {
            return sizeId;
        }

        public void setSizeId(Long sizeId) {
            this.sizeId = sizeId;
        }

        public String getSizeName() {
            return sizeName;
        }

        public void setSizeName(String sizeName) {
            this.sizeName = sizeName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
