import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistorialStock {
    private String accion;
    private int stock;
    private LocalDateTime fechaHora;

    public HistorialStock(String accion, int stock) {
        this.accion = accion;
        this.stock = stock;
        this.fechaHora = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "[" + fechaHora.format(formato) + "] Acción: " + accion + " | Stock: " + stock;
    }

    public LocalDateTime getFecha() {
        return fechaHora;
    }
}

