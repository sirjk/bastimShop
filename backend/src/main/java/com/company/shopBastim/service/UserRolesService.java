package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRolesService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserRolesService(UserRepository userRepositoryArg, RoleRepository roleRepositoryArg){
        userRepository = userRepositoryArg;
        roleRepository = roleRepositoryArg;

    }

    public Set<Role> getUserRoles(Long userId) throws DoesNotExistException{
        Optional<User> specificUserRoles = userRepository.findById(userId);
        if(specificUserRoles.isEmpty()){
            throw new DoesNotExistException("User Role");
        }
        return (Set<Role>) specificUserRoles.get().getRoles();
    }


    public ResponseEntity<String> postUserRoles(Long userId, List<Role> roles) {

        String response = "";

        if(userRepository.findById(userId).isEmpty()){
            response += "User: " + userId +" does not exist\n";
            return new ResponseEntity<String>(response, HttpStatus.OK);
        }


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


        Set<Role> toBeSaved = new HashSet<>();
        for(Role role : roles){
            if(roleRepository.findRoleByName(role.getRoleName()).isEmpty()){
                response += "Role with provided name: " + role.getRoleName() +  " does not exist\n";
                continue;
            }

            role = roleRepository.findRoleByName(role.getRoleName()).get();


            Boolean userRoleAlreadyThere = userRepository.getById(userId).getRoles().stream().map(v -> v.getId()).collect(Collectors.toSet()).contains(role.getId());


            if(userRoleAlreadyThere){
                response += "Role exists on: " + role.getRoleName() +  "\n";
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
            User userToBeSaved = userRepository.getById(userId);
            userToBeSaved.addRoles(toBeSaved);
            userRepository.save(userToBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }

    }

    public ResponseEntity<String> deleteUserRole(Long userId, Long roleId) {
        if(userRepository.findById(userId).isEmpty()){
            return new ResponseEntity<String>("User: " + userId +" does not exist\n", HttpStatus.OK);
        }

        Boolean userRoleExists = userRepository.getById(userId).getRoles().stream().map(v -> v.getId()).collect(Collectors.toSet()).contains(roleId);
        //Boolean userRoleExists = userRepository.getById(userId).getRoles().contains(roleRepository.getById(roleId));
        if(userRoleExists){
            User userRoleToBeDeleted = userRepository.getById(userId);
            userRoleToBeDeleted.deleteRole(roleRepository.getById(roleId));
            userRepository.save(userRoleToBeDeleted);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. Role with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
