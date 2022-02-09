package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.exceptions.RefreshTokenMissingException;
import com.company.shopBastim.exceptions.RefreshTokenTTLBelowOneException;
import com.company.shopBastim.filter.BlackList;
import com.company.shopBastim.model.Product;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import com.company.shopBastim.specifications.ProductSpecifications;
import com.company.shopBastim.specifications.UserSpecifications;
import com.company.shopBastim.utility.Initializer;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import com.company.shopBastim.utility.RefreshTokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    //BlackList blackList = BlackList.getInstance();
    //private RefreshTokenUtility refreshTokenUtility;
    private Initializer initializer;


    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepositoryArg, RoleRepository roleRepositoryArg, PasswordEncoder passwordEncoderArg, Initializer initializerArg){
        userRepository = userRepositoryArg;
        roleRepository = roleRepositoryArg;
        passwordEncoder = passwordEncoderArg;
        initializer = initializerArg;
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


        Optional<User> querer = userRepository.findUserByEmail(principal.getName()); // name w principalu to jest email
        if(querer.isEmpty()){                   //W przypadku zmiany danych ~
            throw new NoPermissionException();
        }
        AtomicReference<Integer> flag = new AtomicReference<>(0);
        querer.get().getRoles().forEach( role -> {
            role.getPermissions().forEach( permission -> {
               if(permission.getName().equals("READ_USER")){
                   flag.set(1);
               }
            });
        });

        if(output.isEmpty() && !flag.get().equals(1)){
            throw new NoPermissionException();          //aby nie dawać uzytkownikowi bez permisji informacji o ilosci uzytkownikow
        }
        else if(output.isEmpty()){
            throw new DoesNotExistException("User");
        }

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

    public String putUser(Long id, User user, Principal principal, String password,HttpServletRequest request, HttpServletResponse response) throws DoesNotExistException, NoPermissionException  {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        user.setId(id);



        Optional<User> userToBeChanged = userRepository.findById(id);

        Optional<User> querer = userRepository.findUserByEmail(principal.getName()); // name w principalu to jest email
        if(querer.isEmpty())
            throw new NoPermissionException();

        AtomicReference<Integer> flag = new AtomicReference<>(0);
        querer.get().getRoles().forEach( role -> {
            role.getPermissions().forEach( permission -> {
                if(permission.getName().equals("READ_USER")){
                    flag.set(1);
                }
            });
        });



        if(flag.get().equals(1)){
            if(userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())){
                return ("Email taken.");
            }


            if(user.getEmail()==null) {
                return ("cannot put - Email cannot be null.");
            }
            else{
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return("Put.");
            }
        }
        else if(querer.get().getEmail().equals(userToBeChanged.get().getEmail()) && user.getPassword() == null){


            if(!passwordEncoder.matches(password,userToBeChanged.get().getPassword())){
                return ("Fail: Wrong password.");
            }

            if(userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())){
                return ("Fail: Email taken.");
            }
            if(user.getEmail()==null) {
                return "Fail: Email cannot be null.";
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
            else{

                user.setPoints(querer.get().getPoints());
                user.setRoles((Set<Role>)querer.get().getRoles());
                user.setPassword(querer.get().getPassword());



                if(!querer.get().getEmail().equals(user.getEmail())){
                    userRepository.save(user);
                    try{
                        RefreshTokenUtility refreshTokenUtility = initializer.returnRefreshTokenUtility(this);
                        refreshTokenUtility.refreshToken(request, response, user.getEmail());
                    }
                    catch (Exception exception){
                        if(exception.getClass() == RefreshTokenMissingException.class){
                            System.out.println("Refresh Token missing - because of that token wanst refreshed on email change");
                        }
                        else if(exception.getClass() == RefreshTokenTTLBelowOneException.class){
                            System.out.println("TTL of refresh token below 1 - because of that token wanst refreshed on email change");
                        }
                    }
                }
                else{
                    userRepository.save(user);
                }




                return("Success: Data changed.");
            }
        }
        else if(querer.get().getEmail().equals(userToBeChanged.get().getEmail()) && !(user.getPassword() == null)){
            if(!passwordEncoder.matches(password,userToBeChanged.get().getPassword())){
                return ("Fail: Wrong password.");
            }
            if(user.getPassword()==null || user.getPassword().length() < 7 || user.getPassword().length() > 50){
                return  "Fail: Wrong new password.";
            }
            else{

                String newPassword = passwordEncoder.encode(user.getPassword());
                user = querer.get();
                user.setPassword(newPassword);
                userRepository.save(user);

                return("Success: Password changed.");
            }
        }
        else{
            throw new NoPermissionException();
        }



    }


}
