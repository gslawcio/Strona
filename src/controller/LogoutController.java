package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * Wylogowanie u¿ytkownika sprowadza siê do usuniêcia sesji danego u¿ytkownika przy pomocy metody invalidate() obiektu HttpSession. 
 * Po tej operacji przekierowujemy u¿ytkownika do strony g³ównej.
 */


@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/");
	}

}
