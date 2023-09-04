package blog_cinema.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blog_cinema.db.GenereQuery;
import blog_cinema.db.RecensioneQuery;
import blog_cinema.dominio.Genere;
import blog_cinema.dominio.Recensione;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// verifica se esiste una sessione gi� creata
		// se no: reindirizzo l'utente in login.html
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("loggedIn")==null || !session.getAttribute("loggedIn").equals(true)) {
			response.sendRedirect("login.html");
			return;
		}
				
		// crea mappa che associa genere->lista recensioni
		Map<Genere, ArrayList<Recensione>> map = new HashMap<Genere, ArrayList<Recensione>>();
		
		// prendi la lista generi da GenereFactory
		ArrayList<Genere> lista_generi = GenereQuery.get_generi();
		
		// prendi le recensioni per ogni genere
		for(Genere genere : lista_generi) {
			try {
				map.put(genere, RecensioneQuery.get_recensioni(genere));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// salva la map nella request
		request.setAttribute("map", map);
		
		// restituisci contenuto generato main.jsp
		request.setAttribute("pagina_attuale", "main");
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
