package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Product;
import com.company.shopBastim.model.User;
import com.company.shopBastim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.*;

import static java.util.Arrays.stream;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userServiceArg){
        userService = userServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam Map<String, String> params) {
        return new ResponseEntity<Page<User>>(userService.getUsers(params),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, Principal principal) {
        try{
            return  new ResponseEntity<User>(userService.getUserById(id, principal), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("User with provided id does not exist", HttpStatus.OK);
            else if(exception.getClass() == NoPermissionException.class){
                return new ResponseEntity<String>("You do not have required permission to access this resource", HttpStatus.FORBIDDEN);
            }
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> postUser(@RequestBody List<User> users){

        if(users.size() >= 1){
            return userService.postUsers(users);
        }
        else{
            return new ResponseEntity<String>("Not enough users.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){ return userService.deleteUser(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putUser(@PathVariable Long id, @RequestBody User user){ return userService.putUser(id, user);}

}

