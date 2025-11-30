package model;

import model.interfaces.PermissaoPedido;

import java.time.LocalDateTime;
import java.util.*;

public class Cliente extends Pessoa implements PermissaoPedido {

	private String id;
	private String mesa;
	private String codigoRecup;
	private boolean isAdmin = false;
	private Restaurante restaurante;
	private List<Pedido> pedidos = new ArrayList<>();
	private Set<UUID> historicoRestaurantes = new HashSet<>();
	private LocalDateTime criadoEm = LocalDateTime.now();
	private Random random;
	private Set<String> idsGerados = new HashSet<>();


	// =====================
	// CONSTRUTOR
	// =====================
	public Cliente(String nome, String login, String senha, String telefone, String cpf, String email) {
		super(nome, login, senha, telefone, cpf, email);
		this.id = id;
	}

	@Override
	public String gerarId() {
		String id;
		do {
			id = "CL" + (100000 + random.nextInt(900000));
		} while (idsGerados.contains(id));
		idsGerados.add(id);
		return id;
	}

	// =====================
	// GETTERS / SETTERS
	// =====================
	@Override
	public String getId() {
		return id;
	}

	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public String getCodigoRecup() {
		return codigoRecup;
	}

	public void setCodigoRecup(String codigoRecup) {
		this.codigoRecup = codigoRecup;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void adicionarPedido(Pedido pedido) {
		if (pedido != null) {
			this.pedidos.add(pedido);
		}
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public Set<UUID> getHistoricoRestaurantes() {
		return historicoRestaurantes;
	}

	@Override
	public boolean podeCancelarPedido(Pedido pedido) {
		return pedido.podeSerCancelado();
	}

	@Override
	public String toString() {
		return "Cliente: " +
				"\nID: " + id +
				"\nNome: " + nome +
				"\nMesa: " + mesa;
	}
}
