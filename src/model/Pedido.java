package model;

import enums.StatusPedido;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
	private UUID id;
	private String mesa;
	private List<ItemPedido> itens;
	private StatusPedido status;

	public Pedido (String mesa) {
		this.id = UUID.randomUUID();
		this.mesa = mesa;
		this.itens = new ArrayList<>();
		this.status = StatusPedido.EM_PREPARO;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void adicionarItem(ItemPedido item) { itens.add(item); }

	public void removerItem(ItemPedido item) { itens.remove(item); }

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pedido: " +
				"\nID: " + id +
				", \nMesa: " + mesa +
				", \nItens: " + itens +
				", \nStatus: " + status;
	}

	public double getTotal() {
		double total = 0;
		for (ItemPedido item : itens) {
			total += item.getSubtotal();
		}
		return total;
	}

	public void emitirRecibo() {
		System.out.println("==============================================");
		System.out.println("			NOTA FISCAL - RECIBO			  ");
		System.out.println("==============================================");
		System.out.println("Pedido ID 	: " + this.id);
		System.out.println("Mesa		: " + this.mesa);
		System.out.println("Data/Hora 	: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		System.out.println("----------------------------------------------");

		for (ItemPedido item : itens) {
			System.out.printf("%-20s x%d	R$ %.2f\n",
					item.getNome(), item.getQtd(), item.getSubtotal());
		}

		System.out.println("---------------------------------------------");
		System.out.println("TOTAL A PAGAR: 						R$ %.2f\n", getTotal());
		System.out.println("Status do Pedido: " + this.status);
		System.out.println("=============================================");
	}

	public void reciboTxt() {
		String nomeArq = "recibo_mesa_" + this.mesa + ".txt";
		try (PrintWriter writer = new PrintWriter(nomeArq)) {
			writer.println("==============================================");
			writer.println("			NOTA FISCAL - RECIBO			  ");
			writer.println("==============================================");
			writer.println("ID: " + this.id);
			writer.println("Mesa: " + this.mesa);
			writer.println("Data/Hora: " +
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			writer.println("------------------------------------");

			for (ItemPedido item : itens) {
				writer.printf("%-20s x%d    R$ %.2f\n",
						item.getNome(), item.getQtd(), item.getSubtotal());
			}

			writer.println("------------------------------------");
			writer.printf("TOTAL:                    R$ %.2f\n", getTotal());
			writer.println("Status: " + this.status);
			writer.println("====================================");

			System.out.println("Recibo salvo em: " + nomeArq);
		} catch (Exception e) {
			System.out.println("Erro ao salvar recibo: " + e.getMessage());
		}
	}
}


