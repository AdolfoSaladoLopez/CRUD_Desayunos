package Principal;

import Controlador.PedidoDAOMySQL;
import Modelo.Pedido;
import Modelo.Producto;

import java.sql.Date;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        PedidoDAOMySQL pedidoDAOMySQL = new PedidoDAOMySQL();

        ArrayList<Pedido> listadoPedido = new ArrayList<>();
        listadoPedido.addAll(pedidoDAOMySQL.verPedidosPendientesHoy());

        listadoPedido.forEach(
            Pedido -> System.out.println(Pedido)
        );

       

    }
}