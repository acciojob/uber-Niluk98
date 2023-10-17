package com.driver.controllers;

import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	@Autowired
	DriverService driverService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<Void> registerDriver(@RequestParam("mobile") String mobile, @RequestParam("password") String password){
		try {
			driverService.register(mobile,password);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/delete")
	public void deleteDriver(@RequestParam Integer driverId){
		try{
			driverService.removeDriver(driverId);
		}catch (Exception e){

		}
	}

	@PutMapping("/status")
	public void updateStatus(@RequestParam Integer driverId){
		try {
			driverService.updateStatus(driverId);

		}catch (Exception e){

		}
	}
}
