package service;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.*;

import java.util.List;
import java.util.UUID;

public class PedidoService {

	public Pedido iniciarPedido(Cliente cliente, Restaurante restaurante, String mesaOuNome, TipoAtendimento tipo) {

	}

	public boolean editarPedido(Pedido pedido, List<ItemPedido> novosItens) {

	}

	public boolean fecharPedido(Pedido pedido) {
		if (pedido != null && !pedido.getItens().isEmpty()) {
			pedido.setStatusPedido(enums.StatusPedido.EM_PREPARO);
			return true;
		}
		return false;
	}

	public boolean pagarPedido(Pedido pedido, FormaPagto formaPagto) {
		if (pedido != null && !pedido.isPago()) {
			pedido.setFormaPagto(formaPagto);
			pedido.setPago(true);
			return true;
		}
		return false;
	}

	public boolean cadastrarCartao(Cliente cliente, String numero, String validade, String nomeTitular) {
		// Aqui poderia salvar os dados em um sistema real
		System.out.println("Cartão cadastrado para cliente " + cliente.getNome());
		return true;
	}

	public Pedido buscarPedidoPorId(List<Pedido> pedidos, String id) {
		for (Pedido pedido : pedidos) {
			if (pedido.getId().toString().equals(id)) {
				return pedido;
			}
		}
		return null;
	}
}
