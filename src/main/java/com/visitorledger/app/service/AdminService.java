package com.visitorledger.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visitorledger.app.Response.DateAndEmployee;
import com.visitorledger.app.Response.Reason;
import com.visitorledger.app.entity.Admin;
import com.visitorledger.app.entity.Employee;
import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.repository.AdminRepository;
import com.visitorledger.app.repository.EmployeeRepository;
import com.visitorledger.app.repository.VisitorRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
//@Transactional
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private EmployeeRepository employeeRepository;

	// finding Admin by username
	public Admin findByName(String userName) {
		try {
			return adminRepository.findByUserName(userName);

		} catch (NoSuchElementException e) {

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	// Authentication of Admin Login
	public boolean adminAuth(String username, String Password) {
		Admin admin = adminRepository.findByUserName(username);
		if (!admin.equals(null)) {
			if (admin.getPassword().equals(Password)) {
				return true;
			} else {
				System.out.println("Invalid password enter valid password");
			}
		} else {
			System.out.println("Invalid username");
		}
		return false;
	}

	// getting all visitors
	public List<Visitor> getAllVisitors() {
		return visitorRepository.findAll();
	}

	// getting all visitors data by reason for meeting
	public List<Visitor> findAllVisitors(String reason) {
		List<Visitor> visitor = visitorRepository.findByReasonForMeeting(reason);
		return visitor;
	}

	// getting visitor details by single date
	public List<Visitor> getVisitorsByDate(LocalDate date) {
		return visitorRepository.findByDate(date);
	}

	// getting visitor details by dates
	public List<Visitor> findVisitorsByDateRange(LocalDate startDate, LocalDate endDate) {

		return visitorRepository.findByDateBetween(startDate, endDate);
	}

	// getting visitor details by employee name
	public List<Visitor> findVisitorsByEmployee(String employeeName) {
		Employee employee = visitorService.findByName(employeeName);
		return visitorRepository.findByEmployee(employee);
	}

	// count of employees being visited
	public int countEmployeesVisitedByVisitorsToday() {
		LocalDate today = LocalDate.now();
		List<Visitor> visitors = visitorRepository.findByDate(today);

		return visitors.stream().map(Visitor::getEmployee).distinct().mapToInt(e -> 1).sum();
	}

	// admin's change password
	public void changePassword(String userName, String password, String newPassword, String confirmPassword) {
		Admin admin = adminRepository.findByUserName(userName);
		System.out.println(admin);
		if (admin != null) {
			if (admin.getPassword().equals(password)) {
				if (newPassword.equals(confirmPassword)) {
					// admin.setPassword(passwordEncoder.encode(newPassword));
					// System.out.println("done");
					System.out.println(admin != null);
					System.out.println(admin.getPassword().equals(password));
					System.out.println(newPassword.equals(confirmPassword));
					admin.setPassword(newPassword);
					adminRepository.save(admin);
					System.out.println(newPassword);
				} else {
					System.out.println("Confirm password doesn't matched");
				}
			} else {
				System.out.println("Incorrect Password");
			}
		} else {
			System.out.println("Admin not found");
		}
	}

	// get total number of visitors count
	public int getTotalVisitorsCount() {
		return adminRepository.findAll().size();
	}

	// add admin to database
	public void save(Admin admin) {

		adminRepository.save(admin);
	}

	// get visitors count object by reason for meeting
	public Reason getCountVisitor() {
		List<Object> objects = adminRepository.countVisitorsByReason();

		Reason reason = new Reason();
		for (Object obj : objects) {
			Object[] result = (Object[]) obj;
			String reasonForMeeting = (String) result[0];
			Long visitorCount = (Long) result[1];

			if ("personal".equals(reasonForMeeting)) {
				reason.setPersonal(visitorCount.intValue());
			} else if ("official".equals(reasonForMeeting)) {
				reason.setOfficial(visitorCount.intValue());
			}
		}

		return reason;
	}

	// getting count of employee's being visited
	public DateAndEmployee getCountEmployeeBeingVisited(LocalDate date) {
		List<Object[]> result = adminRepository.countDistinctEmployeeIdsByDate(date);

		if (result.isEmpty()) {
			DateAndEmployee dateAndEmployee = new DateAndEmployee();
			dateAndEmployee.setDate(date);
			dateAndEmployee.setEmployeeCount((long) 0);
			return dateAndEmployee;
		}

		Object[] row = result.get(0);
		LocalDate resultDate = (LocalDate) row[0];
		Long employeeCount = (Long) row[1];
		Long count = (employeeCount != null) ? employeeCount.longValue() : 0;
		DateAndEmployee dateAndEmployee = new DateAndEmployee();
		dateAndEmployee.setDate(resultDate);
		dateAndEmployee.setEmployeeCount(count);

		return dateAndEmployee;
	}

	// generate excel with visitor data
	public void generateExcel(HttpServletResponse response) throws Exception {

		List<Visitor> visitors = visitorRepository.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Courses Info");
		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Email");
		row.createCell(3).setCellValue("Contact Number");
		row.createCell(4).setCellValue("Age");
		row.createCell(5).setCellValue("Gender");
		row.createCell(6).setCellValue("Reason For Meeting");
		row.createCell(7).setCellValue("Visitor Organzation");
		row.createCell(8).setCellValue("Employee Name");
		row.createCell(9).setCellValue("Date");
		row.createCell(10).setCellValue("In Time");
		row.createCell(11).setCellValue("Out Time");

		int dataRowIndex = 1;

		for (Visitor visitor : visitors) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(visitor.getId());
			dataRow.createCell(1).setCellValue(visitor.getName());
			dataRow.createCell(2).setCellValue(visitor.getEmail());
			dataRow.createCell(3).setCellValue(visitor.getContactNumber());
			dataRow.createCell(4).setCellValue(visitor.getAge());
			dataRow.createCell(5).setCellValue(visitor.getGender());
			dataRow.createCell(6).setCellValue(visitor.getReasonForMeeting());
			dataRow.createCell(7).setCellValue(visitor.getVisitorOrganization());
			dataRow.createCell(8).setCellValue(visitor.getEmployee().getName());
			dataRow.createCell(9).setCellValue(visitor.getDate());
			dataRow.createCell(10).setCellValue(visitor.getInTime().toString());
			dataRow.createCell(11).setCellValue(visitor.getOutTime().toString());

			dataRowIndex++;
		}

		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();

	}

}
