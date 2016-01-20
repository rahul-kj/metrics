package com.rahul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.domain.Users;

@Service
public class UserService {
	
	@Autowired
	CustomCloudFoundryClientService clientService;
	
	public Users getAllUsers() {
		return clientService.getUsers();
	}

}
