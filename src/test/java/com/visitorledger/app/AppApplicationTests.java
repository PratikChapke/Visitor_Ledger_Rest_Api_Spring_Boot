package com.visitorledger.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.visitorledger.app.entity.Admin;
import com.visitorledger.app.repository.AdminRepository;
import com.visitorledger.app.service.AdminService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    private AdminService adminService;
	
	@Autowired
	private AdminRepository adminRepository;

    @Test
    public void testSaveAdmin() {
    	 // Create an admin object
        Admin admin = new Admin();
        admin.setUserName("admin1");
        admin.setPassword("password1");
        admin.setName("John Doe");
        admin.setEmail("johndoe@example.com");

        // Call the save() method of the admin service
        adminService.save(admin);

        // Retrieve the admin object from the repository
        Admin savedAdmin = adminRepository.findByUserName(admin.getUserName());

        // Assert that the saved admin object is not null and has the expected properties
        assertNotNull(savedAdmin);
        assertEquals(admin.getUserName(), savedAdmin.getUserName());
        assertEquals(admin.getPassword(), savedAdmin.getPassword());
        assertEquals(admin.getName(), savedAdmin.getName());
        assertEquals(admin.getEmail(), savedAdmin.getEmail());
    }
    
    
    
 

}
