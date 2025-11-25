package model;

import enums.PrioridadeEntrega;
import enums.StatusPedido;
import enums.TipoAtendimento;
import enums.*;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Pedido {
	private UUID id;
	private Cliente cliente;
	private Restaurante restaurante;
	private LocalDateTime dataHoraPronto;
	private String mesa, justifRecusa;
	private List<ItemPedido> itens;
	private StatusPedido statusPedido = StatusPedido.EM_PREPARO;
	private TipoAtendimento tipoAtendimento;
	private TiposPagamento tipoPagto;
	private boolean pago;
	private PrioridadeEntrega prioridadeEntrega = PrioridadeEntrega.NORMAL;

	public Pedido (String mesaOuCliente, TipoAtendimento tipoAtendimento) {
		this.id = UUID.randomUUID();
		this.mesa = tipoAtendimento == TipoAtendimento.LOCAL ? mesaOuCliente : null;
		this.itens = new ArrayList<>();
		this.statusPedido = StatusPedido.EM_PREPARO;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public LocalDateTime getDataHoraPronto() {
		return dataHoraPronto;
	}

	public void setDataHoraPronto(LocalDateTime dataHoraPronto) {
		this.dataHoraPronto = dataHoraPronto;
	}

	public void setTipoPagto(TiposPagamento tipoPagto) {
		this.tipoPagto = tipoPagto;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
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

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	// Métodos de alterar/consultar o status do pediod  //

	public void marcarPronto() { this.statusPedido = StatusPedido.PRONTO; }

	public void marcarEntregue() {
		this.statusPedido = StatusPedido.ENTREGUE;
		this.dataHoraPronto = LocalDateTime.now();
	}

	public void cancelarPedido() { this.statusPedido = StatusPedido.CANCELADO; }

	public boolean isEntregue() { return this.statusPedido == StatusPedido.ENTREGUE; }

	public double calcularTotal() {
		double total = 0;

		for (ItemPedido item : itens) {
			total += item.getSubtotal();
		}
		total *= getTaxaPrioridade();
		return total;
	}

	@Override
	public String toString() {
		return "Pedido: " +
				"\nID: " + id +
				", \nMesa: " + mesa +
				", \nItens: " + itens +
				", \nStatus: " + statusPedido;
	}

	public double getTotal() {
		double total = 0;
		for (ItemPedido item : itens) {
			total += item.getSubtotal();
		}
		return total;
	}

	public boolean isPago() { return pago; }

	public TiposPagamento getTipoPagto() { return tipoPagto; }

	public int getTempoPreparoEstim() { return itens.stream().mapToInt(ItemPedido::getTempoPreparo).max().orElse(0); }

	public String getHrPrevistoPedido() { LocalTime agora = LocalTime.now(); int minutos = getTempoPreparoEstim(); LocalTime previsto = agora.plusMinutes(minutos); return previsto.format(DateTimeFormatter.ofPattern("HH:mm")); }

}


