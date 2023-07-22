package com.visitorledger.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.visitorledger.app.entity.Security;

@Repository
public interface SecurityRepository extends JpaRepository<Security, String> {

	Security findByUserName(String username);

}
