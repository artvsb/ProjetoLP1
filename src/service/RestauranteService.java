package service;

import enums.NivelAcesso;
import enums.StatusPedido;
import model.ItemPedido;
import model.Pedido;
import model.Funcionario;

public class RestauranteService {

	public void notifNovoPedido(Pedido pp) {
		System.out.println("\n NOVO PEDIDO ");
		System.out.println("Cliente: " + pp.getCliente().getNome());
		System.out.println("Itens do pedido:");
		for (ItemPedido item : pp.getItens()) {
			System.out.println("- " + item.getQtd() + "x " + item.getNome());
		}
		System.out.println("Preparar em até: " + pp.getTempoPreparoEstim() + " minutos");
		System.out.println("Horário previsto para conclusão: " + pp.getHrPrevistoPedido());
		pp.setStatusPedido(StatusPedido.EM_PREPARO);
	}


	public void aceitarPedido(Pedido pedido) {
		pedido.setStatusPedido(StatusPedido.EM_PREPARO);
		System.out.println("Pedido aceito e em preparo.");
	}

	public void recusarPedido(Pedido pedido, Funcionario funcionario, String justificativa) {
		NivelAcesso nivel = funcionario.getNivelAcesso();

		if (podeRecusarPedido(funcionario.getNivelAcesso())) {
			pedido.setStatusPedido(StatusPedido.CANCELADO);
			pedido.setJustifRecusa(justificativa);
			System.out.println("Que Pena! Seu pedido foi recusado");
			System.out.println("Motivo: " + justificativa);
		} else {
			System.out.println("Acesso negado! Opção reservada à Administração.");
		}
	}

	public void marcarComoPronto(Pedido pedido) {
		pedido.marcarPronto();
		System.out.println("Pedido pronto para entrega.");
	}

	public void marcarComoEntregue(Pedido pedido) {
		pedido.marcarEntregue();
		System.out.println("Pedido entregue às " + pedido.getDataHoraPronto());
	}

	private boolean podeRecusarPedido(NivelAcesso nivelAcesso) {
		return nivelAcesso == NivelAcesso.GERENCIA || nivelAcesso == NivelAcesso.PROPRIETARIO;
	}
	// só é usado dentro da própria classe

}

