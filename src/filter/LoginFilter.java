package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import model.User;
import service.UserService;


/*
 *Klasa sprawdzaj�ca czy u�ytkownik si� zalogowa� przed odwiedzeniem jakiejkolwiek strony, je�eli tak, to zapisze odpowiedni obiekt na poziomie sesji.
 *
 *Niestety zapisywanie u�ytkownika na poziomie sesji nie mo�emy obs�u�y� na poziomie kontrolera UserController, 
 *poniewa� istniej� inne strony(nie tylko /login), kt�re b�d� wymaga�y uwierzytelnienia 
 */
@WebFilter("/*")
public class LoginFilter implements Filter  {
	private static final long serialVersionUID = 1L;

	/*Filtr posiada mapowanie, kt�re sprawia, �e metoda doFilter() wykona si� przed przej�ciem do jakiejkolwiek strony.
	 * 
	 * 
	 * Sprawdzamy w niej bardzo prosty warunek i je�eli u�ytkownik si� zalogowa�(metoda getUserPrincipal() zwraca warto�� r�n� od null) 
	 * oraz jednocze�nie obiekt user na poziomie sesji nie istnieje, to go tam zapisujemy pobieraj�c u�ytkownika z bazy danych na podstawie znanej nam nazwy u�ytkownika. 
	 * Nast�pnie ��danie przekazywane jest dalej do adresu docelowego.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		if(httpServletRequest.getUserPrincipal() != null && httpServletRequest.getSession().getAttribute("user") == null) {
			saveUserInSesion(httpServletRequest);
		}
		chain.doFilter(request, response);
	}
	
	private void saveUserInSesion(HttpServletRequest request) {
		UserService userService = new UserService();
		String username = request.getUserPrincipal().getName();
		User userByUsername = userService.getUserByUsername(username);
		request.getSession(true).setAttribute("user", userByUsername);
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
