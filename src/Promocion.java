import java.util.ArrayList;
import java.util.List;


public class Promocion extends Producto {
    private static int contador = 1;
    private int idPromocion;
    private List<DetallePromocion> detalles;


    public Promocion(String nombre, double precioPromocional) {
        // Seteamos cantidad a 0 ya que se calculará dinámicamente
        super(nombre, 0, precioPromocional);
        this.idPromocion = contador++;
        this.detalles = new ArrayList<>();
    }


    public void agregarProducto(Producto producto, int cantidad) {
        detalles.add(new DetallePromocion(producto, cantidad));
    }


    public int getIdPromocion() {
        return idPromocion;
    }


    public List<DetallePromocion> getDetalles() {
        return detalles;
    }


    // ⚠️ Sobreescribimos getCantidad para que el stock sea calculado dinámicamente
    @Override
    public int getCantidad() {
        int stockDisponible = Integer.MAX_VALUE;
        for (DetallePromocion d : detalles) {
            Producto p = d.getProducto();
            int requerido = d.getCantidad();
            stockDisponible = Math.min(stockDisponible, p.getCantidad() / requerido);
        }
        return stockDisponible;
    }


    // ❌ Deshabilitamos reponerStock para promociones
    @Override
    public void reponerStock(int cantidad) {
        System.out.println("❌ No se puede reponer stock directamente en una promoción.");
    }


    // ✅ Reducimos stock de los productos componentes, no de la promoción
    @Override
    public boolean reducirCantidad(int cantidad) {
        // Verificamos que hay stock suficiente en todos los productos
        for (DetallePromocion d : detalles) {
            if (d.getProducto().getCantidad() < d.getCantidad() * cantidad) {
                System.out.println("❌ No hay suficiente stock de " + d.getProducto().getNombre());
                return false;
            }
        }


        // Reducimos stock
        for (DetallePromocion d : detalles) {
            d.getProducto().reducirCantidad(d.getCantidad() * cantidad);
        }


        return true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Promoción [").append(idPromocion).append("] ").append(getNombre())
                .append(" | Precio: S/ ").append(getPrecio())
                .append(" | Stock disponible: ").append(getCantidad())
                .append("\nIncluye:\n");


        for (DetallePromocion d : detalles) {
            sb.append("   - ").append(d).append("\n");
        }


        return sb.toString();
    }
}

