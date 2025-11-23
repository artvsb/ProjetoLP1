package model.interfaces;

import model.Cliente;
import model.Restaurante;

import java.util.Map;
import java.util.UUID;

public interface VisualizarPerfil {
	void visualizarPerfil(Cliente cc, Map<UUID, Restaurante> restaurantes);
}
