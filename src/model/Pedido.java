package model;

import enums.PrioridadeEntrega;
import enums.StatusPedido;
import enums.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
	private String id, comentarioAvaliacao;
	private int notaAvaliacao;
	private boolean avaliado = false;
	private Cliente cliente;
	private double total;
	private boolean entregue;
	private Restaurante restaurante;
	private String mesa, justifRecusa;
	private List<ItemPedido> itens;
	private StatusPedido statusPedido = StatusPedido.EM_PREPARO;
	private TipoAtendimento tipoAtendimento;
	private FormaPagto formaPagto;
	private boolean pago;
	private Funcionario funcionarioResponsavel;
	private PrioridadeEntrega prioridadeEntrega = PrioridadeEntrega.NORMAL;
	private LocalDateTime criadoEm, canceladoEm, previsaoEntrega, prontoEm, entregueEm, dataHoraProntoPrevisao;
	private double taxaCancelamento = 0.0; //valor padrão zeor

	public Pedido (Cliente cliente, Restaurante restaurante,
				   Funcionario funcionarioResponsavel,double total) {
		this.itens = new ArrayList<>();
		this.statusPedido = StatusPedido.EM_PREPARO;
		this.justifRecusa = null;
		this.entregue = false;
		this.total = total;
		this.funcionarioResponsavel = funcionarioResponsavel;
		this.criadoEm = LocalDateTime.now();
	}
	/* a terceira linha do mét0do define que o nr da mesa só será definido se o atendimento for local;
	se for para viagem, a  mesa ficará com valor nulo (null). */

	public double getTaxaCancelamento() {
		return taxaCancelamento;
	}

	public void setTaxaCancelamento(double taxaCancelamento) {
		if (this.taxaCancelamento > 0) {
			System.out.println("A taxa de cancelamento já foi registrada para este pedido.");
			return;
		}
		this.taxaCancelamento = taxaCancelamento;
	}

	public double getValorEstornado() {
		if (taxaCancelamento > 0) {
			return calcularTotal() - taxaCancelamento;
		}
		return 0.0;
	}

	public LocalDateTime getCanceladoEm() {
		return canceladoEm;
	}

	public void marcarCancelado() {
		this.statusPedido = StatusPedido.CANCELADO;
		this.canceladoEm = LocalDateTime.now();
	}

	public String getJustifRecusa() { return justifRecusa; }

	public void setJustifRecusa(String justifRecusa) { this.justifRecusa = justifRecusa; }

	public void avaliar(int nota, String comentario) {
		this.notaAvaliacao = nota;
		this.comentarioAvaliacao = comentario;
		this.avaliado = true;
	}

	public boolean isAvaliado() {
		return avaliado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setComentarioAvaliacao(String comentarioAvaliacao) {
		this.comentarioAvaliacao = comentarioAvaliacao;
	}

	public void setNotaAvaliacao(int notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	public void setAvaliado(boolean avaliado) {
		this.avaliado = avaliado;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public Funcionario getFuncionarioResponsavel() {
		return funcionarioResponsavel;
	}

	public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
		this.funcionarioResponsavel = funcionarioResponsavel;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public void setCanceladoEm(LocalDateTime canceladoEm) {
		this.canceladoEm = canceladoEm;
	}

	public void setProntoEm(LocalDateTime prontoEm) {
		this.prontoEm = prontoEm;
	}

	public void setEntregueEm(LocalDateTime entregueEm) {
		this.entregueEm = entregueEm;
	}

	public int getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public String getComentarioAvaliacao() {
		return comentarioAvaliacao;
	}

	public LocalDateTime getCriadoEm() { return criadoEm; }

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public LocalDateTime getPrevisaoEntrega() {
		return previsaoEntrega;
	}

	public void statusCancelado() {
		this.statusPedido = StatusPedido.CANCELADO;
	}

	public boolean podeSerCancelado() {
		return !isEntregue() && this.statusPedido != StatusPedido.CANCELADO;
	}

	public String getResumoItens() {
		if (itens == null || itens.isEmpty()) {
			return "Nenhum item no pedido.";
		}

		StringBuilder resumo = new StringBuilder();
		for (ItemPedido item : itens) {
			resumo.append("- ")
					.append(item.getQtd()).append("x ")
					.append(item.getNome()).append("\n");
		}
		return resumo.toString();
	}

	public void setPrevisaoEntrega(LocalDateTime previsaoEntrega) {
		this.previsaoEntrega = previsaoEntrega;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public LocalDateTime getDataHoraProntoPrevisao() {
		return dataHoraProntoPrevisao;
	}

	public void setDataHoraProntoPrevisao(LocalDateTime dataHoraProntoPrevisao) {
		this.dataHoraProntoPrevisao = dataHoraProntoPrevisao;
	}

	public void setFormaPagto(FormaPagto formaPagto) {
		this.formaPagto = formaPagto;
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

	public void setPronto() {
		this.statusPedido = StatusPedido.PRONTO;
		this.prontoEm = LocalDateTime.now();
		this.dataHoraProntoPrevisao = prontoEm.plusMinutes(2);
	}

	public void setEntregue() {
		this.statusPedido = StatusPedido.ENTREGUE;
		this.entregueEm = LocalDateTime.now();
	}

	public LocalDateTime getProntoEm() {
		return prontoEm;
	}

	public LocalDateTime getEntregueEm() {
		return entregueEm;
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

	public FormaPagto getFormaPagto() { return formaPagto; }

	public int getTempoPreparoEstim() { return itens.stream().mapToInt(ItemPedido::getTempoPreparo).max().orElse(0); }

	public String getHrPrevistoPedido() { LocalTime agora = LocalTime.now(); int minutos = getTempoPreparoEstim(); LocalTime previsto = agora.plusMinutes(minutos); return previsto.format(DateTimeFormatter.ofPattern("HH:mm")); }

	public long getTempoRestante() {
		LocalDateTime agora = LocalDateTime.now();
		int tempoEstimadoMin = getTempoPreparoEstim();
		LocalDateTime previsao = this.getDataHoraProntoPrevisao() != null
				? this.getDataHoraProntoPrevisao()
				: this.getCriadoEm().plusMinutes(tempoEstimadoMin);

		return Duration.between(agora, previsao).toMinutes();
	}

	public FormaPagto getFormaPagamento() { return formaPagto; }

}


