package com.visitorledger.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visitorledger.app.Response.DateObject;
import com.visitorledger.app.Response.DateResponse;
import com.visitorledger.app.Response.EmployeesBeingVisited;
import com.visitorledger.app.Response.Reason;
import com.visitorledger.app.entity.Admin;
import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.service.AdminService;
import com.visitorledger.app.service.EmailSenderService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private EmailSenderService emailService;

	// getting all visitors
	@GetMapping("/visitors")
	public ResponseEntity<?> getAllVisitors() {
		try {
			List<Visitor> visitors = adminService.getAllVisitors();
			return ResponseEntity.ok(visitors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// getting admin details by admin username
	@GetMapping("/{userName}")
	public ResponseEntity<?> getAdmin(@PathVariable String userName) {
		try {
			Admin admin = adminService.findByName(userName);
			return ResponseEntity.ok(admin);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// authentication of admin details
	@GetMapping("/{userName}/{password}")
	public ResponseEntity<Boolean> getAuth(@PathVariable String userName, @PathVariable String password) {
		try {
			boolean isAuthenticated = adminService.adminAuth(userName, password);
			if (isAuthenticated) {
				return ResponseEntity.ok(isAuthenticated);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(isAuthenticated);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	// getting visitor details by reason for meeting
	@GetMapping("/visitors/{reason}")
	public ResponseEntity<?> getAllVisitors(@PathVariable String reason) {
		try {
			List<Visitor> visitors = adminService.findAllVisitors(reason);
			return ResponseEntity.ok(visitors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// getting visitor details by date
	@GetMapping("/visitors/date/{date}")
	public ResponseEntity<?> getVisitorsByDateRange(@PathVariable LocalDate date) {
		try {
			List<Visitor> visitors = adminService.getVisitorsByDate(date);
			return ResponseEntity.ok(visitors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// getting visitor details by dates
	@GetMapping("/visitors/date/{startDate}/{endDate}")
	public ResponseEntity<?> getVisitorsByDateRange(@PathVariable LocalDate startDate,
			@PathVariable LocalDate endDate) {
		try {
			List<Visitor> visitors = adminService.findVisitorsByDateRange(startDate, endDate);
			return ResponseEntity.ok(visitors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// getting visitors by employee name
	@GetMapping("/visitors/employee/{name}")
	public ResponseEntity<?> getVisitorsByEmployee(@PathVariable String name) {
		try {
			List<Visitor> visitors = adminService.findVisitorsByEmployee(name);
			return ResponseEntity.ok(visitors);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// counting total number of employees got visitors
	@GetMapping("/employees/count")
	public ResponseEntity<Integer> countEmployeesVisitedByVisitorsToday() {
		try {
			int count = adminService.countEmployeesVisitedByVisitorsToday();
			return ResponseEntity.ok(count);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
		}
	}

	// admin's change password
	@PutMapping("/change/{userName}/{currentPassword}/{newPassword}/{confirmPassword}")
	public ResponseEntity<?> changePassword(@PathVariable String userName, @PathVariable String currentPassword,
			@PathVariable String newPassword, @PathVariable String confirmPassword) {
		try {
			adminService.changePassword(userName, currentPassword, newPassword, confirmPassword);
			return ResponseEntity.ok("Password changed successfully");
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	// total number of visitors
	@GetMapping("/visitors/count")
	public ResponseEntity<?> getTotalVisitorsCount() {
	    try {
	        int count = adminService.getTotalVisitorsCount();
	        return ResponseEntity.ok(count);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }
	}


	// add admin
	 @PostMapping("/save")
	    public ResponseEntity<String> add(@RequestBody Admin admin) {
	        try {
	            adminService.save(admin);
	            return ResponseEntity.ok("Admin saved successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save admin");
	        }
	    }
	 
	// get visitors count object by reason for meeting
	 @GetMapping("/count")
	 public ResponseEntity<?> getCount() {
	     try {
	         Reason count = adminService.getCountVisitor();
	         return ResponseEntity.ok(count);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	     }
	 }

	 
	 //get number of visitors by date 
	 @GetMapping("/dashboard/date")
	 public ResponseEntity<DateResponse> getData() {
	     try {
	         LocalDate today = LocalDate.now();
	         LocalDate yesterday = today.minusDays(1);
	         DateObject thisWeek = new DateObject();
	         thisWeek.setNow(today);
	         thisWeek.setPrevious(today.minusWeeks(1));

	         DateObject previousWeek = new DateObject();
	         previousWeek.setNow(thisWeek.getPrevious().minusDays(1));
	         previousWeek.setPrevious(previousWeek.getNow().minusWeeks(1));

	         DateObject thisMonth = new DateObject();
	         thisMonth.setNow(today);
	         thisMonth.setPrevious(today.minusMonths(1));

	         DateObject previousMonth = new DateObject();
	         previousMonth.setNow(thisMonth.getPrevious().minusDays(1));
	         previousMonth.setPrevious(previousMonth.getNow().minusMonths(1));

	         DateResponse response = new DateResponse();
	         response.setToday(adminService.getVisitorsByDate(today).size());
	         response.setYesterday(adminService.getVisitorsByDate(yesterday).size());
	         response.setThisWeek(adminService.findVisitorsByDateRange(thisWeek.getPrevious(), thisWeek.getNow()).size());
	         response.setPreviousWeek(
	                 adminService.findVisitorsByDateRange(previousWeek.getPrevious(), previousWeek.getNow()).size());
	         response.setThisMonth(adminService.findVisitorsByDateRange(thisMonth.getPrevious(), thisMonth.getNow()).size());
	         response.setPreviousMonth(
	                 adminService.findVisitorsByDateRange(previousMonth.getPrevious(), previousMonth.getNow()).size());

	         return ResponseEntity.ok(response);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	     }
	 }

	 //get count of employees being visited
	 @GetMapping("/dashboard/employee")
	 public ResponseEntity<?> getEmployeesCountBeingVisited() {
	     try {
	         EmployeesBeingVisited response = new EmployeesBeingVisited();
	         LocalDate today = LocalDate.now();
	         response.setOne(adminService.getCountEmployeeBeingVisited(today));
	         response.setTwo(adminService.getCountEmployeeBeingVisited(today.minusDays(1)));
	         response.setThree(adminService.getCountEmployeeBeingVisited(today.minusDays(2)));
	         response.setFour(adminService.getCountEmployeeBeingVisited(today.minusDays(3)));
	         response.setFive(adminService.getCountEmployeeBeingVisited(today.minusDays(4)));
	         response.setSix(adminService.getCountEmployeeBeingVisited(today.minusDays(5)));
	         response.setSeven(adminService.getCountEmployeeBeingVisited(today.minusDays(6)));

	         return ResponseEntity.ok(response);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	     }
	 }

//	@GetMapping("/excel")
//	public void generateExcelReport(HttpServletResponse response) throws Exception {
//		response.setContentType("application/octet-stream");
//		String headerKey = "Content-Disposition";
//		String headerValue = "attachment;filename=visitors.xls";
//		response.setHeader(headerKey, headerValue);
//		adminService.generateExcel(response);
//
//		response.flushBuffer();
//	}
	 
	 //generating excel sheet of visitors data
	 @GetMapping("/excel")
	 public ResponseEntity<?> generateExcelReport(HttpServletResponse response) {
	     try {
	         response.setContentType("application/octet-stream");
	         String headerKey = "Content-Disposition";
	         String headerValue = "attachment;filename=visitors.xls";
	         response.setHeader(headerKey, headerValue);
	         adminService.generateExcel(response);

	         response.flushBuffer();

	         return ResponseEntity.ok().build();
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	     }
	 }

}
