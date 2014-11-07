package net.ticket.android.network.provider;

import java.util.ArrayList;

public class Parametrage {

	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private String bus;
	private String chauffeur;
	private String adverse;
	private String line;
	private String itineraire;
	private String first_road;
	private String back_road;
	private String organisation;
	private String user;
	private String password;
	private String token;
	private String dateDeb;
	public static  String PHONEIMSI;
	public static  String PHONEIMEI;
	private String sections;
	private String reference;
	
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSections() {
		return sections;
	}
	public void setSections(String sections) {
		this.sections = sections;
	}
	
	public String getBus() {
		return bus;
	}
	public void setBus(String bus) {
		this.bus = bus;
	}
	public String getChauffeur() {
		return chauffeur;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public String getAdverse() {
		return adverse;
	}
	public void setAdverse(String adverse) {
		this.adverse = adverse;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getItineraire() {
		return itineraire;
	}
	public void setItineraire(String itineraire) {
		this.itineraire = itineraire;
	}
	public String getFirst_road() {
		return first_road;
	}
	public void setFirst_road(String first_road) {
		this.first_road = first_road;
	}
	public String getBack_road() {
		return back_road;
	}
	public void setBack_road(String back_road) {
		this.back_road = back_road;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDateDeb() {
		return dateDeb;
	}
	public void setDateDeb(String dateDeb) {
		this.dateDeb = dateDeb;
	}
	
	
}
