package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Permission;
import com.company.shopBastim.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionServiceArg){
        permissionService = permissionServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<Permission>> getAllRolePermissions(@RequestParam Map<String, String> params) {
        return new ResponseEntity<Page<Permission>>(permissionService.getRolePermissions(params),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRolePermissionById(@PathVariable Long id) {
        try{
            return  new ResponseEntity<Permission>(permissionService.getRolePermissionById(id), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("RolePermission with provided id does not exist", HttpStatus.OK);
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> postRolePermission(@RequestBody List<Permission> permissions){
        if(permissions.size() >= 1){
            return permissionService.postRolePermissions(permissions);
        }
        else{
            return new ResponseEntity<String>("Not enough rolePermissions.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRolePermission(@PathVariable Long id){ return permissionService.deleteRolePermission(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putRolePermission(@PathVariable Long id, @RequestBody Permission permission){ return permissionService.putRolePermission(id, permission);}
}
