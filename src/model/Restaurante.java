package model;

import java.util.*;
import java.util.stream.Collectors;
import enums.StatusPedido;
import model.interfaces.GerarID;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
	private Map<String, String> mapaMesas = new HashMap<>();
	private List<Pedido> pedidos = new ArrayList<>();
	private String id, nome, cnpj, telefone, email, endereco;
	private Administrador administrador;
	private List<Funcionario> funcionarios;
	private Map<String, Restaurante> restaurantes = new HashMap<>();

	public Map<String, String> getMapaMesas() { return mapaMesas; }

	public Restaurante(String nome, String cnpj, String telefone, String email, String endereco) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public void adicionarMesa(String qrCode, String numeroMesa) {
		mapaMesas.put(qrCode, numeroMesa);
	}

	public void adicionarPedido(Pedido pedido) {
		if (pedido != null) {
			pedidos.add(pedido);
		}
	}

	public List<Pedido> getPedidosAtivos() {
		return pedidos.stream()
				.filter(p -> p.getStatusPedido() == StatusPedido.EM_PREPARO || p.getStatusPedido() == StatusPedido.PRONTO)
				.collect(Collectors.toList());
	}

	public List<Pedido> getTodosPedidos() {
		return pedidos;
	}

	public Map<String, Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getNome() {
		return nome;
	}

	public String getId() {
		return id;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
}
