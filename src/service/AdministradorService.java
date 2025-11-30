package service;

import model.*;
import model.interfaces.IDGenerator;

import java.util.*;

public class AdministradorService implements IDGenerator {
	private Restaurante restaurante;
	private List<Restaurante> restaurantesGeral = new ArrayList<>();
	private final Set<String> idsAdministrador = new HashSet<>();
	private final Random random = new Random();
	private final List<Administrador> administradores = new ArrayList<>();


	public AdministradorService(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	// ======== ADMIN ========

	public void cadastrarAdministrador(Administrador a) {
		a.setId(gerarId());
		administradores.add(a);
	}

	public List<Administrador> listarAdministradores() {
		return new ArrayList<>(administradores);
	}

	public Administrador buscarPorId(String id) {
		return administradores.stream()
				.filter(a -> id.equals(a.getIdAdministrador()))
				.findFirst()
				.orElse(null);
	}

	// ========== RESTAURANTE ==================

	public void criarRestaurante(Restaurante restaurante) {
		if (restaurante != null) {
			restaurantesGeral.add(restaurante);
		}
	}

	public boolean removerRestaurante(String idRestaurante) {
		return restaurantesGeral.removeIf(r -> r.getId().equals(idRestaurante));
	}

	public List<Restaurante> getRestaurantes() {
		return restaurantesGeral;
	}

	public Restaurante buscarRestauranteId(String id) {
		return restaurantesGeral.stream()
				.filter(r -> r.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public Restaurante buscarRestauranteNome(String nome) {
		if (nome == null || nome.isBlank()) {
			return null;
		}

		for (Restaurante r : restaurantesGeral) {
			if (r.getNome().equalsIgnoreCase(nome)) {
				return r;
			}
		}
		return null;
	}

	public Restaurante buscarRestauranteCnpj(String cnpj) {
		if (cnpj == null || cnpj.isBlank()) {
			return null;
		}

		for (Restaurante r : restaurantesGeral) {
			if (r.getCnpj().equals(cnpj)) {
				return r;
			}
		}
		return null;
	}

	// ================== MENU ==================

	public void criarMenu() {
		List<ItemCardapio> novoMenu = new ArrayList<>();
		restaurante.setMenu(novoMenu);
	}

	public void adicionarItemAoMenu(String nome, String descricao, double preco, int tempoPreparo) {
		List<ItemCardapio> menu = restaurante.getMenu();
		if (menu == null) {
			menu = new ArrayList<>();
			restaurante.setMenu(menu);
		}
		menu.add(new ItemCardapio(nome, descricao, preco, tempoPreparo));
	}

	public void editarMenu(ItemCardapio item, String novoNome, double novoPreco, String novaDescricao, int novoTempoPreparo) {
		item.setNome(novoNome);
		item.setPreco(novoPreco);
		item.setDescricao(novaDescricao);
		item.setTempoPreparo(novoTempoPreparo);
	}

	public void deletarItem(ItemCardapio item) {
		if (item != null && restaurante.getMenu() != null) {
			restaurante.getMenu().remove(item);
		}
	}

	// ================== MESAS ==================
	public void cadastrarMesa(String qrCode, String numeroMesa) {
		if (qrCode != null && numeroMesa != null) {
			restaurante.getMapaMesas().put(qrCode, numeroMesa);
		}
	}

	public void editarMesa(String qrCodeAntigo, String novoNumeroMesa) {
		if (qrCodeAntigo != null && novoNumeroMesa != null) {
			if (restaurante.getMapaMesas().containsKey(qrCodeAntigo)) {
				restaurante.getMapaMesas().put(qrCodeAntigo, novoNumeroMesa);
			}
		}
	}

	public void removerMesa(String numeroMesa) {
		if (numeroMesa != null) {
			String qrCode = null;
			for (Map.Entry<String, String> entry : restaurante.getMapaMesas().entrySet()) {
				if (entry.getValue().equals(numeroMesa)) {
					qrCode = entry.getKey();
					break;
				}
			}
			if (qrCode != null) {
				restaurante.getMapaMesas().remove(qrCode);
			}
		}
	}

	// ================== ACESSO ==================
	public void gerenciarAcessos(String idFuncionario, boolean concederAcesso) {
		if (idFuncionario == null) {
			return;
		}
		for (Funcionario f : restaurante.getFuncionarios()) {
			if (f.getId().equals(idFuncionario)) {
				if (concederAcesso) {
					f.ativarAcessoCozinha();
				} else {
					f.desativarAcessoCozinha();
				}
				break;
			}
		}
	}

	// ================== BÔNUS FUNCIONÁRIO ==================
	public void concederBonusFuncionario(Funcionario funcionario) {
		if (funcionario != null && !funcionario.isRecebeBonus()) {
			funcionario.setRecebeBonus(true);
		}
	}

	public Map<Funcionario, Double> consultarBonusFuncionarios() {
		Map<Funcionario, Double> bonusMap = new HashMap<>();
		for (Funcionario funcionario : restaurante.getFuncionarios()) {
			bonusMap.put(funcionario, funcionario.getTotalBonus());
		}
		return bonusMap;
	}

	// ================== DADOS RESTAURANTE ==================
	public void editarDadosRestaurante(Restaurante dadosAtualizados) {
		if (dadosAtualizados == null) return;

		restaurante.setNome(dadosAtualizados.getNome());
		restaurante.setTelefone(dadosAtualizados.getTelefone());
		restaurante.setEmail(dadosAtualizados.getEmail());
	}

	// ================== PEDIDOS ==================
	public boolean cancelarPedido(Pedido pedido) {
		if (pedido != null && pedido.podeSerCancelado()) {
			pedido.statusCancelado();
			return true;
		}
		return false;
	}

	public List<String> consultarAvaliacoes() {
		List<String> avaliacoes = new ArrayList<>();
		for (Pedido pedido : restaurante.getPedidos()) {
			if (pedido.isAvaliado()) {
				String avaliacao = "Pedido ID: " + pedido.getId()
						+ "\nNota: " + pedido.getNotaAvaliacao()
						+ "\nComentário: " + pedido.getComentarioAvaliacao();
				avaliacoes.add(avaliacao);
			}
		}
		return avaliacoes;
	}

	// ================== TAXAS ==================
	public boolean alterarTaxaEntrega(double novaTaxa) {
		if (novaTaxa < 0 || novaTaxa > 0.20) {
			return false;
		}
		restaurante.setTxEntregaPrioritaria(novaTaxa);
		return true;
	}

	public boolean alterarTaxaCancelamento(double novaTaxa) {
		if (novaTaxa < 0 || novaTaxa > 0.20) {
			return false;
		}
		restaurante.setTxCancelamento(novaTaxa);
		return true;
	}

	// ================== VOUCHER ==================
	public boolean criarVoucher(String codigo, double desconto) {
		if (codigo == null || codigo.isBlank() || desconto <= 0 || desconto > 1) {
			return false;
		}
		Map<String, Double> vouchers = restaurante.getVouchers();
		String codigoUpper = codigo.toUpperCase();
		if (vouchers.containsKey(codigoUpper)) {
			return false;
		}
		vouchers.put(codigoUpper, desconto);
		return true;
	}

	public boolean removerVoucher(String codigo) {
		if (codigo == null || codigo.isBlank()) return false;
		String codigoUpper = codigo.toUpperCase();
		return restaurante.getVouchers().remove(codigoUpper) != null;
	}

	public boolean editarVoucher(String codigo, double novoDesconto) {
		if (codigo == null || codigo.isBlank() || novoDesconto <= 0 || novoDesconto > 1) return false;
		Map<String, Double> vouchers = restaurante.getVouchers();
		String codigoUpper = codigo.toUpperCase();
		if (!vouchers.containsKey(codigoUpper)) return false;
		vouchers.put(codigoUpper, novoDesconto);
		return true;
	}

	@Override
	public String gerarId() {
		String id;
		do {
			StringBuilder sb = new StringBuilder("A"); // prefixo de Administrador
			for (int i = 0; i < 7; i++) {
				sb.append(random.nextInt(10));
			}
			id = sb.toString();
		} while (idsAdministrador.contains(id));

		idsAdministrador.add(id);
		return id;;
	}
}
