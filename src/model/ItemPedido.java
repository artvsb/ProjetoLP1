package model;

public class ItemPedido {
	private ItemCardapio item;
	private int qtd;

	public ItemPedido(ItemCardapio item, int qtd) {
		this.item = item;
		this.qtd = qtd;
	}

	public ItemCardapio getItem() { return item; }

	public String getNome() {
		return item.getNome();
	} // retorna o nome direto de ItemCardapio; não tem setter


	public int getQtd() { return qtd; }

	public void setQtd(int qtd) { this.qtd = qtd; }

	public double getSubtotal() {	return qtd * item.getPreco();	}

	public int getTempoPreparo() {	return item.getTempoPreparo();	}

	@Override
	public String toString() {
		return String.format("%d x %s = R$ %.2f\nDescrição: %s",
				qtd, item.getNome(), getSubtotal(), item.getDescricao());
	}
}
