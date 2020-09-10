package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.AccountTypeVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerUpdateVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.PayeeApproveVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.RoleVO;

public interface CustomerService {

	CustomerVO createAccount(CustomerVO customerVO);

	CustomerAccountInfoVO createBankAccount(int csaid);

	List<CustomerVO> findCustomers();

	byte[] findPhotoByid(int cid);

	void updateProfile(CustomerUpdateVO customerVO);
	
	public List<RoleVO> getRoles();

	CustomerVO searchCustomer(String name);
	
	List<AccountTypeVO> findAccountTypes();

	String findCustomerByEmail(String email);

	String findCustomerByMobile(String mobile);

	void addPayee(PayeeInfoVO payeeInfoVO);

	List<PayeeInfoVO> pendingPayeeList();

	List<PayeeInfoVO> registeredPayeeList(String customerId);
	
	String approveDisApprovePayee(PayeeApproveVO payeeApproveVO);

	List<CustomerVO> searchCustomers(String searchText);

	byte[] imageSearch(String email);
	
	String convertToWords(String amount);

}
