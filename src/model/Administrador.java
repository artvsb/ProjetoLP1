package model;

import enums.NivelAcesso;
import model.interfaces.PermissaoPedido;

import java.util.EnumSet;
import java.util.Scanner;

public class Administrador extends Pessoa implements PermissaoPedido {
	private NivelAcesso nivelAcesso;
	private boolean atv;
	protected Restaurante restaurante;
	private boolean podeCancelarPedido = true;

	public Administrador(String nome, String login, String senha, String telefone, String cpf, String email, NivelAcesso nivelAcesso, Restaurante restaurante) {
			super(nome, login, senha, telefone, cpf, email);
			this.nivelAcesso = nivelAcesso;
			this.atv = true;
			this.restaurante = restaurante;
	}

	public NivelAcesso getNivelAcesso() { return nivelAcesso; }

	public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

	public boolean isAtv() { return atv; }

	public boolean isPodeCancelarPedido() {
		return podeCancelarPedido;
	}

	public void setPodeCancelarPedido(boolean podeCancelarPedido) {
		this.podeCancelarPedido = podeCancelarPedido;
	}

	@Override
	public void desativar() { this.atv = false; }

	@Override
	public void ativar() { this.atv = true; }

	@Override
	public String gerarId() {
		return "";
	}

	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + login + "\nTelefone: " + telefone + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + atv;
	}

	@Override
	public boolean podeCancelarPedido(Pedido pedido) {
		// Administrador pode cancelar qualquer pedido não entregue
		return pedido.podeSerCancelado();
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}



