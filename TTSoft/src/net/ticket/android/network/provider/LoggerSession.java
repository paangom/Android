package net.ticket.android.network.provider;

public class LoggerSession {
	
	private int maxTickets;
	private int syncfrequence;
	private String username;
	private String password;
	private long id;
	public int getMaxTickets() {
		return maxTickets;
	}
	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}
	public int getSyncfrequence() {
		return syncfrequence;
	}
	public void setSyncfrequence(int syncfrequence) {
		this.syncfrequence = syncfrequence;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


}
