package service;

import enums.FormaPagto;
import enums.TipoAtendimento;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Restaurante;
import enums.StatusPedido;
import model.Mesa;

import java.util.*;

public class ClienteService {

	private List<Cliente> clientes = new ArrayList<>();
	private Map<String, List<String>> cartoesPorCliente = new HashMap<>();
	private PedidoService pedidoService;

	public ClienteService() {}

	public ClienteService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public Pedido iniciarPedido(Cliente cliente,
								Restaurante restaurante,
								TipoAtendimento tipoAtendimento,
								String qrCodeLido) {

		if (cliente == null || restaurante == null || tipoAtendimento == null || qrCodeLido == null) {
			return null;
		}

		Mesa mesa = cliente.getMesa();

		if (!validarQrCode(mesa, qrCodeLido)) {
			System.out.println("QR code inválido para esta mesa. Não é possível iniciar o pedido.");
			return null;
		}

		if (mesa != null && !mesa.isVirtual()) {
			List<Pedido> pedidosAtuais = pedidoService.listarPedidos();

			boolean mesaOcupada = pedidosAtuais.stream()
					.anyMatch(p -> mesa.equals(p.getMesa()) && !p.isEntregue());

			if (mesaOcupada) {
				System.out.println("Não é possível iniciar novo pedido: a mesa já está ocupada.");
				return null;
			}
		}

		Pedido novoPedido = new Pedido(cliente, restaurante, qrCodeLido);

		if (mesa != null) {
			novoPedido.setMesa(mesa);

			if (!mesa.isVirtual()) {
				mesa.setOcupada(true);
			}
		}

		novoPedido.setTipoAtendimento(tipoAtendimento);
		novoPedido.setStatusPedido(StatusPedido.ABERTO);
		novoPedido.setPago(false);
		novoPedido.setFormaPagto(null);

		pedidoService.cadastrarPedido(novoPedido);
		cliente.getPedidos().add(novoPedido);
		restaurante.adicionarPedido(novoPedido);

		return novoPedido;
	}

	private boolean validarQrCode(Mesa mesa, String qrCodeLido) {
		if (mesa == null || qrCodeLido == null) {
			return false;
		}

		String qrCodeMesa = mesa.getQrCode();
		return qrCodeMesa != null && qrCodeMesa.equals(qrCodeLido);
	}


	public void salvar(Cliente cliente) {
		clientes.add(cliente);
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public Cliente buscarPorId(String id) {
		return clientes.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public void editarDadosPessoais(Cliente cliente) {
		for (Cliente c : clientes) {
			if (c.getId().equals(cliente.getId())) {
				c.setNome(cliente.getNome());
				c.setEmail(cliente.getEmail());
				c.setTelefone(cliente.getTelefone());
				c.setCpf(cliente.getCpf());
				c.setSenha(cliente.getSenha());
				break;
			}
		}
	}

	public void editarPedido(Cliente cliente, Pedido pedido, List<ItemPedido> novosItens) {
		if (cliente != null && pedido != null && novosItens != null && cliente.getPedidos().contains(pedido)) {
			pedido.setItens(novosItens);
		}
	}

	public void fecharPedido(Cliente cliente, Pedido pedido, FormaPagto formaPagto) {
		if (cliente != null && pedido != null && cliente.getPedidos().contains(pedido)) {
			pedido.setFormaPagto(formaPagto);
			pedido.setPronto();  // Ou outro status, conforme seu fluxo
		}
	}

	public void pagarPedido(Pedido pedido) {
		if (pedido != null && !pedido.isPago()) {
			pedido.setPago(true);
		}
	}

// === Cartões ===


	public void cadastrarCartao(Cliente cliente, String numero, String nomeTitular, String validade, String cvv) {
		if (cliente == null || numero == null || nomeTitular == null || validade == null || cvv == null) {
			return;
		}

		String cartao = String.format("Cartão: %s, Titular: %s, Validade: %s", numero, nomeTitular, validade);
		cartoesPorCliente.computeIfAbsent(cliente.getId(), k -> new ArrayList<>()).add(cartao);
	}

	public List<String> getCartoesCadastrados(Cliente cliente) {
		if (cliente == null) {
			return new ArrayList<>();
		}
		return cartoesPorCliente.getOrDefault(cliente.getId(), new ArrayList<>());
	}

	public boolean cancelarPedido(Cliente cliente, Restaurante restaurante, double taxaCancelamento) {
		Optional<Pedido> pedidoAtivo = restaurante.getPedidos().stream()
				.filter(p -> p.getCliente().equals(cliente)
						&& p.podeSerCancelado())
				.findFirst();

		if (pedidoAtivo.isPresent()) {
			Pedido pedido = pedidoAtivo.get();
			double taxa = pedido.calcularTotal() * (taxaCancelamento / 100);
			pedido.setTaxaCancelamento(taxa);
			pedido.statusCancelado();

			// Estorno simulado (90% ou conforme lógica)
			double valorEstornado = pedido.calcularTotal() - taxa;
			// aqui poderia ter integração com pagamento, se existisse

			return true;
		}

		return false;
	}

	public boolean avaliarPedido(Cliente cliente, String pedidoId, int nota, String comentario) {
		for (Pedido p : cliente.getPedidos()) {
			if (p.getId().toString().equals(pedidoId) && !p.isAvaliado()) {
				p.avaliar(nota, comentario);
				return true;
			}
		}
		return false;
	}

	public boolean deletarConta(Cliente cliente) {
		return clientes.removeIf(c -> c.getId().equals(cliente.getId()));

	}
}
