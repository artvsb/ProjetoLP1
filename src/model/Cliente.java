package model;

public class Cliente extends Pessoa {
	private String mesa;

	public Cliente() { super(); }

	public Cliente(String nome, String login, String senha, int telefone, String mesa) {
		super(nome, login, senha, telefone);
		this.mesa = mesa;
	}

	public String getMesa() { return mesa; }

	public void setMesa(String mesa) { this.mesa = mesa; }

	@Override
	public String toString() {
		return "Cliente: " +
				"\nID: " + getId() +
				"\nNome: " + nome +
				"\nMesa nr. " + mesa;
	}
}
