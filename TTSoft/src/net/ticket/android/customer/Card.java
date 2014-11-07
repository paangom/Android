package net.ticket.android.customer;

import org.json.JSONException;
import org.json.JSONObject;

public class Card {

	private String type;
	private String reference;
	private String created;
	private boolean crypte=false;
	private boolean active;
	
	public Card(String type, String reference, String tagId, boolean active,
			boolean crypte) {
		super();
		this.type = type;
		this.reference = reference;
		this.active = active;
		this.crypte = crypte;
	}
	
	public Card(JSONObject object) throws JSONException {
		super();
		this.type = object.getString("type");
		this.reference = object.getString("reference");
		this.active = object.getBoolean("active");
		this.crypte = object.getBoolean("crypted");
		this.created = object.getString("created");

	}
	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public boolean isCrypte() {
		return crypte;
	}
	public void setCrypte(boolean crypte) {
		this.crypte = crypte;
	}
	
	public JSONObject toJSon() throws JSONException
	  {
		JSONObject object = new JSONObject();
        object.put("crypted", this.crypte);
        object.put("active", this.active);
        object.put("reference", this.reference);
        object.put("created", this.created);
        object.put("type", this.type);
		return object;  
		  
	  }
	
	
	public boolean isValidIsValid()
	{
		return this.active;
	}
	
	public String toString() {
		String msg = "Type :" + type +"\n";
		msg = msg+ "Reference:" + reference +"\n";
		msg = msg+ "Etat :" + active +"\n";
		return msg;
	}
	
}
