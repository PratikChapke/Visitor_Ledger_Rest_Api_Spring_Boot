package com.visitorledger.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visitorledger.app.entity.Security;
import com.visitorledger.app.repository.SecurityRepository;

@Service
public class SecurityService {

	@Autowired
	private SecurityRepository securityRepository;

	@Autowired
	private VisitorService visitorService;

	// security authentication
	public boolean authenticate(String username, String Password) {
		Security security = securityRepository.findByUserName(username);
		if (!security.equals(null)) {
			if (security.getPassword().equals(Password)) {
				return true;
			} else {
				System.out.println("Invalid password enter valid password");
			}
		} else {
			System.out.println("Invalid username");
		}
		return false;
	}

	// update out time of visitor
	public void updateOutTime(Integer uid) {

		visitorService.updateOutTime(uid);
	}

}
