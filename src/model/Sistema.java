package model;

import java.util.HashMap;
import java.util.Map;

public class Sistema {
	private Map<String, Cliente> clientesPorLogin = new HashMap<>();
	private Map<String, Cliente> clientesPorCpf = new HashMap<>();
	private Map<String, Cliente> clientesPorEmail = new HashMap<>();
}
