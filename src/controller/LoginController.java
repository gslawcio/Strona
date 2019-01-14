package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			/*
			 * metoda request.getUserPrincipal(). Po zalogowaniu przez mechanizm Tomcata zwraca ona obiekt klasy Principal, 
			 * z kt�rego mo�emy odczyta� nazw� u�ytkownika oraz role u�ytkownika. 
			 * Z tego powodu w UserDAO przyda nam si� metoda zwracaj�ca obiekt reprezentuj�cy u�ytkownika z bazy danych na podstawie jego nazwy u�ytkownika. 
			 */
			
			
			if(request.getUserPrincipal() != null) {
				response.sendRedirect(request.getContextPath()+"/");
			}else {
				response.sendError(403);
			}
	}

}
