import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Venta {
    private Map<Producto, Integer> productos;
    private LocalDateTime fechaVenta;

    public Venta() {
        this.productos = new HashMap<>();
        this.fechaVenta = LocalDateTime.now();
    }

    public void agregarProducto(Producto p, int cantidad) {
        productos.put(p, productos.getOrDefault(p, 0) + cantidad);
        p.reducirCantidad(cantidad);
    }

    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        return total;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public boolean esDeHoy() {
        return fechaVenta.toLocalDate().equals(LocalDate.now());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(fechaVenta).append("\n");
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            sb.append("Producto: ").append(entry.getKey().getNombre())
                    .append(" | Cantidad: ").append(entry.getValue())
                    .append(" | Precio unitario: S/ ").append(entry.getKey().getPrecio())
                    .append("\n");
        }
        sb.append("Total: S/ ").append(calcularTotal());
        return sb.toString();
    }
}
