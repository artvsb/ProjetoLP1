package model;

import enums.StatusPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {
	private UUID id;
	private String mesa;
	private List<ItemPedido> itens;
	private StatusPedido status;

	public Pedido (String mesa) {
		this.id = UUID.randomUUID();
		this.mesa = mesa;
		this.itens = new ArrayList<>();
		this.status = StatusPedido.EM_PREPARO;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void adicionarItem(ItemPedido item) { itens.add(item); }

	public void removerItem(ItemPedido item) { itens.remove(item); }

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pedido: " +
				"\nID: " + id +
				", \nMesa: " + mesa +
				", \nItens: " + itens +
				", \nStatus: " + status;
	}

	public double getTotal() {
		double total = 0;
		for (ItemPedido item : itens) {
			total += item.getSubtotal();
		}
		return total;
	}
}

