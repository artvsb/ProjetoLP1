package controller;

import model.Funcionario;
import service.FuncionarioService;
import java.util.List;

public class FuncionarioController {
    private FuncionarioService funcionarioService = new FuncionarioService();

    public void cadastrarFuncionario(Funcionario f) {
        funcionarioService.cadastrarFuncionario();
    }
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }
    public void atualizarFuncionario(String nome, String novoNome) {
        funcionarioService.atualizarFuncionario(nome, novoNome);
    }
    public void deletarFuncionario(String nome) {
        funcionarioService.deletarFuncionario(nome);
    }

	public double consultarBonus(Funcionario funcionario) {
		return funcionarioService.consultarBonus(funcionario);
	}

}
