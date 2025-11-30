package controller;

import model.Cliente;
import model.Administrador;
import model.Restaurante;
import service.ClienteService;
import service.FuncionarioService;
import service.RestauranteService;

import java.util.Scanner;

public class SistemaController {

	private final Scanner tcl = new Scanner(System.in);
	private final ClienteController pessoaController = new ClienteController();
	private final RestauranteService restauranteService = new RestauranteService();
	private final ClienteService clienteService = new ClienteService();
	private final FuncionarioService funcionarioService = new FuncionarioService();

}
