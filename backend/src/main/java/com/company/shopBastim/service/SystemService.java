package com.company.shopBastim.service;

import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SystemService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SystemService(UserRepository userRepositoryArg, RoleRepository roleRepositoryArg, PasswordEncoder passwordEncoder){
        userRepository = userRepositoryArg;
        roleRepository = roleRepositoryArg;
        this.passwordEncoder = passwordEncoder;
    }


    public String registerUser(User user){
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()){
            return "Fail: Email taken.";
        }
        if(user.getEmail()==null) {
            return "Fail: Email cannot be null.";
        }
        if(user.getPassword()==null || user.getPassword().length() < 7 || user.getPassword().length() > 50){
            return  "Fail: Wrong password.";
        }
        if(user.getAddress() == null){
            return  "Fail: Address cannot be null";
        }
        if(user.getBirthDate() == null){
            return  "Fail: Birth date cannot be null";
        }
        if(user.getCity() == null){
            return  "Fail: City cannot be null";
        }
        if(user.getCountry() == null){
            return  "Fail: Country cannot be null";
        }
        if(user.getFirstName() == null){
            return  "Fail: First name cannot be null";
        }
        if(user.getLastName() == null){
            return  "Fail: Last name cannot be null";
        }
        if(user.getPostalAddress() == null){
            return  "Fail: Postal address name cannot be null";
        }

        user.setId(null);


        Set<Role> roles= new HashSet<>();
        Role userRole = roleRepository.findById(3L).get(); // Fetching customer role
        roles.add(userRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setPoints(0);

        userRepository.save(user);
        return "Success: Registration process successful";
    }
}
