package it.polito.ai14.lab.entities;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="users")
public class User {
	private Integer id;
	private String username;
	private String password;
	private Boolean admin;
	private Date lastChange;
	private Set<Booking> prenotazioni;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@Column(name="isAdmin")
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastChange() {
		return lastChange;
	}
	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}
	
	@OneToMany(mappedBy="IDUser")
	public Set<Booking> getPrenotazioni() {
		return prenotazioni;
	}
}
