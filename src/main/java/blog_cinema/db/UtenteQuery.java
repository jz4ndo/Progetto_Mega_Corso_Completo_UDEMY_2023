package blog_cinema.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import blog_cinema.dominio.Utente;
import blog_cinema.dominio.Utente.TipoUtente;

public class UtenteQuery extends ConnectDB{
	
	public static Utente get_utente(int id) {
		Utente utente = null;
		try {
			String query = String.join("", "select * from utente where id=",Integer.toString(id));
			
			Container container = crea_connessione();
			
			ResultSet res = container.getSt().executeQuery(query);
			
			if(res.next()) {
				utente = get_data_from_res(res);
			}
			
			chiudi_connessione(container);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return utente;
	}
	
	public static Utente get_utente(String username, String password) {
		Utente utente = null;
		try {
			String query = String.join("", "select * from utente where username=",username,"and password=",password);
			
			Container container = crea_connessione();
			
			ResultSet res = container.getSt().executeQuery(query);
			
			if(res.next()) {
				utente = get_data_from_res(res);
			}
			
			chiudi_connessione(container);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return utente;
	}
	
	public static boolean rimuovi_utente(Utente utente) {
		try {
			String query = String.join("", "delete from utente where id=",Integer.toString(utente.getId()));
			Container container = crea_connessione();
			container.getSt().execute(query);
			
			chiudi_connessione(container);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static Utente get_data_from_res(ResultSet res) throws SQLException {
		
		Utente utente = new Utente();
		utente.setId(res.getInt("id"));
		utente.setNome(res.getString("nome"));
		utente.setCognome(res.getString("cognome"));
		utente.setCognome(res.getString("username"));
		utente.setCognome(res.getString("email"));
		utente.setCognome(res.getString("password"));
		utente.setTipo(TipoUtente.values()[res.getInt("tipoutente")-1]);
		
		return utente;
	}
}
