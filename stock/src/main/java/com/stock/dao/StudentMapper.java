package com.stock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stock.beans.Customer;

public class StudentMapper implements RowMapper<Customer> {

	public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Customer customer=new Customer();
		customer.setUsername(resultSet.getString("userName"));
		customer.setBalance(resultSet.getDouble("balance"));
		customer.setCustomerName(resultSet.getString("name"));
		customer.setCustomerId(resultSet.getInt("id"));
		return customer;
	}


}
