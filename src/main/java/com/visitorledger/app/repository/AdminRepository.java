package com.visitorledger.app.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.visitorledger.app.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

	Admin findByUserName(String userName);

	@Query("SELECT v.reasonForMeeting, COUNT(v) AS visitorCount FROM Visitor v GROUP BY v.reasonForMeeting")
	List<Object> countVisitorsByReason();

	@Query("SELECT v.date, COUNT(DISTINCT v.employee.id) AS employee_count FROM Visitor v WHERE v.date = :date GROUP BY v.date")
	List<Object[]> countDistinctEmployeeIdsByDate(@Param("date") LocalDate date);

}
