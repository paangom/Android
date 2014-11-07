package net.ticket.android.customer;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {
	private String city;
	private String reference;
	private String phone;
	private String street;
	private String zipcode;
	private Person user;
	private Card card;
	private boolean active;
	private String email;
	
	public Customer(JSONObject object) throws JSONException {
		super();
		
		this.setActive(object.getBoolean("active"));
		this.city = object.getString("city");
		this.phone = object.getString("phone");
		this.street = object.getString("street");
		this.zipcode = object.getString("zip_code");
		this.reference = object.getString("reference");
		this.email = object.getString("email");
		
		JSONObject user =  object.getJSONObject("user");
		JSONObject card =  object.getJSONObject("card");
		this.user = new Person(user);
		this.card = new Card(card);
		// TODO Auto-generated constructor stub
	}

	public Customer(String city, String reference, String phone, String street,
			String zipcode, Person user, Card card, boolean active) {
		super();
		this.city = city;
		this.reference = reference;
		this.phone = phone;
		this.street = street;
		this.zipcode = zipcode;
		this.user = user;
		this.card = card;
		this.setActive(active);
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public JSONObject toJSon() throws JSONException
	  {
	   JSONObject object = new JSONObject();
	   object.put("active", this.active);
	   object.put("city", this.city);
	   object.put("email", this.email);
    
	   object.put("phoner", this.phone);
	   object.put("street", this.street);
	   object.put("zip_code", this.zipcode);
	   object.put("user", this.user.toJSon());
	   object.put("card", this.card.toJSon());

    
	   return object;  
		  
	  }
	
	public boolean isValidIsValid()
	{
		if(this.card.isValidIsValid()==true && this.active==true)
			return true;
		else return false;
	}
    
	public String toString() {
		String msg = "Telephone :" + phone +"\n";
		msg = msg+ "Email:" + email +"\n";
		msg = msg+ "Adresse :"+city +" "+ street +"\n";
		msg = msg+ "Code postal :" + zipcode +"\n";
		msg = msg+ "=========Info Personnelle=======\n";
		msg = msg+ user.toString();
		msg = msg+ "=========Info Carte=======\n";
		msg = msg+ card.toString();
		return msg;
	}
}
