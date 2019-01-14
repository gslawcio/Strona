package dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.User;
import util.ConnectionProvider;

public class UserDAOImpl implements UserDAO{

	// nazwy parametr�w poprzedzonych dwukropkiem, poniewa� wykorzystujemy obiekt klasy ze Spring JDBC o nazwie NamedParameterJdbcTemplate
	private final static String CREATE_USER ="INSERT INTO user(username,email,password,is_active) VALUES (:username, :email, :password, :active);";
	private final static String READ_USER = "SELECT user_id,username,email,password,is_active FROM user WHERE user_id=:id;";
	private final static String READ_USER_BY_USERNAME = "SELECT user_id,username,email, password, is_active FROM user WHERE username=:username; ";
	
	
	private NamedParameterJdbcTemplate template;
	
	public UserDAOImpl() {
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}
	
	
	@Override
	public User create(User user) {
		User resultUser = new User(user);
		KeyHolder holder = new GeneratedKeyHolder();		//interface, pozwalaj� przechwyci� klucz automatycznie wygenerowany przez silnik bazy danych przy dodawaniu konkretnego rekordu.
															//Klucz jest nam potrzebny, poniewa� w wyniku metody create() chcemy zwr�ci� obiekt uzupe�niony o to pole, 
															//co b�dzie jednocze�nie potwierdzeniem, �e odpowiedni rekord w bazie danych zosta� utworzony.
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
		int update = template.update(CREATE_USER, paramSource, holder); //Po wywo�aniu metody template.update(CREATE_USER, paramSource, holder) sprawdzamy ile wierszy w bazie danych zosta�o zmodyfikowanych(b�dzie to 1 albo 0)
		if(update >0) {
			resultUser.setId(holder.getKey().longValue());
			setPrivigiles(resultUser);
		}
		return resultUser;
	}

	private void setPrivigiles(User user) {
		final String userRoleQuerty = "INSERT INTO user_role(username) VALUES (:username);";
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
		template.update(userRoleQuerty, parameterSource);
	}

	/*
	 * metoda read() wyszukuje u�ytkownika na podstawie jego id, czyli klucza g��wnego tabeli user. 
	 * Tworzymy wi�c odpowiedni obiekt MapSqlParameterSource z przypisaniem warto�ci primaryKey do id, 
	 * kt�re nast�pnie wstawione zostan� do zapytania READ_USER. Do utworzenia wynikowego obiektu potrzebna jest nam pomocnicza klasa wewn�trzna UserRowMapper
	 *  implementuj�ca interfejs RowMapper. Posiada on tylko jedn� metod� mapRow(), w kt�rej tworzymy obiekt User i ustawiamy jego kolejne pola poprzez settery. 
	 *  Warto�ci pobieramy z obiektu ResultSet, w kt�rym do odpowiednich kolumn mo�emy odwo�ywa� si� po ich nazwach lub indeksach.
	 *
	 *W metodzie read() wynik metody queryForObject() przypisujemy do obiektu User i go zwracamy.
	 *
	 *Metoda getUserByUsername() jest bardzo podobna, jednak tym razem jako warunek w zapytaniu wykorzystujemy unikaln� nazw� u�ytkownika(username), a nie jego id.
	 */

	@Override
	public User read(Long primaryKey) {
		User resultUser = null;
		SqlParameterSource pramSource = new MapSqlParameterSource("id",primaryKey);
		resultUser = template.queryForObject(READ_USER, pramSource, new UserRowMapper());
		return resultUser;
	}

	@Override
	public boolean update(User updateObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		User resultUser = null;
		SqlParameterSource parameterSource = new MapSqlParameterSource("username", username);
		resultUser = template.queryForObject(READ_USER_BY_USERNAME, parameterSource, new UserRowMapper());
		return resultUser;
	}

}
