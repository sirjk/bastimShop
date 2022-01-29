package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Permission;
import com.company.shopBastim.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/roles/{roleId}/permissions")
public class RolePermissionsController {
    private final RolePermissionsService rolePermissionsService;

    @Autowired
    public RolePermissionsController(RolePermissionsService rolePermissionsServiceArg){
        this.rolePermissionsService = rolePermissionsServiceArg;
    }

    @GetMapping
    public ResponseEntity<?> getAllRolePermissions(@PathVariable Long roleId) {
        try {
            return new ResponseEntity<Set<Permission>>(rolePermissionsService.getRolePermissions(roleId), HttpStatus.OK);
        }
        catch (Exception exception){
            if(exception.getClass() == DoesNotExistException.class){
                return new ResponseEntity<String>("The role does not exist", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Shloudnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    @PostMapping
    public ResponseEntity<String> postRolePermissions(@PathVariable Long roleId,@RequestBody List<Permission> permissions){
        if(permissions.size() >= 1){
            return rolePermissionsService.postRolePermissions(roleId, permissions);
        }
        else{
            return new ResponseEntity<String>("Not enough role permissions.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId, @PathVariable Long permissionId){ return rolePermissionsService.deleteRolePermission(roleId, permissionId);}

}
