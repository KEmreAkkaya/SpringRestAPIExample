package com.example.springmvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.springmvc.entity.AuthorityEntity;
import com.example.springmvc.entity.RoleEntity;
import com.example.springmvc.entity.UserEntity;
import com.example.springmvc.repository.AuthorityRepository;
import com.example.springmvc.repository.RoleRepository;
import com.example.springmvc.repository.UserRepository;
import com.example.springmvc.userdto.Utils;

@Component
public class InitialUserConfig {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	Utils utils;

	@Autowired
	UserRepository userRepository;

	private Random random = new Random();

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("From Application ready event...");

		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

		RoleEntity roleUser = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
		RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(),
				Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		//Random email number generator
		long x = Math.abs(random.nextLong());

		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Emre");
		adminUser.setLastName("A");
		adminUser.setEmail("admin" + x + "@test.com");
		adminUser.setEmailVerificationStatus(true);
		adminUser.setUserId(utils.generateUserId(30));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		adminUser.setRoles(Arrays.asList(roleAdmin));

		UserEntity storedUserDetails = userRepository.findUserByEmail("admin" + x + "@test.com");
		if (storedUserDetails == null) {
			userRepository.save(adminUser);
		}

		//Random email number generator2
		long y = Math.abs(random.nextLong());

		UserEntity user = new UserEntity();
		user.setFirstName("Emre");
		user.setLastName("B");
		user.setEmail("user" + y + "@test.com");
		user.setEmailVerificationStatus(true);
		user.setUserId(utils.generateUserId(30));
		user.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		user.setRoles(Arrays.asList(roleUser));

		UserEntity storedUserDetails2 = userRepository.findUserByEmail("user" + y + "@test.com");
		if (storedUserDetails2 == null) {
			userRepository.save(user);
		}
		System.out.println("    Created admin" + x + "@test.com and Password:123 ");
		System.out.println("    Created user" + y + "@test.com and Password:123");

	}

	@Transactional
	private AuthorityEntity createAuthority(String name) {

		AuthorityEntity authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}

	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {

		RoleEntity role = roleRepository.findByName(name);
		if (role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}

}