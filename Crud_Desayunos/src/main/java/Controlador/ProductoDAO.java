package Controlador;

import Modelo.Pedido;
import Modelo.Producto;
import java.util.ArrayList;

public interface ProductoDAO {
    ArrayList<Producto> obtenerProductosDeUnPedido(Pedido pedido);
}
