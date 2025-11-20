package model;
import java.util.UUID;
// ID único

public abstract class Pessoa {
	private UUID id;
    protected String nome;
    protected String login;
    protected String senha;
    protected int telefone;

	public UUID getId() { return id; }

	public Pessoa() { this.id = UUID.randomUUID(); }

    public Pessoa(String nome, String login, String senha, int telefone) {
        this.id = UUID.randomUUID();
		this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", telefone=" + telefone +
                '}';
    }
}
