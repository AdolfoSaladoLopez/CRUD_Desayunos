package Principal;

import Controlador.PedidoDAOMySQL;
import Modelo.Pedido;
import Modelo.Producto;

import java.sql.Date;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PedidoDAOMySQL pedidoDAOMySQL = new PedidoDAOMySQL();

        Producto producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombreProducto("Catalana");
        producto.setTipoProducto("Bocadillo");
        producto.setPrecioProducto(1.80F);
        producto.setDisponibilidadProducto(true);

        //System.out.println(producto);

        var listaProductos = new ArrayList<Producto>();
        listaProductos.add(producto);

        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setIdMismoPedido(1);
        pedido.setFechaPedido(Date.valueOf("2022-10-17"));
        pedido.setEstadoPedido("Pendiente");
        pedido.setNombreCliente("Estefanía");
        pedido.setListaProductos(listaProductos);

        System.out.println(pedido);

        var listadoPedidos = new ArrayList<Pedido>();
        listadoPedidos = pedidoDAOMySQL.verPedidosPendientesHoy();

        listadoPedidos.forEach(
                Pedido -> System.out.println(Pedido)
        );

    }
}