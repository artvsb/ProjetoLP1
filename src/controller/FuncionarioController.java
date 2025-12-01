package controller;

import model.Funcionario;
import model.Restaurante;
import service.FuncionarioService;
import service.RestauranteService;

import java.util.List;

public class FuncionarioController {
    private FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public void cadastrarFuncionario(Funcionario f) {
		funcionarioService.cadastrar(f);
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
