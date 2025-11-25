package service;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import enums.TipoAtendimento;
import model.*;
import enums.TiposPagamento;
import enums.StatusPedido;

import java.time.LocalDateTime;

public class PedidoService {

	public int getTempoPreparoEstim(Pedido pedido) {
		return pedido.getItens().stream().mapToInt(ItemPedido::getTempoPreparo).max().orElse(0);
	}

	public String getHrPrevistoPedido(Pedido pedido) {
		LocalTime agora = LocalTime.now();
		int minutos = getTempoPreparoEstim(pedido);
		LocalTime previsto = agora.plusMinutes(minutos);
		return previsto.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public double getTotal(Pedido pedido) {
		double total = 0;
		for (ItemPedido item : pedido.getItens()) {
			total += item.getSubtotal();
		}
		return total;
	}



	public void notificarCozinha(Pedido pedido) {
		System.out.println("\n NOTIFICAÇÃO PARA COZINHA ");
		System.out.println("Pedido ID: " + pedido.getId());
		for (ItemPedido item : pedido.getItens()) {
			System.out.println("- " + item.getQtd() + "x " + item.getNome());
		}
		pedido.setStatusPedido(StatusPedido.EM_PREPARO);
	}

	public void emitirRecibo(Pedido pedido) {
		System.out.println("======= RECIBO =======");
		System.out.println("Pedido ID: " + pedido.getId());
		System.out.println("Total: R$ " + pedido.getTotal());
		System.out.println("Pagamento: " + pedido.getTipoPagto());
		System.out.println("Data: " + LocalDateTime.now());
		System.out.println("======================");
	}

	public void reciboTxt(Pedido pedido) {
		String nomeArq = "recibo_mesa_" + pedido.getMesa() + "id_" + pedido.getId() + ".txt";
		try (PrintWriter writer = new PrintWriter(nomeArq)) {
			writer.println("=============================================");
			writer.println("					RECIBO				  	");
			writer.println("=============================================");
			writer.println("ID: " + pedido.getId());
			writer.println("Mesa: " + pedido.getMesa());
			writer.println("Data/Hora: " +
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			writer.println("------------------------------------");

			for (ItemPedido item : pedido.getItens()) {
				writer.printf("%dx %-20s     R$ %.2f (%d min)",
						item.getQtd(), item.getNome(), item.getSubtotal(), item.getTempoPreparo());
			}

			writer.println("------------------------------------");
			writer.printf("TOTAL:                    R$ %.2f\n", pedido.calcularTotal());
			System.out.println("Tempo estimado de preparo: " + pedido.getTempoPreparoEstim() + " minutos");
			System.out.println("Previsão de entrega: " + pedido.getHrPrevistoPedido());
			writer.println("Status: " + pedido.getStatusPedido());
			writer.println("====================================");

			System.out.println("Recibo salvo em: " + nomeArq);
		} catch (Exception e) {
			System.out.println("Erro ao salvar recibo: " + e.getMessage());
		}


	}

	public Pedido criarPedido(Cliente cliente, Restaurante restaurante, String conteudoQRCode) {
		Map<String, String> mapaMesas = restaurante.getMapaMesas(); // QR -> número da mesa

		if (!mapaMesas.containsKey(conteudoQRCode)) {
			System.out.println("QR Code inválido ou mesa não registrada.");
			return null;
		}

		String mesa = mapaMesas.get(conteudoQRCode);

		// Verifica se cliente já tem pedido ativo na mesa
		for (Pedido pedido : restaurante.getPedidosAtivos()) {
			if (pedido.getCliente().equals(cliente) &&
					mesa.equals(pedido.getMesa()) &&
					!pedido.isEntregue() &&
					!pedido.getStatusPedido().equals(StatusPedido.CANCELADO)) {
				System.out.println("Você já possui um pedido em andamento nesta mesa.");
				return null;
			}
		}

		Pedido novoPedido = new Pedido(mesa, TipoAtendimento.LOCAL);
		novoPedido.setCliente(cliente);
		novoPedido.setRestaurante(restaurante);

		restaurante.addPedido(novoPedido);
		cliente.getPedidos().add(novoPedido);

		System.out.println("Pedido iniciado com sucesso na mesa " + mesa);
		return novoPedido;
	}

	public void finalizarPagamento(Pedido pedido) {
		pedido.setPago(true);
		System.out.println("Pagamento realizado com sucesso!");

		notificarCozinha(pedido);
	}

	public void finalizarPagamento(Pedido pedido, TiposPagamento tipoPagto) {
	}
}
