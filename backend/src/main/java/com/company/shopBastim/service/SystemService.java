package com.company.shopBastim.service;

import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
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
        if(user.getFirstName() == null || Objects.equals(user.getFirstName(), "")){
            return  "Fail: Pole Imię nie może być puste.";
        }
        if(user.getLastName() == null || Objects.equals(user.getLastName(), "")){
            return  "Fail: Pole Nazwisko nie może być puste.";
        }
        if(userOptional.isPresent()){
            return "Fail: Podany adres e-mail jest już zajęty.";
        }
        if(user.getEmail()==null|| Objects.equals(user.getEmail(), "")) {
            return "Fail: Pole E-mail nie może być puste.";
        }
        if(user.getPassword()==null || user.getPassword().length() < 7 || user.getPassword().length() > 50){
            return  "Fail: Hasło musi mieć minimum 8 znaków i maksymalnie 50 znaków.";
        }
        if(user.getBirthDate() == null){
            return  "Fail: Pole Data urodzenia nie może być puste";
        }
        if(user.getCountry() == null || Objects.equals(user.getCountry(), "")){
            return  "Fail: Pole Kraj nie może być puste.";
        }
        if(user.getCity() == null || Objects.equals(user.getCity(), "")){
            return  "Fail: Pole Miasto nie może być puste.";
        }
        if(user.getAddress() == null || Objects.equals(user.getAddress(), "")){
            return  "Fail: Pole Adres nie może być puste";
        }
        if(user.getPostalAddress() == null || Objects.equals(user.getPostalAddress(), "")){
            return  "Fail: Pole Kod pocztowy nie może być puste.";
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
