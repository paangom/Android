package net.ticket.android.network.provider;

public class Rapport {

	String section;
	int tickets;
	int total;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getTickets() {
		return tickets;
	}
	public void setTickets(int tickets) {
		this.tickets = tickets;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	  public String toString() {
		if(section.length()==0)
			return "Nombres: " +tickets+ " Total vendu:"+ total+" FCFA"; 
		else 
			return "Section :"+section+ " Nombres:" +tickets+ " total:"+ total+" FCFA";  
	}

}
