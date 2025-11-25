package model;

import java.time.LocalTime;

public class HorarioMenu {
	private LocalTime horaInicio, horaFim;

	public HorarioMenu(LocalTime horaInicio, LocalTime horaFim) {
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
	}

	public boolean isHorarioAtual() {
		LocalTime agora = LocalTime.now();
		return agora.isAfter(horaInicio) && agora.isBefore(horaFim);
	}

	public LocalTime getHoraInicio() { return horaInicio; }
	public LocalTime getHoraFim() { return horaFim; }

}

