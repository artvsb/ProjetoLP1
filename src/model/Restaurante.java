package model;

import enums.StatusPedido;

import java.util.*;
import java.util.stream.Collectors;

public class Restaurante {
	private String id;
	private String nome;
	private String cnpj;
	private String telefone;
	private String email;
	private String endereco;
	private Map<String, Double> vouchers = new HashMap<>();
	private Administrador administrador;
	private List<ItemCardapio> menu = new ArrayList<>();
	private Map<String, String> mapaMesas;
	private List<Funcionario> funcionarios;
	private double TxEntregaPrioritaria, TxCancelamento;
	private List<Mesa> mesas = new ArrayList<>();
	private List<Pedido> pedidos = new ArrayList<>();

	private static final Map<String, String> QR_CODE_MESA = new HashMap<>();

	static {
		QR_CODE_MESA.put("QR-MESA-01", "1");
		QR_CODE_MESA.put("QR-MESA-02", "2");
		QR_CODE_MESA.put("QR-MESA-03", "3");
		QR_CODE_MESA.put("QR-RETIRADA", "VIRTUAL");
	}

	// ============================
	// CONSTRUTOR
	// ============================
	public Restaurante(String nome, String cnpj, String telefone, String email, String endereco) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
		this.pedidos = new ArrayList<>();
		this.mapaMesas = new HashMap<>();
		this.funcionarios = new ArrayList<>();
	}

	// ============================
	// MÉTODOS DE MESA E PEDIDOS
	// ============================
	public void adicionarMesa(String qrCode, String numeroMesa) {
		mapaMesas.put(qrCode, numeroMesa);
	}

	public void adicionarPedido(Pedido pedido) {
		if (pedido != null) {
			pedidos.add(pedido);
		}
	}

	public List<Pedido> getPedidosAtivos() {
		return pedidos.stream()
				.filter(p -> p.getStatusPedido() == StatusPedido.EM_PREPARO || p.getStatusPedido() == StatusPedido.PRONTO)
				.collect(Collectors.toList());
	}

	public List<Pedido> getPedidosAtivosOrdenadosPorUrgencia() {
		return getPedidosAtivos().stream()
				.sorted(Comparator.comparing(Pedido::getPrevisaoEntrega))
				.collect(Collectors.toList());
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	// ============================
	// FUNCIONÁRIOS
	// ============================
	public void addFuncionario(Funcionario funcionario) {
		if (funcionario != null) {
			funcionarios.add(funcionario);
		}
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	// ============================
	// GETTERS / SETTERS
	// ============================

	public List<ItemCardapio> getMenu() {
		return menu;
	}

	public void setMenu(List<ItemCardapio> menu) {
		this.menu = menu;
	}


	public Map<String, String> getMapaMesas() {
		return mapaMesas;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTxEntregaPrioritaria() {
		return TxEntregaPrioritaria;
	}

	public void setTxEntregaPrioritaria(double txEntregaPrioritaria) {
		TxEntregaPrioritaria = txEntregaPrioritaria;
	}

	public double getTxCancelamento() {
		return TxCancelamento;
	}

	public void setTxCancelamento(double txCancelamento) {
		TxCancelamento = txCancelamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Map<String, Double> getVouchers() {
		return vouchers;
	}

	public Double getDescontoVoucher(String codigo) {
		return vouchers.getOrDefault(codigo.toUpperCase(), 0.0);
	}

	public boolean isVoucherValido(String codigo) {
		return vouchers.containsKey(codigo.toUpperCase());
	}

	public boolean possuiVoucher(String codigo) {
		return (vouchers != null);
	}
}
