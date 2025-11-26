package model;

public class ItemCardapio {
	private String nome;
	private String descricao;
	private double preco;
	private int tempoPreparo;

	public ItemCardapio(String nome, String descricao, double preco, int tempoPreparo) {
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.tempoPreparo = tempoPreparo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
}
