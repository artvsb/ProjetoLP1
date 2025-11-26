package controller;

import controller.ClienteController;

import java.util.Scanner;

public class SistemaController {

	private final Scanner tcl = new Scanner(System.in);
	private final ClienteController pessoaController = new ClienteController();

	public void iniciarSistema() {
		while (true) {
			pessoaController.HomePage();
		}
	}
	// esse métod0 traz a HomePage automaticamente ao colocar return
}
