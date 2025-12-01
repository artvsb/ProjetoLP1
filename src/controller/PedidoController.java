package controller;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.*;
import service.PedidoService;

import java.util.List;

public class PedidoController {

	private PedidoService pedidoService = new PedidoService();

	public Pedido iniciarPedido(Cliente cliente, Restaurante restaurante, String mesaOuNome, TipoAtendimento tipoAtendimento) {
		return pedidoService.iniciarPedido(cliente, restaurante, tipoAtendimento);
	}

	public boolean editarPedido(Pedido pedido, List<ItemPedido> novosItens) {
		return pedidoService.editarPedido(pedido, novosItens);
	}

	public boolean fecharPedido(Pedido pedido) {
		return pedidoService.aceitarPedido(pedido);
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
}
