package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * Klasa ConnectionProvider daje nam mo�liwo�� pobrania �r�d�a danych(DataSource) skonfigurowanego w pliku context.xml oraz pojedynczego po��czenia. 
 * W aplikacji wykorzystywali b�dziemy Spring JDBC, wi�c raczej pierwsza z opcji b�dzie nam bardziej przydatna.
 */

public class ConnectionProvider {

	private static DataSource dataSource;
	
	
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
	
	
	public static DataSource getDataSource() {
		if(dataSource == null ) {
			try{
				Context conn = new InitialContext();
				Context envContext = (Context)conn.lookup("java:comp/env");
				DataSource ds = (DataSource)envContext.lookup("jdbc/week");
				dataSource = ds;
			}catch(NamingException e) {
				e.printStackTrace();
			}
		} return dataSource;
	}
}
