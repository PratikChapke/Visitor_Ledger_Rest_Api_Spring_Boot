package com.visitorledger.app.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visitorledger.app.entity.Employee;
import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.service.VisitorService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/visitor")
public class VisitorController {

	@Autowired
	private VisitorService visitorService;

	// saving visitor details
	@PostMapping("/save")
	public void add(@RequestBody Visitor visitor) {

		visitorService.save(visitor.getWhomToMeet(), visitor);
	}

	// getting employee details by employee name
	@GetMapping("/employee/{employeeName}")
	public Employee getEmployeeId(@PathVariable String employeeName) {
		return visitorService.findByName(employeeName);
	}

	// getting Date of visitor by visitor Id
	@GetMapping("/{id}")
	public LocalDate getLocalDate(@PathVariable Integer id) {
		return visitorService.findByVisitorId(id);
	}

	// getting all the employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return visitorService.getAllEmployees();
	}

	// updating out time
	@GetMapping("/update/{uid}")
	public ResponseEntity<String> updateOutTime(@PathVariable int uid) {
		try {
			visitorService.updateOutTime(uid);
			return ResponseEntity.ok("Out time updated successfully");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update out time");
		}
	}

}
