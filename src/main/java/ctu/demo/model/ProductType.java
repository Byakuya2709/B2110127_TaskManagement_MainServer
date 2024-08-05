/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name="product_type")
//@JsonIgnoreProperties({"products"})
public class ProductType implements Serializable {
    @Id
    private String type_id;

    @Column(nullable= false, unique=true)
    private String type_name;

   @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @JsonIgnore
   private Set<Product> products;
    
    public ProductType(String type_id, String type_name) {
        this.type_id = type_id;
        this.type_name = type_name; 
    }

    public ProductType() {
    }

    
    
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return Objects.equals(type_id, that.type_id) &&
               Objects.equals(type_name, that.type_name);
    }
}
