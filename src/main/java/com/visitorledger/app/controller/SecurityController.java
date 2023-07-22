package com.visitorledger.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visitorledger.app.service.SecurityService;

@RestController
@RequestMapping("/security")
public class SecurityController {

	@Autowired
	private SecurityService securityService;

	// security authentication
	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<?> getAuth(@PathVariable String userName, @PathVariable String password) {
		try {
			boolean isAuthenticated = securityService.authenticate(userName, password);
			if (isAuthenticated) {
				return ResponseEntity.ok("Authentication successful");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// update visitor's out time
	@PutMapping("/visitor/update/{uid}")
	public ResponseEntity<String> updateOutTime(@PathVariable int uid) {
		try {
			securityService.updateOutTime(uid);
			return ResponseEntity.ok("Out time updated successfully");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update out time");
		}
	}

}
