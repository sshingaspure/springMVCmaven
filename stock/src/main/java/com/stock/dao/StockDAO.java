package com.stock.dao;

import java.util.List;

import com.stock.beans.Customer;

public interface StockDAO {

	//public void setDataSource(DataSource ds);
	
	public Customer checkLogin(String userName,String password);
	
	public List<Customer> listCustomers();
	
}
