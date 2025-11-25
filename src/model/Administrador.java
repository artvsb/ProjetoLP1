package model;

import enums.CARGO;
import enums.NivelAcesso;
import enums.Poderes;
import model.interfaces.CustomMenu;
import model.interfaces.Gerenciavel;
import model.interfaces.VisualizarPerfil;

import java.util.Scanner;

public class Administrador extends Pessoa implements Gerenciavel, CustomMenu, VisualizarPerfil {
	private NivelAcesso nivelAcesso;
	private boolean atv;
	private Restaurante rr;

	public Administrador(String nome, String login, String senha, int telefone, NivelAcesso nivelAcesso, Restaurante rr) {
			super(nome, login, senha, telefone);
			this.nivelAcesso = nivelAcesso;
			this.atv = true;
			this.rr = rr;
	}

	public Restaurante rr() { return rr; }

	public void darPoderes(Funcionario ff) {
		if (ff.getCargo() == CARGO.COZINHA) {
			ff.concederPoderes(Poderes.ACEITAR_PEDIDO);
			ff.concederPoderes(Poderes.REJEITAR_PEDIDO);
			System.out.println("Permissões básicas atribuídas a " + ff.getNome());
		} else if (ff.getCargo() == CARGO.GERENCIA){
			ff.concederPoderes(Poderes.ACEITAR_PEDIDO);
			ff.concederPoderes(Poderes.REJEITAR_PEDIDO);
			ff.concederPoderes(Poderes.CONCEDER_ACESSO_COZINHA);
			ff.concederPoderes(Poderes.CONCEDER_DESCONTO);
			ff.concederPoderes(Poderes.CONCEDER_BRINDES);
			ff.concederPoderes(Poderes.CONCEDER_BONUS_FUNCIONARIO);
			System.out.println("Permissões administrativas atribuídas a " + ff.getNome());
		} else {
			System.out.println("Cargo não elegível para permissões especiais!");
		}
	}

	public NivelAcesso getNivelAcesso() { return nivelAcesso; }

	public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

	public boolean isAtv() { return atv; }

	@Override
	public void desativar() { this.atv = false; }

	@Override
	public void ativar() { this.atv = true; }

	@Override
	public String toString() {
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + login + "\nTelefone: " + telefone + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + atv;
	}

	@Override
	public void addItemMenu(Menu menu, Scanner tcl) {
		if (nivelAcesso != NivelAcesso.PROPRIETARIO && nivelAcesso != NivelAcesso.GERENCIA) {
			System.out.println("===== PERMISSÃO NEGADA =====");
			System.out.println("Opção reservada à GERÊNCIA");
			return;
		} else {
			System.out.println("===== ADICIONAR PRATO =====");
			System.out.println("Insira o nome do item: ");
			String nomePrato = tcl.nextLine();

			System.out.println("Breve descrição do item: ");
			String descricao = tcl.nextLine();

			System.out.println("Preço(R$) : ");
			double preco = tcl.nextDouble();

			System.out.println("Tempo de preparo médio (min): ");
			int tempoPreparo = tcl.nextInt();

			menu.addItem(new ItemCardapio(nome, preco, tempoPreparo, descricao));
			System.out.println("Prato adicionado com sucesso!");
		}
	}

	@Override
	public void delItemMenu(Menu menu, Scanner tcl) {
		if (nivelAcesso != NivelAcesso.PROPRIETARIO && nivelAcesso != NivelAcesso.GERENCIA) {
			System.out.println("===== PERMISSÃO NEGADA =====");
			System.out.println("Opção reservada à GERÊNCIA");
			return;
		} else {
			menu.exibirMenu();
			System.out.println("Digite o número do item a remover: ");
			int indice = tcl.nextInt();

			if (indice < 1 || indice > menu.getMenu().size()) {
				System.out.println("Número Inválido!");
				return;
			}

			ItemCardapio deletado = menu.getMenu().remove(indice - 1);
			System.out.println("Item removido: " + deletado.getNome());
		}
	}

	@Override
	public void editarItemMenu(Menu menu, Scanner tcl) {
		if (nivelAcesso != NivelAcesso.PROPRIETARIO && nivelAcesso != NivelAcesso.GERENCIA) {
			System.out.println("===== PERMISSÃO NEGADA =====");
			System.out.println("Opção reservada à GERÊNCIA");
			return;
		}

		if (menu.getMenu().isEmpty()) {
			System.out.println("Menu está vazio!");
			return;
		}

		menu.exibirMenu();
		System.out.println("Digite o número do item que deseja editar: ");
		int indice = tcl.nextInt();
		tcl.nextLine(); // consumir quebra de linha

		if (indice < 1 || indice > menu.getMenu().size()) {
			System.out.println("Número Inválido!");
			return;
		}

		ItemCardapio item = menu.getMenu().get(indice - 1);

		System.out.println("Editando item: " + item.getNome());

		System.out.print("Novo nome (ou pressione Enter para manter): ");
		String novoNome = tcl.nextLine();
		if (!novoNome.isBlank()) {	item.setNome(novoNome);	}

			System.out.print("Nova descrição (ou pressione Enter para manter): ");
			String novaDescricao = tcl.nextLine();
			if (!novaDescricao.isBlank()) { item.setDescricao(novaDescricao); }

			System.out.print("Novo preço (ou -1 para manter): ");
			double novoPreco = tcl.nextDouble();
			if (novoPreco >= 0) { item.setPreco(novoPreco); }

			System.out.print("Novo tempo de preparo (minutos, ou -1 para manter): ");
			int novoTempo = tcl.nextInt();
			if (novoTempo >= 0) { item.setTempoPreparo(novoTempo); }

			System.out.println("Item atualizado com sucesso!");
	}

	public void definirTxEntregaPrioritaria(Scanner tcl) {
		if (nivelAcesso == NivelAcesso.PROPRIETARIO || nivelAcesso == NivelAcesso.GERENCIA) {
			System.out.println("Apenas gestores podem definir a taxa de entrega prioritária.");
			return;
		}

		System.out.println("\n==== DEFINIR TAXA DE ENTREGA PRIORITÁRIA ====");
		System.out.print("Informe a nova taxa de entrega prioritária (%): ");

		int percentual;
		try {
			percentual = Integer.parseInt(tcl.nextLine());

			if (percentual < 0 || percentual > 50) {
				System.out.println("Taxa inválida. Digite um valor entre 0 e 50%");
				return;
			}

			rr.setTxEntregaPrioritaria(percentual);

		} catch (NumberFormatException e) {
			System.out.println("Valor inválido. Por favor, insira um número inteiro.");
		}
	}

}



