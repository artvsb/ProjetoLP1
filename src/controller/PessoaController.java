package controller;

import model.Pessoa;
import service.PessoaService;

import java.util.Scanner;

public class PessoaController {
	private PessoaService pessoaService = new PessoaService();
	private Scanner tcl = new Scanner(System.in);

	public void cadastrarPessoa() {
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

		Pessoa novo = pessoaService.cadastrarPessoa(nome, cpf, login, telefone, email, senha);

		if (novo != null) {
			System.out.println("Cadastro realizado com sucesso.");
		} else {
			System.out.println("Falha no cadastro. CPF ou Login já cadastrados.");
		}
	}

	public void HomePage() {
		int opcao = -1;

		while (opcao != 3) {
			System.out.println("\n===========================================");
			System.out.println("===========     MENU DIGITAL     ===========");
			System.out.println("===========================================");
			System.out.println("1. Entrar");
			System.out.println("2. Registrar-se");
			System.out.println("3. Sair");
			System.out.print("Escolha uma opção: ");

			try {
				opcao = Integer.parseInt(tcl.nextLine());

				switch (opcao) {
					case 1:
						realizarLogin();
						break;
					case 2:
						cadastrarPessoa();
						break;
					case 3:
						System.out.println("Encerrando o sistema. Até logo!");
						break;
					default:
						System.out.println("Opção inválida. Tente novamente.");
				}

			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Digite um número.");
			}
		}
	}

	// Stubs de métodos que você irá implementar


	public void realizarLogin() {
		System.out.println("\n==== LOGIN ====");
		System.out.print("Digite seu login ou CPF: ");
		String loginOuCpf = tcl.nextLine();

		System.out.print("Digite sua senha: ");
		String senha = tcl.nextLine();

		Pessoa pessoa = pessoaService.autenticar(loginOuCpf, senha);
		if (pessoa != null) {
			System.out.println("Login realizado com sucesso. Bem-vindo(a), " + pessoa.getNome() + "!");
			// Aqui você pode redirecionar para menu de cliente ou funcionário
		} else {
			System.out.println("Falha na autenticação. Verifique suas credenciais.");
		}
	}

}
