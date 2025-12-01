package service;

import model.Funcionario;
import model.interfaces.IDGenerator;

import java.util.*;

public class FuncionarioService implements IDGenerator {
	private Set<String> idsFuncionario = new HashSet<>();
	private Random random = new Random();
	private List<Funcionario> funcionarios = new ArrayList<>();


	public FuncionarioService() {
		this.idsFuncionario = new HashSet<>();
		this.random = new Random();
		this.funcionarios = new ArrayList<>();
	}

	public void cadastrar(Funcionario f) {
		f.setId(gerarId()); // usa o mét0do da interface, implementado logo abixo
		funcionarios.add(f);
	}

	@Override
	public String gerarId() {
		String id;
		do {
			StringBuilder sb = new StringBuilder("F");
			for (int i = 0; i < 7; i++) {
				sb.append(random.nextInt(10));
			}
			id = sb.toString();
		} while (idsFuncionario.contains(id));

		idsFuncionario.add(id);
		return id;
	}


	public void listar() {
        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome());
        }
    }

    public void atualizarFuncionario(String nomepesq, String novoNome) {
        for (Funcionario f : funcionarios) {
            if (f.getNome().equalsIgnoreCase(nomepesq)) {
                f.setNome(novoNome);
                System.out.println("Nome atualizado: " + novoNome);
                return;
            }
        }
        System.out.println("Funcionário não encontrado.");
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }

    public void deletarFuncionario(String nome) {
		for (Funcionario f : funcionarios) {
			if (f.getNome().equalsIgnoreCase(nome)) {
				funcionarios.remove(f);
				System.out.println("Removido com sucesso.");
			}
		}
		System.out.println("Funcionário não encontrado.");
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
