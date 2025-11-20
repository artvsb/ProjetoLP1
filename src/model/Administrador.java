package model;

import enums.NivelAcesso;
import model.interfaces.Gerenciavel;

public class Administrador extends Pessoa implements Gerenciavel {
	private String nivelAcesso;
	private boolean atv;

	public Administrador(String nome, String login, String senha, String telefone, NivelAcesso nivelAcesso) {
		super();
		this.atv = true;
	}

	public Administrador(String nome, String login, String senha, int telefone, NivelAcesso nivelAcesso) {
			super(nome, login, senha, telefone);
			this.nivelAcesso = nivelAcesso;
			this.atv = true;
	}

	public NivelAcesso getNivelAcesso() { return nivelAcesso; }

	public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

	public boolean isAtv() { return atv; }

	@Override
	public void desativar() { this.atv = false; }

	@Override
	public void ativar() { this.atv = true; }

	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + "\nTelefone: " + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + atv;
	}
}
