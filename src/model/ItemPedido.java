package model;

public class ItemPedido {
	private String nome;
	private double preco;
	private int qtd;

	public ItemPedido(String nome, double preco, int qtd) {
		this.nome = nome;
		this.preco = preco;
		this.qtd = qtd;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public double getSubtotal() {
		return qtd * preco;
	}

	@Override
	public String toString() {
		return qtd + " x " + nome + " = R$ " + getSubtotal();
	}
}
