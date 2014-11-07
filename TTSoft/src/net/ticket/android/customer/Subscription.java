package net.ticket.android.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Subscription {
	private String type;
	private String name;
	private String expireDate;
	private Customer customer;
	ArrayList<String> lines = new ArrayList<String>();
	private boolean available;
	
	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Subscription(String type, String name, String expireDate,
			ArrayList<String> lines, boolean available) {
		super();
		this.type = type;
		this.name = name;
		this.expireDate = expireDate;
		this.lines = lines;
		this.available = available;
	}
	
	public Subscription(JSONObject object) throws JSONException {
		super();
		this.expireDate = object.getString("expire");
		this.customer = new Customer(object.getJSONObject("customer"));
		JSONObject plan =  object.getJSONObject("plan");
		this.name = plan.getString("name");
		this.available = plan.getBoolean("available");
		JSONObject _type =  plan.getJSONObject("type");
		this.type = _type.getString("name");
		JSONArray _lines =  object.getJSONArray("lines");
		//this.lines = lines;
		lines = new ArrayList<String>();
  	    for (int i = 0; i < _lines.length(); i++) {
  	         Object oject = _lines.get(i);
  	        lines.add((String)oject);
  	      }
		
		
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public ArrayList<String> getLines() {
		return lines;
	}
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
    
	private boolean isValideDate(String str_date) throws ParseException
	{
		Date today = new Date();
		Date date;  
        System.out.println("Today is : " + today);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = (Date)dateFormat.parse(str_date);
        System.out.println("Today in yyyy-MM-dd format : " + date.toString());
        if (date.after(today))
        	return true;
        else 
        	return false;
       
		
	}
	public boolean isSubcriptionIsValid(String line) throws ParseException
	{   
        
		if(this.customer.isValidIsValid() &&this.available && this.isValideDate(this.expireDate) &&this.containsLine(line))
        	return true;
        else return false;
		
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	private boolean containsLine(String line)
	{
		if (lines.size()>0)
			{ if(lines.contains(line))
			   return true;
			  else return false;
			}
		else return true;
	}
	private String subcribeLines()
	{
		if (lines.size()>0)
		{ 
		   return "Lignes :" + lines.toString() +"\n";
		
		}
	else return "Toutes les lignes \n";
	}
	public String toString() {
		String msg = "";
		msg = msg+ "=========Info Client=======\n";
		msg = msg+ customer.toString();
		msg = msg+ "=========Info Abonnement=======\n";
		msg = msg+ "Type abonnement:" + type +" "+name +"\n";
		msg = msg+ "Date d'expiration :"+expireDate +"\n";
		msg = msg+  this.subcribeLines() ;
		return msg;
	}
}
