package Controlador;

import Modelo.Pedido;
import Modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PedidoDAOMySQL implements PedidoDAO, ProductoDAO {
    private static Connection conexion = Conexion.getConexion();
    private static PedidoDAOMySQL dao = new PedidoDAOMySQL();

    /* SENTENCIAS PRODUCTO */
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
            SELECT * FROM producto WHERE disponibilidad = 1
            """;

    private static final String OBTENER_PRODUCTOS_NO_DISPONIBLES = """
            SELECT * FROM producto WHERE disponibilidad = 0
            """;

    private static final String INSERTAR_NUEVO_PRODUCTO = """
            INSERT INTO producto(nombre, tipo, precio, disponibilidad)
             VALUES (?, ?, ?, ?);
                """;

    private static final String CAMBIAR_DISPONIBILIDAD_PRODUCTO = """
            UPDATE producto SET disponibilidad = (?)
             WHERE id_producto = (?);

            """;

    /* SENTENCIAS PEDIDO */
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

    @Override
    public Boolean insertarNuevoProducto(Producto producto) {
        Boolean resultado = false;

        try (var ps = conexion.prepareStatement(INSERTAR_NUEVO_PRODUCTO)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getTipoProducto());
            ps.setFloat(3, producto.getPrecioProducto());
            ps.setBoolean(4, producto.getDisponibilidadProducto());

            if (ps.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return resultado;
    }

    @Override
    public Boolean cambiarDisponibilidadProducto(Integer idProducto, Boolean disponibilidad) {
        Boolean resultado = false;

        try (var ps = conexion.prepareStatement(CAMBIAR_DISPONIBILIDAD_PRODUCTO)) {
            ps.setBoolean(1, disponibilidad);
            ps.setInt(2, idProducto);

            if (ps.executeUpdate() == 0) {
                resultado = true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return resultado;
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
            System.out.println("3.- Mostrar productos disponibles.");
            System.out.println("4.- Mostrar productos no disponibles.");
            System.out.println("5.- Insertar un nuevo producto.");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    var listadoProductos = new ArrayList<Producto>();
                    listadoProductos.addAll(dao.obtenerProductosCarta());

                    System.out.println("Listado de producto: ");
                    listadoProductos.forEach(
                            producto -> System.out
                                    .println("\t" + producto.getIdProducto() + ".- " + producto.getNombreProducto()));
                    break;
                case 2:
                    listadoProductos = new ArrayList<Producto>();
                    listadoProductos.addAll(dao.obtenerProductosCarta());

                    System.out.println();
                    System.out.println("Listado de producto: ");
                    listadoProductos.forEach(
                            producto -> System.out
                                    .println("\t" + producto.getIdProducto() + ".- " + producto.getNombreProducto()));

                    System.out.print("¿De qué producto quiere ver información?: ");
                    Integer eleccionProducto = sc.nextInt();
                    if (eleccionProducto > 0 && eleccionProducto < listadoProductos.size()) {
                        var producto = dao.obtenerProductoPorId(eleccionProducto);

                        System.out.println(producto);
                        break;
                    } else {
                        System.out.println("No ha introducido un número correcto.");
                        break;
                    }
                case 3:
                    var listaProductosDisponibles = new ArrayList<Producto>();
                    listaProductosDisponibles.addAll(dao.obtenerProductosDisponibles());

                    System.out.println();
                    System.out.println("Listado de productos disponibles: ");

                    if (listaProductosDisponibles.size() > 0) {
                        listaProductosDisponibles.forEach(
                                producto -> System.out.println("\t- Nombre: " + producto.getNombreProducto() +
                                        " - Precio: " + producto.getPrecioProducto()));
                    } else {
                        System.out.println("Actualmente ningún producto está disponible.");
                    }

                    break;
                case 4:
                    var listaProductosNoDisponibles = new ArrayList<Producto>();
                    listaProductosNoDisponibles.addAll(dao.obtenerProductosNoDisponible());

                    System.out.println();
                    System.out.println("Listado de productos no disponibles: ");

                    if (listaProductosNoDisponibles.size() > 0) {
                        listaProductosNoDisponibles.forEach(
                                producto -> System.out.println("\t- Nombre: " + producto.getNombreProducto() +
                                        " - Precio: " + producto.getPrecioProducto()));
                    } else {
                        System.out.println("Actualmente todos los productos están disponibles.");
                    }

                    break;
                case 5:
                    var listaProductosNombre = new ArrayList<Producto>();
                    listaProductosNombre.addAll(dao.obtenerProductosCarta());
                    Scanner sc2 = new Scanner(System.in);

                    var productoNuevo = new Producto();

                    Boolean productoValido = false;
                    String nombreProducto = "";
                    Integer contador = 0;

                    while (productoValido != true) {
                        System.out.println();
                        System.out.println("Insertar nuevo producto:");
                        System.out.print("Indique el nombre de su producto: ");
                        nombreProducto = sc2.nextLine();
    
                        for (Producto producto : listaProductosNombre) {
                            if (producto.getNombreProducto().toLowerCase().equals(nombreProducto.toLowerCase())) {
                               contador++;
                            } 
                            
                        }

                        if (contador == 0) {
                            productoValido = true;
                        } else {
                            System.out.println("El producto ya se encuentra en la carta.");
                            contador = 0;
                        }

                    }
                    
                    if (productoValido) {

                        Integer opcionTipo = 5;

                        while (opcionTipo < 0 || opcionTipo > 4) {
                            System.out.println();
                            System.out.println("Insertar tipo de producto: ");
                            System.out.println("1.- Refresco.");
                            System.out.println("2.- Bebida.");
                            System.out.println("3.- Bocadillo");
                            System.out.println("4.- Bollería.");
                            System.out.print("Elija la opción ");
                            opcionTipo = sc.nextInt();

                            if (opcionTipo < 0 && opcionTipo > 5) {
                                System.out.println("No ha introducido un tipo correcto.");
                            }
                        }

                        System.out.println();
                        System.out.print("Introduzca el precio del producto: ");
                        Float precioProducto = sc.nextFloat();

                        Boolean comprobarResultado = false;
                        Integer opcionDisponibilidad = 2;
                        Boolean opcionBoolean = false;

                        while (comprobarResultado != true) {
                            System.out.println();
                            System.out.println("Introduzca disponibilidad del producto: ");
                            System.out.println("0.- No disponible. ");
                            System.out.println("1.- Disponible. ");
                            System.out.print("Escriba: ");
                            opcionDisponibilidad = sc.nextInt();

                            if (opcionDisponibilidad != 0 && opcionDisponibilidad != 1) {
                                System.out.println("No ha elegido una opción correcta.");
                            } else {
                                comprobarResultado = true;

                                
                                if (opcionDisponibilidad  == 0) {
                                    opcionBoolean = false;
                                } else {
                                    opcionBoolean = true;
                                }
                            }
                        }

                        String tipoProducto = "";

                        switch (opcionTipo) {
                            case 1: tipoProducto = "Refresco"; break;
                            case 2: tipoProducto = "Bebida"; break;
                            case 3: tipoProducto = "Bocadillo"; break;
                            case 4: tipoProducto = "Bolleria"; break;
                        }

                        productoNuevo.setNombreProducto(nombreProducto);
                        productoNuevo.setTipoProducto(tipoProducto);
                        productoNuevo.setPrecioProducto(precioProducto);
                        productoNuevo.setDisponibilidadProducto(opcionBoolean);

                  

                    } else {
                        System.out.println();
                        System.out.println("El producto ya se encuentra en la carta.");
                        dao.mostrarMenu();
                    }

                    if (dao.insertarNuevoProducto(productoNuevo) == true) {
                        System.out.println();
                        System.out.println("Producto insertado con éxito.");
                    }

                    break;

                default:
                    System.out.println("Elección incorrecta.");
            }

        }
    }

}
