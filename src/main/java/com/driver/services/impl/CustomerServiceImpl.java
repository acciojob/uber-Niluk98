package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		Customer customer1=new Customer();
		customer1.setMobile(customer.getMobile());
		customer1.setPassword(customer.getPassword());
		customer1.setTripBookings(null);
		customerRepository2.save(customer1);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> drivers=driverRepository2.findAll();

		Collections.sort(drivers,
				(o1, o2) -> Integer.compare(o1.getDriverId(),o2.getDriverId()));

		Driver available_driver=null;
		for(Driver driver:drivers){
			Cab cab=driver.getCab();
			if(cab!=null){
				if(cab.getAvailable()){
					available_driver=driver;
					break;
				}

			}

		}
		if (available_driver==null){
			throw new Exception("No cab available!");
		}
		available_driver.getCab().setAvailable(false);
		TripBooking tripBooking=new TripBooking();

		Optional<Customer> optionalCustomer=customerRepository2.findById(customerId);
		if(optionalCustomer.isEmpty()){
			throw new Exception("Customer not found");
		}


		tripBooking.setStatus(TripStatus.CONFIRMED);

		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(available_driver.getCab().getPerKmRate()*distanceInKm);


		 Customer customer=optionalCustomer.get();
		 customer.getTripBookings().add(tripBooking);


		available_driver.getTripBookings().add(tripBooking);

		tripBooking.setCustomer(customer);
		tripBooking.setDriver(available_driver);

		TripBooking savedTripBooking=tripBookingRepository2.save(tripBooking);
		 return savedTripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking=tripBookingRepository2.findById(tripId);


		TripBooking tripBooking=optionalTripBooking.get();

		Driver driver=tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBooking.setStatus(TripStatus.CANCELED);


		tripBookingRepository2.save(tripBooking);

		driverRepository2.save(driver);


	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking=tripBookingRepository2.findById(tripId);


		TripBooking tripBooking=optionalTripBooking.get();
		Customer customer=tripBooking.getCustomer();
		Driver driver=tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBooking.setStatus(TripStatus.COMPLETED);


		tripBookingRepository2.save(tripBooking);

		driverRepository2.save(driver);

	}
}
