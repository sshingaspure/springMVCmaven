package com.stock.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.stock.beans.Company;
import com.stock.beans.Customer;
import com.stock.beans.Shares;

public class StockJDBCTemplate implements StockDAO {

	private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;


	public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplateObject = jdbcTemplateObject;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public Customer checkLogin(String userName, String password) {
		String SQL = "select * from customer where cust_uname = ? and password= ?";
		Customer customer;
		try {
			customer = jdbcTemplateObject
					.queryForObject(SQL, new Object[] { userName, password }, new CustomerMapper());

			return customer;

		} catch (Exception e) {
			return null;
		}
	}

	public List<Customer> listCustomers() {

		return null;
	}

	public List<Shares> listShares(int cust_id) {
		String sql = "select cm.cmp_name, sh.shares from customer cc inner join shares sh on cc.cust_id=sh.cust_id "
				+ "inner join company cm on cm.cmp_id=sh.cmp_id where sh.cust_id=?";
		/*
		 * List<Map<String, Object>> listShares=null; try { listShares =
		 * jdbcTemplateObject.queryForList(sql,new Object[]{1},new
		 * SharesMapper()); System.out.println("Print result of select query");
		 * for (Map<String, Object> map : listShares) { for (Entry<String,
		 * Object> entry : map.entrySet()) {
		 * System.out.println(entry.getKey()+"     "
		 * +entry.getValue().toString()); } } return listShares; } catch
		 * (Exception e) {
		 * System.out.println("Exception has occured: "+e.getMessage()); return
		 * listShares; }
		 */

		List<Shares> list = null;
		try {
			list = jdbcTemplateObject.query(sql, new Object[] { cust_id }, new SharesMapper());
			for (Shares shares : list) {
				shares.toString();
			}

			return list;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return list;
		}

	}

	public List<Company> listCompanies() {
		String sql = "select * from company";

		List<Company> list = null;
		try {
			list = jdbcTemplateObject.query(sql, new BeanPropertyRowMapper<Company>(Company.class));
			return list;
			
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return list;
		}

	}

	public int getShareNumbers(String cust_id, String cmp_id) {

		String sql = "select shares from shares where cust_id=? and cmp_id=?;";
		int number = 0;

		try {
			number = jdbcTemplateObject.queryForInt(sql, new Object[] { cust_id, cmp_id });

		} catch (Exception e) {
			System.out.println("Error occured while getting number of shares: " + e.getMessage());
		}
		return number;
	}

	public Company getCompany(int cmpID) {
		
		String sql="select * from company where cmp_id=?";
		Company company=null;
		try {
			company=(Company) jdbcTemplateObject.queryForObject(sql, new Object[]{cmpID},new BeanPropertyRowMapper<Company>(Company.class));
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}
		return company;
	}

	public boolean buyShres(int customerId, int cmp_id, int numOfSharestoBuy) {
		//String
		boolean success=false;
		String sql;
		TransactionDefinition definition=new DefaultTransactionDefinition();
		TransactionStatus status=transactionManager.getTransaction(definition);
		try {
			sql="select balance from customer where cust_id=?";
			double balance=jdbcTemplateObject.queryForObject(sql,new Object[]{customerId},Double.class);
			sql="select share_value from company where cmp_id=?";
			double shareValue=jdbcTemplateObject.queryForObject(sql, new Object[]{cmp_id}, Double.class);
			double amountRequired=shareValue*numOfSharestoBuy;
			if (balance>=amountRequired) {
				sql="update customer set balance = balance-? where cust_id=?";
				jdbcTemplateObject.update(sql, new Object[]{amountRequired,customerId});
				sql="select shares from shares where cust_id=? and cmp_id=?";
				Integer shares=jdbcTemplateObject.queryForObject(sql, new Object[]{customerId,cmp_id},Integer.class);
				if (shares==null) {
					sql="insert into shares values(?,?,?)";
					jdbcTemplateObject.update(sql, customerId,cmp_id,numOfSharestoBuy);
				}else {
					sql="update shares set shares=shares+? where cust_id=? and cmp_id=?";
					jdbcTemplateObject.update(sql, numOfSharestoBuy,customerId,cmp_id);
				}
			}
			
			transactionManager.commit(status);
			success=true;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			transactionManager.rollback(status);
			return success;
		}
		
		System.out.println("updated the share successfully");
		return success;
	}

	public Customer getCustomer(int cust_id){
		String sql="select * from customer where cust_id=?";
		Customer customer=null;
		try {
			customer=(Customer) jdbcTemplateObject.queryForObject(sql, new Object[]{cust_id},new CustomerMapper());
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}
		System.out.println(customer.toString());
		return customer;
	}

	public boolean sellShres(int customerId, int cmp_id, int numOfSharestoSell) {
		boolean success=false;
		
		String sql;
		TransactionDefinition definition=new DefaultTransactionDefinition();
		TransactionStatus status=transactionManager.getTransaction(definition);
		
		try {
			sql="select share_value from company where cmp_id=?";
			double share_value=jdbcTemplateObject.queryForObject(sql,new Object[]{cmp_id},Double.class);
			double amountGain=numOfSharestoSell*share_value;
			
			sql="update shares set shares=shares-? where cust_id=? and cmp_id=?";
			jdbcTemplateObject.update(sql, numOfSharestoSell,customerId,cmp_id);
			sql="update customer set balance = balance+? where cust_id=?";
			jdbcTemplateObject.update(sql, new Object[]{amountGain,customerId});
			transactionManager.commit(status);
			success=true;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			transactionManager.rollback(status);
			return success;
		}
		System.out.println("Sold the share successfully");
		return success;
	}
}
