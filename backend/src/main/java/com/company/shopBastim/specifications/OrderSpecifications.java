package com.company.shopBastim.specifications;

import com.company.shopBastim.enums.ShipmentState;
import com.company.shopBastim.enums.OrderState;
import com.company.shopBastim.model.Order;
import com.company.shopBastim.model.Order_;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class OrderSpecifications {
    public static Specification<Order> eqUserIds(List<Long> userIds){
        if(userIds.isEmpty()){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return root.get(Order_.USER_ID).in(userIds);
        });
    }

    public static Specification<Order> geTotalCost(Float totalCost){
        if(totalCost == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.ge(root.get(Order_.TOTAL_COST), totalCost);
        });
    }
    public static Specification<Order> leTotalCost(Float totalCost){
        if(totalCost == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.le(root.get(Order_.TOTAL_COST), totalCost);
        });
    }

    public static Specification<Order> geShipmentDate(LocalDate shipmentDateProvided){
        if(shipmentDateProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.SHIPMENT_DATE), shipmentDateProvided);
        });
    }
    public static Specification<Order> leShipmentDate(LocalDate shipmentDateProvided){
        if(shipmentDateProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(Order_.SHIPMENT_DATE), shipmentDateProvided);
        });
    }

    public static Specification<Order> geBuyDate(LocalDate buyDateProvided){
        if(buyDateProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.BUY_DATE), buyDateProvided);
        });
    }
    public static Specification<Order> leBuyDate(LocalDate buyDateProvided) {
        if (buyDateProvided == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(Order_.BUY_DATE), buyDateProvided);
        });
    }

    public static Specification<Order> equalShipmentState(List<ShipmentState> shipmentStatesProvided) {
        if (shipmentStatesProvided.isEmpty()) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return root.get(Order_.SHIPMENT_STATE).in(shipmentStatesProvided);
        });
    }

        public static Specification<Order> equalOrderState(List<OrderState> orderStatesProvided) {
            if (orderStatesProvided.isEmpty()) {
                return null;
            }
            return ((root, query, criteriaBuilder) -> {
                return root.get(Order_.ORDER_STATE).in(orderStatesProvided);
            });
    }




}
