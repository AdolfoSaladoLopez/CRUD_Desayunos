package Controlador;

import Modelo.Pedido;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PedidoDAOMySQL implements PedidoDAO, ProductoDAO {
    private static Connection conexion = Conexion.getConexion();
    private static PedidoDAOMySQL dao = new PedidoDAOMySQL();

    private static final String VER_PEDIDOS_HOY_QUERY = "SELECT * " +
            "FROM pedido " +
            "WHERE fecha =  CURDATE() " +
            "AND estado = 'Pendiente'";

    private static final String OBTENER_LISTADO_PRODUCTOS_QUERY = """
            SELECT * FROM producto
            """;

    private static final String OBTENER_PRODUCTO_ID = """
            SELECT * FROM producto WHERE id_producto = (?)
            """;

    private static final String OBTENER_PRODUCTOS_DISPONIBLES = """
            SELECT * FROM producto WHERE estado = 1
            """;

    private static final String OBTENER_PRODUCTOS_NO_DISPONIBLES = """
            SELECT * FROM producto WHERE estado = 0
            """;

    @Override
    public ArrayList<Producto> obtenerProductosCarta() {
        var cartaProductos = new ArrayList<Producto>();

        try (var st = conexion.createStatement()) {
            ResultSet rs = st.executeQuery(OBTENER_LISTADO_PRODUCTOS_QUERY);

            while (rs.next()) {
                var producto = new Producto();

                setearValoresProducto(rs, producto);

                cartaProductos.add(producto);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cartaProductos;
    }

    @Override
    public Producto obtenerProductoPorId(Integer id) {
        var producto = new Producto();

        try (var ps = conexion.prepareStatement(OBTENER_PRODUCTO_ID)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                setearValoresProducto(rs, producto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return producto;
    }

    @Override
    public ArrayList<Producto> obtenerProductosDisponibles() {
        var listadoProductosDisponibles = new ArrayList<Producto>();

        try (var st = conexion.createStatement()) {
            ResultSet rs = st.executeQuery(OBTENER_PRODUCTOS_DISPONIBLES);

            while (rs.next()) {
                var producto = new Producto();

                setearValoresProducto(rs, producto);

                listadoProductosDisponibles.add(producto);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return listadoProductosDisponibles;
    }

    @Override
    public ArrayList<Producto> obtenerProductosNoDisponible() {
        var listadoProductosDisponibles = new ArrayList<Producto>();

        try (var st = conexion.createStatement()) {
            ResultSet rs = st.executeQuery(OBTENER_PRODUCTOS_NO_DISPONIBLES);

            while (rs.next()) {
                var producto = new Producto();

                setearValoresProducto(rs, producto);

                listadoProductosDisponibles.add(producto);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return listadoProductosDisponibles;
    }

    private void setearValoresProducto(ResultSet rs, Producto producto) throws SQLException {
        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombreProducto(rs.getString("nombre"));
        producto.setTipoProducto(rs.getString("tipo"));
        producto.setPrecioProducto(rs.getFloat("precio"));
        producto.setDisponibilidadProducto(rs.getBoolean("disponibilidad"));
    }

    @Override
    public Boolean insertarNuevoPedido(Pedido nuevoPedido) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean cambiarEstadoARecogido(Pedido nuevoPedido) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Pedido> verPedidosPendientesHoy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<Pedido> verPedidosUsuarioConcreto(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mostrarListadoPedidos(Pedido pedido) {
        // TODO Auto-generated method stub

    }

    public void mostrarMenu() {
        var sc = new Scanner(System.in);

        Integer opcion = 1;

        while (opcion != 0) {
            System.out.println();
            System.out.println("!Bienvenid@!");
            System.out.println("1.- Mostrar carta de productos. ");
            System.out.println("2.- Mostrar información de un producto. ");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    var listadoProductos = new ArrayList<Producto>();
                    listadoProductos.addAll(dao.obtenerProductosCarta());

                    System.out.println("Listado de producto: ");
                    listadoProductos.forEach(
                            producto -> System.out.println("\t" + producto.getIdProducto() + ".- " + producto.getNombreProducto()));
                    break;
                case 2:
                    listadoProductos = new ArrayList<Producto>();
                    listadoProductos.addAll(dao.obtenerProductosCarta());

                    System.out.println();
                    System.out.println("Listado de producto: ");
                    listadoProductos.forEach(
                            producto -> System.out.println("\t" + producto.getIdProducto() + ".- " + producto.getNombreProducto()));

                    System.out.print("¿De qué producto quiere ver información?: ");
                    Integer eleccionProducto = sc.nextInt();
                    if (eleccionProducto > 0 && eleccionProducto < listadoProductos.size() - 1) {
                        var producto = dao.obtenerProductoPorId(eleccionProducto);

                        System.out.println(producto);
                        break;
                    }


                default:
                    System.out.println("Elección incorrecta.");
            }

        }
    }

}
