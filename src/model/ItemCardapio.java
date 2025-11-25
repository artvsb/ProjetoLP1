package model;

public class ItemCardapio {
	private String nome;
	private double preco;
	private int tempoPreparo;
	private String descricao;
	private int qtd;

	public ItemCardapio(String nome, double preco, int tempoPreparo, String descricao) {
		this.nome = nome;
		this.preco = preco;
		this.tempoPreparo = tempoPreparo;
		this.descricao = descricao;
	}

	public ItemPedido toItemPedido(int qtd) {
		return new ItemPedido(this, qtd);
	}

	public String getNome() { return nome; }

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setTempoPreparo(int tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public int getTempoPreparo() {
		return tempoPreparo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return String.format("%s | R$ %.2f | %d min\nDescrição: %s",
				nome, preco, tempoPreparo, descricao);
	}
}
