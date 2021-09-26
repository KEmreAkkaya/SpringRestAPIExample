package com.example.springmvc.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.springmvc.entity.UserEntity;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>
{
	
     UserEntity findUserByEmail(String email);
     UserEntity findByUserId(String userId);
  
}
