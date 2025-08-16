import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private static int contador = 1;
    private int idProducto;
    private String nombre;
    private int cantidad;
    private double precio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaReposicion;
    private List<HistorialStock> historial;

    public Producto(String nombre, int cantidad, double precio) {
        this.idProducto = contador++;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaReposicion = fechaCreacion;
        this.historial = new ArrayList<>();

        historial.add(new HistorialStock("Producto creado", this.cantidad));
    }

    public int getId() { return idProducto; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getUltimaReposicion() { return ultimaReposicion; }

    public boolean reducirCantidad(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            historial.add(new HistorialStock("Stock reducido -" + cantidad, this.cantidad));
            return true;
        } else {
            return false;
        }
    }

    public void reponerStock(int cantidad) {
        this.cantidad += cantidad;
        this.ultimaReposicion = LocalDateTime.now();
        historial.add(new HistorialStock("Stock repuesto +" + cantidad, this.cantidad));
    }

    public List<HistorialStock> getHistorial() {
        return historial;
    }

    @Override
    public String toString() {
        return "ID: " + idProducto +
                " | Nombre: " + nombre +
                " | Cantidad: " + cantidad +
                " | Precio: S/ " + precio;
    }

}
