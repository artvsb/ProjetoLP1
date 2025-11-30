package model;

import model.interfaces.IDGenerator;
import model.interfaces.PermissaoPedido;

import java.time.LocalDateTime;
import java.util.*;

public class Cliente extends Pessoa implements IDGenerator, PermissaoPedido {

	private String mesa;
	private String codigoRecup;
	private boolean isAdmin = false;
	private Restaurante restaurante;
	private List<Pedido> pedidos = new ArrayList<>();
	private Set<UUID> historicoRestaurantes = new HashSet<>();
	private LocalDateTime criadoEm = LocalDateTime.now();
	private Random random;
	private final Set<String> idsCliente = new HashSet<>();
	private Map<String, Cliente> clientesPorLogin = new HashMap<>();
	private Map<String, Cliente> clientesPorCpf = new HashMap<>();
	private Map<String, Cliente> clientesPorEmail = new HashMap<>();


	// =====================
	// CONSTRUTOR
	// =====================
	public Cliente(String nome, String login, String senha, String telefone, String cpf, String email) {
		super(nome, login, senha, telefone, cpf, email);
	}

	// =====================
	// GETTERS / SETTERS
	// =====================


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

	@Override
	public String gerarId() {
		String id;
		do {
			StringBuilder sb = new StringBuilder("C");
			for (int i = 0; i < 7; i++) {
				sb.append(random.nextInt(10));
			}
			id = sb.toString();
		} while (idsCliente.contains(id));

		idsCliente.add(id);
		return id;
	}
}
