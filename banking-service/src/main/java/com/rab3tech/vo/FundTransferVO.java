package com.rab3tech.vo;

public class FundTransferVO {
	private String fromAccount;
	private String toAccount;
	private double amount;
	private String remarks;
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "FundTransferVO [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount
				+ ", remarks=" + remarks + "]";
	}

}
