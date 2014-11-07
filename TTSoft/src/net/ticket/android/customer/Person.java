package net.ticket.android.customer;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
	private String firstName;
	private String lastName;
	private String birthdate;
	private String expiredate;
	private String issueddate;
	private String authority;
	
	private String job;

	private String numberID;
	private String typeID;
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Person(JSONObject object ) throws JSONException {
		super();
		JSONObject job =  object.getJSONObject("job");
		this.firstName = object.getString("first_name");
		this.lastName = object.getString("last_name");
		this.birthdate = object.getString("date_of_birth");
		
		this.expiredate = object.getString("expiration_date");
		this.issueddate = object.getString("issue_date");
		this.authority = object.getString("issue_authority");
		this.numberID = object.getString("id_number");
		this.typeID = object.getString("id_type");
		
		this.job = job.getString("name");
		

		
		// TODO Auto-generated constructor stub
	}
	public Person(String firstName, String lastName, String birthdate,
			String expiredate, String issueddate, String authority,
			String number, String job, String type) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.expiredate = expiredate;
		this.issueddate = issueddate;
		this.authority = authority;
		
		this.job = job;
		
	}
	public String getNumberID() {
		return numberID;
	}


	public void setNumberID(String numberID) {
		this.numberID = numberID;
	}


	public String getTypeID() {
		return typeID;
	}


	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}


	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public String getIssueddate() {
		return issueddate;
	}
	public void setIssueddate(String issueddate) {
		this.issueddate = issueddate;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public JSONObject toJSon() throws JSONException
	  {
		
	   JSONObject object = new JSONObject();
	   object.put("date_of_birth", this.birthdate);
	   object.put("first_name", this.firstName);
	   object.put("last_name", this.lastName);
      
	   object.put("id_number", this.numberID);
	   object.put("id_type", this.typeID);
	   object.put("issue_authority", this.authority);
	   object.put("issue_date", this.issueddate);
	   object.put("expiration_date", this.expiredate);
      
	   JSONObject job = new JSONObject();
	   job.put("name", this.job);
	   object.put("job", job);
      
	   return object;  
		  
	  }



	@Override
	public String toString() {
		String msg = "Nom :" + firstName +"\n";
		msg = msg+ "Prenom:" + lastName +"\n";
		msg = msg+ "Profession :" + job +"\n";
		return msg;
	}
	
	

}
