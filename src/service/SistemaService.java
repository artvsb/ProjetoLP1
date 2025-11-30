package service;

import model.Mesa;
import model.Restaurante;

import java.util.HashMap;
import java.util.Map;

public class SistemaService {
	private Map<String, Mesa> mapaQRCodeGeral = new HashMap<>();

	public void registrarQRCode(String qrCode, Restaurante restaurante, String numeroMesa) {
		mapaQRCodeGeral.put(qrCode, new Mesa(restaurante, numeroMesa));
	} // -> deve ser usada em registrarMesa()

	public Mesa buscarQRCode(String qrCode) {
		return mapaQRCodeGeral.get(qrCode);
	}

}
