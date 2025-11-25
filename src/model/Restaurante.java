package model;

import java.util.*;
import java.util.stream.Collectors;
import enums.StatusPedido;
import model.Pedido;

public class Restaurante {
	private UUID idRestaurant;
	private List<String> mesasDisponiveis = new ArrayList<>();
	private List<Pedido> pedidosAtivos = new ArrayList<>();
	private String nome;
	private String endereco;
	private Menu menu;
	private double txEntregaPrioritaria;
	private Map<String, String> mapaMesas = new HashMap<>(); // chave: QR Code, valor: nr Mesa

	public Restaurante(String nome, String endereco) {
		this.idRestaurant = UUID.randomUUID();
		this.nome = nome;
		this.endereco = endereco;
		this.mapaMesas = new HashMap<>();
	}

	public void registrarMesa(String codigoQR, String numeroMesa) {
		mapaMesas.put(codigoQR, numeroMesa);
		//teste
		mapaMesas.put("ABC12345", "111");
		mapaMesas.put("XZY09876", "999");
	}

	public Map<String, String> getMapaMesas() {	 return mapaMesas;	}

	public double getTxEntregaPrioritaria() {
		return txEntregaPrioritaria;
	}

	public void setTxEntregaPrioritaria(int percentual) {
		if (percentual < 0) {
			System.out.println("A taxa não pode ser negativa!");
			return;
		}

		this.txEntregaPrioritaria = 1 + (percentual / 100.0);
		this.txEntregaPrioritaria = txEntregaPrioritaria;

		System.out.println("Nova taxa de entrega prioritária : " + percentual + "%");
	}

	public void notificarAdministracao(Cliente cliente) {
		System.out.println("[ADMIN] Novo cliente conectado: " + cliente.getNome() + " | Mesa: " + cliente.getMesa());
	}

	public void notificarCozinha(Cliente cliente) {
		System.out.println("[COZINHA] Cliente " + cliente.getNome() + " ocupou a mesa " + cliente.getMesa());
	}


	public void addMesa(String mesa) {
		if (!mesasDisponiveis.contains(mesa)) { mesasDisponiveis.add(mesa); }
	}

	public void addPedido(Pedido pedido) {
		if (pedido != null) {	pedidosAtivos.add(pedido);	}
	}

	public List<String> getMesasDisponiveis() {
		return mesasDisponiveis;
	}

	public void setMesasDisponiveis(List<String> mesasDisponiveis) {
		this.mesasDisponiveis = mesasDisponiveis;
	}

	public List<Pedido> getPedidosAtivos() {
		return pedidosAtivos;
	}

	public void setPedidosAtivos(List<Pedido> pedidosAtivos) {
		this.pedidosAtivos = pedidosAtivos;
	}

	public UUID getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(UUID idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return nome + " (" + idRestaurant + ")";
	}
}
