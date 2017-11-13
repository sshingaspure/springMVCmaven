package com.stock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stock.beans.Customer;

public class CustomerMapper implements RowMapper<Customer> {

	public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Customer customer=new Customer();
		customer.setUsername(resultSet.getString("cust_uname"));
		customer.setBalance(resultSet.getDouble("balance"));
		customer.setCustomerName(resultSet.getString("cust_name"));
		customer.setCustomerId(resultSet.getInt("cust_id"));
		return customer;
	}

}
