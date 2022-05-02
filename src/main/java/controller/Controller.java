package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.AdminDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.CustomerModel;

public class Controller {
	private static final Logger logger = LogManager.getLogger(Controller.class);
	
	CustomerDAO Customer = new CustomerDAO();
	EmployeeDAO Employee = new EmployeeDAO();
	AdminDAO Admin = new AdminDAO();
	
	String cURL = "/users/{username}";
	String eURL = "/employee/{username}";
	String aURL = "/admin/{username}";

	
	public Controller(Javalin app) {
		app.get(cURL, getAccount);
		app.post(cURL, treefiddy);
		app.delete(cURL, smite);
	}
	
	
	public Handler getAccount = ctx -> {
		logger.warn("GET attempted.");
		String username = ctx.pathParam("username");
		CustomerModel user = Customer.get(username);
		ctx.json(user);
	};
	
	public Handler treefiddy = ctx -> {
		logger.warn("POST attempted.");
		String username = ctx.pathParam("username");
		CustomerModel user = Customer.get(username);
		Customer.create(user);
		Customer.getAccounts();
		
		ctx.status(201);
	};
	
	public Handler smite = ctx -> {
		logger.warn("DELETE attempted.");
		CustomerModel user = ctx.bodyAsClass(CustomerModel.class);
		Admin.login("God", "Love");
		Admin.cancel(user.username);
		
		ctx.status(200);
	};

}
