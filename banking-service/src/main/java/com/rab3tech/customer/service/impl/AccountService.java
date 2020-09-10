package com.rab3tech.customer.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rab3tech.vo.LocationVO;
import com.rab3tech.vo.UpdateLocationVO;
@Service
@Transactional
public class AccountService implements com.rab3tech.customer.service.AccountService{

	@Override
	public List<LocationVO> findLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateLocation(UpdateLocationVO updateLocationVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addLocation(String lcode, String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> AllLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllAccType() {
		// TODO Auto-generated method stub
		return null;
	}

}
