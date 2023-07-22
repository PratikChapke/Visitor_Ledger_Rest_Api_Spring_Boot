package com.visitorledger.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.service.AdminService;
import com.visitorledger.app.service.EmailSenderService;

@RestController
@RequestMapping("/api")
public class EmailController {
	@Autowired
	private final AdminService adminService;

	@Autowired
	private EmailSenderService emailSenderService;

	public EmailController(AdminService adminService) {
		this.adminService = adminService;
	}

	// @Scheduled(cron = "0 0 13 * * MON") dont remove it uncomment in personal
	// laptop
	public void sendWeeklyEmail() {
		LocalDate startDate = LocalDate.now().minusDays(6);
		LocalDate endDate = LocalDate.now();
		List<Visitor> visitors = adminService.findVisitorsByDateRange(startDate, endDate);
		emailSenderService.sendEmailWithAttachment(visitors);
	}

}
