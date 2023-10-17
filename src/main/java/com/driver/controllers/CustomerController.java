package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import com.driver.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerServiceImpl customerService;
	@PostMapping("/register")
	public ResponseEntity<Void> registerCustomer(@RequestBody Customer customer){
		try{
			customerService.register(customer);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}


	}

	@DeleteMapping("/delete")
	public void deleteCustomer(@RequestParam("id") Integer customerId){
		try{
			customerService.deleteCustomer(customerId);

		}catch (Exception e){

		}
	}

	@PostMapping("/bookTrip")
	public ResponseEntity<Integer> bookTrip(@RequestParam("id") Integer customerId, @RequestParam("fromLocation") String fromLocation, @RequestParam("toLocation") String toLocation, @RequestParam("distanceInKm") Integer distanceInKm) throws Exception {


			TripBooking bookedTrip=customerService.bookTrip(customerId,fromLocation,toLocation,distanceInKm);
			return new ResponseEntity<>(bookedTrip.getTripBookingId(), HttpStatus.CREATED);



	}

	@DeleteMapping("/complete")
	public void completeTrip(@RequestParam Integer tripId) throws Exception{

		try{
			customerService.completeTrip(tripId);
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}

	}

	@DeleteMapping("/cancelTrip")
	public void cancelTrip(@RequestParam Integer tripId) throws Exception{
		try{
			customerService.cancelTrip(tripId);
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
	}
}
