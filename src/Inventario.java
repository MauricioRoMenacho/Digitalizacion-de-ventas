import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos;
    private List<Venta> ventas;

    public Inventario() {
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p instanceof Promocion) {
                if (((Promocion) p).getIdPromocion() == id) return p;
            } else {
                if (p.getId() == id) return p;
            }
        }
        return null;
    }

    public void mostrarProductos() {
        System.out.println("\n=== INVENTARIO TOTAL ===");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public void mostrarHistorialProducto(String nombreProducto) {
        boolean encontrado = false;
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombreProducto)) {
                encontrado = true;
                System.out.println("\nHistorial de stock para: " + p.getNombre());
                for (HistorialStock h : p.getHistorial()) {
                    System.out.println(h);
                }
            }
        }
        if (!encontrado) {
            System.out.println("❌ Producto no encontrado en inventario.");
        }
    }

    public void mostrarListaPrecios() {
        System.out.println("\n=== LISTA DE PRECIOS ===");
        for (Producto p : productos) {
            System.out.printf("ID: %d | Nombre: %s | Precio: S/ %.2f%n",
                    (p instanceof Promocion) ? ((Promocion) p).getIdPromocion() : p.getId(),
                    p.getNombre(), p.getPrecio());
        }
    }

    public void mostrarVentasDelDia() {
        LocalDate hoy = LocalDate.now();
        double totalVentas = 0;
        System.out.println("\n=== VENTAS DEL DÍA (" + hoy.format(DateTimeFormatter.ISO_DATE) + ") ===");
        boolean hayVentas = false;
        for (Venta v : ventas) {
            if (v.getFechaVenta().toLocalDate().equals(hoy)) {
                System.out.println(v);
                totalVentas += v.calcularTotal();
                hayVentas = true;
            }
        }
        if (!hayVentas) {
            System.out.println("No se registraron ventas hoy.");
        } else {
            System.out.printf("Total ventas del día: S/ %.2f%n", totalVentas);
        }
    }

    public void mostrarInventarioPorFecha(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("\n=== INVENTARIO DEL DÍA: " + fecha.format(formatter) + " ===");

        for (Producto p : productos) {
            System.out.println("Producto: " + p.getNombre());
            boolean movimientosDelDia = false;
            for (HistorialStock h : p.getHistorial()) {
                if (h.getFecha().toLocalDate().equals(fecha)) {
                    System.out.println("  " + h);
                    movimientosDelDia = true;
                }
            }
            if (!movimientosDelDia) {
                System.out.println("  Sin movimientos ese día. Stock actual: " + p.getCantidad());
            }
        }
    }

    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    public void mostrarHistorialVentas() {
        System.out.println("\n=== HISTORIAL DE VENTAS ===");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas aún.");
            return;
        }
        int contador = 1;
        for (Venta v : ventas) {
            System.out.println("Venta #" + contador++);
            System.out.println(v);
            System.out.println("-------------------");
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
}
