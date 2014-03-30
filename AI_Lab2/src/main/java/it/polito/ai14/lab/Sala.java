package it.polito.ai14.lab;

import java.util.concurrent.ConcurrentHashMap;

/* http://stackoverflow.com/questions/567068/java-synchronized-block-vs-collections-synchronizedmap */
/* Inviare una mail a Malnati */

/* Nella HashMap Long � il timestamp in secondi della prenotazione, mentre String � l'username che
 * ha prenotato quella sala. */

public class Sala {
	private String nome;
	private ConcurrentHashMap  <Long, String> prenotazioni = new ConcurrentHashMap <Long, String>();
	
	public Sala(){};
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ConcurrentHashMap  <Long, String> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(ConcurrentHashMap  <Long, String> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
}
