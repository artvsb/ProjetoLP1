package service;

import java.util.*;

import model.Pessoa;
import model.Cliente;
import model.interfaces.GerarID;

public class PessoaService implements GerarID {

	private Map<String, Pessoa> usuariosPorLogin;
	private Map<String, Pessoa> usuariosPorCpf;
	private Map<String, Pessoa> usuariosPorId;
	private Set<Integer> idsGerados;
	private Random random;

	public PessoaService() {
		this.usuariosPorLogin = new HashMap<>();
		this.usuariosPorCpf = new HashMap<>();
		this.usuariosPorId = new HashMap<>();
		this.idsGerados = new HashMap<>();
		this.random = new Random();

	}

	@Override
	private String gerarID() {
		String id;
		do {
			id = 1_000_000 + random.nextInt(9_000_000); // de 1000000 até 9999999
		} while (idsGerados.contains(id));
		idsGerados.add(id);
		return id;
	}

	public Pessoa cadastrarPessoa(String nomeCompleto, String cpf, String login, String telefone, String email, String senha) {
		if (usuariosPorLogin.containsKey(login) || usuariosPorCpf.containsKey(cpf)) {
			return null; // CPF ou login já existem
		}

		Cliente novoCliente = new Cliente(nomeCompleto, cpf, login, telefone, email, senha);
		usuariosPorLogin.put(login, novoCliente);
		usuariosPorCpf.put(cpf, novoCliente);
		return novoCliente;
	}

	public Pessoa autenticar(String loginOuCpf, String senha) {
		Pessoa pessoa = usuariosPorLogin.getOrDefault(loginOuCpf, usuariosPorCpf.get(loginOuCpf));
		if (pessoa != null && pessoa.getSenha().equals(senha)) {
			return pessoa;
		}
		return null;
	}


}

