package model;
import enums.CARGO;
import enums.Poderes;
import model.interfaces.Gerenciavel;

import java.util.HashSet;
import java.util.Set;

public class Funcionario extends Pessoa implements Gerenciavel {
    private String especialidade;
    public CARGO cargo;
	private boolean atv;
	private Set<Poderes> poderes;

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
}
