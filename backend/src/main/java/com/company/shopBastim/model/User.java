package com.company.shopBastim.model;

import com.company.shopBastim.enums.UserState;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Integer points;
    private String password;
    private String country;
    private String city;
    private String address;
    private String postalAddress;
    @Enumerated(EnumType.STRING)
    private UserState state;



    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, LocalDate birthDate, Integer points, String password, String country, String city, String address, String postalAddress, Set<Role> roles,UserState state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.points = points;
        this.password = password;
        this.country = country;
        this.city = city;
        this.address = address;
        this.postalAddress = postalAddress;
        this.roles = roles;
        this.state=state;
    }

    public User(String firstName, String lastName, String email, LocalDate birthDate, Integer points, String password, String country, String city, String address, String postalAddress, Set<Role> roles,UserState state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.points = points;
        this.password = password;
        this.country = country;
        this.city = city;
        this.address = address;
        this.postalAddress = postalAddress;
        this.roles = roles;
        this.state=state;
    }


    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRoles(Set<Role> rolesToBeAdded){
        roles.addAll(rolesToBeAdded);
    }

    public void deleteRole(Role rolesToBeDeleted){
        roles.remove(rolesToBeDeleted);
    }
}
