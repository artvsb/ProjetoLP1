import controller.*;
import enums.FormaPagto;
import enums.TipoAtendimento;
import model.*;
import service.FuncionarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner tcl = new Scanner(System.in);
    private static Restaurante restaurante;
    private static AdministradorController adminController;
    private static FuncionarioController funcController;
	private static FuncionarioService funcService;
    private static ClienteController clienteController;
    private static PedidoController pedidoController;

    // Listas para armazenar usuários
    private static List<Administrador> administradores = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        inicializarSistema();
        menuPrincipal();
    }

	// Listas globais para o sistema
	private static List<Restaurante> listaRestaurantes = new ArrayList<>();
	private static List<Pedido> listaPedidos = new ArrayList<>();

    private static void inicializarSistema() {
        // Criar restaurante padrão
        restaurante = new Restaurante(
            "Restaurante Exemplo",
            "12.345.678/0001-90",
            "(84) 98888-8888",
            "contato@restaurante.com",
            "Rua Principal, 123"
        );
        restaurante.setId("REST-001");
        restaurante.setTxEntregaPrioritaria(5.0);
        restaurante.setTxCancelamento(3.0);

		// Inivializa service
		funcService = new FuncionarioService();

		// Inicializar controllers
        adminController = new AdministradorController(restaurante);
        funcController = new FuncionarioController(funcService);
        clienteController = new ClienteController();
        pedidoController = new PedidoController();

        // Criar administrador padrão
        Administrador admin = new Administrador(
            "Admin Master",
            "admin",
            "admin123",
            "(84) 99999-9999",
            "111.111.111-11",
            "admin@restaurante.com",
            restaurante
        );
        admin.setId("ADM-001");
        administradores.add(admin);
        restaurante.setAdministrador(admin);

		// Criar restaurante padrão

		restaurante = new Restaurante(
				"Restaurante Exemplo",
				"12.345.678/0001-90",
				"(84) 98888-8888",
				"contato@restaurante.com",
				"Rua Principal, 123"
		);
		restaurante.setId("REST-001");
		listaRestaurantes.add(restaurante);

        // Criar algumas mesas padrão
        adminController.cadastrarMesas("QR-MESA-01", "1");
        adminController.cadastrarMesas("QR-MESA-02", "2");
        adminController.cadastrarMesas("QR-MESA-03", "3");
        adminController.cadastrarMesas("QR-RETIRADA", "VIRTUAL");

        // Criar alguns itens no menu
        adminController.adicionarItemAoMenu("Pizza Margherita", "Pizza tradicional com molho de tomate e queijo", 35.90, 30);
        adminController.adicionarItemAoMenu("Hambúrguer Artesanal", "Hambúrguer 180g com queijo e bacon", 28.50, 20);
        adminController.adicionarItemAoMenu("Refrigerante Lata", "Refrigerante 350ml", 5.00, 2);
        adminController.adicionarItemAoMenu("Batata Frita", "Porção de batata frita crocante", 15.00, 15);

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("   SISTEMA DE MENU DIGITAL - RESTAURANTE");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("Sistema inicializado com sucesso!");
        System.out.println("Restaurante: " + restaurante.getNome());
        System.out.println("═══════════════════════════════════════════════\n");
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║         MENU PRINCIPAL                ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1. Entrar como Administrador");
            System.out.println("2. Entrar como Funcionário");
            System.out.println("3. Entrar como Cliente");
            System.out.println("4. Cadastrar novo usuário");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    loginAdministrador();
                    break;
                case 2:
                    loginFuncionario();
                    break;
                case 3:
                    loginCliente();
                    break;
                case 4:
                    menuCadastro();
                    break;
                case 0:
                    System.out.println("\nEncerrando sistema... Até logo!");
                    System.exit(0);
                default:
                    System.out.println("\nOpção inválida!");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    // MENU DE CADASTRO
    // ═══════════════════════════════════════════════════════════

    private static void menuCadastro() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      CADASTRO DE NOVO USUÁRIO         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Cadastrar Funcionário");
        System.out.println("2. Cadastrar Cliente");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                cadastrarFuncionario();
                break;
            case 2:
                cadastrarCliente();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }

	private static void cadastrarFuncionario() {
		System.out.println("\n--- Cadastro de Funcionário ---");
		System.out.print("Nome: ");
		String nome = tcl.nextLine();
		System.out.print("Login: ");
		String login = tcl.nextLine();
		System.out.print("Senha: ");
		String senha = lerSenhaSegura();
		System.out.print("Telefone: ");
		String telefone = tcl.nextLine();
		System.out.print("CPF: ");
		String cpf = tcl.nextLine();
		System.out.print("Email: ");
		String email = tcl.nextLine();

		Funcionario func = new Funcionario(nome, login, senha, telefone, cpf, email);

		// Aqui o service (via controller) vai chamar gerarId() e setar o ID:
		funcController.cadastrarFuncionario(func);

		// Se quiser manter uma lista local na Main, ok:
		funcionarios.add(func);
		restaurante.addFuncionario(func);

		System.out.println("\nFuncionário cadastrado com sucesso!");
		System.out.println("ID: " + func.getId());
	}
	private static void cadastrarCliente() {
		System.out.println("\n--- Cadastro de Cliente ---");
		System.out.print("Nome: ");
		String nome = tcl.nextLine();
		System.out.print("Login: ");
		String login = tcl.nextLine();
		System.out.print("Senha: ");
		String senha = lerSenhaSegura(); // usa a mesma validação de senha do funcionário
		System.out.print("Telefone: ");
		String telefone = tcl.nextLine();
		System.out.print("CPF: ");
		String cpf = tcl.nextLine();
		System.out.print("Email: ");
		String email = tcl.nextLine();

		Cliente cliente = new Cliente(nome, login, senha, telefone, cpf, email);

		// TENTA CADASTRAR PELO CONTROLLER
		boolean cadastrado = clienteController.cadastrarCliente(cliente);
		if (!cadastrado) {
			System.out.println("\nJá existe um cliente com esse login. Tente outro.");
			return;
		}

		// Se deu certo no service/controller, mantém também na lista local da Main
		clientes.add(cliente);

		System.out.println("\nCliente cadastrado com sucesso!");
		System.out.println("Login (ID do cliente): " + cliente.getLogin());
	}

    // ═══════════════════════════════════════════════════════════
    // LOGIN ADMINISTRADOR
    // ═══════════════════════════════════════════════════════════

    private static void loginAdministrador() {
        System.out.println("\n--- Login Administrador ---");
        System.out.print("Login: ");
        String login = tcl.nextLine();
        System.out.print("Senha: ");
        String senha = tcl.nextLine();

        Administrador admin = buscarAdministrador(login, senha);
        if (admin != null) {
            System.out.println("\nLogin realizado com sucesso!");
            System.out.println("Bem-vindo, " + admin.getNome() + "!");
            menuAdministrador(admin);
        } else {
            System.out.println("\nLogin ou senha incorretos!");
        }
    }

    private static Administrador buscarAdministrador(String login, String senha) {
        for (Administrador admin : administradores) {
            if (admin.getLogin().equals(login) && admin.getSenha().equals(senha)) {
                return admin;
            }
        }
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // MENU ADMINISTRADOR
    // ═══════════════════════════════════════════════════════════

    private static void menuAdministrador(Administrador admin) {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║      MENU ADMINISTRADOR               ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1.  Gerenciar Menu");
            System.out.println("2.  Gerenciar Mesas");
            System.out.println("3.  Gerenciar Funcionários");
            System.out.println("4.  Gerenciar Pedidos");
            System.out.println("5.  Gerenciar Vouchers");
            System.out.println("6.  Configurar Taxas");
            System.out.println("7.  Consultar Avaliações");
            System.out.println("8.  Consultar Bônus de Funcionários");
            System.out.println("9.  Editar Dados do Restaurante");
            System.out.println("10. Listar Restaurantes");
            System.out.println("0.  Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    menuGerenciarMenu();
                    break;
                case 2:
                    menuGerenciarMesas();
                    break;
                case 3:
                    menuGerenciarFuncionarios();
                    break;
                case 4:
                    menuGerenciarPedidos();
                    break;
                case 5:
                    menuGerenciarVouchers();
                    break;
                case 6:
                    menuConfigurarTaxas();
                    break;
                case 7:
                    consultarAvaliacoes();
                    break;
                case 8:
                    consultarBonusFuncionarios();
                    break;
                case 9:
                    editarDadosRestaurante();
                    break;
                case 10:
                    listarRestaurantes();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nOpção inválida!");
            }
        }
    }

    private static void menuGerenciarMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERENCIAR MENU                  ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Adicionar item ao menu");
        System.out.println("2. Editar item do menu");
        System.out.println("3. Remover item do menu");
        System.out.println("4. Listar menu");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                adicionarItemMenu();
                break;
            case 2:
                editarItemMenu();
                break;
            case 3:
                removerItemMenu();
                break;
            case 4:
                listarMenu();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }

    private static void adicionarItemMenu() {
        System.out.println("\n--- Adicionar Item ao Menu ---");
        System.out.print("Nome do item: ");
        String nome = tcl.nextLine();
        System.out.print("Descrição: ");
        String descricao = tcl.nextLine();
        System.out.print("Preço: R$ ");
        double preco = lerDouble();
        System.out.print("Tempo de preparo (minutos): ");
        int tempo = lerInteiro();

        adminController.adicionarItemAoMenu(nome, descricao, preco, tempo);
        System.out.println("\nItem adicionado com sucesso!");
    }

    private static void editarItemMenu() {
        listarMenu();
        System.out.print("\nNúmero do item a editar: ");
        int index = lerInteiro() - 1;

        List<ItemCardapio> menu = restaurante.getMenu();
        if (index >= 0 && index < menu.size()) {
            ItemCardapio item = menu.get(index);

            System.out.println("\n--- Editar Item (deixe em branco para manter) ---");
            System.out.print("Novo nome [" + item.getNome() + "]: ");
            String nome = tcl.nextLine();
            if (nome.isEmpty()) nome = item.getNome();

            System.out.print("Nova descrição [" + item.getDescricao() + "]: ");
            String descricao = tcl.nextLine();
            if (descricao.isEmpty()) descricao = item.getDescricao();

            System.out.print("Novo preço [R$ " + item.getPreco() + "]: ");
            String precoStr = tcl.nextLine();
            double preco = precoStr.isEmpty() ? item.getPreco() : Double.parseDouble(precoStr);

            System.out.print("Novo tempo de preparo [" + item.getTempoPreparo() + " min]: ");
            String tempoStr = tcl.nextLine();
            int tempo = tempoStr.isEmpty() ? item.getTempoPreparo() : Integer.parseInt(tempoStr);

            adminController.editarMenu(item, nome, preco, descricao, tempo);
            System.out.println("\nItem editado com sucesso!");
        } else {
            System.out.println("\nItem não encontrado!");
        }
    }

    private static void removerItemMenu() {
        listarMenu();
        System.out.print("\nNúmero do item a remover: ");
        int index = lerInteiro() - 1;

        List<ItemCardapio> menu = restaurante.getMenu();
        if (index >= 0 && index < menu.size()) {
            ItemCardapio item = menu.get(index);
            adminController.deletarItem(item);
            System.out.println("\nItem removido com sucesso!");
        } else {
            System.out.println("\nItem não encontrado!");
        }
    }

    private static void listarMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                    MENU DO RESTAURANTE                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        List<ItemCardapio> menu = restaurante.getMenu();
        if (menu.isEmpty()) {
            System.out.println("Menu vazio.");
        } else {
            for (int i = 0; i < menu.size(); i++) {
                ItemCardapio item = menu.get(i);
                System.out.printf("%d. %s - R$ %.2f\n", (i + 1), item.getNome(), item.getPreco());
                System.out.printf("   %s\n", item.getDescricao());
                System.out.printf("   Tempo de preparo: %d minutos\n\n", item.getTempoPreparo());
            }
        }
    }

    private static void menuGerenciarMesas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERENCIAR MESAS                 ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Cadastrar mesa");
        System.out.println("2. Editar mesa");
        System.out.println("3. Remover mesa");
        System.out.println("4. Listar mesas");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                cadastrarMesa();
                break;
            case 2:
                editarMesa();
                break;
            case 3:
                removerMesa();
                break;
            case 4:
                listarMesas();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }

    private static void cadastrarMesa() {
        System.out.println("\n--- Cadastrar Mesa ---");
        System.out.print("Número da mesa: ");
        String numero = tcl.nextLine();
        System.out.print("Código QR: ");
        String qrCode = tcl.nextLine();

        adminController.cadastrarMesas(qrCode, numero);
        System.out.println("\nMesa cadastrada com sucesso!");
    }

    private static void editarMesa() {
        listarMesas();
        System.out.print("\nCódigo QR da mesa a editar: ");
        String qrCode = tcl.nextLine();
        System.out.print("Novo número da mesa: ");
        String novoNumero = tcl.nextLine();

        adminController.editarMesas(qrCode, novoNumero);
        System.out.println("\nMesa editada com sucesso!");
    }

    private static void removerMesa() {
        listarMesas();
        System.out.print("\nNúmero da mesa a remover: ");
        String numero = tcl.nextLine();

        adminController.removerMesa(numero);
        System.out.println("\nMesa removida com sucesso!");
    }

    private static void listarMesas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           MESAS CADASTRADAS           ║");
        System.out.println("╚═══════════════════════════════════════╝");

        var mesas = restaurante.getMapaMesas();
        if (mesas.isEmpty()) {
            System.out.println("Nenhuma mesa cadastrada.");
        } else {
            for (var entry : mesas.entrySet()) {
                System.out.printf("Mesa %s - QR Code: %s\n", entry.getValue(), entry.getKey());
            }
        }
    }

    private static void menuGerenciarFuncionarios() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║     GERENCIAR FUNCIONÁRIOS            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Listar funcionários");
        System.out.println("2. Conceder bônus a funcionário");
        System.out.println("3. Gerenciar acessos");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                listarFuncionarios();
                break;
            case 2:
                concederBonus();
                break;
            case 3:
                gerenciarAcessos();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      FUNCIONÁRIOS CADASTRADOS         ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<Funcionario> funcs = funcController.listarFuncionarios();
        if (funcs.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (int i = 0; i < funcs.size(); i++) {
                Funcionario f = funcs.get(i);
                System.out.printf("%d. %s (ID: %s)\n", (i + 1), f.getNome(), f.getId());
                System.out.printf("   CPF: %s | Telefone: %s\n", f.getCpf(), f.getTelefone());
                System.out.printf("   Bônus acumulado: R$ %.2f\n\n", f.getTotalBonus());
            }
        }
    }

    private static void concederBonus() {
        listarFuncionarios();
        System.out.print("\nNúmero do funcionário: ");
        int index = lerInteiro() - 1;

        List<Funcionario> funcs = funcController.listarFuncionarios();
        if (index >= 0 && index < funcs.size()) {
            Funcionario func = funcs.get(index);
            adminController.concederBonusFuncionario(func);
            System.out.println("\nBônus concedido com sucesso!");
        } else {
            System.out.println("\nFuncionário não encontrado!");
        }
    }

    private static void gerenciarAcessos() {
        listarFuncionarios();
        System.out.print("\nID do funcionário: ");
        String id = tcl.nextLine();
        System.out.print("Conceder acesso? (S/N): ");
        String resposta = tcl.nextLine();
        boolean conceder = resposta.equalsIgnoreCase("S");

        adminController.gerenciarAcessos(id, conceder);
        System.out.println("\nAcesso " + (conceder ? "concedido" : "revogado") + " com sucesso!");
    }

    private static void menuGerenciarPedidos() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERENCIAR PEDIDOS               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Listar pedidos ativos");
        System.out.println("2. Listar todos os pedidos");
        System.out.println("3. Cancelar pedido");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                listarPedidosAtivos();
                break;
            case 2:
                listarTodosPedidos();
                break;
            case 3:
                cancelarPedidoAdmin();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }

    private static void listarPedidosAtivos() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         PEDIDOS ATIVOS                ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<Pedido> pedidos = restaurante.getPedidosAtivos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido ativo no momento.");
        } else {
            for (Pedido p : pedidos) {
                System.out.printf("Pedido #%s - Status: %s\n", p.getId(), p.getStatusPedido());
                System.out.printf("Cliente: %s | Mesa: %s\n", 
                    p.getCliente().getNome(), 
                    p.getMesa() != null ? p.getMesa().getNrMesa() : "Retirada");
                System.out.printf("Total: R$ %.2f\n\n", p.getTotal());
            }
        }
    }

    private static void listarTodosPedidos() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         TODOS OS PEDIDOS              ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<Pedido> pedidos = restaurante.getPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
        } else {
            for (Pedido p : pedidos) {
                System.out.printf("Pedido #%s - Status: %s\n", p.getId(), p.getStatusPedido());
                System.out.printf("Cliente: %s | Total: R$ %.2f\n\n", 
                    p.getCliente().getNome(), p.getTotal());
            }
        }
    }

    private static void cancelarPedidoAdmin() {
        listarPedidosAtivos();
        System.out.print("\nID do pedido a cancelar: ");
        String id = tcl.nextLine();

        Pedido pedido = pedidoController.buscarPedidoPorId(restaurante.getPedidos(), id);
        if (pedido != null) {
            boolean cancelado = adminController.cancelarPedido(pedido);
            if (cancelado) {
                System.out.println("\nPedido cancelado com sucesso!");
            } else {
                System.out.println("\nNão foi possível cancelar o pedido!");
            }
        } else {
            System.out.println("\nPedido não encontrado!");
        }
    }

    private static void menuGerenciarVouchers() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       GERENCIAR VOUCHERS              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Criar voucher");
        System.out.println("2. Editar voucher");
        System.out.println("3. Remover voucher");
        System.out.println("4. Listar vouchers");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                criarVoucher();
                break;
            case 2:
                editarVoucher();
                break;
            case 3:
                removerVoucher();
                break;
            case 4:
                listarVouchers();
                break;
            case 0:
                return;
            default:
                System.out.println("\n❌ Opção inválida!");
        }
    }

    private static void criarVoucher() {
        System.out.println("\n--- Criar Voucher ---");
        System.out.print("Código do voucher: ");
        String codigo = tcl.nextLine();
        System.out.print("Desconto (%): ");
        double desconto = lerDouble();

        boolean criado = adminController.criarVoucher(codigo, desconto);
        if (criado) {
            System.out.println("\n✅ Voucher criado com sucesso!");
        } else {
            System.out.println("\n❌ Erro ao criar voucher!");
        }
    }

    private static void editarVoucher() {
        listarVouchers();
        System.out.print("\nCódigo do voucher a editar: ");
        String codigo = tcl.nextLine();
        System.out.print("Novo desconto (%): ");
        double desconto = lerDouble();

        boolean editado = adminController.editarVoucher(codigo, desconto);
        if (editado) {
            System.out.println("\n✅ Voucher editado com sucesso!");
        } else {
            System.out.println("\n❌ Voucher não encontrado!");
        }
    }

    private static void removerVoucher() {
        listarVouchers();
        System.out.print("\nCódigo do voucher a remover: ");
        String codigo = tcl.nextLine();

        boolean removido = adminController.removerVoucher(codigo);
        if (removido) {
            System.out.println("\n✅ Voucher removido com sucesso!");
        } else {
            System.out.println("\n❌ Voucher não encontrado!");
        }
    }

    private static void listarVouchers() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║        VOUCHERS CADASTRADOS           ║");
        System.out.println("╚═══════════════════════════════════════╝");

        var vouchers = restaurante.getVouchers();
        if (vouchers.isEmpty()) {
            System.out.println("Nenhum voucher cadastrado.");
        } else {
            for (var entry : vouchers.entrySet()) {
                System.out.printf("Código: %s - Desconto: %.1f%%\n", 
                    entry.getKey(), entry.getValue());
            }
        }
    }

    private static void menuConfigurarTaxas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       CONFIGURAR TAXAS                ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Alterar taxa de entrega");
        System.out.println("2. Alterar taxa de cancelamento");
        System.out.println("3. Visualizar taxas atuais");
        System.out.println("0. Voltar");
        System.out.print("\nEscolha uma opção: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                System.out.print("\nNova taxa de entrega: R$ ");
                double taxaEntrega = lerDouble();
                adminController.alterarTaxaEntrega(taxaEntrega);
                System.out.println("\n✅ Taxa de entrega alterada!");
                break;
            case 2:
                System.out.print("\nNova taxa de cancelamento: R$ ");
                double taxaCancelamento = lerDouble();
                adminController.alterarTaxaCancelamento(taxaCancelamento);
                System.out.println("\n✅ Taxa de cancelamento alterada!");
                break;
            case 3:
                System.out.printf("\nTaxa de entrega: R$ %.2f\n", restaurante.getTxEntregaPrioritaria());
                System.out.printf("Taxa de cancelamento: R$ %.2f\n", restaurante.getTxCancelamento());
                break;
            case 0:
                return;
            default:
                System.out.println("\n❌ Opção inválida!");
        }
    }

    private static void consultarAvaliacoes() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           AVALIAÇÕES                  ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<String> avaliacoes = adminController.consultarAvaliacoes();
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação registrada.");
        } else {
            for (String avaliacao : avaliacoes) {
                System.out.println(avaliacao);
                System.out.println("---");
            }
        }
    }

    private static void consultarBonusFuncionarios() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      BÔNUS DE FUNCIONÁRIOS            ║");
        System.out.println("╚═══════════════════════════════════════╝");

        var bonus = adminController.consultarBonusFuncionarios();
        if (bonus.isEmpty()) {
            System.out.println("Nenhum bônus registrado.");
        } else {
            for (var entry : bonus.entrySet()) {
                System.out.printf("%s: R$ %.2f\n", 
                    entry.getKey().getNome(), entry.getValue());
            }
        }
    }

    private static void editarDadosRestaurante() {
        System.out.println("\n--- Editar Dados do Restaurante ---");
        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Nome [" + restaurante.getNome() + "]: ");
        String nome = tcl.nextLine();
        if (!nome.isEmpty()) restaurante.setNome(nome);

        System.out.print("Telefone [" + restaurante.getTelefone() + "]: ");
        String telefone = tcl.nextLine();
        if (!telefone.isEmpty()) restaurante.setTelefone(telefone);

        System.out.print("Email [" + restaurante.getEmail() + "]: ");
        String email = tcl.nextLine();
        if (!email.isEmpty()) restaurante.setEmail(email);

        System.out.print("Endereço [" + restaurante.getEndereco() + "]: ");
        String endereco = tcl.nextLine();
        if (!endereco.isEmpty()) restaurante.setEndereco(endereco);

        System.out.println("\n✅ Dados atualizados com sucesso!");
    }

    private static void listarRestaurantes() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      RESTAURANTES CADASTRADOS         ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<Restaurante> restaurantes = adminController.listarRestaurantes();
        if (restaurantes.isEmpty()) {
            System.out.println("Nenhum restaurante cadastrado.");
        } else {
            for (Restaurante r : restaurantes) {
                System.out.printf("ID: %s\n", r.getId());
                System.out.printf("Nome: %s\n", r.getNome());
                System.out.printf("CNPJ: %s\n", r.getCnpj());
                System.out.printf("Telefone: %s\n", r.getTelefone());
                System.out.println("---");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    // LOGIN FUNCIONÁRIO
    // ═══════════════════════════════════════════════════════════

    private static void loginFuncionario() {
        System.out.println("\n--- Login Funcionário ---");
        System.out.print("Login: ");
        String login = tcl.nextLine();
        System.out.print("Senha: ");
        String senha = tcl.nextLine();

        Funcionario func = buscarFuncionario(login, senha);
        if (func != null) {
            System.out.println("\n✅ Login realizado com sucesso!");
            System.out.println("Bem-vindo, " + func.getNome() + "!");
            menuFuncionario(func);
        } else {
            System.out.println("\n❌ Login ou senha incorretos!");
        }
    }

    private static Funcionario buscarFuncionario(String login, String senha) {
        for (Funcionario func : funcionarios) {
            if (func.getLogin().equals(login) && func.getSenha().equals(senha)) {
                return func;
            }
        }
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // MENU FUNCIONÁRIO
    // ═══════════════════════════════════════════════════════════

    private static void menuFuncionario(Funcionario func) {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║       MENU FUNCIONÁRIO                ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1. Ver pedidos ativos");
            System.out.println("2. Aceitar pedido");
            System.out.println("3. Marcar pedido como entregue");
            System.out.println("4. Consultar meu bônus");
            System.out.println("5. Ver menu do restaurante");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    listarPedidosAtivos();
                    break;
                case 2:
                    aceitarPedido(func);
                    break;
                case 3:
                    marcarPedidoEntregue();
                    break;
                case 4:
                    consultarBonusFuncionario(func);
                    break;
                case 5:
                    listarMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nOpção inválida!");
            }
        }
    }

    private static void aceitarPedido(Funcionario func) {
        listarPedidosAtivos();
        System.out.print("\nID do pedido a aceitar: ");
        String id = tcl.nextLine();

        Pedido pedido = pedidoController.buscarPedidoPorId(restaurante.getPedidos(), id);
        if (pedido != null) {
            boolean aceito = pedidoController.aceitarPedido(pedido, func);
            if (aceito) {
                System.out.println("\nPedido aceito com sucesso!");
            } else {
                System.out.println("\nNão foi possível aceitar o pedido!");
            }
        } else {
            System.out.println("\n Pedido não encontrado!");
        }
    }

    private static void marcarPedidoEntregue() {
        listarPedidosAtivos();
        System.out.print("\nID do pedido entregue: ");
        String id = tcl.nextLine();

        Pedido pedido = pedidoController.buscarPedidoPorId(restaurante.getPedidos(), id);
        if (pedido != null) {
            pedidoController.marcarPedidoEntregue(pedido);
            System.out.println("\nPedido marcado como entregue!");
        } else {
            System.out.println("\nPedido não encontrado!");
        }
    }

    private static void consultarBonusFuncionario(Funcionario func) {
        double bonus = funcController.consultarBonus(func);
        System.out.printf("\nSeu bônus acumulado: R$ %.2f\n", bonus);
    }

    // ═══════════════════════════════════════════════════════════
    // LOGIN CLIENTE
    // ═══════════════════════════════════════════════════════════

    private static void loginCliente() {
        System.out.println("\n--- Login Cliente ---");
        System.out.print("Login: ");
        String login = tcl.nextLine();
        System.out.print("Senha: ");
        String senha = tcl.nextLine();

        Cliente cliente = buscarCliente(login, senha);
        if (cliente != null) {
            System.out.println("\nLogin realizado com sucesso!");
            System.out.println("Bem-vindo, " + cliente.getNome() + "!");
            menuCliente(cliente);
        } else {
            System.out.println("\nLogin ou senha incorretos!");
        }
    }

    private static Cliente buscarCliente(String login, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getLogin().equals(login) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // MENU CLIENTE
    // ═══════════════════════════════════════════════════════════

    private static void menuCliente(Cliente cliente) {
        while (true) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║         MENU CLIENTE                  ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("1. Ver menu");
            System.out.println("2. Fazer pedido");
            System.out.println("3. Meus pedidos");
            System.out.println("4. Avaliar pedido");
            System.out.println("5. Cadastrar cartão");
            System.out.println("6. Meus cartões");
            System.out.println("7. Editar dados pessoais");
            System.out.println("8. Consultar voucher");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    listarMenu();
                    break;
                case 2:
                    fazerPedido(cliente);
                    break;
                case 3:
                    meusPedidos(cliente);
                    break;
                case 4:
                    avaliarPedido(cliente);
                    break;
                case 5:
                    cadastrarCartao(cliente);
                    break;
                case 6:
                    listarCartoes(cliente);
                    break;
                case 7:
                    editarDadosPessoaisCliente(cliente);
                    break;
                case 8:
                    consultarVoucher();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n Opção inválida!");
            }
        }
    }

    private static void fazerPedido(Cliente cliente) {
        System.out.println("\n--- Fazer Pedido ---");
        System.out.println("1. Pedido na mesa");
        System.out.println("2. Pedido para retirada");
        System.out.print("\nEscolha o tipo de atendimento: ");

        int tipo = lerInteiro();
        TipoAtendimento tipoAtendimento;
        String qrCode;

        if (tipo == 1) {
            tipoAtendimento = TipoAtendimento.LOCAL;
            System.out.print("Código QR da mesa: ");
            qrCode = tcl.nextLine();
        } else {
            tipoAtendimento = TipoAtendimento.RETIRADA;
            qrCode = "QR-RETIRADA";
        }

        Pedido pedido = clienteController.iniciarPedido(cliente, restaurante, tipoAtendimento, qrCode);

        if (pedido == null) {
            System.out.println("\n Erro ao iniciar pedido!");
            return;
        }

        List<ItemPedido> itens = new ArrayList<>();
        boolean adicionandoItens = true;

        while (adicionandoItens) {
            listarMenu();
            System.out.print("\nNúmero do item (0 para finalizar): ");
            int itemNum = lerInteiro();

            if (itemNum == 0) {
                adicionandoItens = false;
            } else {
                List<ItemCardapio> menu = restaurante.getMenu();
                if (itemNum > 0 && itemNum <= menu.size()) {
                    ItemCardapio itemCardapio = menu.get(itemNum - 1);
                    System.out.print("Quantidade: ");
                    int quantidade = lerInteiro();

                    ItemPedido itemPedido = new ItemPedido(itemCardapio, quantidade);
                    itens.add(itemPedido);
                    System.out.println("\n Item adicionado!");
                } else {
                    System.out.println("\n Item inválido!");
                }
            }
        }

        if (itens.isEmpty()) {
            System.out.println("\n Pedido cancelado - nenhum item adicionado!");
            return;
        }

        clienteController.editarPedido(cliente, pedido, itens);

        System.out.println("\n--- Forma de Pagamento ---");
        System.out.println("1. Dinheiro");
        System.out.println("2. Cartão de Crédito");
        System.out.println("3. Cartão de Débito");
        System.out.println("4. PIX");
        System.out.print("\nEscolha: ");

        int formaPagtoNum = lerInteiro();
        FormaPagto formaPagto;

        switch (formaPagtoNum) {
            case 1:
                formaPagto = FormaPagto.DINHEIRO;
                break;
            case 2:
                formaPagto = FormaPagto.CARTAO_CREDITO;
                break;
            case 3:
                formaPagto = FormaPagto.CARTAO_DEBITO;
                break;
            case 4:
                formaPagto = FormaPagto.PIX;
                break;
            default:
                formaPagto = FormaPagto.DINHEIRO;
        }

        clienteController.fecharPedido(cliente, pedido, formaPagto);
        restaurante.adicionarPedido(pedido);

		// lista global de pedidos
		listaPedidos.add(pedido);

        System.out.println("\nPedido realizado com sucesso!");
        System.out.printf("Pedido #%s - Total: R$ %.2f\n", pedido.getId(), pedido.getTotal());
    }

    private static void meusPedidos(Cliente cliente) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          MEUS PEDIDOS                 ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<Pedido> pedidos = cliente.getPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Você ainda não fez nenhum pedido.");
        } else {
            for (Pedido p : pedidos) {
                System.out.printf("Pedido #%s\n", p.getId());
                System.out.printf("Status: %s\n", p.getStatusPedido());
                System.out.printf("Total: R$ %.2f\n", p.getTotal());
                System.out.printf("Entregue: %s\n\n", p.isEntregue() ? "Sim" : "Não");
            }
        }
    }

    private static void avaliarPedido(Cliente cliente) {
        meusPedidos(cliente);
        System.out.print("\nID do pedido a avaliar: ");
        String id = tcl.nextLine();
        System.out.print("Nota (1-5): ");
        int nota = lerInteiro();
        System.out.print("Comentário: ");
        String comentario = tcl.nextLine();

        boolean avaliado = clienteController.avaliarPedido(cliente, id, nota, comentario);
        if (avaliado) {
            System.out.println("\nAvaliação registrada com sucesso!");
        } else {
            System.out.println("\nErro ao avaliar pedido!");
        }
    }

    private static void cadastrarCartao(Cliente cliente) {
        System.out.println("\n--- Cadastrar Cartão ---");
        System.out.print("Número do cartão: ");
        String numero = tcl.nextLine();
        System.out.print("Nome do titular: ");
        String titular = tcl.nextLine();
        System.out.print("Validade (MM/AA): ");
        String validade = tcl.nextLine();
        System.out.print("CVV: ");
        String cvv = tcl.nextLine();

        clienteController.cadastrarCartao(cliente, numero, titular, validade, cvv);
        System.out.println("\nCartão cadastrado com sucesso!");
    }

    private static void listarCartoes(Cliente cliente) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║        CARTÕES CADASTRADOS            ║");
        System.out.println("╚═══════════════════════════════════════╝");

        List<String> cartoes = clienteController.listarCartoes(cliente);
        if (cartoes.isEmpty()) {
            System.out.println("Nenhum cartão cadastrado.");
        } else {
            for (String cartao : cartoes) {
                System.out.println(cartao);
            }
        }
    }

    private static void editarDadosPessoaisCliente(Cliente cliente) {
        System.out.println("\n--- Editar Dados Pessoais ---");
        System.out.println("(Deixe em branco para manter o valor atual)");

        System.out.print("Nome [" + cliente.getNome() + "]: ");
        String nome = tcl.nextLine();
        if (!nome.isEmpty()) cliente.setNome(nome);

        System.out.print("Telefone [" + cliente.getTelefone() + "]: ");
        String telefone = tcl.nextLine();
        if (!telefone.isEmpty()) cliente.setTelefone(telefone);

        System.out.print("Email [" + cliente.getEmail() + "]: ");
        String email = tcl.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);

        clienteController.editarDadosPessoais(cliente);
        System.out.println("\nDados atualizados com sucesso!");
    }

    private static void consultarVoucher() {
        System.out.print("\nCódigo do voucher: ");
        String codigo = tcl.nextLine();

        boolean valido = clienteController.consultarVoucher(restaurante, codigo);
        if (valido) {
            double desconto = restaurante.getDescontoVoucher(codigo);
            System.out.printf("\n Voucher válido! Desconto: %.1f%%\n", desconto);
        } else {
            System.out.println("\nVoucher inválido!");
        }
    }

    // ═══════════════════════════════════════════════════════════
    // MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════

    private static int lerInteiro() {
        while (true) {
            try {
                String input = tcl.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                String input = tcl.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um valor válido: ");
            }
        }
    }

	private static String lerSenhaSegura() {
		while (true) {
			String senha = tcl.nextLine();

			if (senha.length() < 8) {
				System.out.print("Senha muito curta. Deve ter pelo menos 8 caracteres. Digite novamente: ");
				continue;
			}
			if (!senha.matches(".*[A-Z].*")) {
				System.out.print("A senha deve ter pelo menos 1 letra MAIÚSCULA. Digite novamente: ");
				continue;
			}
			if (!senha.matches(".*[a-z].*")) {
				System.out.print("A senha deve ter pelo menos 1 letra minúscula. Digite novamente: ");
				continue;
			}
			if (!senha.matches(".*\\d.*")) {
				System.out.print("A senha deve ter pelo menos 1 número. Digite novamente: ");
				continue;
			}

			// Se passou em todos os critérios, retorna
			return senha;
		}
	}

	private static boolean loginUsado(String login) {
		for (Cliente c : clientes) {
			if (c.getLogin().equalsIgnoreCase(login)) {
				return true;
			}
		}
		return false;
	}
}
