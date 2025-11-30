package service;

import model.Funcionario;

import java.util.*;

public class FuncionarioService implements Cadastro<Funcionario> {
    private ArrayList<Funcionario> funcionarios;
	private Random random;
	private Set<String> idsGerados;

	public FuncionarioService() {
		this.idsGerados = new HashSet<>();
		this.random = new Random();
		this.funcionarios = new ArrayList<>();
	}

	public void cadastrar(Funcionario f) {
        funcionarios.add(f);
        System.out.println("Funcionário cadastrado: " + f.getNome());
    }

    public void listar() {
        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome());
        }
    }

    public void atualizar(String nomepesq, String novoNome) {
        for (Funcionario f : funcionarios) {
            if (f.getNome().equalsIgnoreCase(nomepesq)) {
                f.setNome(novoNome);
                System.out.println("Nome atualizado: " + novoNome);
                return;
            }
        }
        System.out.println("Funcionário não encontrado.");
    }

    public void deletar(String nome) {
        for (Funcionario f : funcionarios) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                funcionarios.remove(f);
                System.out.println("Removido com sucesso.");
            }
        }
        System.out.println("Funcionário não encontrado.");
    }

    public List<Funcionario> listarFuncionarios() {
        return null;
    }

    public void atualizarFuncionario(String nome, String novoNome) {
    }

    public void deletarFuncionario(String nome) {
    }

    public void cadastrarFuncionario() {
    }

	@Override
	public void salvar(Funcionario funcionario) {
		String id = gerarId();
		funcionario.setId(id);
		funcionarios.add(funcionario);
		System.out.println("Funcionário cadastrado com ID: " + id);
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public Funcionario buscarPorId(String id) {
		return funcionarios.stream()
				.filter(f -> f.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public double consultarBonus(Funcionario funcionario) {
		if (funcionario != null) {
			return funcionario.consultarBonus();
		}
		return 0.0;
	}


}
