package ch.makery.address.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaItem  {
	
    private Date fecha;
    private boolean vacio;
    
    Calendar calendario;
    
    public FechaItem() {
    }

    private String fechaToString(){
        if (vacio) {
            calendario = new GregorianCalendar();
            return "";
        } else {
            calendario = new GregorianCalendar();
            calendario.setTime(fecha);
            return String.valueOf(calendario.get(Calendar.DATE));
        }
    }
    
    public String getFecha(Date fech) {
    	
    	fecha=fech;
		vacio=false;
		return fechaToString();
    }
    
    public String getFechaVacia() {
    	
    	vacio=true;   
    	return fechaToString();
    }

}