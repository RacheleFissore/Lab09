package it.polito.tdp.borders.model;

public class Country {
	String statoAbb;
	int codice;
	String nomeStato;
	
	public Country(String statoAbb, int codice, String nomeStato) {
		super();
		this.statoAbb = statoAbb;
		this.codice = codice;
		this.nomeStato = nomeStato;
	}
	
	public String getStatoAbb() {
		return statoAbb;
	}
	public void setStatoAbb(String statoAbb) {
		this.statoAbb = statoAbb;
	}
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getNomeStato() {
		return nomeStato;
	}
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codice;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (codice != other.codice)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeStato;
	}
	
}
