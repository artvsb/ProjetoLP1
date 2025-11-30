package service;

import model.Funcionario;
import model.Restaurante;

import java.util.*;

public class FuncionarioService {
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
        System.out.println("Id: " + f.getId());
        System.out.println("Nome: " + f.getNome());
        System.out.println("CPF: " + f.getCpf());
        System.out.println("Telefone: " + f.getTelefone());
        System.out.println("E-mail: " + f.getEmail());
        System.out.println("Login: " + f.getLogin());
        System.out.println("Senha: " + f.getSenha());
    }

    public void listar() {
        for (Funcionario f : funcionarios) {
            System.out.println("Id: " + f.getId());
            System.out.println("Nome: " + f.getNome());
            System.out.println("CPF: " + f.getCpf());
            System.out.println("Telefone: " + f.getTelefone());
            System.out.println("E-mail: " + f.getEmail());
        }
    }

    public void atualizar(String nomePesq, String novoNome, String telPesq, String novoTel, String emailPesq, String novoEmail) {
        for (Funcionario f : funcionarios) {
            if (f.getNome().equalsIgnoreCase(nomePesq)) {
                f.setNome(novoNome);
                System.out.println("Nome atualizado: " + novoNome);
                return;
            }
            if (f.getTelefone().equalsIgnoreCase(telPesq)) {
                f.setTelefone(novoTel);
                System.out.println("Telefone atualizado: " + novoTel);
                return;
            }
            if (f.getEmail().equalsIgnoreCase(emailPesq)) {
                f.setEmail(novoEmail);
                System.out.println("E-mail atualizado: " + novoEmail);
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

    public void cadastrarFuncionario(Funcionario f) {
		String id = f.gerarId();
		f.setId(id);
		funcionarios.add(f);
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
