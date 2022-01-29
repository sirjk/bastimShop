package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Permission;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RolePermissionsService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RolePermissionsService(RoleRepository roleRepositoryArg, PermissionRepository permissionRepositoryArg){
        roleRepository = roleRepositoryArg;
        permissionRepository = permissionRepositoryArg;
    }

    public Set<Permission> getRolePermissions(Long roleId) throws DoesNotExistException {
        Optional<Role> specificRole = roleRepository.findById(roleId);
        if(specificRole.isEmpty()){
            throw new DoesNotExistException("Role Permission");
        }
        return (Set<Permission>)specificRole.get().getPermissions();
    }


    public ResponseEntity<String> postRolePermissions(Long roleId, List<Permission> permissions) {


        String response = "";
        if(roleRepository.findById(roleId).isEmpty()){
            response += "Role: " + roleId + " does not exit\n";
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }

        Map<String, Permission> temp = new HashMap<>();
        for(Permission permission : permissions){
            if(!temp.containsKey(permission.getName())){
                temp.put(permission.getName(), permission);
            }
            else{
                response += "Entry with duplicate name deleted on: " + permission.getName() +"\n";
            }
        }
        permissions = new ArrayList<>();

        for(Permission permission : temp.values()){
            permissions.add(permission);
        }

        //permissions = permissions.stream().map(v -> permissionRepository.findRolePermissionByName(v.getName()).get()).collect(Collectors.toList());

        Set<Permission> toBeSaved = new HashSet<>();
        for(Permission permission : permissions){
            if(permissionRepository.findRolePermissionByName(permission.getName()).isEmpty()){
                response += "Permission with provided name: " + permission.getName() +  " does not exist\n";
                continue;
            }

            permission = permissionRepository.findRolePermissionByName(permission.getName()).get();

            Boolean rolePermissionAlreadyThere = roleRepository.getById(roleId).getPermissions().stream().map(v -> v.getId()).collect(Collectors.toSet()).contains(permission.getId());
            if(rolePermissionAlreadyThere){
                response += "Permission exists on: " + permission.getName() +  "\n";
            }
            else{
                response += "Ok on: " + permission.getName() + "\n";
                toBeSaved.add(permission);
            }
        }

        if (!response.contains("Ok")){
            return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            Role roleToBeSaved = roleRepository.getById(roleId);
            roleToBeSaved.addPermissions(toBeSaved);
            roleRepository.save(roleToBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<String> deleteRolePermission(Long roleId, Long permissionId) {
        if(roleRepository.findById(roleId).isEmpty()){
            return new ResponseEntity<String>("Role: " + roleId + " does not exit\n", HttpStatus.CREATED);
        }
        Boolean rolePermissionExist = roleRepository.getById(roleId).getPermissions().stream().map(v -> v.getId()).collect(Collectors.toSet()).contains(permissionId);
        //Boolean rolePermissionExist = roleRepository.getById(roleId).getPermissions().contains(permissionRepository.getById(permissionId));
        if(rolePermissionExist){
            Role roleToBeSaved = roleRepository.getById(roleId);
            roleToBeSaved.deletePermission(permissionRepository.getById(permissionId));
            roleRepository.save(roleToBeSaved);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. RolePermission with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
