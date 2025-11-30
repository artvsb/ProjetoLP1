package model;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Funcionario extends Pessoa {
    protected String id;
	private boolean recebeBonus;
	private double totalBonus;
	private boolean ativo;
	private boolean acessoCozinha;
	private Set<String> idsFuncionario = new HashSet<>();
	private Random random;
	private Map<String, Funcionario> funcionariosPorId;
	private Map<String, Funcionario> funcionariosPorCpf;
	private Map<String, Funcionario> funcionarioPorEmail;
	private Restaurante restaurante;

	public Funcionario(String nome, String login, String senha, String telefone, String cpf, String email) {
		super(nome, login, senha, telefone, cpf, email);
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

	/* public void setId(String id) {
		this.id = id;
	} deve ser removida, pois já existe o métod0 gerarId() em Service */


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

	@Override
	public String toString() {
		return "Funcionario{" +
				"id='" + id + '\'' +
				", recebeBonus=" + recebeBonus +
				", totalBonus=" + totalBonus +
				", ativo=" + ativo +
				", acessoCozinha=" + acessoCozinha +
				", idsFuncionario=" + idsFuncionario +
				", random=" + random +
				", funcionariosPorId=" + funcionariosPorId +
				", funcionariosPorCpf=" + funcionariosPorCpf +
				", funcionarioPorEmail=" + funcionarioPorEmail +
				", restaurante=" + restaurante +
				'}';
	}

}

