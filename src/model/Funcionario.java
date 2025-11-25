package model;
import enums.CARGO;
import enums.NivelAcesso;
import enums.Poderes;
import enums.StatusPedido;
import model.interfaces.CustomMenu;
import model.interfaces.Gerenciavel;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Funcionario extends Pessoa implements Gerenciavel, CustomMenu {
    private String especialidade;
    public CARGO cargo;
	private boolean atv;
	private Set<Poderes> poderes;
	private Pedido pedido;
	private NivelAcesso nivelAcesso;

	public Funcionario(String nome, String login, String senha, int telefone, CARGO cargo) {
		super(nome, login, senha, telefone);
		this.cargo = cargo;
		this.atv = true;
		this.poderes = new HashSet<>();
    }

	// talvez apagar depois
    public Funcionario(String nome, String login, String senha, int telefone, String especialidade){
        super(nome, login, senha, telefone);
        this.especialidade = especialidade;
		this.poderes = new HashSet<>();
    }

    public String getEspecialidade() {
        return especialidade;
    }

	public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public CARGO getCargo() {
        return cargo;
    }

	public NivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(NivelAcesso nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public void setCargo(CARGO cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "especialidade='" + especialidade + '\'' +
                ", cargo=" + cargo +
                '}';
    }

	@Override
	public void ativar() {
		this.atv = true;
	}

	@Override
	public void desativar() {
		this.atv = false;
	}

	@Override
	public boolean isAtv() {
		return atv;
	}

	public boolean checkPoderes(Poderes p) { return poderes.contains(p); }

	public void concederPoderes(Poderes p) { poderes.add(p); }

	public void removerPoderes(Poderes p) { poderes.remove(p); }

	public void aceitarPedido(Pedido pp) {
		this.pedido = pp;
		if (checkPoderes(Poderes.ACEITAR_PEDIDO)) {
			pp.setStatusPedido(StatusPedido.PRONTO);
			System.out.println("Pedido aceito e está agora PRONTO.");
		} else {
			System.out.println("Permissão negada para aceitar pedidos.");
		}
	}

	public void recusarPedido(Pedido pedido, String justificativa) {
		if (checkPoderes(Poderes.REJEITAR_PEDIDO)) {
			pedido.setStatusPedido(StatusPedido.CANCELADO);
			System.out.println("Pedido foi recusado. Justificativa: " + justificativa);
		} else {
			System.out.println("Permissão negada para recusar pedidos.");
		}
	}

	@Override
	public void addItemMenu(Menu menu, Scanner tcl) {

	}

	@Override
	public void delItemMenu(Menu menu, Scanner tcl) {

	}
}
