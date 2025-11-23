package model;

import enums.StatusMesa;
import java.util.UUID;

public class Mesa {
	private final String nrMesa;
	private StatusMesa status;
	private Cliente clienteAtual;

	public Mesa(String nrMesa) {
		this.nrMesa = nrMesa;
		this.status = StatusMesa.LIVRE;
		this.clienteAtual = null;
	}

	public String getNrMesa() {
		return nrMesa;
	}

	public StatusMesa getStatus() {
		return status;
	}

	public void setStatus(StatusMesa status) {
		this.status = status;
	}

	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	public void ocuparMesa(Cliente cc) {
		this.clienteAtual = cc;
		this.status = StatusMesa.OCUPADA;
		cc.setMesa(nrMesa); // também define no cliente
	}

	public void liberarMesa() {
		if (clienteAtual != null) {
			clienteAtual.setMesa(null); // remove mesa do cliente
		}
		this.clienteAtual = null;
		this.status = StatusMesa.LIVRE;
	}
}
