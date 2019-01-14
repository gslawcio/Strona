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
	 * Je¿eli u¿ytkownik odwo³uje siê do strony /add poprzez ¿¹danie GET, to sprawdzamy, czy jest uwierzytelniony, czy te¿ nie. 
	 * Jeœli tak, to wyœwietlamy mu stronê z formularzem dodawania nowej treœci(new.jsp) a w innym przypadku
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getUserPrincipal() != null) {
			request.getRequestDispatcher("WEB-INF/new.jsp").forward(request, response);
		}else
			response.sendError(403);
	}
	/*
	 * ustawiamy kodowanie znaków na UTF-8 i pobieramy odpowiednie parametry ¿¹dania, niezbêdne do dodania nowego znaleziska. 
	 * Tworzymy obiekt DiscoveryService i zapisujemy nowy rekord w bazie danych przez wywo³anie metody addDiscovery(). 
	 * Je¿eli wszystko przebiegnie pomyœlnie, przekierowujemy u¿ytkownika do strony g³ównej. 
	 * Podobnie jak przy ¿¹daniu GET sprawdzamy, czy u¿ytkownik na pewno jest zalogowany
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
