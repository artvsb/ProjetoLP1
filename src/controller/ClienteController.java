package controller;

import model.Cliente;
import java.util.List;
import java.util.Scanner;

public class ClienteController {

	private Scanner tcl = new Scanner(System.in);

	public Cliente cadastrarCliente(List<Cliente> clientes) {
		System.out.println("===== CADASTRO DE CLIENTE =====");

		System.out.print("Nome: ");
		String nome = tcl.nextLine();

		System.out.println("CPF: ");
		String cpf = tcl.nextLine();

		for (Cliente c : clientes) {
			if (c.getCpf().equals(cpf)) {
				System.out.println("CPF já cadastrado!");
				return null;
			}
		}

		System.out.println("E-mail: ");
		String email = tcl.nextLine();

		System.out.println("Login: ");
		String login = tcl.nextLine();

		System.out.print("Senha: ");
		String senha = tcl.nextLine();

		System.out.print("Telefone: ");
		int telefone = Integer.parseInt(tcl.nextLine());

		Cliente novo = new Cliente(nome, login, senha, telefone, cpf, email);
		clientes.add(novo);

		System.out.println("Cliente cadastrado com sucesso!");
		System.out.println("ID nr. " + );
		return novo;
	}
}
