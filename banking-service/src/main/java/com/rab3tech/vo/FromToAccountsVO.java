package com.rab3tech.vo;

import java.util.List;

public class FromToAccountsVO {
	private String fromAccount;

	@Override
	public String toString() {
		return "FromToAccountsVO [fromAccount=" + fromAccount + ", toAccount=" + toAccount + "]";
	}

	public List<String> getToAccount() {
		return toAccount;
	}

	public void setToAccount(List<String> toAccount) {
		this.toAccount = toAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	private List<String> toAccount;

	public String getFromAccount() {
		return fromAccount;
	}
}
