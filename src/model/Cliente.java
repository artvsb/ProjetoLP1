package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
	private String mesa;
	private List<Pedido> pedidos;

	public Cliente() {
		super();
		this.pedidos = new ArrayList<>();
	}

	public Cliente(String nome, String login, String senha, int telefone, String mesa) {
		super(nome, login, senha, telefone);
		this.mesa = mesa;
		this.pedidos = new ArrayList<>();
	}

	public String getMesa() { return mesa; }

	public void setMesa(String mesa) { this.mesa = mesa; }

	public List<Pedido> getPedidos() { return pedidos; }

	public void criarPedido(Pedido pedido) { pedidos.add(pedido); }

	@Override
	public String toString() {
		return "Cliente: " +
				"\nID: " + getId() +
				"\nNome: " + nome +
				"\nMesa nr. " + mesa;
	}
}
