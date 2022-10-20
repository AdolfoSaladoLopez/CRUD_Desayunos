package Controlador;

import Modelo.Producto;
import java.util.ArrayList;

public interface ProductoDAO {
    ArrayList<Producto> obtenerProductosCarta();
    Producto obtenerProductoPorId(Integer id);
    ArrayList<Producto> obtenerProductosDisponibles();
    ArrayList<Producto> obtenerProductosNoDisponible();
    Boolean insertarNuevoProducto(Producto producto);
    Boolean cambiarDisponibilidadProducto(Integer idProducto, Boolean disponibilidad);
}
