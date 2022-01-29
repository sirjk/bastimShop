package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/users/{userId}/roles")
public class UserRolesController {

    private final UserRolesService userRolesService;

    @Autowired
    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUserRoles(@PathVariable Long userId) {
        try {
            return new ResponseEntity<Set<Role>>(userRolesService.getUserRoles(userId), HttpStatus.OK);
        }
        catch (Exception exception){
            if(exception.getClass() == DoesNotExistException.class){
                return new ResponseEntity<String>("User does not exist", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Shouldn't occur", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }



    @PostMapping
    public ResponseEntity<String> postUserRoles(@PathVariable Long userId, @RequestBody List<Role> roles){
        if(roles.size() >= 1){
            return userRolesService.postUserRoles(userId, roles);
        }
        else{
            return new ResponseEntity<String>("Not enough roles.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteUserRoles(@PathVariable Long userId, @PathVariable Long roleId){ return userRolesService.deleteUserRole(userId, roleId);}

}
