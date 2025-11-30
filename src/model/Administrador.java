package model;

import enums.NivelAcesso;
import model.interfaces.IDGenerator;
import model.interfaces.PermissaoPedido;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Administrador extends Pessoa implements IDGenerator {
	private NivelAcesso nivelAcesso;
	private boolean ativo;
	protected Restaurante restaurante;
	private Set<String> idsAdministrador = new HashSet<>();
	private boolean podeCancelarPedido = true;

	public Administrador(String nome, String login, String senha, String telefone, String cpf, String email, NivelAcesso nivelAcesso, Restaurante restaurante) {
			super(nome, login, senha, telefone, cpf, email);
			this.nivelAcesso = nivelAcesso;
			this.ativo = true;
			this.restaurante = restaurante;
	}

	public NivelAcesso getNivelAcesso() { return nivelAcesso; }

	public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

	public boolean isAtivo() { return ativo; }

	public boolean isPodeCancelarPedido() {
		return podeCancelarPedido;
	}

	public void setPodeCancelarPedido(boolean podeCancelarPedido) {
		this.podeCancelarPedido = podeCancelarPedido;
	}

	public void desativar() { this.ativo = false; }

	public void ativar() { this.ativo = true; }

	@Override
	public String gerarId() {
		String id;
		do {
			long hash = Math.abs(UUID.randomUUID().getMostSignificantBits());
			long numero = hash % 100000L;
			id = String.format("A%05d", numero);
		} while (idsAdministrador.contains(id));

		idsAdministrador.add(id);
		return id;
	}


	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + login + "\nTelefone: " + telefone + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + ativo;
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



