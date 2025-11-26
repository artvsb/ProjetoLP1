package service;

import enums.StatusPedido;
import model.Cliente;
import model.Pedido;
import model.Restaurante;
import model.interfaces.GerarID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClienteService implements GerarID {

	private Map<String, Cliente> clientesPorLogin = new HashMap<>();
	private Map<String, Cliente> clientesPorCpf = new HashMap<>();
	private Map<String, Cliente> clientesPorEmail = new HashMap<>();
	private Random rd;
	private Set<String> idsGerados;


	public Cliente cadastrarCliente(String nomeCompleto, String cpf, String login, String telefone, String email, String senha) {
		if (clientesPorLogin.containsKey(login) || clientesPorCpf.containsKey(cpf) || clientesPorEmail.containsKey(email)) {
			return null;
		}

		Cliente novo = new Cliente(nomeCompleto, login, senha, telefone, cpf, email);
		clientesPorLogin.put(login, novo);
		clientesPorCpf.put(cpf, novo);
		clientesPorEmail.put(email, novo);
		return novo;
	}

	public Cliente autenticarCliente(String identificador, String senha) {
		Cliente cliente = clientesPorLogin.getOrDefault(identificador,
				clientesPorCpf.getOrDefault(identificador,
						clientesPorEmail.get(identificador)));

		if (cliente != null && cliente.getSenha().equals(senha)) {
			return cliente;
		}
		return null;
	}

	public static void vincularMesaQRCode(Cliente cliente, Restaurante restaurante) {
		if (cliente == null) {
			System.out.println("Cliente inválido.");
			return;
		}

		Scanner tcl = new Scanner(System.in);
		System.out.print("Digite o conteúdo do QR Code: ");
		String conteudoQRCode = tcl.nextLine();

		String mesa = restaurante.getMapaMesas().get(conteudoQRCode);

		if (mesa == null) {
			System.out.println("QR Code inválido ou não registrado.");
			return;
		}

		// Verifica se a mesa já tem um pedido ativo
		for (Pedido p : restaurante.getPedidosAtivos()) {
			if (mesa.equals(p.getMesa())
					&& !p.isEntregue()
					&& !p.getStatusPedido().equals(StatusPedido.CANCELADO)) {
				System.out.println("Já existe um pedido em andamento nesta mesa.");
				return;
			}
		}

		cliente.setMesa(mesa);
		System.out.println("Mesa atribuída com sucesso: " + mesa);
	}

	public Cliente buscarClientePorIdentificador(String identificador) {
		return clientesPorLogin.getOrDefault(identificador,
				clientesPorCpf.getOrDefault(identificador,
						clientesPorEmail.get(identificador)));
	}

	public boolean atualizarSenha(Cliente pessoa, String novaSenha) {
		if (pessoa == null) return false;
		pessoa.setSenha(novaSenha);
		return true;
	} // MÉTOD0 DE ATUALIZAR SENHA COM O CLIENTE LOGADO

	@Override
	public String gerarID() {
		String id;
		do {
			id = String.valueOf(1_000_000 + rd.nextInt(9_000_000)); // de 1000000 até 9999999
		} while (idsGerados.contains(id));
		idsGerados.add(id);
		return id;
	}

	public Cliente autenticar(String loginOuCpf, String senha) {
		Cliente pessoa = clientesPorLogin.getOrDefault(loginOuCpf, clientesPorCpf.get(loginOuCpf));
		if (pessoa != null && pessoa.getSenha().equals(senha)) {
			return pessoa;
		}
		return null;
	}

	public static String formatarDataHora(LocalDateTime data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	public static String formatarHora(LocalDateTime data) {
		return data.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
}
