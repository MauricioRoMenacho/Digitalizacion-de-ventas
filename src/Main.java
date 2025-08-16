import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import java.util.Scanner;

public class Main {

    public static int leerEntero(Scanner sc, String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Intenta de nuevo.");
            }
        }
        return valor;
    }

    public static double leerDouble(Scanner sc, String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Intenta de nuevo.");
            }
        }
        return valor;
    }

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Scanner sc = new Scanner(System.in);
        int opcion;

        List<Venta> ventas = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Datos Maestros");
            System.out.println("2. Opciones de Procesos");
            System.out.println("3. Reportes");
            System.out.println("4. Realizar venta");
            System.out.println("5. Salir");
            opcion = leerEntero(sc, "Elige una opción: ");

            switch (opcion) {
                case 1 -> { // Datos Maestros
                    int op;
                    do {
                        System.out.println("\n--- DATOS MAESTROS ---");
                        System.out.println("1. Agregar producto");
                        System.out.println("2. Agregar promoción");
                        System.out.println("3. Mostrar historial de un producto");
                        System.out.println("4. Volver al menú principal");
                        op = leerEntero(sc, "Selecciona una opción: ");

                        switch (op) {
                            case 1 -> {
                                System.out.print("Nombre del producto: ");
                                String nombre = sc.nextLine();
                                int cantidad = leerEntero(sc, "Cantidad inicial: ");
                                double precio = leerDouble(sc, "Precio: ");
                                inventario.agregarProducto(new Producto(nombre, cantidad, precio));
                            }
                            case 2 -> {
                                System.out.print("Nombre de la promoción: ");
                                String nombrePromo = sc.nextLine();
                                double precioPromo = leerDouble(sc, "Precio de la promoción: ");
                                Promocion promo = new Promocion(nombrePromo, precioPromo);

                                boolean continuar;
                                do {
                                    inventario.mostrarProductos();
                                    int idProd = leerEntero(sc, "ID del producto a incluir en la promoción: ");
                                    int cantPromo = leerEntero(sc, "Cantidad de este producto en la promoción: ");
                                    Producto prod = inventario.buscarProductoPorId(idProd);
                                    if (prod != null) {
                                        promo.agregarProducto(prod, cantPromo);
                                        System.out.println("✅ Producto agregado a la promoción.");
                                    } else {
                                        System.out.println("❌ Producto no encontrado.");
                                    }
                                    System.out.print("¿Agregar otro producto a la promoción? (s/n): ");
                                    continuar = sc.nextLine().equalsIgnoreCase("s");
                                } while (continuar);

                                inventario.agregarProducto(promo);
                                System.out.println("✅ Promoción creada exitosamente!");
                            }
                            case 3 -> {
                                System.out.print("Nombre del producto para ver historial: ");
                                String nomHist = sc.nextLine().trim();

                                if (!nomHist.isEmpty()) {
                                    inventario.mostrarHistorialProducto(nomHist);
                                } else {
                                    System.out.println("❌ Debes ingresar un nombre de producto válido.");
                                }
                            }
                            case 4 -> System.out.println("🔙 Volviendo al menú principal...");
                            default -> System.out.println("❌ Opción inválida.");
                        }
                    } while (op != 4);
                }

                case 2 -> { // Procesos
                    int op;
                    do {
                        System.out.println("\n--- PROCESOS ---");
                        System.out.println("1. Reponer stock");
                        System.out.println("2. Mostrar inventario");
                        System.out.println("3. Volver al menú principal");
                        op = leerEntero(sc, "Selecciona una opción: ");

                        switch (op) {
                            case 1 -> {
                                inventario.mostrarProductos();
                                int idRep = leerEntero(sc, "ID del producto a reponer: ");
                                int cantRep = leerEntero(sc, "Cantidad a agregar: ");
                                Producto prodRep = inventario.buscarProductoPorId(idRep);
                                if (prodRep != null) {
                                    prodRep.reponerStock(cantRep);
                                    System.out.println("✅ Stock repuesto.");
                                } else {
                                    System.out.println("❌ Producto no encontrado.");
                                }
                            }
                            case 2 -> inventario.mostrarProductos();
                            case 3 -> System.out.println("🔙 Volviendo al menú principal...");
                            default -> System.out.println("❌ Opción inválida.");
                        }
                    } while (op != 3);
                }

                case 3 -> { // REPORTES
                    int op;
                    do {
                        System.out.println("\n--- REPORTES ---");
                        System.out.println("1. Historial de ventas");
                        System.out.println("2. Lista de precios");
                        System.out.println("3. Ventas del día");
                        System.out.println("4. Inventario por fecha");
                        System.out.println("5. Inventario total");
                        System.out.println("6. Volver al menú principal");

                        op = leerEntero(sc, "Selecciona una opción: ");

                        switch (op) {
                            case 1 -> {
                                System.out.println("\n=== HISTORIAL DE TODAS LAS VENTAS ===");
                                if (ventas.isEmpty()) {
                                    System.out.println("No hay ventas registradas aún.");
                                } else {
                                    for (int i = 0; i < ventas.size(); i++) {
                                        System.out.println("Venta #" + (i + 1));
                                        System.out.println(ventas.get(i));
                                        System.out.println("---------------------");
                                    }
                                }
                            }
                            case 2 -> inventario.mostrarListaPrecios();
                            case 3 -> {
                                System.out.println("\n📈 VENTAS DEL DÍA:");
                                boolean hayVentas = false;
                                for (Venta v : ventas) {
                                    if (v.esDeHoy()) {
                                        System.out.println(v);
                                        System.out.println("------------------");
                                        hayVentas = true;
                                    }
                                }
                                if (!hayVentas) System.out.println("No hay ventas registradas hoy.");
                            }
                            case 4 -> {
                                System.out.print("Ingresa fecha (YYYY-MM-DD): ");
                                String fechaStr = sc.nextLine().trim();

                                if (!fechaStr.isEmpty()) {
                                    try {
                                        LocalDate fecha = LocalDate.parse(fechaStr);
                                        inventario.mostrarInventarioPorFecha(fecha);
                                    } catch (Exception e) {
                                        System.out.println("❌ Formato de fecha inválido. Usa YYYY-MM-DD.");
                                    }
                                } else {
                                    System.out.println("❌ Debes ingresar una fecha válida.");
                                }
                            }

                            case 5 -> {
                                System.out.println("\n📦 INVENTARIO TOTAL ACTUAL:");
                                inventario.mostrarProductos();
                            }
                            case 6 -> System.out.println("🔙 Volviendo al menú principal...");
                            default -> System.out.println("❌ Opción inválida.");
                        }
                    } while (op != 6);
                }

                case 4 -> { // Realizar venta
                    Venta venta = new Venta();
                    int seguir;
                    do {
                        inventario.mostrarProductos();
                        int idV = leerEntero(sc, "ID del producto a vender: ");
                        int cantV = leerEntero(sc, "Cantidad: ");
                        Producto p = inventario.buscarProductoPorId(idV);
                        if (p != null) {
                            venta.agregarProducto(p, cantV);
                        } else {
                            System.out.println("❌ Producto no encontrado.");
                        }
                        seguir = leerEntero(sc, "¿Agregar otro producto a la venta? (1=Sí, 0=No): ");
                    } while (seguir == 1);

                    if (venta.calcularTotal() > 0) {
                        ventas.add(venta);
                        System.out.println("=== Venta realizada ===");
                        System.out.println(venta);
                    } else {
                        System.out.println("❌ No se registró la venta.");
                    }
                }

                case 5 -> System.out.println("👋 Saliendo del sistema...");

                default -> System.out.println("❌ Opción inválida.");
            }

        } while (opcion != 5);

        sc.close();
    }
}
