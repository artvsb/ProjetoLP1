package model;

import java.util.*;

public class Cliente extends Pessoa {
	private String mesa;
	private List<Pedido> pedidos;
	private List<Cliente> clientes;
	private Set<UUID> historicoRestaurantes = new HashSet<>();

	public Cliente() {
		super();
		this.pedidos = new ArrayList<>();
	}

	public Cliente(String nome, String login, String senha, int telefone, String mesa) {
		super(nome, login, senha, telefone);
		this.mesa = null;
		this.pedidos = new ArrayList<>();
	}

	public String getMesa() { return mesa; }

	public void setMesa(String mesa) { this.mesa = mesa; }

	public List<Pedido> getPedidos() { return pedidos; }

	public static Cliente novoCliente(Scanner tcl, List<Cliente> clientes) {
		System.out.println("==============================================");
		System.out.println("============ CADASTRO DE CLIENTE =============");

		System.out.print("Nome: ");
		String nome = tcl.nextLine();

		System.out.print("Login: ");
		String login = tcl.nextLine();

		for (Cliente c : clientes) {
			if (c.getLogin().equalsIgnoreCase(login)) {
				System.out.println("Já existe um cliente com esse login!");
				return null;
			}
		}

		System.out.print("Senha: ");
		String senha = tcl.nextLine();

		System.out.print("Telefone: ");
		String telefone = tcl.nextLine();

		Cliente novoCliente = new Cliente(nome, login, senha, telefone, null); // mesa = null
		clientes.add(novoCliente);

		System.out.println("Cliente cadastrado com sucesso!");
		return novoCliente;
	}


	public void criarPedido(Pedido pp) { pedidos.add(pp); }

	public void exibirPedidos() {
		if (pedidos.isEmpty()) {
			System.out.println("Nenhum pedido registrado para o cliente selecionado.");
			return;
		}

		System.out.println("Pedidos do cliente " + nome + ":");
		for (Pedido pp : pedidos) {
			System.out.println("Pedido nr. " + pp.getId() +
					"\nStatus: " + pp.getStatusPedido() +
					"\nTotal: R$ " + String.format("%.2f", pp.getTotal()));
		}
	}

	public static void vincularMesaViaQRCode(Cliente cliente, String mesaQR) {
		if (cliente == null) {
			System.out.println("Cliente inválido.");
			return;
		}
		cliente.setMesa(mesaQR);
		System.out.println("Mesa atribuída com sucesso!");
		System.out.println("Mesa nr " + mesaQR);
	}

	public void addRestauranteHistorico(UUID idRestaurant) {
		historicoRestaurantes.add(idRestaurant);
	}

	public boolean frequentouRestaurante (UUID idRestaurant) {
		return historicoRestaurantes.contains(idRestaurant);
	}

	public Set<UUID> getHistoricoRestaurantes() {
		return historicoRestaurantes;
	}

	@Override
	public String toString() {
		return "Cliente: " +
				"\nID: " + getId() +
				"\nNome: " + nome +
				"\nMesa nr. " + mesa;
	}
}
