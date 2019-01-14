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
 * Klasa DiscoveryService jest opakowaniem klasy DAO. Odpowiada za utworzenie obiektu Discovery na podstawie podanych parametrów(oraz u¿ytkownika, 
 * którego pobierzemy z atrybutu sesji, który bêdzie tam zapisany po uwierzytelnieniu). Dalszy schemat jest identyczny jak w przypadku klasy UserService
 *  - pobieramy instancjê fabryki DAO, nastêpnie implementacji obiektu DiscoveryDAOImpl i zapisujemy obiekt w bazie danych poprzez metodê create().
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
	 * Do klasy DiscoveryService dodaliœmy dwie przeci¹¿one wersje metody getAllDiscoveries(), które umo¿liwiaj¹ albo pobranie listy wszystkich treœci, 
	 * albo ich dodatkowe posortowanie po dowolnym parametrze w zale¿noœci od implementacji przekazanego jako argument Comparatora.
	 *  Poniewa¿ Comparator jest interfejsem funkcyjnym to od Javy 8 mo¿emy w to miejsce przekazaæ nie tylko obiekt klasy Comparator utworzony 
	 *  np. poprzez anonimow¹ klasê wewnêtrzn¹, ale równie¿ uproszczone wyra¿enie lambda.
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
	 * Dodaliœmy tu dwie metody getDiscoveryById(), która zwraca obiekt Discovery na podstawie przekazanego id(klucza g³ównego tabeli discovery) 
	 * oraz metodê update(), która pozwala zaktualizowaæ dany rekord w bazie danych(wykorzystanie wczeœniej zaimplementowanej metody update z klasy DiscoveryDAOImpl).
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
