package com.company.shopBastim.service;

import com.company.shopBastim.enums.OrderState;
import com.company.shopBastim.enums.ShipmentState;
import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.*;
import com.company.shopBastim.repository.OrderRepository;
import com.company.shopBastim.repository.ProductRepository;
import com.company.shopBastim.repository.UserRepository;
import com.company.shopBastim.specifications.OrderSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.naming.NoPermissionException;
import javax.persistence.EntityManager;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private PlatformTransactionManager transactionManager;
    private EntityManager entityManager;

    @Autowired
    public OrderService(OrderRepository orderRepositoryArg, ProductRepository productRepositoryArg, UserRepository userRepositoryArg, PlatformTransactionManager transactionManagerArg, EntityManager entityManagerArg){
        orderRepository = orderRepositoryArg;
        productRepository = productRepositoryArg;
        userRepository = userRepositoryArg;
        transactionManager = transactionManagerArg;
        entityManager = entityManagerArg;
    }

    public Page<Order> getOrders(Map<String, String> params) {

        PrepareQueryForDatabase PQFD = new PrepareQueryForDatabase();
        Pageable pageable = PQFD.setupPegableFromParams(params);


        // ========================================== FILTERS SETUP ==========================================
        Specification<Order> spec = null;

        if(params.get("user-ids") != null) {
            List<Long> idsProvided = new ArrayList<>();
            String[] temp = params.get("user-ids").split(",");
            for (String oneId : temp) {
                try {
                    idsProvided.add(Long.parseLong(oneId));
                } catch (Exception ignored) {

                }
            }
            if (!idsProvided.isEmpty()) {
                if (spec == null) {
                    spec = OrderSpecifications.eqUserIds(idsProvided);
                } else {
                    spec = spec.and(OrderSpecifications.eqUserIds(idsProvided));
                }
            }
        }

        if(params.get("shipment-states") != null) {
            List<ShipmentState> shipmentStatesProvided = new ArrayList<>();
            String[] temp = params.get("shipment-states").split(",");

            for (String oneId : temp) {
                try {
                    shipmentStatesProvided.add(ShipmentState.valueOf(oneId));
                } catch (Exception ignored) {

                }

            }
            if (!shipmentStatesProvided.isEmpty()) {
                if (spec == null) {
                    spec = OrderSpecifications.equalShipmentState(shipmentStatesProvided);
                } else {
                    spec = spec.and(OrderSpecifications.equalShipmentState(shipmentStatesProvided));
                }
            }
        }
        if(params.get("order-states") != null) {
            List<OrderState> orderStatesProvided = new ArrayList<>();
            String[] temp = params.get("order-states").split(",");

            for (String oneId : temp) {
                try {
                    orderStatesProvided.add(OrderState.valueOf(oneId));
                } catch (Exception ignored) {

                }

            }
            if (!orderStatesProvided.isEmpty()) {
                if (spec == null) {
                    spec = OrderSpecifications.equalOrderState(orderStatesProvided);
                } else {
                    spec = spec.and(OrderSpecifications.equalOrderState(orderStatesProvided));
                }
            }
        }

        if(params.get("min-total-cost") != null){
            if (spec == null) {
                spec = OrderSpecifications.geTotalCost(Float.parseFloat(params.get("min-total-cost")));
            } else {
                spec = spec.and(OrderSpecifications.geTotalCost(Float.parseFloat(params.get("min-total-cost"))));
            }
        }
        if(params.get("max-total-cost") != null){
            if (spec == null) {
                spec = OrderSpecifications.leTotalCost(Float.parseFloat(params.get("min-total-cost")));
            } else {
                spec = spec.and(OrderSpecifications.leTotalCost(Float.parseFloat(params.get("min-total-cost"))));
            }
        }
        if(params.get("from-shipment-date") != null){
            if (spec == null) {
                spec = OrderSpecifications.geShipmentDate( LocalDate.parse(params.get("from-shipment-date")));
            } else {
                spec = spec.and(OrderSpecifications.geShipmentDate( LocalDate.parse(params.get("from-shipment-date"))) );
            }
        }
        if(params.get("to-shipment-date") != null){
            if (spec == null) {
                spec = OrderSpecifications.leShipmentDate( LocalDate.parse(params.get("to-shipment-date")));
            } else {
                spec = spec.and(OrderSpecifications.leShipmentDate( LocalDate.parse(params.get("to-shipment-date"))));
            }
        }
        if(params.get("from-buy-date") != null){
            if (spec == null) {
                spec = OrderSpecifications.geBuyDate( LocalDate.parse(params.get("from-buy-date")));
            } else {
                spec = spec.and(OrderSpecifications.geBuyDate( LocalDate.parse(params.get("from-buy-date"))) );
            }
        }
        if(params.get("to-buy-date") != null){
            if (spec == null) {
                spec = OrderSpecifications.leBuyDate(LocalDate.parse(params.get("to-buy-date")));
            } else {
                spec = spec.and(OrderSpecifications.leBuyDate(LocalDate.parse(params.get("to-buy-date"))));
            }
        }

        // ========================================== END OF FILTERS SETUP ==========================================

        return orderRepository.findAll(spec, pageable);
    }

    public Order getOrderById(Long id, Principal principal) throws DoesNotExistException, NoPermissionException {
        Optional<Order> output = orderRepository.findById(id);
        if(output.isEmpty()){
            throw new DoesNotExistException("Order");
        }
        Optional<User> querer = userRepository.findUserByEmail(principal.getName()); // name w principalu to jest email

        AtomicReference<Integer> flag = new AtomicReference<>(0);
        querer.get().getRoles().forEach( role -> {
            role.getPermissions().forEach( permission -> {
                if(permission.getName().equals("READ_ORDER")){
                    flag.set(1);
                }
            });
        });

        boolean a =querer.get().getEmail().equals( userRepository.findById(output.get().getUserId()).get().getEmail() );

        if(flag.get().equals(1) || querer.get().getEmail().equals( userRepository.findById(output.get().getUserId()).get().getEmail() )){
            return output.get();
        }
        else{
            throw new NoPermissionException();
        }
    }

    public ResponseEntity<String> postOrders(List<Order> orders, Principal principal) throws NoPermissionException {
        String response = "";
        Optional<User> querer = userRepository.findUserByEmail(principal.getName()); // name w principalu to jest email

        AtomicReference<Integer> flag = new AtomicReference<>(0);
        AtomicReference<Integer> flagWriteOrderSelf = new AtomicReference<>(0);
        querer.get().getRoles().forEach( role -> {
            role.getPermissions().forEach( permission -> {
                if(permission.getName().equals("WRITE_ORDER")){
                    flag.set(1);
                }
                else if(permission.getName().equals("WRITE_ORDER_SELF")){
                    flagWriteOrderSelf.set(1);
                }
            });
        });
        List<Object> checkOrdersForProductsOutput = checkOrdersForProducts(orders);
        Set<Order> ordersToBeSaved = (Set<Order>) checkOrdersForProductsOutput.get(0);
        response += (String) checkOrdersForProductsOutput.get(1);
        if(flag.get().equals(1)){
            orderRepository.saveAll(ordersToBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
        else if(flagWriteOrderSelf.get().equals(1)){

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute(status -> {
                Set<Long> OrdersIds = new HashSet<>();
                for (Order order : ordersToBeSaved) {
                    order.setId(null);
                    order.setOrderState(OrderState.waitingForPayment);
                    order.setBuyDate(LocalDate.now());
                    order.setShipmentDate(null);
                    order.setShipmentState(ShipmentState.processing);
                    order.setUserId(querer.get().getId());

                    Float calculatedTotalCost = 0.0f;
                    Set<Product> updatedProducts = new HashSet<>();

                    for (ProductEntryInOrder productEntryInOrder : order.getProductMap().values()) {
                        calculatedTotalCost += productRepository.findById(productEntryInOrder.getProduct().getId()).get().getPrice() * (productEntryInOrder.getQuantity());
                        Product productWithNewQuantity = productRepository.findById(productEntryInOrder.getProduct().getId()).get();
                        productWithNewQuantity.setQuantityInStock(productWithNewQuantity.getQuantityInStock() - productEntryInOrder.getQuantity());
                        updatedProducts.add(productWithNewQuantity);
                    }

                    productRepository.saveAll(updatedProducts);

                    order.setTotalCost(calculatedTotalCost);

                    entityManager.persist(order);
                    OrdersIds.add(order.getId());
                }



                //orderRepository.saveAll(ordersToBeSaved);


                return OrdersIds;
            });


            return new ResponseEntity<String>(response, HttpStatus.CREATED);

        }
        else{
            throw new NoPermissionException();
        }


    }

    public ResponseEntity<String> deleteOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            orderRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. Order with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private List<Object> checkOrdersForProducts(List<Order> orders){
        String response = "";
        Set<Order> ordersToBeSaved= new HashSet<>();
        for(Order order: orders){
            Boolean saveFlag = false;

            //nie mozna dodac orderu jesli produkt nie isrnieje lub jesli nie ma go w magazynie
            for(Long key : order.getProductMap().keySet()) {
                int productIndex = productRepository.findAll().indexOf(productRepository.getById(key));
                if (!productRepository.findAll().contains(productRepository.getById(key))) {
                    response += "Cannot add order without existing products\n";
                } else if (productRepository.findAll().get(productIndex).getQuantityInStock() < order.getProductMap().get(key).getQuantity()) {
                    response += "Cannot add order because there is not enough product '" +productRepository.findAll().get(productIndex).getName() +"' in the stock\n";
                }
                else{
                    response += "Ok.\n";
                    saveFlag = true;
                }
            }

            if(saveFlag){
                ordersToBeSaved.add(order);
            }

        }
        List<Object> output = new ArrayList<>();
        output.add(ordersToBeSaved);
        output.add(response);
        return output;
    }

    public ResponseEntity<String> putOrder(Long id, Order order) {
        order.setId(id);
        orderRepository.save(order);
        return new ResponseEntity<String>("Put.", HttpStatus.OK);
    }
}