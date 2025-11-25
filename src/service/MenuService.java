package service;

import model.HorarioMenu;
import model.ItemCardapio;
import model.Menu;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MenuService {
	private List<Menu> menus = new ArrayList<>();

	public void criarMenu(String nome, LocalTime inicio, LocalTime fim) {

		if (horariosSobrepostos(inicio, fim)) {
			System.out.println("Já existe um menu ativo nesse horário!");
			return;
		}

		HorarioMenu horario = new HorarioMenu(inicio, fim);
		Menu menu = new Menu(nome, horario);
		menus.add(menu);
		System.out.println("Menu criado: " + nome + " (" + horario + ")");
	}

	public void editarMenu(String nomeMenu, String novoNome, LocalTime novaHoraInicio, LocalTime novaHoraFim) {
		for (Menu menu : menus) {
			if (menu.getNome().equalsIgnoreCase(nomeMenu)) {

				if (!novoNome.isEmpty()) {
					menu.setNome(novoNome);
				}

				if (novaHoraInicio != null && novaHoraFim != null) {
					if (horariosSobrepostos(novaHoraInicio, novaHoraFim)) {
						System.out.println("Erro: Novo horário se sobrepõe a outro menu.");
						return;
					}
					menu.getHorario().setHoraInicio(novaHoraInicio);
					menu.getHorario().setHoraFim(novaHoraFim);
				}

				System.out.println("Menu atualizado com sucesso.");
				return;
			}
		}
		System.out.println("Menu não encontrado.");
	}


	public void adicionarItemAoMenu(String nomeMenu, ItemCardapio item) {
		for (Menu menu : menus) {
			if (menu.getNome().equalsIgnoreCase(nomeMenu)) {
				menu.addItem(item);
				System.out.println("Item adicionado ao menu " + nomeMenu);
				return;
			}
		}
		System.out.println("Menu não encontrado.");
	}

	public Menu menuAtual() {
		for (Menu menu : menus) {
			if (menu.getHorario().isHorarioAtual()) {
				return menu;
			}
		}
		return null;
	}

	public List<Menu> listarMenus() {
		return menus;
	}

	private boolean horariosSobrepostos(LocalTime inicio, LocalTime fim) {
		for (Menu menu : menus) {
			LocalTime iniExistente = menu.getHorario().getHoraInicio();
			LocalTime fimExistente = menu.getHorario().getHoraFim();

			if (inicio.isBefore(fimExistente) && fim.isAfter(iniExistente)) {
				return true;
			}
		}
		return false;
	}

}
