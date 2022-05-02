package controller;

import dao.AdminDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.CustomerModel;

public class Controller {
	
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
		String username = ctx.pathParam("username");
		CustomerModel user = Customer.get(username);
		ctx.json(user);
	};
	
	public Handler treefiddy = ctx -> {
		CustomerModel user = ctx.bodyAsClass(CustomerModel.class);
		Customer.create(user);
		Customer.getAccounts();
		
		ctx.status(201);
	};
	
	public Handler smite = ctx -> {
		CustomerModel user = ctx.bodyAsClass(CustomerModel.class);
		Customer.create(user);
		Admin.login("God", "Love");
		Admin.cancel(user.username);
		
		ctx.status(200);
	};

}
