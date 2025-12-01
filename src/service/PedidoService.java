package service;

import enums.FormaPagto;
import enums.StatusPedido;
import model.*;
import model.interfaces.IDGenerator;

import java.util.*;

public class PedidoService implements IDGenerator {
	private final Set<String> idsPedido = new HashSet<>();
	private final Random random = new Random();
	private final List<Pedido> pedidos = new ArrayList<>();

	@Override
	public String gerarId() {
		String id;
		do {
			StringBuilder sb = new StringBuilder("P"); // prefixo de Pedido
			for (int i = 0; i < 7; i++) {
				sb.append(random.nextInt(10));
			}
			id = sb.toString();
		} while (idsPedido.contains(id));

		idsPedido.add(id);
		return id;
	}

	public List<Pedido> listarPedidos() {
		return new ArrayList<>(pedidos);
	}

	public Pedido buscarPedido(String id) {
		return pedidos.stream()
				.filter(p -> id.equals(p.getId()))
				.findFirst()
				.orElse(null);
	}

	public boolean editarPedido(Pedido pedido, List<ItemPedido> novosItens) {
		if (pedido == null || pedido.isPago()) {
			return false;
		}

		if (novosItens == null || novosItens.isEmpty()) {
			return false;
		}

		pedido.getItens().clear();
		pedido.getItens().addAll(novosItens);

		double total = 0.0;
		for (ItemPedido item : novosItens) {
			total += item.getPreco() * item.getQtd();
		}
		pedido.setTotal(total);

		return true;
	}

	public boolean aceitarPedido(Pedido pedido, Funcionario funcionario) {
		if (pedido == null || funcionario == null) {
			return false;
		}

		if (pedido.isEntregue()) {
			System.out.println("Não é possível aceitar um pedido já entregue.");
			return false;
		}

		if (pedido.getFuncionarioResponsavel() != null) {
			System.out.println("Este pedido já possui um funcionário responsável.");
			return false;
		}

		pedido.setFuncionarioResponsavel(funcionario);
		pedido.setStatusPedido(StatusPedido.EM_PREPARO);

		return true;
	}

	public void cadastrarPedido(Pedido pedido) {
		if (pedido == null) {
			return;
		}

		// gera ID do pedido antes de salvar
		pedido.setId(gerarId());

		pedidos.add(pedido);
		System.out.println("Pedido cadastrado | ID Pedido: " + pedido.getId());
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

	public Pedido buscarPedido(List<Pedido> pedidos, String id) {
		for (Pedido pedido : pedidos) {
			if (pedido.getId().equals(id)) {
				return pedido;
			}
		}
		return null;
	}


}
