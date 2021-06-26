package com.sapient.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SQFlow.sql.dao.UserDAO;
import com.SQFlow.sql.entity.User;

import lombok.extern.slf4j.Slf4j;

@WebServlet("/web-content/html/registration")
@Slf4j
public class RegistrationController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDAO userController = new UserDAO();
		User user = new User();
		String userName = req.getParameter("user-name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String uid = userController.crypt(email);
		user.setEmail(email);
		user.setUserId(uid);
		user.setUserName(userName);
		user.setGender(gender);
		user.setPassword(password);
		resp.setContentType("text/html");
		if (userController.addUser(user)) {
			req.getRequestDispatcher("/home-page.html").forward(req, resp);
		} else {
			resp.getWriter().println("<p style=\"color:red\">*email id already exist</p>");
			userController = null;
			user = null;
			req.getRequestDispatcher("/web-content/html/registration.jsp").include(req, resp);
		}
	}
}
