package model;

import enums.StatusMesa;

public class Mesa {
	private String nrMesa;
	private StatusMesa status;
	private Restaurante restaurante;
	private boolean virtual; // Mesa sem limite de nº pedidos simultaneos
	private boolean ocupada;
	private String qrCode;

	public Mesa(Restaurante restaurante, String nrMesa) {
		this.nrMesa = nrMesa;
		this.status = StatusMesa.LIVRE;
		this.restaurante = restaurante;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getNrMesa() {
		return nrMesa;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setNrMesa(String nrMesa) {
		this.nrMesa = nrMesa;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public StatusMesa getStatus() {
		return status;
	}

	public void setStatus(StatusMesa status) {
		this.status = status;
	}
}
