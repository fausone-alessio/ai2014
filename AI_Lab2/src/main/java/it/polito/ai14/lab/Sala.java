package it.polito.ai14.lab;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/* http://stackoverflow.com/questions/567068/java-synchronized-block-vs-collections-synchronizedmap */
/* Inviare una mail a Malnati */

public class Sala {
	private String nome;
	private ConcurrentHashMap  <Date, String> prenotazioni = new ConcurrentHashMap <Date, String>();
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ConcurrentHashMap  <Date, String> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(ConcurrentHashMap  <Date, String> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
}
