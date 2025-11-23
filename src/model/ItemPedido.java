package model;

public class ItemPedido {
	private String nome;
	private double preco;
	private int qtd;
	private int tempoPreparo;
	private String descricao;

	public ItemPedido(String nome, double preco, int qtd, int tempoPreparo, String descricao) {
		this.nome = nome;
		this.preco = preco;
		this.qtd = qtd;
		this.tempoPreparo = tempoPreparo;
		this.descricao = descricao;
	}

	public String getDescricao() {	return descricao;	}

	public void setDescricao(String descricao) {	this.descricao = descricao;		}

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

	public int getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(int tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	@Override
	public String toString() {
		return qtd + " x " + nome + " = R$ " + getSubtotal() + "\nDescrição: " + descricao;
	}
}
