package com.example.springmvc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springmvc.entity.RoleEntity;
import com.example.springmvc.entity.UserEntity;
import com.example.springmvc.exceptions.UserServiceException;
import com.example.springmvc.repository.RoleRepository;
import com.example.springmvc.repository.UserRepository;
import com.example.springmvc.security.UserPrincipal;
import com.example.springmvc.service.UserService;
import com.example.springmvc.uimodelresponse.ErrorMessages;
import com.example.springmvc.userdto.AddressDto;
import com.example.springmvc.userdto.UserDto;
import com.example.springmvc.userdto.Utils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findUserByEmail(user.getEmail()) != null)
			throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

		
		for(int i=0; i < user.getAddresses().size();i++) 
		{
			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(30));
			user.getAddresses().set(i, address);
			
		}

		
		
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user,UserEntity.class);
		
		String publicUserId = utils.generateRandomString(30);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);
		
		
		Collection<RoleEntity> roleEntities = new HashSet<>();
		for(String role:user.getRoles()) 
		{
			RoleEntity roleEntity = roleRepository.findByName(role);
			
			if(roleEntity != null ) 
			{
				roleEntities.add(roleEntity);
			}
			
		}
		
		userEntity.setRoles(roleEntities);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(storedUserDetails,UserDto.class);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findUserByEmail(email);

		if (user == null)
			throw new UsernameNotFoundException(email);

		return new UserPrincipal(user);
		
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity user = userRepository.findUserByEmail(email);
		if (user == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(user, returnValue);

		return returnValue;

	}

	@Override
	public UserDto getUserByUserId(String userId) {
	
		UserDto returnValue  = new UserDto();
		UserEntity user = userRepository.findByUserId(userId);
		if (user == null)
			throw new UsernameNotFoundException("User id : " +userId + " not found!");
		
		BeanUtils.copyProperties(user, returnValue);
		
		
		return returnValue;
	}



	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue  = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getAllUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page <UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for(UserEntity userEntity :  users) 
		{
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
			
		}
		
		return returnValue;
	}

	




}
