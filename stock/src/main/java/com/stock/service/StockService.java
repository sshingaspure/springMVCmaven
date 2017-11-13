package com.stock.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.stock.beans.*;
import com.stock.dao.StockJDBCTemplate;

public class StockService {

	@Autowired
	private StockJDBCTemplate jdbcTemplate;

	public void setJdbcTemplate(StockJDBCTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static HttpSession session;

	public ModelAndView loginService(HttpServletRequest request) {
		if (session != null) {
			ModelAndView modelAndView = getmodelAndViewForLoggedInUser();
			return modelAndView;
		}

		String userName = request.getParameter("name");
		String password = request.getParameter("password");

		Customer customer = jdbcTemplate.checkLogin(userName, password);
		if (customer != null) {
			ModelAndView modelAndView = getModelAndViewObject(request, customer);
			return modelAndView;
		} else {
			return new ModelAndView("index", "message", "Sorry, username or password error");
		}
	}

	private ModelAndView getModelAndViewObject(HttpServletRequest request, Customer customer) {
		String message = "HELLO " + customer.getCustomerName();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("firstPage");
		modelAndView.addObject("message", message);
		modelAndView.addObject("customer", customer);
		session = request.getSession();
		session.setAttribute("loggedUser", customer);
		return modelAndView;
	}

	private ModelAndView getmodelAndViewForLoggedInUser() {
		Customer customer = (Customer) session.getAttribute("loggedUser");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("firstPage");
		modelAndView.addObject("message", "Hello " + customer.getCustomerName());
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}

	public ModelAndView viewStockService() {
		Customer customer = getLoggedInUser();
		if (customer == null)
			return new ModelAndView("index", "message", "You must login to view this page");

		List<Shares> list = jdbcTemplate.listShares(customer.getCustomerId());

		if (list.size() != 0) {
			return new ModelAndView("viewStock", "listShares", list);
		} else {
			return new ModelAndView("viewStock", "message", "No shares information is present");
		}
	}

	public String logoutService() {
		if (session != null) {
			session.invalidate();
			session = null;
		}
		return "redirect:index";
	}

	public ModelAndView viewCompaniesService() {
		List<Company> companies = jdbcTemplate.listCompanies();
		if (companies.size() != 0) {
			return new ModelAndView("viewCompany", "companyList", companies);
		} else {
			return new ModelAndView("viewCompany", "message", "No shares information is present");
		}
	}

	public ModelAndView buySellStockService(String message) {
		Customer customer = getLoggedInUser();
		if (customer == null)
			return new ModelAndView("index", "message", "You must login to view this page");

		customer = jdbcTemplate.getCustomer(customer.getCustomerId());
		session.setAttribute("loggedUser", customer);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("message", message);
		modelAndView.setViewName("trading");
		// System.out.println(customer.toString());

		modelAndView.addObject("companyList", jdbcTemplate.listCompanies());

		return modelAndView;
	}

	public ModelAndView buyStocksForCompanyService(HttpServletRequest request) {
		Customer customer = getLoggedInUser();
		if (customer == null)
			return new ModelAndView("index", "message", "You must login to view this page");

		Company company = getCompany(request);

		int numOfSharestoBuy = new Integer(request.getParameter("numofshare"));
		// System.out.println(company.toString());
		double shareValue = company.getShare_value();
		double balance = customer.getBalance();
		double requiredAmount = shareValue * numOfSharestoBuy;

		ModelAndView modelAndView = new ModelAndView(new RedirectView("buySellStock"));

		if (balance >= requiredAmount) {
			boolean success = jdbcTemplate.buyShres(customer.getCustomerId(), company.getCmp_id(), numOfSharestoBuy);
			// System.out.println("out put from jdbc: " + success);
			if (success) {
				modelAndView.addObject("message", "Successfully bought the shares");
			} else {
				modelAndView.addObject("message", "Error while transaction. Please try again");
			}
		} else {
			modelAndView.addObject("message", "Error while transaction. You do not have sufficent balance.");
		}

		return modelAndView;
	}

	private Company getCompany(HttpServletRequest request) {
		String companyString = request.getParameter("cmpname");
		System.out.println(companyString);
		int cmpID = new Integer(companyString.split(",")[0]);
		// System.out.println("cmpID: " + cmpID);
		Company company = jdbcTemplate.getCompany(cmpID);
		return company;
	}

	public String getNumberOfSharesService(String custid, String cmpid) {
		String cust_id = custid;
		String cmp_id = cmpid;

		int num = jdbcTemplate.getShareNumbers(cust_id, cmp_id);
		// System.out.println("Number of shares: " + num);
		return new String("" + num);
	}

	public ModelAndView sellStocksForCompanyService(HttpServletRequest request) {
		Customer customer = getLoggedInUser();
		if (customer == null)
			return new ModelAndView("index", "message", "You must login to view this page");

		Company company = getCompany(request);

		int numOfSharestoSell = new Integer(request.getParameter("numOfSharesToSell"));
		int numberOfSharesCustHave = jdbcTemplate.getShareNumbers(new String("" + customer.getCustomerId()),
				new String("" + company.getCmp_id()));

		ModelAndView modelAndView = new ModelAndView(new RedirectView("buySellStock"));

		if (numberOfSharesCustHave >= numOfSharestoSell) {
			boolean success = jdbcTemplate.sellShres(customer.getCustomerId(), company.getCmp_id(), numOfSharestoSell);
			if (success) {
				modelAndView.addObject("message", "Successfully Sold the shares");
			} else {
				modelAndView.addObject("message", "Error while transaction. Please try again");
			}
		} else {
			modelAndView.addObject("message", "Number of shares entered is less than what you have. You have only "
					+ numberOfSharesCustHave + " shares. ");
		}

		return modelAndView;
	}

	private Customer getLoggedInUser() {
		Customer customer;
		if (session == null)
			return null;
		customer = (Customer) session.getAttribute("loggedUser");
		return customer;
	}

}
