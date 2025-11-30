package model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Administrador extends Pessoa {
	protected Restaurante restaurante;
	private Set<String> idsAdministrador = new HashSet<>();
	private boolean podeCancelarPedido = true;
	protected String id;

	public Administrador(String nome, String login, String senha, String telefone, String cpf, String email, Restaurante restaurante) {
			super(nome, login, senha, telefone, cpf, email);
			this.restaurante = restaurante;
	}

	public boolean isPodeCancelarPedido() {
		return podeCancelarPedido;
	}

	public void setPodeCancelarPedido(boolean podeCancelarPedido) {
		this.podeCancelarPedido = podeCancelarPedido;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + login + "\nTelefone: " + telefone;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}



