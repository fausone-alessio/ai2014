package it.polito.ai14.lab.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bookings")
public class Booking {
	private Integer id;
	private Date startTime;
	private Integer room;
	private User IDUser;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	
	@ManyToOne
	@JoinColumn(name="IDUser")
	public User getIDUser() {
		return IDUser;
	}
	public void setIDUser(User iDUser) {
		IDUser = iDUser;
	}	
}
