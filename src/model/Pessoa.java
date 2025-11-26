package model;
// ID único

public abstract class Pessoa {
	protected String id;
    protected String nome;
    protected String login;
    protected String senha;
    protected String telefone;
	protected String cpf;
	protected String email;

	public String getId() { return id; }

    public Pessoa(String nome, String login, String senha, String telefone, String cpf, String email) {
        this.id = id;
		this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.telefone = telefone;
		this.cpf = cpf;
		this.email = email;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
        return nome;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
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
