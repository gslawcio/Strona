package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Discovery;
import service.DiscoveryService;

//Mapowanie w adnotacji @WebServlet ustawiliœmy na pusty String. Takie rozwi¹zanie sprawi, ¿e kontroler ten bêdzie obs³ugiwa³ stronê g³ówn¹ aplikacji.
@WebServlet("")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		saveDiscoveriesInRequest(request);
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
		
	}

	private void saveDiscoveriesInRequest(HttpServletRequest request) {

		DiscoveryService discoveryService = new DiscoveryService();
		
					//	Sotrowanie po dacie	
	List<Discovery> allDiscoveries = 
		discoveryService.getAllDiscoveries((d1, d2) -> d2.getTimestamp().compareTo(d1.getTimestamp()));
		
//		List<Discovery> allDiscoveries = discoveryService.getAllDiscoveries(new Comparator<Discovery>() {
//
//			@Override
//			public int compare(Discovery d1, Discovery d2) {
//				int d1Vote = d1.getUpVote() - d1.getDownVote();
//				int d2Vote = d2.getUpVote() - d2.getDownVote();
//				if(d1Vote < d2Vote) {
//					return 1;
//				} else if(d1Vote > d2Vote ) {
//					return -1;
//				}
//				return 0;
//			}
//				});
		request.setAttribute("discoveries", allDiscoveries);		
	}

}
