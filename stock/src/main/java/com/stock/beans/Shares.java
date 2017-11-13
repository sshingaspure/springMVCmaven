package com.stock.beans;

public class Shares {

	private String cmp_name;
	private int share_num;

	public String getCmp_name() {
		return cmp_name;
	}

	public void setCmp_name(String cmp_name) {
		this.cmp_name = cmp_name;
	}

	public int getShare_num() {
		return share_num;
	}

	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}

	@Override
	public String toString() {
		return "Shares [cmp_name=" + cmp_name + ", share_num=" + share_num + "]";
	}
	
	
}
