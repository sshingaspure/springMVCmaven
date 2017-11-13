package com.stock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stock.service.StockService;

@Controller
@Scope(value = "session")
public class StockController {

	@Autowired
	private StockService stockService;

	@RequestMapping("/login")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) {
		return stockService.loginService(request);
	}

	@RequestMapping(value = {"/","/index"})
	public String printHello(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "/viewStock", method = RequestMethod.GET)
	public ModelAndView viewStock(ModelMap model) {
		return stockService.viewStockService();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return stockService.logoutService();
	}

	@RequestMapping(value = "/viewCompanies", method = RequestMethod.GET)
	public ModelAndView viewCompanies(ModelMap model) {
		return stockService.viewCompaniesService();
	}

	@RequestMapping(value = "/buySellStock", method = RequestMethod.GET)
	public ModelAndView buySellStock(String message) {
		return stockService.buySellStockService(message);
	}

	@RequestMapping(value = "/getNumberOfShares", method = RequestMethod.GET)
	public @ResponseBody String getNumberOfShares(@RequestParam String custid, @RequestParam String cmpid) {
		return stockService.getNumberOfSharesService(custid, cmpid);
	}

	@RequestMapping("/buyStocksForCompany")
	public ModelAndView buyStocksForCompany(HttpServletRequest request, HttpServletResponse res) {
		return stockService.buyStocksForCompanyService(request);
	}

	@RequestMapping("/sellStocksForCompany")
	public ModelAndView sellStocksForCompany(HttpServletRequest request, HttpServletResponse res) {
		return stockService.sellStocksForCompanyService(request);
	}

	}
