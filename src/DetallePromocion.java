public class DetallePromocion {
    private Producto producto;
    private int cantidad;

    public DetallePromocion(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad;
    }
}
