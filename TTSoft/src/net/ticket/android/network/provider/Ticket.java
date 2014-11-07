package net.ticket.android.network.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ticket {
	private long id;
	private long date;
    private long amount;
    private int statut;
    private int is_sync;
    private String gie;
    private String reference; 
    private String user;
    private int section;
    private String itineraire;
    private String ligne;
    private String bus;
    private String device;
    private String card;
    private String type;
    private String etat;
    
    public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
	private String generateDate;
    public String getDevice() {
		return device;
	}
    public void setDevice(String device) {
		this.device = device;
	}
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getGie() {
		return gie;
	}
	public void setGie(String gie) {
		this.gie = gie;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public String getItineraire() {
		return itineraire;
	}
	public void setItineraire(String itineraire) {
		this.itineraire = itineraire;
	}
	public String getLigne() {
		return ligne;
	}
	public void setLigne(String ligne) {
		this.ligne = ligne;
	}
	public String getBus() {
		return bus;
	}
	public void setBus(String bus) {
		this.bus = bus;
	}
	public int getIs_sync() {
		return is_sync;
	}
	public void setIs_sync(int is_sync) {
		this.is_sync = is_sync;
	}
	
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    @Override
    public String toString() {
   
		String BILL = "================================\n";
		BILL = BILL  + "==========COMPAGNIE  ATT=======\n";
		BILL = BILL + "================================\n";
		
		BILL = BILL+ gie+"\n";
		
		BILL = BILL  + "Bus:" + " " + bus+" == "+ "Ligne:" + " " + ligne+"\n";
		  
		BILL = BILL + "Trajet:" + " " + itineraire+"\n";
	
    	BILL = BILL + "Section:" + " " + section +" == "+ "Prix:" + " " + amount+" FCFA \n";;
    	
		BILL = BILL + "Ticket SN:" + " " + reference+ "\n";
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
		String newtime = "Date: "+ sdfDateTime.format(new Date(date*1000))+"\n";
		
	    return BILL+ newtime; 
	  }
	
    
    public String toString(String sectionnement, String adverse) {
    	   
		String BILL = "================================\n";
		BILL = BILL  + "==========COMPAGNIE  ATT=======\n";
		BILL = BILL + "================================\n";
		
		BILL = BILL+ gie+"\n";
		BILL = BILL  + "Bus:" + " " + bus+" == "+ "Ligne:" + " " + ligne+"\n";
		  
		BILL = BILL + "Trajet:" + " " + itineraire+"\n";
	
    	BILL = BILL + "Section:" + " " + section +" == "+ "Prix:" + " " + amount+" FCFA \n";;
    	BILL = BILL + "ZONE = " + " " + sectionnement+"\n";
	
		BILL = BILL + "SN:" + " " + reference+ "\n";
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
		String newtime = "Date: "+ sdfDateTime.format(new Date(date*1000))+"\n";
		BILL = BILL+ newtime;
		BILL = BILL + "================================\n";
		BILL = BILL + adverse;
		BILL = BILL + "\n";
		
		
	    return BILL; 
	  }
	
	
	
	
} 