package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	private List<ItemPedido> itens;
	private String nomePrato;

	public List<ItemPedido> getItens() { return itens; }

	public Menu() { this.itens = new ArrayList<>(); }

	public void addItem(ItemPedido item) { itens.add(item); }

	public void exibirMenu() {
		System.out.println("================== MENU ==================");
		if (itens.isEmpty()) {
			System.out.println("Cardápio Vazio!");
		} else {
			for (int i = 0; i < itens.size(); i++) {
				ItemPedido item = itens.get(i);
				System.out.printf("%d - %s | R$ %.2f | %d min\n",
						i + 1,
						item.getNome(),
						item.getPreco(),
						item.getTempoPreparo(),
						item.getDescricao()
				);
				System.out.println("Descrição: " + item.getDescricao());
				System.out.println("---------------------------------------");
			}
		}

		System.out.println("==========================================");
	}

	public String getNomePrato() {	return nomePrato;	}

	public void setNomePrato(String nomePrato) {	this.nomePrato = nomePrato;		}
}
