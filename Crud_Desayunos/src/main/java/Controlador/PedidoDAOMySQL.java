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
    private final static String PWD = "12345";

    private static final String VER_PEDIDOS_HOY_QUERY = "SELECT * " +
            "FROM pedido " +
            "WHERE fecha =  CURDATE() " +
            "AND estado = 'Pendiente'";

    private static final String OBTENER_PRODUCTOS_PEDIDO = "SELECT p.id_pedido, p.id_mismo_pedido," +
    " p.fecha, p.cliente, p.estado, pro.nombre" +
    "FROM pedido p, producto pro " +
    "WHERE p.id_producto = pro.id_producto " +
    "AND fecha =  CURDATE() " +
    "AND estado = 'Pendiente'";

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
                
                Producto producto = new Producto();
                producto.setNombreProducto(rs.getString("nombre"));

                listadoProductosPedido.add(producto);
            

                if ()
                listadoPedidosPendientes.add(pedido);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listadoPedidosPendientes;
    }

    @Override
    public ArrayList<Pedido> verPedidosUsuarioConcreto(String nombre) {
        var listadoPedidos = new ArrayList<Pedido>();

             

        return listadoPedidos;
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

                producto.setIdProducto(rs.getInt("id_producto"));
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

    @Override
    public void mostrarListadoPedidos(Pedido listadoPedidos) {
        System.out.println("Pedido: \n" +
                "\t- ID del pedido: " + listadoPedidos.getIdMismoPedido() +
                "\n\t- Fecha del pedido: " + listadoPedidos.getFechaPedido() +
                "\n\t- Nombre del cliente: " + listadoPedidos.getNombreCliente() +
                "\n\t- Estado del pedido: " + listadoPedidos.getEstadoPedido() + "\n");


        var listadoProductos = new ArrayList<Producto>();
        listadoProductos.addAll(listadoPedidos.getListaProductos());
        
        listadoProductos.forEach(
            Producto -> System.out.println(Producto)
        );


        
    }
}
