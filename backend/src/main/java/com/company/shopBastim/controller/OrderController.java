package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Order;
import com.company.shopBastim.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderServiceArg){
        orderService = orderServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAllOrders(@RequestParam Map<String, String> params, Principal principal) throws NoPermissionException {
        return new ResponseEntity<Page<Order>>(orderService.getOrders(params, principal),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id, Principal principal) {
        try{
            return  new ResponseEntity<Order>(orderService.getOrderById(id, principal), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("Order with provided id does not exist", HttpStatus.OK);
            else if(exception.getClass() == NoPermissionException.class)
                return  new ResponseEntity<String>("You do not have required permission to access this resource", HttpStatus.UNAUTHORIZED);
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping
    public ResponseEntity<String> postOrder(@RequestBody List<Order> orders, Principal principal){
        // if(orders.size() == 1){
        //     return orderService.postOrder(orders.get(0));
        // }
        if(orders.size() >= 1){
            try {
                return orderService.postOrders(orders, principal);
            }
            catch (Exception exception){
                if(exception.getClass() == NoPermissionException.class){
                    return new ResponseEntity<String>("You do not have required permissions.", HttpStatus.UNAUTHORIZED);
                }
                else{
                    return new ResponseEntity<String>("Shouldnt occur.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        }
        else{
            return new ResponseEntity<String>("Not enough orders.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){ return orderService.deleteOrder(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putOrder(@PathVariable Long id, @RequestBody Order order){ return orderService.putOrder(id, order);}
}
