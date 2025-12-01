package service;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.*;
import model.interfaces.IDGenerator;

import java.util.*;

public class PedidoService implements IDGenerator {
	private final Set<String> idsPedido = new HashSet<>();
	private final Random random = new Random();
	private final List<Pedido> pedidos = new ArrayList<>();

	public Pedido iniciarPedido(Cliente cliente, Restaurante restaurante, String mesaOuNome, TipoAtendimento tipo) {

	}

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

	public void cadastrar(Pedido p) {
		p.setId(gerarId());
		pedidos.add(p);
		System.out.println("Pedido cadastrado | ID Pedido: " + p.getId());
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

	public Pedido buscarPedido(List<Pedido> pedidos, String id) {
		for (Pedido pedido : pedidos) {
			if (pedido.getId().toString().equals(id)) {
				return pedido;
			}
		}
		return null;
	}


}
