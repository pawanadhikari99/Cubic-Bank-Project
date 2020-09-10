package com.rab3tech.customer.service;

public interface CreditCardInterface {

	byte[] generatedCreditCard(String cardNumber, String exp, String name);

	String generateCreditCardNumber();

	String generateCreditCardExpireDate();

	String generateCCVNumber();

	byte[] generatedBackCreditCard(String cvv);

	void saveCreditInfo();

	

}
