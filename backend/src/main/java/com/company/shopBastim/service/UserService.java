package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Product;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import com.company.shopBastim.specifications.ProductSpecifications;
import com.company.shopBastim.specifications.UserSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepositoryArg, RoleRepository roleRepositoryArg, PasswordEncoder passwordEncoder){
        userRepository = userRepositoryArg;
        roleRepository = roleRepositoryArg;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Email not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.get().getRoles().forEach(role->{
            role.getPermissions().forEach(rolePermission -> {
                    authorities.add(new SimpleGrantedAuthority(rolePermission.getName()));
            });
        });
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    public Page<User> getUsers(Map<String, String> params) {

        PrepareQueryForDatabase PQFD =  new PrepareQueryForDatabase();
        Pageable pagingType = PQFD.setupPegableFromParams(params);


        // ========================================== FILTERS SETUP ==========================================
        Specification<User> spec = null;

        if(params.get("search-phrase") != null){

            spec = UserSpecifications.likeFirstName(params.get("search-phrase"))
                    .or(UserSpecifications.likeLastName(params.get("search-phrase")))
                    .or(UserSpecifications.likeEmail(params.get("search-phrase")));
        }
        if(params.get("from-birth-date") != null){
            if(spec == null){
                spec = UserSpecifications.geBirthDate(LocalDate.parse(params.get("from-birth-date")));
            }
            else{
                spec = spec.and(UserSpecifications.geBirthDate(LocalDate.parse(params.get("from-birth-date"))));
            }

        }
        if(params.get("to-birth-date") != null){
            if(spec == null){
                spec = UserSpecifications.leBirthDate(LocalDate.parse(params.get("to-birth-date")));
            }
            else{
                spec = spec.and(UserSpecifications.leBirthDate(LocalDate.parse(params.get("to-birth-date"))));
            }

        }
        if(params.get("min-points") != null){
            if(spec == null){
                spec = UserSpecifications.lePoints(Integer.parseInt(params.get("min-points")));
            }
            else{
                spec = spec.and(UserSpecifications.lePoints(Integer.parseInt(params.get("min-points"))));
            }
        }
        if(params.get("max-points") != null){
            if(spec == null){
                spec = UserSpecifications.gePoints(Integer.parseInt(params.get("max-points")));
            }
            else{
                spec = spec.and(UserSpecifications.gePoints(Integer.parseInt(params.get("max-points"))));
            }
        }
        if(params.get("password") != null){
            if(spec == null){
                spec = UserSpecifications.likePassword(params.get("password"));
            }
            else{
                spec = spec.and(UserSpecifications.likePassword(params.get("password")));
            }
        }
        if(params.get("country") != null){
            if(spec == null){
                spec = UserSpecifications.likeCountry(params.get("country"));
            }
            else{
                spec = spec.and(UserSpecifications.likeCountry(params.get("country")));
            }
        }
        if(params.get("city") != null){
            if(spec == null){
                spec = UserSpecifications.likeCity(params.get("city"));
            }
            else{
                spec = spec.and(UserSpecifications.likeCity(params.get("city")));
            }
        }
        if(params.get("address") != null){
            if(spec == null){
                spec = UserSpecifications.likeAddress(params.get("address"));
            }
            else{
                spec = spec.and(UserSpecifications.likeAddress(params.get("address")));
            }
        }
        if(params.get("postalAddress") != null){
            if(spec == null){
                spec = UserSpecifications.likePostalAddress(params.get("postalAddress"));
            }
            else{
                spec = spec.and(UserSpecifications.likePostalAddress(params.get("postalAddress")));
            }
        }
// ========================================== END OF FILTERS SETUP ==========================================


        return userRepository.findAll(spec, pagingType);
    }




    public User getUserById(Long id, Principal principal) throws DoesNotExistException, NoPermissionException {
        Optional<User> output = userRepository.findById(id);
        if(output.isEmpty()){
            throw new DoesNotExistException("User");
        }
        Optional<User> querer = userRepository.findUserByEmail(principal.getName()); // name w principalu to jest email

        AtomicReference<Integer> flag = new AtomicReference<>(0);
        querer.get().getRoles().forEach( role -> {
            role.getPermissions().forEach( permission -> {
               if(permission.getName().equals("READ_USER")){
                   flag.set(1);
               }
            });
        });

        if(flag.get().equals(1) || querer.get().getEmail().equals(output.get().getEmail())){
            return output.get();
        }
        else{
         throw new NoPermissionException();
        }
    }

    public User getUserByEmail(String email) throws DoesNotExistException {
        Optional<User> output = userRepository.findUserByEmail(email);
        if(output.isEmpty()){
            throw new DoesNotExistException("User");
        }
        return output.get();
    }

    public ResponseEntity<String> postUsers(List<User> users) {

        String response = "";

        Map<String, User> temp = new HashMap<>();
        for(User user : users){
            if(!temp.containsKey(user.getEmail())){
                temp.put(user.getEmail(),user);
            }
            else{
                response += "Entry with duplicate email deleted on: " + user.getEmail() +"\n";
            }
        }
        users = new ArrayList<>();

        for(User user : temp.values()){
            users.add(user);
        }

        List<User> toBeSaved = new ArrayList<User>();
        for(User user : users){
            Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
            if(userOptional.isPresent()){
                response += "Email taken on: " + user.getEmail() +  "\n";

            }
            else{
                if(user.getEmail()==null){
                    response+="Email cannot be null\n";
                }
                else{
                    response += "Ok on: " + user.getEmail() + "\n";
                    user.setPassword(passwordEncoder.encode(user.getPassword())); //szyfrowanie hasła
                    toBeSaved.add(user);
                }
            }
        }

        if (!response.contains("Ok")){
            return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            userRepository.saveAll(toBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<String> deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. User with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> putUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        user.setId(id);
        if(userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())){
            return new ResponseEntity<String>("Email taken.", HttpStatus.NOT_ACCEPTABLE);
        }

        if(user.getEmail()==null) {
            return new ResponseEntity<String>("cannot put - Email cannot be null.", HttpStatus.OK);
        }
        else{
            userRepository.save(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return new ResponseEntity<String>("Put.", HttpStatus.OK);
        }
    }


}
