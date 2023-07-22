package com.visitorledger.app.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.visitorledger.app.entity.Visitor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendVisitorDetails(Visitor visitor) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("vijaydevarapathi1083@gmail.com");
		mimeMessageHelper.setTo(visitor.getEmail());
		mimeMessageHelper.setText(
				"Dear " + visitor.getName() + ", \n\n" + "Thank you for visiting us. Here are your details:\n\n"
						+ "Name: " + visitor.getName() + "\n" + "Email: " + visitor.getEmail() + "\n" + "Age: "
						+ visitor.getAge() + "\n" + "Gender " + visitor.getGender() + "\n" + "Contact Number: "
						+ visitor.getContactNumber() + "\n" + "Your Out time otp: " + visitor.getuId() + "\n"

						+ "Plese submit your otp during out time: " + visitor.getuId());
		mimeMessageHelper.setSubject("Visitor Details");
		javaMailSender.send(mimeMessage);

		System.out.printf("Mail with attachment sent successfully..");

	}

	public void sendEmailWithAttachment(List<Visitor> visitors) {
		try {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Visitor Data");
			Row headerRow = sheet.createRow(0);

			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Name");
			headerRow.createCell(2).setCellValue("Email");
			headerRow.createCell(3).setCellValue("Contact Number");
			headerRow.createCell(4).setCellValue("Age");
			headerRow.createCell(5).setCellValue("Gender");
			headerRow.createCell(6).setCellValue("Reason For Meeting");
			headerRow.createCell(7).setCellValue("Visitor Organzation");
			headerRow.createCell(8).setCellValue("Employee Name");
			headerRow.createCell(9).setCellValue("Date");
			headerRow.createCell(10).setCellValue("In Time");
			headerRow.createCell(11).setCellValue("Out Time");

			int rowIndex = 1;
			for (Visitor visitor : visitors) {
				Row dataRow = sheet.createRow(rowIndex++);
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

			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);

			byte[] excelBytes = outputStream.toByteArray();
			ByteArrayResource resource = new ByteArrayResource(excelBytes) {
				@Override
				public String getFilename() {
					return "visitor_data.xls";
				}
			};

			workbook.close();
			outputStream.close();

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setSubject("Visitor Data");
			helper.setText("Please find the attached visitor data.");
			helper.setFrom("vijaydevarapathi1083@gmail.com");
			helper.setTo("masularaghu046@gmail.com");
			helper.addAttachment(resource.getFilename(), resource);

			javaMailSender.send(mimeMessage);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
			// Handle exception
		}
	}

}
