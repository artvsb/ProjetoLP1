package model;

import enums.CARGO;
import enums.NivelAcesso;
import enums.Poderes;
import model.interfaces.CustomMenu;
import model.interfaces.Gerenciavel;

import java.util.Scanner;

public class Administrador extends Pessoa implements Gerenciavel, CustomMenu {
	private NivelAcesso nivelAcesso;
	private boolean atv;

	public Administrador(String nome, String login, String senha, String telefone, NivelAcesso nivelAcesso) {
		super();
		this.atv = true;
	}

	public Administrador(String nome, String login, String senha, int telefone, NivelAcesso nivelAcesso) {
			super(nome, login, senha, telefone);
			this.nivelAcesso = nivelAcesso;
			this.atv = true;
	}

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
			System.out.println("Insira o nome do prato: ");
			String nomePrato = tcl.nextLine();

			System.out.println("Preço(R$) : ");
			double preco = tcl.nextDouble();

			System.out.println("Tempo de preparo médio (min): ");
			int tempoPreparo = tcl.nextInt();

			menu.addItem(new ItemPedido(nomePrato, preco, 1, tempoPreparo));
			System.out.println("Prato adicionado com sucesso!");
		}
	}

	@Override
	public void delItemMenu(Menu menu, Scanner tcl) {

	}
}
