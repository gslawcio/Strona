package service;

import dao.DAOFactory;
import dao.UserDAO;
import model.User;

public class UserService {
	
	//Na chwilê obecn¹ konto u¿ytkownika bêdzie domyœlnie aktywowane

	public void addUser(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setActive(true);
		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDao = factory.getUserDAO();
		userDao.create(user);
	}
	
	

	//metody getUserById() oraz getUserByUsername() to tylko obudowanie klasy DAO i ukrycie warstwy komunikacji z baz¹ danych.
	public User getUserById(long userId) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		UserDAO userDao = daoFactory.getUserDAO();
		User user = userDao.read(userId);
		return user;
	}
	public User getUserByUsername(String username) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		UserDAO userDao = daoFactory.getUserDAO();
		User user = userDao.getUserByUsername(username);
		return user;
	}
}
