package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService service;	
	
	@PostMapping("/addUser")
	public void addUser(@RequestBody UserDto dto) {
		service.addUser(dto);
	}
	
	@PostMapping("/deleteUser")
	public void deleteUser(@RequestBody UserDto dto) {
		service.deleteUser(dto.getId());
	}
	
	@PostMapping("/updateUser")
	public void updateUser(@RequestBody UserDto dto) {
		service.updateUser(dto);
	}
	
	@PostMapping("/getUser")
	public UserDto getUser(@RequestBody UserDto dto) {
		return service.getUser(dto.getId());
	}
	
	@PostMapping("/getGroupedUser")
	public List<UserDto> getGroupedUser(@RequestBody UserDto dto) {
		return service.getGroupedUser(dto);
	}
	
	@PostMapping("/getUserList")
	public List<UserDto> getUserList() {
		return service.getUserList();
	}
	
	@GetMapping("/getTotalUser")
	public long getTotalUser() {
		return service.getCount();
	}
	
}
