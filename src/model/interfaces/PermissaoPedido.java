package model.interfaces;

import model.Pedido;
import service.ClienteService;

public interface PermissaoPedido {
	boolean podeCancelarPedido(Pedido pedido);
}
