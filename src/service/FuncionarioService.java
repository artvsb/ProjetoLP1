package service;

import model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioService {
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();

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
}
