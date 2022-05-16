package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getString("StateAbb"),  rs.getInt("ccode"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) { // Mi restituisce le coppie degli stati confinanti
		// Essendo un grafo non orientato se ho l'arco che va da Italia a Francia allora in automatico avrò anche quello da Francia a Italia proprio
		// perchè non è orientato e quindi non devo mettere entrambi gli archi ma basta metterne solo uno. Per fare questo posso duplicare la tabella di
		// contiguity e fare una join tra le chiave primarie e poi prendo solo la prima metà della tabella. Quindi in questo modo avrò nel risultato della
		// query state1no = 200 e state2no = 250 avrò anche state1no = 250 e state1no = 200 che è l'arco in verso opposto, quindi questo non devo prenderlo
		// e per evitare di farlo prendo solo la parte di tabella in cui state1no < state2no
		String sqlString = "SELECT DISTINCT c1.state1no, c1.state2no "
				+ "FROM contiguity c1, contiguity c2 "
				+ "WHERE c1.year <= ? "
				+ "AND c1.conttype = 1 "
				+ "AND c1.state1no = c2.state1no "
				+ "AND c1.state2no = c2.state2no "
				+ "AND c1.state1no < c2.state2no";
		
		Connection connection = ConnectDB.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sqlString);
			statement.setInt(1, anno);
			ResultSet resultSet = statement.executeQuery();
			List<Border> results = new ArrayList<Border>();
			
			while (resultSet.next()) {
				Border coppiaId = new Border(resultSet.getInt("state1no"), resultSet.getInt("state2no"));
				results.add(coppiaId);
			}
			
			connection.close();
			return results; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
