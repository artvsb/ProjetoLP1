package model;

import java.util.UUID;

public class QRCode {
	private String mesa;
	private UUID idRestaurante;

	public QRCode(String mesa, UUID idRestaurante) {
		this.mesa = mesa;
		this.idRestaurante = idRestaurante;
	}

	public String getMesa() {
		return mesa;
	}

	public UUID getIdRestaurante() {
		return idRestaurante;
	}

	@Override
	public String toString() {
		return "Mesa: " + mesa + ", Restaurante id: " + idRestaurante;
	}
}
