package model;
import enums.CARGO;

public class Funcionario extends Pessoa {
    private String especialidade;
    public CARGO cargo;

    public Funcionario() {

    }

    public Funcionario(String nome, String login, String senha, int telefone, String especialidade){
        super(nome, login, senha, telefone);
        this.especialidade = especialidade;
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
}
