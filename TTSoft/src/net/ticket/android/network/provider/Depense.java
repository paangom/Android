package net.ticket.android.network.provider;



public class Depense {
	private long divers;
	private long stationnement;
	private long depannage;
	private long gazoil;
	private long ration;
	private long lavage;
	private long prime;
	private long regulateur;
	private long gardiennage;
	private long mecanicien;
	private String reference;
	public long getRation() {
		return ration;
	}
	public void setRation(long ration) {
		this.ration = ration;
	}
	public long getLavage() {
		return lavage;
	}
	public void setLavage(long lavage) {
		this.lavage = lavage;
	}
	public long getPrime() {
		return prime;
	}
	public void setPrime(long prime) {
		this.prime = prime;
	}
	public long getRegulateur() {
		return regulateur;
	}
	public void setRegulateur(long regulateur) {
		this.regulateur = regulateur;
	}
	public long getGardiennage() {
		return gardiennage;
	}
	public void setGardiennage(long gardiennage) {
		this.gardiennage = gardiennage;
	}
	public long getMecanicien() {
		return mecanicien;
	}
	public void setMecanicien(long mecanicien) {
		this.mecanicien = mecanicien;
	}
	private long id;
	private int sync;
	private long date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDivers() {
		return divers;
	}
	public void setDivers(long divers) {
		this.divers = divers;
	}
	public long getStationnement() {
		return stationnement;
	}
	public void setStationnement(long stationnement) {
		this.stationnement = stationnement;
	}
	public long getDepannage() {
		return depannage;
	}
	public void setDepannage(long depannage) {
		this.depannage = depannage;
	}
	public long getGazoil() {
		return gazoil;
	}
	public void setGazoil(long gazoil) {
		this.gazoil = gazoil;
	}

	public long total()
	{
		
		return stationnement+divers+depannage+gazoil+lavage+ration+gardiennage+prime+regulateur;
	}
	
	@Override
	public String toString() {
			//SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
			//String newtime = "Date: "+ sdfDateTime.format(new Date(date))+"\n";
			String BILL = "";
			BILL = "";
	    	BILL = BILL + "Ration : " + " " + ration+" FCFA \n";
			BILL = BILL + "Gardiennage : " + " " + gardiennage+" FCFA \n";
			BILL = BILL + "Regulateur : " + " " + regulateur+" FCFA \n";
			BILL = BILL + "Gazoil : " + " " + gazoil+" FCFA \n";
			BILL = BILL + "Lavage : " + " " + lavage+" FCFA \n";
			BILL = BILL + "Stationnement : " + " " + stationnement+" FCFA \n";
			BILL = BILL + "Prime : " + " " + prime+" FCFA \n";
			BILL = BILL + "Depannage : " + " " + depannage+" FCFA \n";
			BILL = BILL + "Divers : " + " " + divers+" FCFA \n";
			BILL = BILL + "\n";
			BILL = BILL + "Total depenses : " + " " + total()+" FCFA \n";
			//BILL = BILL + newtime;
			BILL = BILL+ "\n";
		    return BILL; 
		  }
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getSync() {
		return sync;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}


}
