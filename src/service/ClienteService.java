package service;

import enums.StatusPedido;
import model.Cliente;
import model.Pedido;
import model.Restaurante;

import java.util.Scanner;

public class ClienteService {

	public static void vincularMesaViaQRCode(Cliente cliente, Restaurante restaurante) {
		if (cliente == null) {
			System.out.println("Cliente inválido.");
			return;
		}

		Scanner tcl = new Scanner(System.in);
		System.out.print("Digite o conteúdo do QR Code: ");
		String conteudoQRCode = tcl.nextLine();

		String mesa = restaurante.getMapaMesas().get(conteudoQRCode);

		if (mesa == null) {
			System.out.println("QR Code inválido ou não registrado.");
			return;
		}

		if (mesa.equalsIgnoreCase("MESA_VIRTUAL")) {
			cliente.setMesa(mesa);
			System.out.println("Você está vinculado à Mesa Virtual.");
			return;
		}

		// Verifica se a mesa já tem um pedido ativo
		for (Pedido p : restaurante.getPedidosAtivos()) {
			if (mesa.equals(p.getMesa())
					&& !p.isEntregue()
					&& !p.getStatusPedido().equals(StatusPedido.CANCELADO)) {
				System.out.println("Já existe um pedido em andamento nesta mesa.");
				return;
			}
		}

		cliente.setMesa(mesa);
		System.out.println("Mesa atribuída com sucesso: " + mesa);
	}


}
