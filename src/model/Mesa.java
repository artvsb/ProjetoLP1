package model;

import enums.StatusMesa;

public class Mesa {
	private String nrMesa;
	private StatusMesa status;
	private Restaurante restaurante;

	public Mesa(Restaurante restaurante, String nrMesa) {
		this.nrMesa = nrMesa;
		this.status = StatusMesa.LIVRE;
		this.restaurante = restaurante;
	}

	public String getNrMesa() {
		return nrMesa;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public StatusMesa getStatus() {
		return status;
	}

	public void setStatus(StatusMesa status) {
		this.status = status;
	}
}
