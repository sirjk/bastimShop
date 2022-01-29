package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.specifications.RoleSpecifications;
import com.company.shopBastim.specifications.UserSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepositoryArg){
        roleRepository = roleRepositoryArg;
    }

    public Page<Role> getRoles(Map<String, String> params) {

        PrepareQueryForDatabase PQFD =  new PrepareQueryForDatabase();
        Pageable pagingType = PQFD.setupPegableFromParams(params);


        // ========================================== FILTERS SETUP ==========================================
        Specification<Role> spec = null;

        if(params.get("search-phrase") != null){

            spec = RoleSpecifications.likeName(params.get("search-phrase"));
        }


// ========================================== END OF FILTERS SETUP ==========================================

        return roleRepository.findAll(spec, pagingType);
    }

    public Role getRoleById(Long id) throws DoesNotExistException {
        Optional<Role> output = roleRepository.findById(id);
        if(output.isEmpty()){
                throw new DoesNotExistException("Role");
        }
        return output.get();
    }

    public ResponseEntity<String> postRoles(List<Role> roles) {
        String response = "";

        Map<String, Role> temp = new HashMap<>();
        for(Role role : roles){
            if(!temp.containsKey(role.getRoleName())){
                temp.put(role.getRoleName(),role);
            }
            else{
                response += "Entry with duplicate name deleted on: " + role.getRoleName() +"\n";
            }
        }
        roles = new ArrayList<>();

        for(Role role : temp.values()){
           roles.add(role);
        }

        List<Role> toBeSaved = new ArrayList<Role>();
        for(Role role : roles){
            Optional<Role> roleOptional = roleRepository.findRoleByName(role.getRoleName());
            if(roleOptional.isPresent()){
                response += "Name taken on: " + role.getRoleName() +  "\n";

            }
            else if(role.getRoleName() == null){
                response += "Name cannot be null. \n";

            }
            else{
                response += "Ok on: " + role.getRoleName() + "\n";
                toBeSaved.add(role);
            }
        }

        if (!response.contains("Ok")){
            return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            roleRepository.saveAll(toBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
    }


    public ResponseEntity<String> deleteRole(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isPresent()){
            roleRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. Role with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> putRole(Long id, Role role) {
        Optional<Role> roleOptional = roleRepository.findRoleByName(role.getRoleName());
        role.setId(id);
        if(roleOptional.isPresent() && !roleOptional.get().getId().equals(role.getId())){
                return new ResponseEntity<String>("Name taken.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(role.getRoleName() ==null){
            return new ResponseEntity<String>("Cannot put - role name cannot be null", HttpStatus.NOT_ACCEPTABLE);
        }
        roleRepository.save(role);
        return new ResponseEntity<String>("Put.", HttpStatus.OK);
    }
}
