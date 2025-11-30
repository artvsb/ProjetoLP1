package service;

import enums.StatusPedido;
import model.Pedido;
import model.Restaurante;

import java.util.*;

public class RestauranteService implements Cadastro<Restaurante> {
	private HashSet<String> idsGerados = new HashSet<>();
	private Random random = new Random();
	private List<Restaurante> restaurantes = new ArrayList<>();

	// Lista simulada de CNPJs válidos (como se fosse uma base da Receita Federal)
	private static final Set<String> CNPJS_VALIDOS = Set.of(
			"12.345.678/0001-90",
			"11.111.111/0001-55",
			"22.222.222/0001-88",
			"33.333.333/0001-99"
	);

	public boolean cnpjValido(String cnpj) {
		return CNPJS_VALIDOS.contains(cnpj);
	}

	public void criarRestaurante(Restaurante restaurante) {
		restaurante.setId(gerarId());
		restaurantes.add(restaurante);
		System.out.println("Restaurante cadastrado com sucesso. ID: " + restaurante.getId());
	}

	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void entregarPedido(Pedido pedido, Restaurante restaurante) {
		if (pedido != null && restaurante.getPedidosAtivos().contains(pedido)) {
			pedido.setStatusPedido(StatusPedido.ENTREGUE);
			pedido.setEntregue(true);
		}
	}

}

