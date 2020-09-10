package com.rab3tech.customer.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.admin.dao.repository.AccountTypeRepository;

@Service
@Transactional
public class AccountTypeServiceImpl implements AccountTypeService {
	@Autowired 
	private AccountTypeRepository accountTypeRepository;
	@Override
	public List<String> findAllAccType(){
		List <String> accType =accountTypeRepository.findAllAccType();
		return accType;
	}

}




