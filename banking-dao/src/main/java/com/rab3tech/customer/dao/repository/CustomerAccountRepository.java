package com.rab3tech.customer.dao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.AccountType;

public interface CustomerAccountRepository extends JpaRepository<AccountType, Long>{
	/*
	 * @Query ("Select distinct a.accType from accType a") public List<String>
	 * findAllAccountType();
	 */

}
