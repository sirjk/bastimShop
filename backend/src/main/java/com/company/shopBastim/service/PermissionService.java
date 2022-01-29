package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.exceptions.EmailTakenException;
import com.company.shopBastim.model.Permission;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.repository.PermissionRepository;
import com.company.shopBastim.specifications.PermissionSpecifications;
import com.company.shopBastim.specifications.RoleSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService {
    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepositoryArg){
        permissionRepository = permissionRepositoryArg;
    }

    public Page<Permission> getRolePermissions(Map<String, String> params) {
        PrepareQueryForDatabase PQFD =  new PrepareQueryForDatabase();
        Pageable pagingType = PQFD.setupPegableFromParams(params);


        // ========================================== FILTERS SETUP ==========================================
        Specification<Permission> spec = null;

        if(params.get("search-phrase") != null){

            spec = PermissionSpecifications.likePermissionName(params.get("search-phrase"));
        }

        // ========================================== END OF FILTERS SETUP ==========================================

        return permissionRepository.findAll(spec, pagingType);
    }

    public Permission getRolePermissionById(Long id) throws DoesNotExistException {
        Optional<Permission> output = permissionRepository.findById(id);
        if(output.isEmpty()){
                throw new DoesNotExistException("RolePermission");

        }
        return output.get();
    }

    public ResponseEntity<String> postRolePermissions(List<Permission> permissions) {

        String response = "";


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


        List<Permission> toBeSaved = new ArrayList<Permission>();
        for(Permission permission : permissions){
            Optional<Permission> rolePermissionOptional = permissionRepository.findRolePermissionByName(permission.getName());
            if(rolePermissionOptional.isPresent()){
                    response += "Permission exists on: " + permission.getName() +  "\n";
            }
            else if(permission.getName() == null){
                response += "Name cannot be null.\n";

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
            permissionRepository.saveAll(toBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<String> deleteRolePermission(Long id) {
        Optional<Permission> rolePermissionOptional = permissionRepository.findById(id);
        if(rolePermissionOptional.isPresent()){
            permissionRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. RolePermission with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> putRolePermission(Long id, Permission permission) {
        Optional<Permission> rolePermissionOptional = permissionRepository.findRolePermissionByName(permission.getName());
        permission.setId(id);
        if(rolePermissionOptional.isPresent() && !rolePermissionOptional.get().getId().equals(permission.getId())){
            try {
                throw new EmailTakenException(permission.getId());
            }
            catch (Exception exception){
                return new ResponseEntity<String>("Permission exists.", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        if(permission.getName()==null){
            return new ResponseEntity<String>("Cannot put - permission name cannot be null", HttpStatus.NOT_ACCEPTABLE);
        }

        permissionRepository.save(permission);
        return new ResponseEntity<String>("Put.", HttpStatus.OK);
    }
}
