package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Menu {
	private LocalDateTime inicioHorarioMenu, fimHorarioMenu;
	private String nomeMenu;
	private List<ItemCardapio> itens;

	public Menu(String nomeMenu, LocalDateTime inicioHorarioMenu, LocalDateTime fimHorarioMenu) {
		this.nomeMenu = nomeMenu;
		this.inicioHorarioMenu = inicioHorarioMenu;
		this.fimHorarioMenu = fimHorarioMenu;
		this.itens = new ArrayList<>();
	}

	public void adicionarItemCardapio(String categoria, ItemCardapio itemCardapio) {
		itens.add(itemCardapio);
	}

	public boolean isAtivoAgora() {
		LocalTime agora = LocalTime.now();
		return (agora.isAfter(inicioHorarioMenu) || agora.equals(inicioHorarioMenu)) &&
				(agora.isBefore(fimHorarioMenu) || agora.equals(fimHorarioMenu));
	}

	public LocalDateTime getInicioHorarioMenu() {
		return inicioHorarioMenu;
	}

	public void setInicioHorarioMenu(LocalDateTime inicioHorarioMenu) {
		this.inicioHorarioMenu = inicioHorarioMenu;
	}

	public LocalDateTime getFimHorarioMenu() {
		return fimHorarioMenu;
	}

	public void setFimHorarioMenu(LocalDateTime fimHorarioMenu) {
		this.fimHorarioMenu = fimHorarioMenu;
	}

	public String getNomeMenu() {
		return nomeMenu;
	}

	public void setNomeMenu(String nomeMenu) {
		this.nomeMenu = nomeMenu;
	}
}
