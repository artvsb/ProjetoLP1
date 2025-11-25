import model.Pedido;
import model.Restaurante;
import service.CozinhaService;

import java.util.List;
import java.util.Scanner;

public class CozinhaController {
	private Restaurante restaurante;
	private final CozinhaService cozinhaService = new CozinhaService(Restaurante restaurante);
	private final Scanner tcl = new Scanner(System.in);

	public void atualizarStatusPedido(Restaurante restaurante) {
		List<Pedido> pedidos = restaurante.getPedidosAtivos();

		if (pedidos.isEmpty()) {
			System.out.println("Não há pedidos ativos no momento.");
			return;
		}

		System.out.println("\n=== SELECIONE UM PEDIDO ===");
		for (int i = 0; i < pedidos.size(); i++) {
			Pedido p = pedidos.get(i);
			System.out.printf("%d - Pedido %s | Mesa: %s | Status: %s | Tempo restante: %d min\n",
					i + 1,
					p.getId().toString().substring(0, 6),
					p.getMesa(),
					p.getStatusPedido(),
					p.getTempoRestante());
		}

		System.out.print("\nEscolha o número do pedido: ");
		int escolha = tcl.nextInt();
		tcl.nextLine();

		if (escolha < 1 || escolha > pedidos.size()) {
			System.out.println("Opção inválida.");
			return;
		}

		Pedido pedido = pedidos.get(escolha - 1);

		System.out.println("\n=== OPÇÕES DE ATUALIZAÇÃO ===");
		System.out.println("1 - Marcar como PRONTO");
		System.out.println("2 - Marcar como ENTREGUE");
		System.out.println("3 - Cancelar pedido");
		System.out.println("0 - Voltar");

		System.out.print("Escolha a ação: ");
		int opcao = tcl.nextInt();
		tcl.nextLine();

		switch (opcao) {
			case 1 -> {
				cozinhaService.marcarPronto(pedido);
				System.out.println("Pedido marcado como PRONTO.");
			}
			case 2 -> {
				cozinhaService.marcarEntregue(pedido);
				System.out.println("Pedido marcado como ENTREGUE.");
			}
			case 3 -> {
				cozinhaService.cancelarPedido(pedido);
				System.out.println("Pedido CANCELADO.");
			}
			case 0 -> System.out.println("Operação cancelada.");
			default -> System.out.println("Opção inválida.");
		}
	}

	public void cancelarPedido();
}

