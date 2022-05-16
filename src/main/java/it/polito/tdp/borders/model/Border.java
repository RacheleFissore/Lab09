package it.polito.tdp.borders.model;

public class Border { // E' l'equivalente della classe coppia id di metro paris
	int codStato1;
	int codStato2;
	
	public Border(int codStato1, int codStato2) {
		super();
		this.codStato1 = codStato1;
		this.codStato2 = codStato2;
	}
	
	public int getCodStato1() {
		return codStato1;
	}
	public void setCodStato1(int codStato1) {
		this.codStato1 = codStato1;
	}
	public int getCodStato2() {
		return codStato2;
	}
	public void setCodStato2(int codStato2) {
		this.codStato2 = codStato2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codStato1;
		result = prime * result + codStato2;
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
		Border other = (Border) obj;
		if (codStato1 != other.codStato1)
			return false;
		if (codStato2 != other.codStato2)
			return false;
		return true;
	}
	
	
}
