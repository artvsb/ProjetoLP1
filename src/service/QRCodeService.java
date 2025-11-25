package service;

import enums.TipoAtendimento;
import model.Cliente;
import model.Pedido;
import model.Restaurante;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QRCodeService {
	private final Map<String, String> qrCodeMap = new HashMap<>(); // <conteudo do QR, nr da mesa>

	public QRCodeService() {
		// Simulando QR Codes para diferentes mesas
		qrCodeMap.put("qr-mesa-10-xpto987", "10");
		qrCodeMap.put("qr-mesa-11-zeta123", "11");
		qrCodeMap.put("qr-mesa-12-alfa456", "12");
	}

	public Pedido lerQRCode(Scanner tcl, Cliente cliente, Restaurante restaurante) {
		System.out.println("Digite o conteúdo do QR Code:");
		String conteudo = tcl.nextLine();

		if (!qrCodeMap.containsKey(conteudo)) {
			System.out.println("QR Code inválido! Mesa não reconhecida.");
			return null;
		}

		String mesa = qrCodeMap.get(conteudo);
		cliente.setMesa(mesa); // atualiza mesa do cliente

		Pedido pedido = new Pedido(mesa, TipoAtendimento.LOCAL);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);

		cliente.criarPedido(pedido);
		restaurante.addPedido(pedido);

		System.out.println("Pedido iniciado para a mesa " + mesa);
		return pedido;
	}
	}
}
