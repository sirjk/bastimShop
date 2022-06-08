package com.company.shopBastim.service;

import com.company.shopBastim.enums.UserState;
import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Permission;
import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.User;
import com.company.shopBastim.repository.RoleRepository;
import com.company.shopBastim.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRolesServiceTest {

    UserRolesService userRolesService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @BeforeEach
    void init() {

        userRolesService = new UserRolesService(userRepository, roleRepository);

    }


    @Test
    void givenValidUserID_whenGetUserRoles_thenSucceed() {
        assertNotNull(userRepository);

        Permission permission1 = new Permission(1L, "PERMISSION_ONE");
        Permission permission2 = new Permission(2L, "PERMISSION_TWO");
        Permission permission3 = new Permission(3L, "PERMISSION_TREE");

        Set<Permission> permissionSet1 = new HashSet<>(Arrays.asList(permission1, permission2));
        Set<Permission> permissionSet2 = new HashSet<>(Arrays.asList(permission3));

        Role role1 = new Role(1L, "ROLE_ONE", permissionSet1);
        Role role2 = new Role(2L, "ROLE_TWO", permissionSet2);

        Set<Role> roleSet = new HashSet<>(Arrays.asList(role1, role2));

        User returnedUser = new User(1L, "Janusz", "Kowalski", "kowalski@gmail.com", LocalDate.of(2000, 10, 19), 1000, "$2a$10$8tEHH/2cCq72qLW.dJgjeepRQi9XW4BbEJgItzn71uQmwXaY./Qtu", "Polska", "Krakow", "Dluga 10", "32-002", roleSet, UserState.active);
        when(userRepository.findById(1L)).thenReturn(Optional.of(returnedUser));

        try{
            Set<Role> expectedSet = (Set<Role>)returnedUser.getRoles();
            assertEquals(expectedSet, userRolesService.getUserRoles(1L));
        }
        catch(Exception exception){
            Assert.fail();
        }

    }

    @Test
    void givenNonExistingUserID_whenGetUserRoles_thenThrowException() {

        assertNotNull(userRepository);

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DoesNotExistException.class, () -> {
            userRolesService.getUserRoles(2L);
        });

    }
}