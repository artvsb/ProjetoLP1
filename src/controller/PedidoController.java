package controller;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.*;
import service.PedidoService;

import java.util.List;

public class PedidoController {

	private PedidoService pedidoService = new PedidoService();

	public boolean editarPedido(Pedido pedido, List<ItemPedido> novosItens) {
		return pedidoService.editarPedido(pedido, novosItens);
	}

	public boolean aceitarPedido(Pedido pedido, Funcionario funcionario) {
		return pedidoService.aceitarPedido(pedido, funcionario);
	}

	public boolean pagarPedido(Pedido pedido, FormaPagto formaPagto) {
		return pedidoService.pagarPedido(pedido, formaPagto);
	}

	public boolean cadastrarCartao(Cliente cliente, String numero, String validade, String nomeTitular) {
		return pedidoService.cadastrarCartao(cliente, numero, validade, nomeTitular);
	}

	public Pedido buscarPedidoPorId(List<Pedido> pedidos, String id) {
		return pedidoService.buscarPedido(pedidos, id);
	}

	public void marcarPedidoEntregue(Pedido pedido) {
		if (pedido == null || pedido.isEntregue()) {
			return;
		}

		pedido.setEntregue(true); // ou pedido.setStatusPedido(StatusPedido.ENTREGUE);

		Mesa mesa = pedido.getMesa();
		if (mesa != null && !mesa.isVirtual()) {
			mesa.setOcupada(false);
		}
	}

}
