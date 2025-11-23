package model;

import enums.PrioridadeEntrega;
import enums.StatusPedido;
import enums.TipoAtendimento;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Pedido {
	private UUID id;
	private String mesa, justifRecusa;
	private List<ItemPedido> itens;
	private StatusPedido status;
	private TipoAtendimento tipoAtendimento;
	private PrioridadeEntrega prioridadeEntrega = PrioridadeEntrega.NORMAL;

	public Pedido (String mesaOuCliente, TipoAtendimento tipoAtendimento) {
		this.id = UUID.randomUUID();
		this.mesa = tipoAtendimento == TipoAtendimento.LOCAL ? mesaOuCliente : null;
		this.itens = new ArrayList<>();
		this.status = StatusPedido.EM_PREPARO;
		this.justifRecusa = null;
	}
	/* a terceira linha do mét0do define que o nr da mesa só será definido se o atendimento for local;
	se for para viagem, a  mesa ficará com valor nulo (null). */

	public String getJustifRecusa() { return justifRecusa; }

	public void setJustifRecusa(String justifRecusa) { this.justifRecusa = justifRecusa; }

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

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public PrioridadeEntrega getPrioridadeEntrega() {
		return prioridadeEntrega;
	}

	public void setPrioridadeEntrega(PrioridadeEntrega prioridadeEntrega) {
		this.prioridadeEntrega = prioridadeEntrega;
	}

	public double getTaxaPrioridade() {
		return prioridadeEntrega == PrioridadeEntrega.PRIORITARIA ? 1.05 : 1.0;
	}

	public TipoAtendimento getTipoAtendimento() { return tipoAtendimento; }

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) { this.tipoAtendimento = tipoAtendimento; }

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void adicionarItem(ItemPedido item) {
		if (item != null) {
			itens.add(item);
			System.out.printf("Item adicionado: %dx %s", item.getQtd(), item.getNome());
		} else {
			System.out.println("Item inválido!");
		}
	}

	public boolean removerItem(ItemPedido item) {
		if (itens.remove(item)) {
			System.out.println("Item removido: " + item.getNome());
			return true;
		} else {
			System.out.println("Item não encontrado.");
			return false;
		}
	}

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
		System.out.println("Tempo estimado de preparo: " + getTempoPreparoEstim() + " minutos");
		System.out.println("Previsão de entrega: " + getHrPrevistoPedido());
		System.out.println("Entrega " + (prioridadeEntrega  == PrioridadeEntrega.NORMAL ? "Normal" : "Prioritária"));
		if (prioridadeEntrega == PrioridadeEntrega.PRIORITARIA) {
			System.out.println("Taxa de entrega prioritária: " + (getTaxaPrioridade() - 1) + "%%");
		}
		System.out.println("Status do Pedido: " + this.status);
		System.out.println("=============================================");
	}

	public void reciboTxt() {
		String nomeArq = "recibo_mesa_" + this.mesa + "id_" + this.id + ".txt";
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
				writer.printf("%dx %-20s     R$ %.2f (%d min)",
						item.getQtd(), item.getNome(), item.getSubtotal(), item.getTempoPreparo());
			}

			writer.println("------------------------------------");
			writer.printf("TOTAL:                    R$ %.2f\n", getTotal());
			System.out.println("Tempo estimado de preparo: " + getTempoPreparoEstim() + " minutos");
			System.out.println("Previsão de entrega: " + getHrPrevistoPedido());
			writer.println("Status: " + this.status);
			writer.println("====================================");

			System.out.println("Recibo salvo em: " + nomeArq);
		} catch (Exception e) {
			System.out.println("Erro ao salvar recibo: " + e.getMessage());
		}


	}

	public int getTempoPreparoEstim() {
		return itens.stream().mapToInt(ItemPedido::getTempoPreparo).max().orElse(0);
	}

	public String getHrPrevistoPedido() {
		LocalTime agora = LocalTime.now();
		int minutos = getTempoPreparoEstim();
		LocalTime previsto = agora.plusMinutes(minutos);
		return previsto.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

}


