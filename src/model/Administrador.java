package model;

public class Administrador extends Pessoa {
	private String nivelAcesso;
	private boolean atv;

	public Administrador() {
		super();
		this.atv = true;
	}

	public Administrador(String nome, String login, String senha, int telefone, String nivelAcesso) {
			super(nome, login, senha, telefone);
			this.nivelAcesso = nivelAcesso;
			this.atv = true;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public boolean isAtv() { return atv; }

	public void desativar() { this.atv = false; }

	public void ativar() { this.atv = true; }

	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + "\nTelefone: " + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + atv;
	}
}
