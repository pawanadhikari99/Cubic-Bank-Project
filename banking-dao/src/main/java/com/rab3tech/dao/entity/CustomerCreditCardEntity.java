package com.rab3tech.dao.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="credit_card_tbl")
public class CustomerCreditCardEntity {
	
	private long cardNumber;
	
	@DateTimeFormat
	private Date expDate;
	
	
	private Customer customer;
	
	private String network; //Visa/Master	
	private int gracePeriodInDay;	//period that interest not counted for late payment
	private double currentBalance;	//all unpaid charges on an account, up to the date of your inquiry.Â 
	private double statementBalance; //amount you owe on your credit card as of the latest billing cycle
	private Date statementDueDate;	
	private double apr;	
	private double monthlyMinPayment; 	//lowest amount of money that one is required to pay on a credit card statement each month, usually 10 to 15 dollars.
	private double cashBack; //in percent
	private double floorLimit; //A floor limit is the amount of money above which credit card transactions must be authorized.
	private long creditScore; //If paid on time, score up, otherwise, score down.
							  //If higher credit score, then lower APR, otherwise high APR.
	private long rewardPoint; //Reward point can be earned when shopping on specific places etc.
	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customerId",referencedColumnName="id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

//	public CreditCardEntity() {
//		super();
//	}
//
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="cardTypeId",referencedColumnName="cardTypeId")
//	public CardTypeEntity getCardType() {
//		return cardType;
//	}
//
//	public void setCardType(CardTypeEntity cardType) {
//		this.cardType = cardType;
//	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public int getGracePeriodInDay() {
		return gracePeriodInDay;
	}

	public void setGracePeriodInDay(int gracePeriodInDay) {
		this.gracePeriodInDay = gracePeriodInDay;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public double getStatementBalance() {
		return statementBalance;
	}

	public void setStatementBalance(double statementBalance) {
		this.statementBalance = statementBalance;
	}

	public Date getStatementDueDate() {
		return statementDueDate;
	}

	public void setStatementDueDate(Date statementDueDate) {
		this.statementDueDate = statementDueDate;
	}


	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public double getMonthlyMinPayment() {
		return monthlyMinPayment;
	}

	public void setMonthlyMinPayment(double monthlyMinPayment) {
		this.monthlyMinPayment = monthlyMinPayment;
	}

	public double getCashBack() {
		return cashBack;
	}

	public void setCashBack(double cashBack) {
		this.cashBack = cashBack;
	}

	public double getFloorLimit() {
		return floorLimit;
	}

	public void setFloorLimit(double floorLimit) {
		this.floorLimit = floorLimit;
	}

	public long getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(long creditScore) {
		this.creditScore = creditScore;
	}

	public long getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(long rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	@Override
	public String toString() {
		return "CustomerCreditCardEntity [cardNumber=" + cardNumber + ", expDate=" + expDate + ", customer=" + customer
				+ ", network=" + network + ", gracePeriodInDay=" + gracePeriodInDay + ", currentBalance="
				+ currentBalance + ", statementBalance=" + statementBalance + ", statementDueDate=" + statementDueDate
				+ ", apr=" + apr + ", monthlyMinPayment=" + monthlyMinPayment + ", cashBack=" + cashBack
				+ ", floorLimit=" + floorLimit + ", creditScore=" + creditScore + ", rewardPoint=" + rewardPoint + "]";
	}
	
}
