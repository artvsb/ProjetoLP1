package controller;

import java.util.Scanner;

import enums.StatusPedido;
import model.Cliente;
import model.Mesa;
import model.Pedido;
import model.Restaurante;
import service.ClienteService;
import service.PedidoService;
import service.SistemaService;

// importado de ClienteService:
import static service.ClienteService.formatarDataHora;
import static service.ClienteService.formatarHora;

public class ClienteController {

	private Scanner tcl = new Scanner(System.in);
	private ClienteService clienteService;
	private PedidoService pedidoService = new PedidoService(); // supondo que exista
	private SistemaService sistemaService;

	public void cadastrarCliente() {
		System.out.println("\n==== CADASTRO ====");
		System.out.print("Nome completo: ");
		String nome = tcl.nextLine();

		System.out.print("CPF: ");
		String cpf = tcl.nextLine();

		System.out.print("Login (único): ");
		String login = tcl.nextLine();

		System.out.print("Telefone: ");
		String telefone = tcl.nextLine();

		System.out.print("Email: ");
		String email = tcl.nextLine();

		System.out.print("Senha: ");
		String senha = tcl.nextLine();

		Cliente novo = clienteService.cadastrarCliente(nome, cpf, login, telefone, email, senha);

		if (novo != null) {
			System.out.println("Cadastro realizado com sucesso. Seu ID é " + novo.getId());
		} else {
			System.out.println("Falha no cadastro. CPF ou Login já cadastrados.");
		}

		return;
	}

	public void HomePage() {
		System.out.println("\n==============================================");
		System.out.println("         BEM-VINDO AO MENU DIGITAL");
		System.out.println("==============================================\n");

		System.out.println("Selecione uma opção:");
		System.out.println("       1. Entrar");
		System.out.println("       2. Cadastrar-se");
		System.out.println("       3. Esqueci minha senha");
		System.out.print("\nDigite a opção desejada: ");

		int opcao = -1;

		try {
			opcao = Integer.parseInt(tcl.nextLine());

			switch (opcao) {
				case 1:
					realizarLogin(); // redireciona para área protegida
					break;
				case 2:
					cadastrarCliente();  // volta para HomePage após cadastrar
					break;
				case 3:
					recuperarSenha(); // volta para HomePage após alterar
					break;
				default:
					System.out.println("Opção inválida.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida.");
		}
	}

	private void iniciarOuAcompanharPedido (Cliente cliente) {
		Pedido pedidoAtual = pedidoService.buscarPedidoAtv(cliente);

		if (pedidoAtual != null) {
			exibirPedidoEmAndamento(cliente, pedidoAtual);
		} else {
			lerQRCode(cliente); // métod0 já existente
		}
	}

	public void lerQRCode(Cliente cliente) {
		Scanner tcl = new Scanner(System.in);
		System.out.print("Digite o conteúdo do QR Code: ");
		String qrCode = tcl.nextLine();

		Mesa mesaRef = sistemaService.buscarQRCode(qrCode);

		if (mesaRef == null) {
			System.out.println("QR Code inválido ou não registrado.");
			menuInicialCliente(cliente);
			return;
		}

		Restaurante restaurante = mesaRef.getRestaurante();
		String mesa = mesaRef.getNrMesa();

		// Verifica se já há pedido ativo naquela mesa
		for (Pedido p : restaurante.getPedidosAtivos()) {
			if (mesa.equals(p.getMesa())
					&& !p.isEntregue()
					&& !p.getStatusPedido().equals(StatusPedido.CANCELADO)) {
				System.out.println("Já existe um pedido em andamento nesta mesa.");
				menuInicialCliente(cliente);
				return;
			}
		}

		cliente.setMesa(mesa);
		cliente.setRestaurante(restaurante);
		System.out.println("Bem-vindo ao Restaurante " + cliente.getRestaurante().getNome());
		System.out.println("Mesa atribuída com sucesso: " + mesa);

		menuClienteVincMesa(cliente);
	}

	private void exibirPedidoEmAndamento(Cliente cliente, Pedido pedido) {
		System.out.println("\nSeu pedido está sendo preparado, " + cliente.getNome() + "\n");

		System.out.println("Informações do pedido:");
		System.out.println("- ID: " + pedido.getId());
		System.out.println("- Pedido criado em: " + formatarDataHora(pedido.getCriadoEm()));
		System.out.println("- Pedido:\n" + pedido.getResumoItens());
		System.out.println("- Previsão de Entrega às " + formatarHora(pedido.getPrevisaoEntrega()));
		System.out.println("- Mesa: " + pedido.getMesa());

		System.out.print("\nDigite CANCELAR se deseja cancelar seu pedido\n");
		System.out.print("Aperte 0 para voltar ao menu inicial: ");

		String entrada = tcl.nextLine().trim();

		if (entrada.equalsIgnoreCase("CANCELAR")) {
			boolean cancelado = pedidoService.cancelarPedido(pedido);
			if (cancelado) {
				System.out.println("Pedido cancelado com sucesso.");
			} else {
				System.out.println("Não foi possível cancelar o pedido.");
			}
		}
		// Qualquer entrada (inclusive 0) retorna ao menu principal
	}

	public void recuperarSenha() {
		System.out.println("\n===== RECUPERAÇÃO DE SENHA COM CÓDIGO =====");
		System.out.print("Informe o e-mail cadastrado: ");
		String email = tcl.nextLine();

		Cliente cliente = clienteService.buscarClientePorIdentificador(email);

		if (cliente == null) {
			System.out.println("E-mail não encontrado.");
			return;
		}

		// Simulação de envio de código por e-mail
		System.out.println("Um código foi enviado para seu e-mail.");
		// Use o código UNIVERSAL123 para continuar

		System.out.print("Digite o código recebido: ");
		String codigoDigitado = tcl.nextLine();

		if ("UNIVERSAL123".equals(codigoDigitado)) {
			System.out.print("Digite a nova senha: ");
			String novaSenha = tcl.nextLine();

			boolean sucesso = clienteService.atualizarSenha(cliente, novaSenha);
			if (sucesso) {
				System.out.println("Senha atualizada com sucesso!");
			} else {
				System.out.println("Erro ao atualizar a senha.");
			}
		} else {
			System.out.println("Código incorreto. Operação cancelada.");
		}
	}

	public void realizarLogin() {
		System.out.println("\n==== LOGIN ====");
		System.out.print("Digite seu login, e-mail ou CPF: ");
		String identificador = tcl.nextLine();

		System.out.print("Digite sua senha: ");
		String senha = tcl.nextLine();

		Cliente cliente = clienteService.autenticar(identificador, senha);

		if (cliente != null) {
			System.out.println("Login realizado com sucesso. Bem-vindo(a), " + cliente.getNome() + "!");
			menuClienteVincMesa(cliente);
		} else {
			System.out.println("Falha na autenticação. Verifique suas credenciais.");
			return;
		}
	}

	/*
			Menu do cliente vinculado a uma mesa.
  			Acesso a cardápio, fazer pedido etc.
 */
	public void menuClienteVincMesa(Cliente cliente) {
		while (true) {
			System.out.println("\n===== ÁREA DO CLIENTE - MESA " + cliente.getMesa() + " =====");
			System.out.println("1. Ver cardápio");
			System.out.println("2. Fazer pedido");
			System.out.println("3. Sair");
			System.out.print("Escolha uma opção: ");

			String option = tcl.nextLine();

			switch (option) {
				case "1":
					verCardapio(cliente); // métod0 que você pode criar
					break;
				case "2":
					iniciarOuAcompanharPedido(cliente); // métod0 que você pode criar
					break;
				case "3":
					System.out.println("Saindo da área do cliente...");
					return; // volta ao menu anterior
				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		}
	}

	/*
	  Menu geral do cliente após login, com acesso a:
	 	- Iniciar/Acompanhar pedido
	 	- Alterar dados
	 	- Avaliar pedidos
	 	- Cartões fidelidade
	 */
	public void menuInicialCliente(Cliente cliente) {
		while (true) {
			Scanner tcl = new Scanner(System.in);
			System.out.println("\n===== Bem-Vindo, " + cliente.getNome() + " =====");
			System.out.println("Selecione a opção desejada:\n");
			System.out.println("1. Iniciar/Acompanhar pedido");
			System.out.println("2. Alterar dados pessoais");
			System.out.println("3. Avaliar pedidos");
			System.out.println("4. Consultar cartões de fidelidade");
			System.out.println("0. Sair do Sistema");
			System.out.print("\nOpção: ");

			String opcao = tcl.nextLine();

			switch (opcao) {
				case "1":
					iniciarOuAcompanharPedido(cliente); // métod0 que você já possui ou vai criar
					break;
				case "2":
					alterarDadosPessoais(cliente);
					break;
				case "3":
					avaliarPedidos(cliente);
					break;
				case "4":
					consultarCartoesFidelidade(cliente);
					break;
				case "0":
					System.out.println("Saindo do sistema...");
					return; // retorna ao fluxo anterior (ex: HomePage)
				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		}

		private void lerQRCode(Cliente cliente) {
			System.out.println("Função lerQRCode() em desenvolvimento...");
			// Simule leitura de QR Code vinculando à mesa
		}

		private void alterarDadosPessoais(Cliente cliente) {
			System.out.println("Função alterarDadosPessoais() em desenvolvimento...");
			// Permitir alterar login, e-mail, telefone, senha etc.
		}

		private void avaliarPedidos(Cliente cliente) {
			System.out.println("Função avaliarPedidos() em desenvolvimento...");
			// Exibir lista de pedidos do cliente e coletar notas/avaliações
		}

		private void consultarCartoesFidelidade(Cliente cliente) {
			System.out.println("Função consultarCartoesFidelidade() em desenvolvimento...");
			// Mostrar saldo de pontos ou recompensas acumuladas
		}
	}

}
