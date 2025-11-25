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
		int telefone = Integer.parseInt(tcl.nextLine());

		Cliente novo = new Cliente(nome, login, senha, telefone, null);
		clientes.add(novo);

		System.out.println("Cliente cadastrado com sucesso!");
		return novo;
	}
}
