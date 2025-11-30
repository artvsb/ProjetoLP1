package controller;

import model.*;
import service.AdministradorService;

import java.util.List;
import java.util.Map;

public class AdministradorController {

	private AdministradorService administradorService;

	public AdministradorController(Restaurante restaurante) {
		this.administradorService = new AdministradorService(restaurante);
	}

	//========== RESTAURANTE =================

	public void criarRestaurante(Restaurante restaurante) {
		administradorService.criarRestaurante(restaurante);
	}

	public boolean removerRestaurante(String idRestaurante) {
		return administradorService.removerRestaurante(idRestaurante);
	}

	public List<Restaurante> listarRestaurantes() {
		return administradorService.getRestaurantes();
	}

	public Restaurante buscarRestaurantePorId(String id) {
		return administradorService.buscarRestauranteId(id);
	}

	public Restaurante buscarRestaurantePorNome(String nome) {
		return administradorService.buscarRestauranteNome(nome);
	}

	public Restaurante buscarRestaurantePorCnpj(String cnpj) {
		return administradorService.buscarRestauranteCnpj(cnpj);
	}


	// ========== MENU ==========
	public void cadastrarMenu() {
		administradorService.criarMenu();
	}

	public void adicionarItemAoMenu(String nome, String descricao, double preco, int tempoPreparo) {
		administradorService.adicionarItemAoMenu(nome, descricao, preco, tempoPreparo);
	}

	public void editarMenu(ItemCardapio item, String novoNome, double novoPreco, String novaDescricao, int novoTempoPreparo) {
		administradorService.editarMenu(item, novoNome, novoPreco, novaDescricao, novoTempoPreparo);
	}

	public void deletarItem(ItemCardapio item) {
		administradorService.deletarItem(item);
	}

	// ========== MESAS ==========
	public void cadastrarMesas(String qrCode, String numeroMesa) {
		administradorService.cadastrarMesa(qrCode, numeroMesa);
	}

	public void editarMesas(String qrCodeAntigo, String novoNumeroMesa) {
		administradorService.editarMesa(qrCodeAntigo, novoNumeroMesa);
	}

	public void removerMesa(String numeroMesa) {
		administradorService.removerMesa(numeroMesa);
	}

	// ========== ACESSOS & PERMISSÕES ==========
	public void gerenciarAcessos(String idFuncionario, boolean concederAcesso) {
		administradorService.gerenciarAcessos(idFuncionario, concederAcesso);
	}

	// ========== BONIFICAÇÃO ==========
	public void concederBonusFuncionario(Funcionario funcionario) {
		administradorService.concederBonusFuncionario(funcionario);
	}

	public Map<Funcionario, Double> consultarBonusFuncionarios() {
		return administradorService.consultarBonusFuncionarios();
	}

	// ========== DADOS DO RESTAURANTE ==========
	public void editarDadosRestaurante(Restaurante dadosAtualizados) {
		administradorService.editarDadosRestaurante(dadosAtualizados);
	}

	// ========== PEDIDOS ==========
	public boolean cancelarPedido(Pedido pedido) {
		return administradorService.cancelarPedido(pedido);
	}

	public List<String> consultarAvaliacoes() {
		return administradorService.consultarAvaliacoes();
	}

	// ========== TAXAS ==========
	public boolean alterarTaxaEntrega(double novaTaxa) {
		return administradorService.alterarTaxaEntrega(novaTaxa);
	}

	public boolean alterarTaxaCancelamento(double novaTaxa) {
		return administradorService.alterarTaxaCancelamento(novaTaxa);
	}

	// ========== VOUCHER ==========
	public boolean criarVoucher(String codigo, double desconto) {
		return administradorService.criarVoucher(codigo, desconto);
	}

	public boolean editarVoucher(String codigo, double novoDesconto) {
		return administradorService.editarVoucher(codigo, novoDesconto);
	}

	public boolean removerVoucher(String codigo) {
		return administradorService.removerVoucher(codigo);
	}
}
