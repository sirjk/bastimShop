package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleServiceArg){
        this.roleService = roleServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAllRoles(@RequestParam Map<String, String> params) {
        return new ResponseEntity<Page<Role>>(roleService.getRoles(params),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try{
            return  new ResponseEntity<Role>(roleService.getRoleById(id), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("Role with provided id does not exist", HttpStatus.OK);
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping
    public ResponseEntity<String> postRole(@RequestBody List<Role> roles){
        if(roles.size() >= 1){
            return roleService.postRoles(roles);
        }
        else{
            return new ResponseEntity<String>("Not enough customers.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id){ return roleService.deleteRole(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putRole(@PathVariable Long id, @RequestBody Role role){ return roleService.putRole(id, role);}
}
