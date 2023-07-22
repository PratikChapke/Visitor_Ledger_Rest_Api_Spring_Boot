package com.visitorledger.app.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.visitorledger.app.entity.Employee;
import com.visitorledger.app.entity.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

	List<Visitor> findByReasonForMeeting(String reason);

	List<Visitor> findByDateBetween(LocalDate startDate, LocalDate endDate);

	List<Visitor> findByEmployee(Employee employee);

	List<Visitor> findByDate(LocalDate date);

	@Query("SELECT MAX(v.id) FROM Visitor v")
	int findMaxId();

	Visitor findByuId(int uid);

}
