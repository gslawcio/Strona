package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.DiscoveryService;


@WebServlet("/add")
public class DiscoveryAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Je�eli u�ytkownik odwo�uje si� do strony /add poprzez ��danie GET, to sprawdzamy, czy jest uwierzytelniony, czy te� nie. 
	 * Je�li tak, to wy�wietlamy mu stron� z formularzem dodawania nowej tre�ci(new.jsp) a w innym przypadku
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getUserPrincipal() != null) {
			request.getRequestDispatcher("WEB-INF/new.jsp").forward(request, response);
		}else
			response.sendError(403);
	}
	/*
	 * ustawiamy kodowanie znak�w na UTF-8 i pobieramy odpowiednie parametry ��dania, niezb�dne do dodania nowego znaleziska. 
	 * Tworzymy obiekt DiscoveryService i zapisujemy nowy rekord w bazie danych przez wywo�anie metody addDiscovery(). 
	 * Je�eli wszystko przebiegnie pomy�lnie, przekierowujemy u�ytkownika do strony g��wnej. 
	 * Podobnie jak przy ��daniu GET sprawdzamy, czy u�ytkownik na pewno jest zalogowany
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("inputName");
		String description = request.getParameter("inputDescription");
		String url = request.getParameter("inputUrl");
		User authenticatedUser = (User)request.getSession().getAttribute("user");
		if(request.getUserPrincipal() != null) {
			DiscoveryService discoveryService = new DiscoveryService();
			discoveryService.addDiscovery(name, description, url, authenticatedUser);
			response.sendRedirect(request.getContextPath()+"/");
		}else {
			response.sendError(403);
		}
	}

}
