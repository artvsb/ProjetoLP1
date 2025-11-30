// Em: src/utils/Utils.java (ou só na raiz do projeto, se não usar pacotes)
package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static String formatarDataHora(LocalDateTime dataHora) {
		if (dataHora == null) return "Data inválida";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return dataHora.format(formatter);
	}

}
