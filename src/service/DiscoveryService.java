package service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dao.DAOFactory;
import dao.DiscoveryDAO;
import model.Discovery;
import model.User;


/*
 * Klasa DiscoveryService jest opakowaniem klasy DAO. Odpowiada za utworzenie obiektu Discovery na podstawie podanych parametr�w(oraz u�ytkownika, 
 * kt�rego pobierzemy z atrybutu sesji, kt�ry b�dzie tam zapisany po uwierzytelnieniu). Dalszy schemat jest identyczny jak w przypadku klasy UserService
 *  - pobieramy instancj� fabryki DAO, nast�pnie implementacji obiektu DiscoveryDAOImpl i zapisujemy obiekt w bazie danych poprzez metod� create().
 */

public class DiscoveryService {

	public void addDiscovery(String name, String desc, String url, User user) {
		Discovery discovery = createDiscoveryObject(name,desc,url,user);
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		DiscoveryDAO discoveryDAO = daoFactory.getDiscoveryDAO();
		discoveryDAO.create(discovery);
	}

	private Discovery createDiscoveryObject(String name, String desc, String url, User user) {
		Discovery discovery = new Discovery();
		discovery.setName(name);
		discovery.setDescription(desc);
		discovery.setUrl(url);
		User copyUser = new User(user);
		discovery.setUser(copyUser);
		discovery.setTimestamp(new Timestamp(new Date().getTime()));
		return discovery;
	}
	
	
	/*
	 * Do klasy DiscoveryService dodali�my dwie przeci��one wersje metody getAllDiscoveries(), kt�re umo�liwiaj� albo pobranie listy wszystkich tre�ci, 
	 * albo ich dodatkowe posortowanie po dowolnym parametrze w zale�no�ci od implementacji przekazanego jako argument Comparatora.
	 *  Poniewa� Comparator jest interfejsem funkcyjnym to od Javy 8 mo�emy w to miejsce przekaza� nie tylko obiekt klasy Comparator utworzony 
	 *  np. poprzez anonimow� klas� wewn�trzn�, ale r�wnie� uproszczone wyra�enie lambda.
	 */
	
	public List<Discovery> getAllDiscoveries(){
		return getAllDiscoveries(null);
		
	}

	public List<Discovery> getAllDiscoveries(Comparator<Discovery> comparator) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		DiscoveryDAO discoveryDAO = daoFactory.getDiscoveryDAO();
		List<Discovery> discoveries = discoveryDAO.getAll();
		if(comparator != null && discoveries != null) {
			discoveries.sort(comparator);
		}
		return discoveries;
	}
	
	
	/*
	 * Dodali�my tu dwie metody getDiscoveryById(), kt�ra zwraca obiekt Discovery na podstawie przekazanego id(klucza g��wnego tabeli discovery) 
	 * oraz metod� update(), kt�ra pozwala zaktualizowa� dany rekord w bazie danych(wykorzystanie wcze�niej zaimplementowanej metody update z klasy DiscoveryDAOImpl).
	 */
	
	public Discovery getDiscoveryById(long discoveryId) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		DiscoveryDAO discoveryDAO = factory.getDiscoveryDAO();
		Discovery discovery = discoveryDAO.read(discoveryId);
		return discovery;
	}
	
	public boolean updateDiscovery(Discovery discovery) {
		DAOFactory factoryDAO = DAOFactory.getDAOFactory();
		DiscoveryDAO discoveryDAO = factoryDAO.getDiscoveryDAO();
		boolean resut = discoveryDAO.update(discovery);
		return resut;
	}
	
	
}
