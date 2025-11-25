package controller;

import enums.TiposPagamento;
import model.Pedido;
import model.ItemPedido;
import model.Cliente;
import model.Restaurante;
import service.PedidoService;
import enums.TipoAtendimento;
import java.util.Scanner;

public class PedidoController {
	private PedidoService pedidoService = new PedidoService();
	private Scanner tcl = new Scanner(System.in);
	private TiposPagamento tiposPagamento;

	public Pedido iniciarPedido(Cliente cliente, Restaurante restaurante, TipoAtendimento tipoAtendimento) {
		System.out.println("Informe a mesa (ou deixe em branco se for viagem): ");
		String mesa = tcl.nextLine();
		Pedido pedido = new Pedido(mesa, tipoAtendimento);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		return pedido;
	}

	public void realizarPagamento(Pedido pedido) {
		if (pedido.isPago()) {
			System.out.println("O pedido " + pedido.getId() + " já está pago!");
			return;
		} else {

			System.out.println("Escolha a forma de pagamento: ");
			System.out.println("1 - Pix");
			System.out.println("2 - Cartão de Débito");
			System.out.println("3 - Cartão de Crédito");
			System.out.print("Opção: ");
			int option = tcl.nextInt();
			tcl.nextLine();

			switch (option) {
				case 1:
					pedido.setTipoPagto(TiposPagamento.PIX);
					break;
				case 2:
					pedido.setTipoPagto(TiposPagamento.CARTAO_DEBITO);
					solicitarDadosCartao("DÉBITO", tcl);
					break;
				case 3:
					pedido.setTipoPagto(TiposPagamento.CARTAO_CREDITO);
					solicitarDadosCartao("CRÉDITO", tcl);
					break;
				default:
					System.out.println("Opção inválida!");
					return;
			}
		}
		pedidoService.finalizarPagamento(pedido, pedido.getTipoPagto());
		System.out.println("Pagamento realizado com sucesso!");

		pedidoService.notificarCozinha(pedido);
	}

	private void solicitarDadosCartao(String tipo, Scanner tcl) {
		System.out.println("Insira os dados do cartão de " + tipo + ":");
		System.out.print("Número do cartão: ");
		String numero = tcl.nextLine();
		System.out.print("Vencimento (MM/AA): ");
		String vencimento = tcl.nextLine();
		System.out.print("CVV: ");
		int cvv = tcl.nextInt();
		tcl.nextLine();
		System.out.println("Pagamento concluído.");
	}

	public void addItemAoPedido(Pedido pedido, ItemPedido item) {
		pedido.adicionarItem(item);
	}

	public void finalizarPedido(Pedido pedido) {
		pedidoService.realizarPagamento(pedido);
		pedidoService.emitirRecibo(pedido);
	}


}
