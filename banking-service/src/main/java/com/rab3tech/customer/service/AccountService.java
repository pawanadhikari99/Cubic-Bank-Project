package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.LocationVO;
import com.rab3tech.vo.UpdateLocationVO;

public interface AccountService {
	List<LocationVO> findLocations();
	String updateLocation(UpdateLocationVO updateLocationVO);
	String addLocation(String lcode, String location);
	
	List<String> AllLocations();
	List<String> findAllAccType();


}
