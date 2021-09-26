package com.example.springmvc.service;

import java.util.List;

import com.example.springmvc.userdto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);
    AddressDto getAddress(String addressId);

}
