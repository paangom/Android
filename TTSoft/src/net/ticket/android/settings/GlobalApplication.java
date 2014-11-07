package net.ticket.android.settings;


	import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

	import android.app.Application;
import android.util.Base64;
import android.util.Log;

	public class GlobalApplication extends Application {
		private String bus;
		private String sectionnement;
		private String chauffeur;
		private String gazoil_litrage;
		private String gazoil_unitprice;
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
		public String getDateDeb() {
			return dateDeb;
		}
		public void setDateDeb(String dateDeb) {
			this.dateDeb = dateDeb;
		}
		private boolean on_service;
		public static  String PHONEIMSI;
		public static  String PHONEIMEI;
		public static Boolean is_connect=false ;
		public static boolean is_bt_enabled=false ;
		public static boolean is_device_connect=false ;
		public static boolean bt_unsupported=false ;
		
	
		private ArrayList<String> sections;
		private ArrayList<String> lines ;
		private String section;
		public static Boolean etat=false;
		
		public String getSection() {
			return section;
		}
		public void setSection(String section) {
			this.section = section;
		}
		//public final static  String BASE_URL="http://54.186.149.87/api/v1/" ;
		public final static  String BASE_URL="http://ec2-54-186-149-87.us-west-2.compute.amazonaws.com/api/v1/" ;
		//public final static  String BASE_URL="http://10.0.2.2:8000/fr/api/v1/";
		public String getBus() {
			return bus;
		}
		public void setBus(String bus) {
			this.bus = bus;
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
		
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getOrganisation() {
			return organisation;
		}
		public void setOrganisation(String organisation) {
			this.organisation = organisation;
		}
		
		public  Map<String, String> createBasicAuthHeader(String username, String password) {
		        Map<String, String> headerMap = new HashMap<String, String>();

		        String credentials = username + ":" + password;
		        String base64EncodedCredentials =
		                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
		        Log.i("GLOBAL",base64EncodedCredentials);
		        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);

		        return headerMap;
		    }
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public ArrayList<String> getSections() {
			return sections;
		}
		public void setSections(ArrayList<String> sections) {
			this.sections = sections;
		}
		public String getSectionnement() {
			return sectionnement;
		}
		public void setSectionnement(String sectionnement) {
			this.sectionnement = sectionnement;
		}
		public String getGazoil_litrage() {
			return gazoil_litrage;
		}
		public void setGazoil_litrage(String gazoil_litrage) {
			this.gazoil_litrage = gazoil_litrage;
		}
		public String getChauffeur() {
			return chauffeur;
		}
		public void setChauffeur(String chauffeur) {
			this.chauffeur = chauffeur;
		}
		public String getGazoil_unitprice() {
			return gazoil_unitprice;
		}
		public void setGazoil_unitprice(String gazoil_unitprice) {
			this.gazoil_unitprice = gazoil_unitprice;
		}
		public String getAdverse() {
			return adverse;
		}
		public void setAdverse(String adverse) {
			this.adverse = adverse;
		}
		
		public void logout()
		{
			if (!this.on_service)
			{	this.first_road=null;
				this.back_road=null;
				this.itineraire=null;
				this.line=null;
				this.bus=null;
				this.adverse=null;
				this.gazoil_unitprice=null;
				this.chauffeur=null;
				this.gazoil_litrage=null;
				this.sectionnement=null;
				this.password=null;
				this.user=null;
				is_bt_enabled=false;
				is_connect=false;
				is_device_connect=false;
			}
			
		}
		public boolean isOn_service() {
			return on_service;
		}
		public void setOn_service(boolean on_service) {
			this.on_service = on_service;
		}
		public ArrayList<String> getLines() {
			return lines;
		}
		public void setLines(ArrayList<String> lines) {
			this.lines = lines;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
	}
