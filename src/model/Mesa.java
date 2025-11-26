package model;

import enums.StatusMesa;

public class Mesa {
	private final String nrMesa;
	private StatusMesa status;
	private Restaurante restaurante;

	public Mesa(Restaurante restaurante, String nrMesa) {
		this.nrMesa = nrMesa;
		this.status = StatusMesa.LIVRE;
	}

	public String getNrMesa() {
		return nrMesa;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}


}
