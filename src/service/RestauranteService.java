package service;

import model.Restaurante;
import model.interfaces.GerarID;

import java.util.*;

public class RestauranteService implements GerarID {
	private Set<String> idsGerados = new HashSet<>();
	private Map<String, Restaurante> restaurantes = new HashMap<>();

	@Override
	public String gerarID(Restaurante restaurante) {
		Random rd = new Random();
		String id;
		do {
			int numero = 100000 + rd.nextInt(900000); // 6 dígitos
			id = "RR" + numero;
		} while (idsGerados.contains(id));
		idsGerados.add(id);
		restaurantes.put(id, restaurante);
		return id;
	}


}

