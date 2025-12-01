package controller;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Restaurante;
import service.ClienteService;

import java.util.List;

public class ClienteController {

	private ClienteService clienteService = new ClienteService();

	// CADASTRO
	public void cadastrarCliente(Cliente cliente) {
		clienteService.salvar(cliente);
	}

	// LISTAGEM
	public List<Cliente> listarClientes() {
		return clienteService.getClientes();
	}

	// editar dados pessoais
	public void editarDadosPessoais(Cliente clienteEditado) {
		clienteService.editarDadosPessoais(clienteEditado);
	}

	// voucher
	public boolean consultarVoucher(Restaurante restaurante, String codigo) {
		return restaurante.isVoucherValido(codigo);
	}

	// PEDIDOS

	public Pedido iniciarPedido(Cliente cliente, Restaurante restaurante, TipoAtendimento tipo, String qrCode) {
		return clienteService.iniciarPedido(cliente, restaurante, tipo, qrCode);
	}

	public void editarPedido(Cliente cliente, Pedido pedido, List<ItemPedido> itens) {
		clienteService.editarPedido(cliente, pedido, itens);
	}

	public void fecharPedido(Cliente cliente, Pedido pedido, FormaPagto formaPagto) {
		clienteService.fecharPedido(cliente, pedido, formaPagto);
	}

	public void pagarPedido(Pedido pedido) {
		clienteService.pagarPedido(pedido);
	}

	public void cadastrarCartao(Cliente cliente, String numero, String nomeTitular, String validade, String cvv) {
		clienteService.cadastrarCartao(cliente, numero, nomeTitular, validade, cvv);
	}

	public List<String> listarCartoes(Cliente cliente) {
		return clienteService.getCartoesCadastrados(cliente);
	}


	// AVALIAÇÃO DE PEDIDO

	public boolean avaliarPedido(Cliente cliente, String pedidoId, int nota, String comentario) {
		return clienteService.avaliarPedido(cliente, pedidoId, nota, comentario);
	}

	// CANCELAR PEDIDO (Cliente paga taxa definida pelo administrador)
	public boolean cancelarPedido(Cliente cliente, Restaurante restaurante, double taxaCancelamento) {
		return clienteService.cancelarPedido(cliente, restaurante, taxaCancelamento);
	}

	// DELETAR CONTA
	public boolean deletarConta(Cliente cliente) {
		return clienteService.deletarConta(cliente);
	}

	// BUSCAR CLIENTE POR ID
	public Cliente buscarPorId(String id) {
		return clienteService.buscarPorId(id);
	}
}
