package models;

import java.time.LocalDate;

public class Admin {
    // Atributos
    private  String usuario;
    private  String clave;
    private Pedido pedido1;
    private Pedido pedido2;
    private Pedido pedido3;
    private Pedido pedido4;

    // Constructor
    public Admin() {
        usuario = "Wiwi";
        clave = "1234";
        pedido1 = null;
        pedido2 = null;
        pedido3 = null;
        pedido4 = null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Pedido getPedido1() {
        return pedido1;
    }

    public void setPedido1(Pedido pedido1) {
        this.pedido1 = pedido1;
    }

    public Pedido getPedido2() {
        return pedido2;
    }

    public void setPedido2(Pedido pedido2) {
        this.pedido2 = pedido2;
    }

    public Pedido getPedido3() {
        return pedido3;
    }

    public void setPedido3(Pedido pedido3) {
        this.pedido3 = pedido3;
    }

    public Pedido getPedido4() {
        return pedido4;
    }

    public void setPedido4(Pedido pedido4) {
        this.pedido4 = pedido4;
    }

    // Metodos

    // Metodo que comprueba si el usuario y contraseña introducidos corresponden con los del admin
    public boolean login (String usuario, String clave) {
        return this.usuario.equals(usuario) && this.clave.equals(clave);
    }

    // Metodo que registra un pedido dentro del perfil del admin
    public boolean registraPedido (Pedido pedido) {
        if (pedidosCompletos()) return false;
        if (pedido1 == null) {
            pedido1 = new Pedido(pedido);
            return true;
        }
        if (pedido2 == null) {
            pedido2 = new Pedido(pedido);
            return true;
        }
        if (pedido3 == null) {
            pedido3 = new Pedido(pedido);
            return true;
        }
        if (pedido4 == null) {
            pedido4 = new Pedido(pedido);
            return true;
        }
        return false;
    }

    // Metodo que comprueba cuantos pedidos hay registrados
    public int numeroPedidos () {
        int salida = 0;
        if (pedido1 != null) salida++;
        if (pedido2 != null) salida++;
        if (pedido3 != null) salida++;
        if (pedido4 != null) salida++;
        return salida;
    }

    // Metodo que comprueba si el admin ya tiene 4 pedidos registrados
    public boolean pedidosCompletos () {
        return numeroPedidos() == 4;
    }

    // Metodo que pinta los pedidos para el admin
    public String pintaEstadosPedidos () {
        String salida = "";
        if (pedido1 != null) salida += pedido1.pintaPedidoParaTrabajadorAdmin();
        if (pedido2 != null) salida += pedido2.pintaPedidoParaTrabajadorAdmin();
        if (pedido3 != null) salida += pedido3.pintaPedidoParaTrabajadorAdmin();
        if (pedido4 != null) salida += pedido4.pintaPedidoParaTrabajadorAdmin();
        return salida;
    }

    // Metodo que pinta los pedidos para el menú de asignación del admin
    public String pintaSeleccionPedido () {
        String salida = "";
        salida += "1.- " + ((pedido1 == null) ? "":pedido1.pintaSeleccionadoAsignacion()) + "\n";
        salida += "2.- " + ((pedido2 == null) ? "":pedido2.pintaSeleccionadoAsignacion()) + "\n";
        salida += "3.- " + ((pedido3 == null) ? "":pedido3.pintaSeleccionadoAsignacion()) + "\n";
        salida += "4.- " + ((pedido4 == null) ? "":pedido4.pintaSeleccionadoAsignacion()) + "\n";
        return salida;
    }

    // Metodo que selecciona un pedido para la asignación
    public Pedido seleccionaPedido (int op) {
        return switch (op) {
            case 1 -> pedido1;
            case 2 -> pedido2;
            case 3 -> pedido3;
            case 4 -> pedido4;
            default -> null;
        };

    }

    // Metodo por el cual se cambia el estado de un pedido
    public void cambiaEstadoPedido (int op, String idPedido) {
        if (pedido1 != null && idPedido.equals(pedido1.getId())) pedido1.cambiaEstado(op);
        if (pedido2 != null && idPedido.equals(pedido2.getId())) pedido2.cambiaEstado(op);
        if (pedido3 != null && idPedido.equals(pedido3.getId())) pedido3.cambiaEstado(op);
        if (pedido4 != null && idPedido.equals(pedido4.getId())) pedido4.cambiaEstado(op);

    }

    // Metodo por el cual se cambia la fecha de entrega estimada de un pedido
    public void cambiaFechaPedido (LocalDate fechaNueva, String idPedido) {
        if (idPedido.equals(pedido1.getId()) && pedido1 != null) pedido1.cambiaFechaEstimada(fechaNueva);
        if (idPedido.equals(pedido2.getId()) && pedido2 != null) pedido2.cambiaFechaEstimada(fechaNueva);
        if (idPedido.equals(pedido3.getId()) && pedido3 != null) pedido3.cambiaFechaEstimada(fechaNueva);
        if (idPedido.equals(pedido4.getId()) && pedido4 != null) pedido4.cambiaFechaEstimada(fechaNueva);
    }

    // Metodo por el cual se le introduce un comentario a un pedido
    public void insertaComentarioPedido(String comentario, String idPedido) {
        if (idPedido.equals(pedido1.getId()) && pedido1 != null) pedido1.insertaComentario(comentario);
        if (idPedido.equals(pedido2.getId()) && pedido2 != null) pedido2.insertaComentario(comentario);
        if (idPedido.equals(pedido3.getId()) && pedido3 != null) pedido3.insertaComentario(comentario);
        if (idPedido.equals(pedido4.getId()) && pedido4 != null) pedido4.insertaComentario(comentario);
    }
}
