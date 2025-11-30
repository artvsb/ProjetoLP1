package model;
import enums.CARGO;
import enums.NivelAcesso;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Funcionario extends Pessoa {
    protected String id;
	private boolean recebeBonus;
	private double totalBonus;
    protected CARGO cargo;
	private boolean ativo;
	private NivelAcesso nivelAcesso;
	private boolean acessoCozinha;
	private Set<String> idsGerados = new HashSet<>();
	private Random random;



	public Funcionario(String nome, String login, String senha, String telefone, String cpf, String email) {
		super(nome, login, senha, telefone, cpf, email);
		this.cargo = cargo;
		this.ativo = true;
    }

	/* talvez apagar depois
    public Funcionario(String nome, String login, String senha, String telefone, String CARGO){
        super(nome, login, senha, telefone);
        this.CARGO = CARGO;
		this.poderes = new HashSet<>();
    } */

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String gerarId(Funcionario funcionario) {
		String id;
		do {
			StringBuilder sb = new StringBuilder("F");
			for (int i = 0; i < 7; i++) {
				sb.append(random.nextInt(10)); // gera de 0 a 9
			}
			id = sb.toString();
		} while (idsGerados.contains(id)); // evita duplicados

		idsGerados.add(id);
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
                "CARGO='" + cargo +
                '}';
    }

	public boolean isRecebeBonus() {
		return recebeBonus;
	}

	public void setRecebeBonus(boolean recebeBonus) {
		this.recebeBonus = recebeBonus;
	}

	public double getTotalBonus() {
		return totalBonus;
	}

	public void adicionarBonus(double valor) {
		this.totalBonus += valor;
	}

	public double consultarBonus() {
		return totalBonus;
	}

	public void entregarPedido(Pedido pedido) {
		if (pedido != null && !pedido.isEntregue()) {
			pedido.setEntregue();

			if (recebeBonus) {
				double bonus = pedido.getTotal() * 0.05; // 5% do valor do pedido
				adicionarBonus(bonus);
			}
		}
	}

	// Ativar
	public void ativarAcessoCozinha() {
		this.acessoCozinha = true;
	}

	// Desativar
	public void desativarAcessoCozinha() {
		this.acessoCozinha = false;
	}

	public boolean temAcessoCozinha() {
		return acessoCozinha;
	}

}
