package ch.makery.address.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MesItem {

	private int anio = LocalDateTime.now().getYear();
	private int mes = LocalDateTime.now().getMonthValue();
	private ArrayList<Date> calendarDates;

	private final String[] mesArr = new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO",
			"AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
	private final String[] dias = new String[] { "L", "M", "M", "J", "V", "S", "D" };

	/**
	 * Este método devuelve una ArrayList de Strings de meses
	 * 
	 * @return
	 */
	public ArrayList<String> generaMes() {
		ArrayList<String> mesCalendario = new ArrayList<>();
		calendarDates = new ArrayList<>();
		Calendar c = new GregorianCalendar();
		c.set(anio, mes - 1, 1);

		int fechasVacias = c.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
		if (fechasVacias < 0) {
			fechasVacias += 7;
		}
		for (int i = 1; i <= fechasVacias; i++) {
			mesCalendario.add(new FechaItem().getFechaVacia());
		}

		while (Integer.valueOf(c.get(Calendar.MONTH)) == mes - 1) {
			mesCalendario.add(new FechaItem().getFecha(c.getTime()));

			calendarDates.add(c.getTime());

			c.add(Calendar.DATE, 1);
		}

		for (int i = mesCalendario.size(); i <= 41; i++) {
			mesCalendario.add(new FechaItem().getFechaVacia());
		}

		return mesCalendario;
	}

	public ArrayList<Date> getDates() {
		return calendarDates;
	}

	public String[] getDias() {
		return dias;
	}

	public String[] getMesArr() {
		return mesArr;
	}

	public String getMes() {
		return mesArr[mes - 1];
	}

	public void siguienteMes() {
		if (mes < 12) {
			mes++;
		}
	}

	public void anteriorMes() {
		if (mes > 1) {
			mes--;
		}
	}

}
