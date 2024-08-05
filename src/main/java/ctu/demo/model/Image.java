/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.model;

/**
 *
 * @author ADMIN
 */


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
@Entity
@Table(name = "image")
//@JsonIgnoreProperties({"productimage"})
public class Image implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;
    
    @Column
    @Lob
    private byte[] data;
//    @Transient là một annotation trong JPA (Java Persistence API) được sử dụng để đánh dấu một thuộc tính trong lớp entity không nên được lưu trữ trong cơ sở dữ liệu.
    @Transient
    private String base64Image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product productimage;

    public Image() {}

    public Image(String fileName, String filePath, Product product) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.productimage = product;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    
    
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Product getProductimage() {
        return productimage;
    }

    public void setProductimage(Product productimage) {
        this.productimage = productimage;
    }

    
    
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

    public Product getProduct() {
        return productimage;
    }

    public void setProduct(Product product) {
        this.productimage = product;
    }
}