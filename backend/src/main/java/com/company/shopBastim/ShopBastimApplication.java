package com.company.shopBastim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShopBastimApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBastimApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//@Bean
	//CommandLineRunner run(UserService userService, RoleService roleService, RolePermissionService rolePermissionService){
	//	List<RolePermission> rolePermissionAdminList = new ArrayList<>();
	//	rolePermissionAdminList.add(new RolePermission(1L, "USER_WRITE"));
	//	rolePermissionAdminList.add(new RolePermission(2L,"USER_READ"));
//
	//	List<RolePermission> rolePermissionCustomerList = new ArrayList<>();
	//	rolePermissionCustomerList.add(new RolePermission("USER_READ"));
//
	//	List<Role> adminRoleList = new ArrayList<>();
	//	adminRoleList.add(new Role("ADMIN", rolePermissionAdminList));
	//	adminRoleList.add(new Role("CUSTOMER", rolePermissionCustomerList));
//
	//	List<Role> customerRoleList = new ArrayList<>();
	//	customerRoleList.add(new Role("CUSTOMER", rolePermissionCustomerList));
//
	//	List<User>userList = new ArrayList<>();
	//	userList.add(new User("Tomasz", "Zalecki", "zalecki@gmail.com", LocalDate.of(2000, Month.APRIL, 21), 10, "admin123", "Poland", "Krakow", "Zwierzyniecka 123", "32-020", adminRoleList));
	//	userList.add(new User("Krzysztof", "Jarzyna", "jarzyna@vp.pl", LocalDate.of(1990, Month.JANUARY, 27), 12, "kasz123", "Polska", "Kraków", "Kwiatowa 32", "30-300", customerRoleList));
//
//
	//	return args -> {
	//		//rolePermissionService.postRolePermissions(rolePermissionAdminList);
	//		roleService.postRoles(adminRoleList);
	//		//userService.postUsers(userList);
//
	//	};
	//}

}
