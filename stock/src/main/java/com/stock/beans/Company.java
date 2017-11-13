package com.stock.beans;

public class Company {
	private int cmp_id;
	private String cmp_name;
	private double share_value;

	public int getCmp_id() {
		return cmp_id;
	}

	public void setCmp_id(int cmp_id) {
		this.cmp_id = cmp_id;
	}

	public String getCmp_name() {
		return cmp_name;
	}

	public void setCmp_name(String cmp_name) {
		this.cmp_name = cmp_name;
		//cmp_name.codePointAt(arg0);
	}

	public double getShare_value() {
		return share_value;
	}

	public void setShare_value(double share_value) {
		this.share_value = share_value;
	}

	@Override
	public String toString() {
		return "Company [cmp_id=" + cmp_id + ", cmp_name=" + cmp_name + ", share_value=" + share_value + "]";
	}

}
