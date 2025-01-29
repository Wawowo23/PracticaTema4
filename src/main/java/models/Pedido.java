package models;

import java.time.LocalDate;

public class Pedido {
    // Atributos
    private String id;
    private float precioTotal;
    private String estado;
    private String comentario;
    private LocalDate fechaPedido;
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;

    // Atributo de la clase
    private static int pedidosCreados = 0;

    //Constructor


    public Pedido() {


        id = generaIdPedido();
        precioTotal = 0;
        estado = "Preparando";
        comentario = "";
        fechaPedido = LocalDate.now();
        producto1 = null;
        producto2 = null;
        producto3 = null;
        pedidosCreados++;
    }

    // Constructor copia
    public Pedido (Pedido pedido) {
        this.id = pedido.id;
        this.precioTotal = pedido.precioTotal;
        this.estado = pedido.estado;
        this.comentario = pedido.comentario;
        this.fechaPedido = pedido.fechaPedido;
        this.producto1 = pedido.producto1;
        this.producto2 = pedido.producto2;
        this.producto3 = pedido.producto3;
        pedidosCreados++;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Producto getProducto1() {
        return producto1;
    }

    public void setProducto1(Producto producto1) {
        this.producto1 = producto1;
    }

    public Producto getProducto2() {
        return producto2;
    }

    public void setProducto2(Producto producto2) {
        this.producto2 = producto2;
    }

    public Producto getProducto3() {
        return producto3;
    }

    public void setProducto3(Producto producto3) {
        this.producto3 = producto3;
    }

    public static int getPedidosCreados() {
        return pedidosCreados;
    }

    public static void setPedidosCreados(int pedidosCreados) {
        Pedido.pedidosCreados = pedidosCreados;
    }

    // Metodos

    // Metodo que genera automáticamente el id de un pedido
    private String generaIdPedido(){
        String salida = "";
        salida += (int)((Math.random() * 100000) + 1);
        while (salida.length() < 6) {
            salida = "0" + salida;
        }
        return salida;
    }

    // Metodo que calcula la fecha estimada de llegada de un pedido
    public String calculaFechaEstimada() {
        // A la fecha de realización del pedido le sumamos 5 días
        return String.valueOf(fechaPedido.plusDays(5));
    }

    // Metodo que pinta los datos de un pedido necesarios para el cliente
    public String pintaPedidoParaCliente() {
        String salida = "";
        salida += "Fecha del pedido: " + fechaPedido + "\n";
        salida += "Fecha de entrega estimada: " + calculaFechaEstimada() + "\n";
        salida += "Comentario del pedido: " + comentario + "\n";
        // Aseguramos los productos que tiene un pedido
        salida += ((producto1 == null)? "": producto1.pintaProductoPedido());
        salida += ((producto2 == null)? "": producto2.pintaProductoPedido());
        salida += ((producto3 == null)? "": producto3.pintaProductoPedido());
        salida += "Total del pedido: " + precioTotal + "E\n";
        return salida;
    }

    // Metodo que pinta los datos de un pedido necesarios para los trabajadores y el admin
    public String pintaPedidoParaTrabajadorAdmin() {
        String salida = "";
        salida += "==========  Pedido: " + id + "  ==========" + "\n";
        salida += "Fecha del pedido: " + fechaPedido + "\n";
        salida += "Fecha de entrega estimada: " + calculaFechaEstimada() + "\n";
        salida += "Comentario del pedido: " + comentario + "\n";
        // Aseguramos los productos que tiene un pedido
        salida += ((producto1 == null)? "": producto1.pintaProductoPedido());
        salida += ((producto2 == null)? "": producto2.pintaProductoPedido());
        salida += ((producto3 == null)? "": producto3.pintaProductoPedido());
        salida += "Total del pedido: " + precioTotal + "E\n";
        salida += "Estado del pedido: " + estado + "\n";
        return salida;
    }

    // Metodo que mete productos ya seleccionados en un pedido
    public boolean insertaProducto(Producto producto, int cantidad) {
        // Aseguramos que el pedido no esté lleno ya
        if (pedidoLleno()) return false;
        // Aseguramos que la cantidad ingresada no es superior a la que hay de stock
        if (!producto.salidaProducto(cantidad)) return false;
        // Comprobamos cual de los productos del pedido está en null para insertar el producto
        if (producto1 == null) {
            producto1 = producto;
            // Calculamos el precio del pedido
            precioTotal += (producto1.getPrecio() * cantidad);
            return true;
        }
        if (producto2 == null) {
            producto2 = producto;
            precioTotal += (producto2.getPrecio() * cantidad);
            return true;
        }
        producto3 = producto;
        precioTotal += (producto3.getPrecio() * cantidad);
        return true;

    }

    // Metodo para comprobar cuantos productos tiene un pedido
    public int cantidadProductos () {
        int salida = 0;
        if (producto1 != null) salida++;
        if (producto2 != null) salida++;
        if (producto3 != null) salida++;
        return salida;
    }

    // Metodo para comprobar si el pedido ya está lleno
    public boolean pedidoLleno () {
        return cantidadProductos() == 3;
    }

    // Metodo que cambia el estado de un pedido en base a una opción ya introducida
    public void cambiaEstado (int op) {
        switch (op) {
            case 1:
                estado = "Recibido";
                break;
            case 2:
                estado = "En preparación";
                break;
            case 3:
                estado = "Retrasado";
                break;
            case 4:
                estado = "Cancelado";
                break;
            case 5:
                estado = "Enviado";
                break;
        }

    }

    // Metodo que cambia la fecha estimada de entrega de un pedido
    public void cambiaFechaEstimada(LocalDate fechaNueva) {
        setFechaPedido(fechaNueva.minusDays(5));
    }

    // Metodo que utilizan los trabajadores y el admin para insertar un comentario en un pedido
    public void insertaComentario (String comentario) {
        this.comentario = comentario;
    }

    // Metodo que pinta un pedido para el menú de asignación de pedidos
    public String pintaSeleccionadoAsignacion() {
        return id + " - " + cantidadProductos() + " producto - " + precioTotal + "E";
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id='" + id + '\'' +
                ", precioTotal=" + precioTotal +
                ", estado='" + estado + '\'' +
                ", comentario='" + comentario + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", producto1=" + producto1 +
                ", producto2=" + producto2 +
                ", producto3=" + producto3 +
                '}';
    }
}
