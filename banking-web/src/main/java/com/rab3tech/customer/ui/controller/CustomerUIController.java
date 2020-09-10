package com.rab3tech.customer.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.customer.service.AccountService;
import com.rab3tech.customer.service.CreditCardInterface;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.LocationService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.impl.AccountTypeService;
import com.rab3tech.customer.service.impl.CustomerEnquiryService;
import com.rab3tech.customer.service.impl.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.FromToAccountsVO;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;

/**
 * 
 * @author nagendra This class for customer GUI
 *
 */
@Controller
//@RequestMapping("/customer")
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@Autowired
	private SecurityQuestionService securityQuestionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTypeService accountTypeService;

	@Autowired
	private CreditCardInterface creditCard;

	@GetMapping("/customer/forget/password")
	public String forgetPassword() {
		// spring.thymeleaf.prefix=classpath:/src/main/resources/templates/
		return "customer/forgetPassword"; // forgetPassword.html
	}

	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName = "customer/dashboard";
		boolean status = loginService.checkPasswordValid(loginid, changePasswordVO.getCurrentPassword());
		if (status) {
			if (changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				viewName = "customer/dashboard";
				loginService.changePassword(changePasswordVO);
			} else {
				model.addAttribute("error", "Sorry , your new password and confirm passwords are not same!");
				return "customer/login"; // login.html
			}
		} else {
			model.addAttribute("error", "Sorry , your username and password are not valid!");
			return "customer/login"; // login.html
		}
		return viewName;
	}

	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to thyme
			// leaf!!!!!
			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		// Write code to send email

		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO);
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}

	/*
	 * @GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome"
	 * }) public String showCustomerEnquiryPage(Model model) { CustomerSavingVO
	 * customerSavingVO = new CustomerSavingVO(); // model is map which is used to
	 * carry object from controller to view model.addAttribute("customerSavingVO",
	 * customerSavingVO); return "customer/customerEnquiry"; // customerEnquiry.html
	 * }
	 */

	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		// LoadLocationAndAccountVO loadLocationAndAccountVOs = new
		// LoadLocationAndAccountVO();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		List<String> locations = locationService.AllLocations();
		List<String> accType = accountTypeService.findAllAccType();
		model.addAttribute("location", locations);
		model.addAttribute("accType", accType);
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}

	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@ModelAttribute CustomerSavingVO customerSavingVO, Model model) {
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}

	@GetMapping("/customer/app/status")
	public String customerAppStatus() {

		return "customer/appstatus";
	}

	@GetMapping("/customer/customerSearch")
	public String customerSearch() {

		return "customer/customerSearch";
	}

	@GetMapping("/customer/addPayee")
	public String customerAddPayee(Model model) {
		PayeeInfoVO payeeInfoVO = new PayeeInfoVO();
		model.addAttribute("payeeInfoVO", payeeInfoVO);
		return "customer/addPayee";
	}

	@PostMapping("/customer/account/addPayee")
	public String newPayee(@Valid @ModelAttribute("payeeInfoVO") PayeeInfoVO payeeInfoVO, BindingResult bindingResult,
			HttpSession session, Model model) {

		if (payeeInfoVO.getAccNumberConfirm() != null
				&& !payeeInfoVO.getAccNumberConfirm().equalsIgnoreCase(payeeInfoVO.getPayeeAccountNo())) {
			// ObjectError objectError=new ObjectError("accNumberConfirm", "Hey!, your
			// account and confirm account are not same");
			bindingResult.rejectValue("accNumberConfirm", "account.msg", "An account already exists for this email.");
			// bindingResult.addError(objectError);
		}

		if (bindingResult.hasErrors()) {
			return "customer/addPayee";
		}

		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		payeeInfoVO.setCustomerId(loginVO.getUsername());
		System.out.println("MY CUSTOMER USERID =========================================" + payeeInfoVO.getCustomerId());
		// String loginId = loginService.findUserByName(payeeInfoVO.getPayeeName());
		// payeeInfoVO.setCustomerId(loginId);
		customerService.addPayee(payeeInfoVO);
		model.addAttribute("successMessage", "Payee added successfully");
		return "redirect:/customer/dashboard";
	}

	@GetMapping("/customer/pendingPayee")
	public String pendinPayeeList(Model model) {
		List<PayeeInfoVO> payeeInfoList = customerService.pendingPayeeList();
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/pendingPayee";

	}

	@GetMapping("/customer/registeredPayee")
	public String registeredPayeeList(HttpSession session,Model model) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String customerEmail = loginVO.getUsername();
		List<PayeeInfoVO> payeeInfoList = customerService.registeredPayeeList(customerEmail);		
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/registeredPayee";

	}
	
	@GetMapping("/customer/fundTransfer")
	public String fundTransfer(Model model) {
		FundTransferVO fundTransferVO = new FundTransferVO();
		model.addAttribute("fundTransferVO", fundTransferVO);
		return "customer/fundTransfer";
	}
	
	@GetMapping("/customer/transferConfirm")
	public String transferConfirm(Model model) {
		FundTransferVO fundTransferVO = new FundTransferVO();
		model.addAttribute("fundTransferVO", fundTransferVO);
		return "customer/transferConfirm";
	}

	@GetMapping("/load/image")
	public void findPhoto(@RequestParam String email, HttpServletResponse response) throws IOException {
		response.setContentType("image/png");
		System.out.println("image");
		byte[] photo = customerService.imageSearch(email);
		System.out.println(photo);
		ServletOutputStream outputStream = response.getOutputStream();
		if (photo != null && photo.length > 0) {
			outputStream.write(photo);
			outputStream.flush();
		}
		outputStream.close();
	}

	@GetMapping("/customers/credit/card")
	public void findCustomerCreditCard(@RequestParam String name, @RequestParam String email,
			HttpServletResponse response) throws IOException {
		byte[] card = generatedCreditCard(creditCard.generateCreditCardNumber(),
				creditCard.generateCreditCardExpireDate(), name);
		response.setContentType("image/png");
		ServletOutputStream outputStream = response.getOutputStream();
		if (card != null) {
			outputStream.write(card);
		} else {
			outputStream.write(new byte[] {});
		}
		outputStream.flush();
		outputStream.close();
	}

	@GetMapping("/customers/credit/bcard")
	public void generateCustomerBackCreditCard(String cvv, HttpServletResponse response) throws IOException {
		byte[] card = creditCard.generatedBackCreditCard(creditCard.generateCCVNumber());
		response.setContentType("image/png");
		ServletOutputStream outputStream = response.getOutputStream();
		if (card != null) {
			outputStream.write(card);
		} else {
			outputStream.write(new byte[] {});
		}
		outputStream.flush();
		outputStream.close();
	}

	private byte[] generatedCreditCard(String cardNumber, String exp, String name) {

		return creditCard.generatedCreditCard(cardNumber, exp, name);
	}

	private Object generateCreditCardExpireDate() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object generateCreditCardNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
