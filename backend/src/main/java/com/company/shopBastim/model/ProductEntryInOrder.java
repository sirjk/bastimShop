package com.company.shopBastim.model;

import javax.persistence.*;

@Entity
@Table(name = "ProductEntryInOrder")
public class ProductEntryInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;
    private Integer quantity;

    public ProductEntryInOrder(Long id, Product product,  Integer quantity) {
        this.id = id;
        this.product = product;
        //this.productId = product.getId();
        this.quantity = quantity;
    }

    public ProductEntryInOrder(Product product,  Integer quantity) {

        this.product = product;
       // this.productId = product.getId();
        this.quantity = quantity;
    }

    public ProductEntryInOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
      //  this.productId = product.getId();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

   // public Long getProductId() {
   //     return productId;
    //}
}
