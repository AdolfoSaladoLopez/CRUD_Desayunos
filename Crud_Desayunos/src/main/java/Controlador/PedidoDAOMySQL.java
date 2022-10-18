package Controlador;

import Modelo.Pedido;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;

public class PedidoDAOMySQL implements PedidoDAO, ProductoDAO {
    private final static Connection CON;
    private final static String BD = "desayunos";
    private final static String PORT = "3306";
    private final static String URL = "jdbc:mysql://localhost:" + PORT + "/" + BD;
    private final static String USER = "root";
    private final static String PWD = "admin";

    private static final String VER_PEDIDOS_HOY_QUERY = "SELECT * " +
            "FROM pedido " +
            "WHERE fecha =  CURDATE() " +
            "AND estado = 'Pendiente'";

    private static final String OBTENER_PRODUCTOS_PEDIDO = "SELECT * " +
            "FROM producto p, pedido pe " +
            "WHERE p.id_producto = pe.id_producto " +
            "AND pe.id_mismo_pedido = (?)";

    static {
        try {
            CON = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Conexión establecida con éxito.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean insertarNuevoPedido(Pedido nuevoPedido) {
        return null;
    }

    @Override
    public Boolean cambiarEstadoARecogido(Pedido nuevoPedido) {
        return null;
    }

    @Override
    public ArrayList<Pedido> verPedidosPendientesHoy() {
        var listadoPedidosPendientes = new ArrayList<Pedido>();
        var listadoProductosPedido = new ArrayList<Producto>();

        try (Statement st = CON.createStatement()) {
            ResultSet rs = st.executeQuery(VER_PEDIDOS_HOY_QUERY);

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setIdMismoPedido(rs.getInt("id_mismo_pedido"));
                pedido.setFechaPedido(rs.getDate("fecha"));
                pedido.setNombreCliente(rs.getString("cliente"));
                pedido.setEstadoPedido(rs.getString("estado"));

                listadoProductosPedido = obtenerProductosDeUnPedido(pedido);

                pedido.setListaProductos(listadoProductosPedido);

                listadoPedidosPendientes.add(pedido);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listadoPedidosPendientes;
    }

    @Override
    public ArrayList<Pedido> verPedidosUsuarioConcreto(String nombre) {
        return null;
    }

    @Override
    public ArrayList<Producto> obtenerProductosDeUnPedido(Pedido pedido) {
        var listadoProducto = new ArrayList<Producto>();
        Integer idMismoPedido = pedido.getIdMismoPedido();

        try (PreparedStatement ps = CON.prepareStatement(OBTENER_PRODUCTOS_PEDIDO)) {
            ps.setInt(1, idMismoPedido);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombre"));
                producto.setTipoProducto(rs.getString("tipo"));
                producto.setPrecioProducto(rs.getFloat("precio"));
                producto.setDisponibilidadProducto(rs.getBoolean("disponibilidad"));

                listadoProducto.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listadoProducto;
    }
}
