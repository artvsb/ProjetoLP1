package controller;

import model.ItemCardapio;
import model.Menu;
import service.MenuService;

import java.time.LocalTime;
import java.util.Scanner;

public class MenuController {
	private MenuService menuService = new MenuService();
	private Scanner tcl = new Scanner(System.in);

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
		this.tcl = new Scanner(System.in);
	}

	public void exibirOpcoesAdm() {
		int opcao;
		do {
			System.out.println("\n===== MENU DE ADMINISTRAÇÃO =====");
			System.out.println("1 - Criar novo menu");
			System.out.println("2 - Adicionar item ao menu");
			System.out.println("3 - Editar menu existente");
			System.out.println("4 - Listar menus");
			System.out.println("0 - Voltar");

			opcao = tcl.nextInt();
			tcl.nextLine();

			switch (opcao) {
				case 1 : criarNovoMenu();
				case 2 : adicionarItemAoMenu();
				case 3 : editarMenu();
				case 4 : listarMenus();
				case 0 : System.out.println("Retornando ao menu principal...");
				default : System.out.println("Opção inválida.");
			}

		} while (opcao != 0);
	}

	public void criarNovoMenu() {
		System.out.println("Nome do menu: ");
		String nome = tcl.nextLine();

		System.out.println("Hora de início (HH:MM): ");
		LocalTime inicio = LocalTime.parse(tcl.nextLine());

		System.out.println("Hora de fim (HH:MM): ");
		LocalTime fim = LocalTime.parse(tcl.nextLine());

		menuService.criarMenu(nome,inicio, fim);
	}

	private void editarMenu() {
		System.out.println("Nome do menu a editar:");
		String nomeMenu = tcl.nextLine();

		System.out.println("Novo nome do menu (pressione Enter para manter):");
		String novoNome = tcl.nextLine();

		System.out.println("Nova hora de início (HH:MM ou Enter):");
		String novaHoraInicioStr = tcl.nextLine();

		System.out.println("Nova hora de fim (HH:MM ou Enter):");
		String novaHoraFimStr = tcl.nextLine();

		LocalTime novaHoraInicio = novaHoraInicioStr.isEmpty() ? null : LocalTime.parse(novaHoraInicioStr);
		LocalTime novaHoraFim = novaHoraFimStr.isEmpty() ? null : LocalTime.parse(novaHoraFimStr);

		menuService.editarMenu(nomeMenu, novoNome, novaHoraInicio, novaHoraFim);
	}

	private void listarMenus() {
		System.out.println("\n====== MENUS REGISTRADOS ======");
		menuService.listarMenus().forEach(menu -> {
			System.out.println("Menu: " + menu.getNome());
			System.out.println("Horário: " + menu.getHorario());
			System.out.println("Itens:");
			menu.getMenu().forEach(item -> {
				System.out.printf("- %s | R$ %.2f | %d min\n", item.getNome(), item.getPreco(), item.getTempoPreparo());
				System.out.println("  " + item.getDescricao());
			});
			System.out.println("-------------------------------");
		});
	}

	public void adicionarItemAoMenu() {
		System.out.println("Nome do menu: ");
		String nomeMenu = tcl.nextLine();

		System.out.println("Nome do prato: ");
		String nome = tcl.nextLine();

		System.out.println("Preço: ");
		double preco = tcl.nextDouble();
		tcl.nextLine();

		System.out.println("Tempo de preparo (minutos): ");
		int tempo = tcl.nextInt();
		tcl.nextLine();

		System.out.println("Descrição: ");
		String descricao = tcl.nextLine();

		ItemCardapio item = new ItemCardapio(nome, preco, tempo, descricao);
		menuService.adicionarItemAoMenu(nomeMenu, item);
	}

	public void exibirMenuAtual() {
		Menu menu = menuService.menuAtual();
		if (menu != null) {
			menu.exibirMenu();
		} else {
			System.out.println("Nenhum menu ativo no momento.");
		}
	}
}
