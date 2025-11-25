package model;
import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<ItemCardapio> menu = new ArrayList<>();
	private String nome;
	private HorarioMenu horario;

	public Menu(String nome, HorarioMenu horario) {
		this.nome = nome;
		this.horario = horario;
	}

	public String getNome() { return nome; }
	public HorarioMenu getHorario() { return horario; }
	public List<ItemCardapio> getMenu() { return menu; }

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setHorario(HorarioMenu horario) {
		this.horario = horario;
	}

	public void addItem(ItemCardapio item) {
		menu.add(item);
	}

	public void exibirMenu() {
		System.out.println("========== " + nome + " ==========");
		for (ItemCardapio item : menu) {
			System.out.println(item);
		}
	}
}