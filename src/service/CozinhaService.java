package service;

import model.*;
import enums.StatusPedido;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CozinhaService {
	private Restaurante restaurante;

	public CozinhaService(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public void exibirPedidosPorPrioridade(Restaurante restaurante) {
		List<Pedido> pedidos = restaurante.getPedidosAtivos();

		Map<StatusPedido, List<Pedido>> categorias = new LinkedHashMap<>();
		categorias.put(StatusPedido.EM_PREPARO, new ArrayList<>());
		categorias.put(StatusPedido.PRONTO, new ArrayList<>());
		categorias.put(StatusPedido.EM_MONTAGEM, new ArrayList<>());
		categorias.put(StatusPedido.CANCELADO, new ArrayList<>());

		List<Pedido> atrasados = new ArrayList<>();
		List<Pedido> noPrazo = new ArrayList<>();

		for (Pedido p : pedidos) {
			if (p.getStatusPedido() == StatusPedido.CANCELADO) {
				categorias.get(StatusPedido.CANCELADO).add(p);
				continue;
			}

			long tempoRestante = p.getTempoRestante();

			if (tempoRestante < 0 && p.getStatusPedido() != StatusPedido.ENTREGUE) {
				atrasados.add(p);
			} else if (tempoRestante >= 0 && p.getStatusPedido() == StatusPedido.EM_PREPARO) {
				noPrazo.add(p);
			} else {
				categorias.getOrDefault(p.getStatusPedido(), new ArrayList<>()).add(p);
			}
		}

		int idx = 1;
		Map<Integer, Pedido> mapaIndices = new HashMap<>();

		System.out.println("======= ATRASADOS =======");
		idx = imprimirCategoria(atrasados, idx, mapaIndices);

		System.out.println("======= NO PRAZO =======");
		idx = imprimirCategoria(noPrazo, idx, mapaIndices);

		System.out.println("======= EM MONTAGEM =======");
		idx = imprimirCategoria(categorias.get(StatusPedido.EM_MONTAGEM), idx, mapaIndices);

		System.out.println("======= CANCELADOS =======");
		imprimirCategoria(categorias.get(StatusPedido.CANCELADO), idx, mapaIndices);

		Scanner tcl = new Scanner(System.in);
		System.out.print("Digite o número do pedido para mais opções: ");
		int escolha = tcl.nextInt();

		Pedido selecionado = mapaIndices.get(escolha);
		if (selecionado != null) {
			exibirMenuPedido(selecionado);
		} else {
			System.out.println("Pedido inválido.");
		}
	}

	private int imprimirCategoria(List<Pedido> lista, int idx, Map<Integer, Pedido> mapa) {
		for (Pedido p : lista) {
			System.out.println(idx + ". Pedido nr " + p.getId() + " - " + p.getCliente().getNome());
			for (ItemPedido item : p.getItens()) {
				System.out.println("   - " + item.getNome());
			}
			System.out.println("------------------------------------");
			mapa.put(idx, p);
			idx++;
		}
		return idx;
	}

	private void exibirMenuPedido(Pedido pedido) {
		Scanner tcl = new Scanner(System.in);
		System.out.println("\n===== OPÇÕES DO PEDIDO =====");
		System.out.println("1 - Exibir perfil do cliente");
		System.out.println("2 - Ver detalhes do pedido");
		System.out.println("0 - Voltar");
		System.out.print("Opção: ");
		int opcao = tcl.nextInt();

		switch (opcao) {
			case 1:
				exibirPerfilCliente(pedido.getCliente());
				break;
			case 2:
				System.out.println(pedido);
				break;
			default:
				System.out.println("Voltando...");
		}
	}

	private void exibirPerfilCliente(Cliente cliente) {
		System.out.println("\n=== PERFIL DO CLIENTE ===");
		System.out.println("Nome: " + cliente.getNome());
		System.out.println("ID: " + cliente.getId());
		System.out.println("Cadastrado desde: " + cliente.getCriadoEm());

		// Top 3 restaurantes mais pedidos
		Map<UUID, Long> frequencia = cliente.getPedidos().stream()
				.collect(Collectors.groupingBy(p -> p.getRestaurante().getIdRestaurant(), Collectors.counting()));

		List<Map.Entry<UUID, Long>> top3 = frequencia.entrySet().stream()
				.sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
				.limit(3)
				.toList();

		System.out.println("Restaurantes mais frequentados:");
		for (Map.Entry<UUID, Long> entry : top3) {
			System.out.println("- Restaurante ID: " + entry.getKey() + " (" + entry.getValue() + " pedidos)");
		}

		System.out.println("Histórico de pedidos:");
		for (Pedido p : cliente.getPedidos()) {
			System.out.println("Pedido ID: " + p.getId() +
					"\n- Criado em: " + p.getCriadoEm() +
					"\n- Entregue em: " + (p.isEntregue() ? p.getDataHoraProntoPrevisao() : "Ainda não entregue"));
			System.out.println("-----------------------------------");
		}
	}
}
