package com.driver.controllers;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.services.AdminService;
import com.driver.services.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminServiceImpl adminService;
	@PostMapping("/register")
	public ResponseEntity<Void> registerAdmin(@RequestBody Admin admin){

		try {
			adminService.adminRegister(admin);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}





	}

	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password){

		try {
		 Admin	updatedAdmin=adminService.updatePassword(adminId,password);
			return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}


	}

	@DeleteMapping("/delete")
	public void deleteAdmin(@RequestParam Integer adminId){
		try {
			adminService.deleteAdmin(adminId);

		}catch (Exception e){

		}
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
		try {
			List<Customer>listOfCustomers=adminService.getListOfCustomers();
			return listOfCustomers;
		}catch (Exception e){
			return null;
		}

	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		try {
			List<Driver>listOfDrivers=adminService.getListOfDrivers();
			return listOfDrivers;
		}catch (Exception e){
			return null;
		}

	}
}
