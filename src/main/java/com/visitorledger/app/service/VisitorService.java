package com.visitorledger.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visitorledger.app.entity.Employee;
import com.visitorledger.app.entity.Visitor;
import com.visitorledger.app.repository.EmployeeRepository;
import com.visitorledger.app.repository.VisitorRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VisitorService {

	private Integer uid = 600000;
	Integer maxId = 0;

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmailSenderService emailService;

	// getting maxId
	public Integer getMaxVisitorId() {

		try {
			Integer maxId = visitorRepository.findMaxId();
			if (maxId == null) {
				return 0;
			}
			return maxId;
		} catch (Exception e) {
			// e.printStackTrace();
			return 0;
		}

	}

	// getting epmployee details by name
	public Employee findByName(String employeeName) {
		List<Employee> employees = employeeRepository.findByName(employeeName);
		if (employees.isEmpty()) {
			throw new NoSuchElementException("No visitor found with the given name");
		}
		return employees.get(0);

	}

	// getting date of visitor by visitor Id
	public LocalDate findByVisitorId(Integer id) {
		Visitor v = visitorRepository.findById(id).get();
		LocalDate d = v.getDate();
		return d;

	}

	public void save(String employeeObj, Visitor visitor) {
		Employee employee = findByName(employeeObj);
		visitor.setuId(uid + getMaxVisitorId());
		visitor.setEmployee(employee);
		visitor.setOutTime(null);
		Visitor visitorobj = visitor;
		visitorRepository.save(visitor);
		try {
			emailService.sendVisitorDetails(visitorobj);
		} catch (Exception e) {
			System.out.println("Sending mail to visitor Error");
		}
	}

	// update out time
	public void updateOutTime(Integer uid) {
		Visitor visitor = visitorRepository.findByuId(uid);
		LocalTime outTime = LocalTime.now();
		visitor.setOutTime(outTime);
	}

	// get all employee's data
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

}
