import java.util.ArrayList;
import java.util.List;

public class HistorialVentas {
    private List<Venta> ventas;

    public HistorialVentas() {
        this.ventas = new ArrayList<>();
    }

    public void registrarVenta(Venta venta) {
        ventas.add(venta);
    }

    public void mostrarVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\n=== HISTORIAL DE VENTAS ===");
        for (Venta v : ventas) {
            System.out.println(v);
            System.out.println("---------------------------");
        }
    }
}
