package com.example.springmvc.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.springmvc.userdto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	List<UserDto> getAllUsers(int page, int limit);
	
	UserDto createUser(UserDto user);
	void deleteUser(String userid );
	UserDto updateUser(String id, UserDto userDto);
	
	
}
