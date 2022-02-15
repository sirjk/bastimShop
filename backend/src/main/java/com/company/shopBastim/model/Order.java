package com.company.shopBastim.model;

import com.company.shopBastim.enums.OrderState;
import com.company.shopBastim.enums.ShipmentState;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "product_id")
    private Map<Long, ProductEntryInOrder> productMap = new HashMap<>();

    private Long userId; //Id kupującego

    private Float totalCost;
    private LocalDate buyDate;
    private LocalDate shipmentDate;
    private ShipmentState shipmentState;
    private OrderState orderState;
    private Long trackingNumber;


    public Order(Long id, Long userId, Map<Long, ProductEntryInOrder> productMap, Float totalCost, LocalDate buyDate, LocalDate shipmentDate, ShipmentState shipmentState, OrderState orderState, Long trackingNumber) {
        this.id = id;
        this.userId = userId;
        this.productMap = productMap;
        this.totalCost = totalCost;
        this.buyDate = buyDate;
        this.shipmentDate = shipmentDate;
        this.shipmentState = shipmentState;
        this.orderState = orderState;
        this.trackingNumber = trackingNumber;
    }


    public Order(Long userId, Map<Long, ProductEntryInOrder> productMap, Float totalCost, LocalDate buyDate, LocalDate shipmentDate, ShipmentState shipmentState, OrderState orderState, Long trackingNumber) {
        this.userId = userId;
        this.productMap = productMap;
        this.totalCost = totalCost;
        this.buyDate = buyDate;
        this.shipmentDate = shipmentDate;
        this.shipmentState = shipmentState;
        this.orderState = orderState;
        this.trackingNumber=trackingNumber;
    }

    public Order() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }


    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, ProductEntryInOrder> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Long, ProductEntryInOrder> productMap) {
        this.productMap = productMap;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public ShipmentState getShipmentState() {
        return shipmentState;
    }

    public void setShipmentState(ShipmentState shipmentState) {
        this.shipmentState = shipmentState;
    }

    public Long getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(Long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }




}
