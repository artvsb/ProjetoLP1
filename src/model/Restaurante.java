package model;

import java.util.UUID;

public class Restaurante {
	private UUID idRestaurant;
	private String nome;
	private String endereco;

	public Restaurante(String nome, String endereco) {
		this.idRestaurant = UUID.randomUUID();
		this.nome = nome;
		this.endereco = endereco;
	}

	public UUID getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(UUID idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return nome + " (" + idRestaurant + ")";
	}
}
