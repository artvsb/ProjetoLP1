package model;

import enums.CARGO;
import enums.NivelAcesso;
import enums.Poderes;
import model.interfaces.CustomMenu;
import model.interfaces.Gerenciavel;
import model.interfaces.VisualizarPerfil;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Administrador extends Pessoa implements Gerenciavel, CustomMenu, VisualizarPerfil {
	private NivelAcesso nivelAcesso;
	private boolean atv;
	private Restaurante rr;

	public Administrador(String nome, String login, String senha, String telefone, NivelAcesso nivelAcesso) {
		super();
		this.atv = true;
	}

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
		return "Administrador: " + "\nNome: " + nome + "\nLogin: " + "\nTelefone: " + "\nNível de Acesso: " + nivelAcesso + "\nAtivo: " + atv;
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

			menu.addItem(new ItemPedido(nomePrato, preco, 1, tempoPreparo, descricao));
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

			if (indice < 1 || indice > menu.getItens().size()) {
				System.out.println("Número Inválido!");
				return;
			}

			ItemPedido deletado = menu.getItens().remove(indice - 1);
			System.out.println("Item removido: " + deletado.getNome());
		}
	}

	@Override
	public void visualizarPerfil(Cliente cc, Map<UUID, Restaurante> restaurantes) {
		UUID idRestaurant = rr.getIdRestaurant();

		if (!cc.frequentouRestaurante(idRestaurant)) {
			System.out.println("O cliente selecionado não pode ser exibido!");
			return;
		}

		System.out.println("\n=== PERFIL DO CLIENTE ===");
		System.out.println("Nome: " + cc.getNome());
		System.out.println("Histórico de restaurantes frequentados:");

		for (UUID id : cc.getHistoricoRestaurantes()) {
			Restaurante r = restaurantes.get(id);
			if (r != null) {
				System.out.println("- " + r.getNome() +
						"\nEndereço: " + r.getEndereco());
			} else {
				System.out.println("Restaurante não encontrado!");
			}
		}
	}
}
