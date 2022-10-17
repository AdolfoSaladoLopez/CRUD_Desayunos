package Modelo;

import java.sql.Date;
import java.util.ArrayList;

public class Pedido {
    private Integer idPedido;
    private Date fechaPedido;
    private String nombreCliente;
    private String estadoPedido;
    private ArrayList<Producto> listaProductos;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String recorrerListadoProductos(ArrayList<Producto> listaProductos) {
        listaProductos.forEach(
                Producto -> super.toString()
        );
        return null;
    }

    @Override
    public String toString() {
        String[] arrayFechas = new String[3];
        arrayFechas = this.fechaPedido.toString().split(" ");
        String año = arrayFechas[0];
        String mes = arrayFechas[1];
        String dia = arrayFechas[2];


        return "Pedido: \n" +
                "\t- ID del pedido: " + this.idPedido +
                "\t- Fecha del pedido: " + dia + " / " + mes + " / " + año +
                "\t- Nombre del cliente: " + this.nombreCliente +
                "\t- Estado del pedido: " + this.estadoPedido + "\n" +
                recorrerListadoProductos(listaProductos);
    }
}
