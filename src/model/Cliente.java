package model;

import java.time.LocalDateTime;
import java.util.*;

public class Cliente extends Pessoa {
	private String mesa;
	private UUID id;
	private List<Pedido> pedidos;
	private Set<UUID> historicoRestaurantes = new HashSet<>();
	private LocalDateTime criadoEm;
	private Restaurante restaurante;

	public Cliente(String nome, String login, String senha, String telefone, String cpf, String email) {
		super(nome, login, senha, telefone, cpf, email);
		this.mesa = null;
		this.id = UUID.randomUUID();
		this.pedidos = new ArrayList<>();
		this.criadoEm = LocalDateTime.now();
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public String getMesa() { return mesa; }

	public void setMesa(String mesa) { this.mesa = mesa; }

	public List<Pedido> getPedidos() { return pedidos; }

	public void criarPedido(Pedido pedido) { pedidos.add(pedido); }

	public void exibirPedidos() {
		if (pedidos.isEmpty()) {
			System.out.println("Nenhum pedido registrado para o cliente selecionado.");
			return;
		}

		System.out.println("Pedidos de " + nome + ":");
		for (Pedido pp : pedidos) {
			System.out.println("Pedido nr. " + pp.getId() +
					"\nStatus: " + pp.getStatusPedido() +
					"\nTotal: R$ " + String.format("%.2f", pp.getTotal()));
		}
	}

	public void top3Restaurantes(Map<UUID, Restaurante> restaurantes) {
		Map<UUID, Integer> podium = new HashMap<>();

		for (Pedido pedido : pedidos) {
			if (pedido.getRestaurante() != null) {
				UUID idRest = pedido.getRestaurante().getIdRestaurant();

				// Se já tiver no mapa, soma +1. Se não tiver, começa com 1.
				if (podium.containsKey(idRest)) {
					int atual = podium.get(idRest);
					podium.put(idRest, atual + 1);
				} else {
					podium.put(idRest, 1);
				}
			}
		}

		if (podium.isEmpty()) {
			System.out.println("O cliente ainda não fez pedidos.");
			return;
		}

		List<Map.Entry<UUID, Integer>> listaOrdenada = new ArrayList<>(podium.entrySet());

		listaOrdenada.sort((a, b) -> b.getValue() - a.getValue());

		System.out.println("TOP 3 Restaurantes mais frequentados:");

		for (int i = 0; i < Math.min(3, listaOrdenada.size()); i++) {
			UUID id = listaOrdenada.get(i).getKey();
			int vezes = listaOrdenada.get(i).getValue();

			Restaurante r = restaurantes.get(id);
			if (r != null) {
				System.out.println((i + 1) + "º - " + r.getNome() + " (" + vezes + " pedidos)");
			} else {
				System.out.println((i + 1) + "º - Restaurante não encontrado (ID: " + id + ")");
			}
		}
	}


	public static void vincularMesaViaQRCode(Cliente cliente, String mesaQR) {
		if (cliente == null) {
			System.out.println("Cliente inválido.");
			return;
		}
		cliente.setMesa(mesaQR);
		System.out.println("Mesa atribuída com sucesso!");
		System.out.println("Mesa nr " + mesaQR);
	}

	public void addRestauranteHistorico(UUID idRestaurant) {
		historicoRestaurantes.add(idRestaurant);
	}

	public boolean frequentouRestaurante (UUID idRestaurant) {
		return historicoRestaurantes.contains(idRestaurant);
	}

	public Set<UUID> getHistoricoRestaurantes() {
		return historicoRestaurantes;
	}

	public long getTempoRestante()

	@Override
	public String toString() {
		return "Cliente: " +
				"\nID: " + getId() +
				"\nNome: " + nome +
				"\nMesa nr. " + mesa;
	}

	public Restaurante setRestaurante(Restaurante restaurante) {
		return restaurante;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}
}
