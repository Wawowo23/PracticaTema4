package models;

import java.time.LocalDate;

public class Trabajador {
    // Atributos
    private String nombre;
    private String apellidos;
    private String usuario;
    private String clave;
    private String idTrabajador;
    private String correo;
    private String direccion;
    private int telefono;
    private Pedido pedido1;
    private Pedido pedido2;
    private String idTelegram;

    // Atributo de la clase
    private static int trabajadoresCreados = 0;

    // Constructor

    public Trabajador(String nombre, String apellidos, String usuario, String clave, String correo, String direccion, int telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.clave = clave;
        this.idTrabajador = generadorIdTrabajador();
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        pedido1 = null;
        pedido2 = null;
        idTelegram = "6622508571";
        trabajadoresCreados++;
    }

    //Constructor copia

    public Trabajador (Trabajador trabajador) {
        nombre = trabajador.nombre;
        apellidos = trabajador.apellidos;
        usuario = trabajador.usuario;
        clave = trabajador.clave;
        idTrabajador = trabajador.idTrabajador;
        correo = trabajador.correo;
        direccion = trabajador.direccion;
        telefono = trabajador.telefono;
        pedido1 = trabajador.pedido1;
        pedido2 = trabajador.pedido2;
        idTelegram = trabajador.idTelegram;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public static int getTrabajadoresCreados() {
        return trabajadoresCreados;
    }

    public static void setTrabajadoresCreados(int trabajadoresCreados) {
        Trabajador.trabajadoresCreados = trabajadoresCreados;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getIdTelegram() {
        return idTelegram;
    }

    public void setIdTelegram(String idTelegram) {
        this.idTelegram = idTelegram;
    }

    // Metodos

    // Metodo que genera automáticaente el id del trabajador
    private String generadorIdTrabajador () {
        String salida = "";
        // Usamos el número de orden de registro de los trabajadores
        salida += (getTrabajadoresCreados() + 1);
        while (salida.length() < 3) {
            salida = "0" + salida;
        }
        // Usamos la inicial del trabajador
        salida += getNombre().toUpperCase().charAt(0);
        return salida;
    }

    // Metodo que comprueba si el usuario y contraseña introducidos corresponden a los del trabajador
    public boolean login (String usuario, String clave) {
        return this.usuario.equals(usuario) && this.clave.equals(clave);
    }

    // Metodo uqe comprueba el número de pedidos que tiene un trabajador asignados
    public int numeroPedidos () {
        int salida = 0;
        if (pedido1 != null) salida++;
        if (pedido2 != null) salida++;
        return salida;
    }

    // Metodo que comprueba si un trabajador ya tiene 2 pedidos asignados
    private boolean pedidosCompletos () {
        return numeroPedidos() == 2;
    }

    // Metodo que comprueba si un trabajador no tiene pedidos asignados
    private boolean pedidosVacios () {
        return numeroPedidos() == 0;
    }

    // Metodo por el cual un pedido es asignado a un trabajador
    public boolean insertaPedido (Pedido pedido) {
        if (pedidosCompletos()) return false;
        if (pedido1 == null) {
            pedido1 = pedido;
            return true;
        }
        pedido2 = pedido;
        return true;
    }

    // Metodo por el cual se le introduce un comentario a un pedido
    public void insertaComentarioPedido(String comentario, String idPedido) {
        if (pedido1 != null && idPedido.equals(pedido1.getId())) pedido1.insertaComentario(comentario);
        if (pedido2 != null && idPedido.equals(pedido2.getId())) pedido2.insertaComentario(comentario);
    }

    // Metodo por el cual se cambia el estado de un pedido
    public void cambiaEstadoPedido (int op, String idPedido) {
        if (pedido1 != null && idPedido.equals(pedido1.getId())) pedido1.cambiaEstado(op);
        if (pedido2 != null && idPedido.equals(pedido2.getId())) pedido2.cambiaEstado(op);

    }

    // Metodo por el cual se cambia la fecha de entrega estimada de un pedido
    public void cambiaFechaPedido (LocalDate fechaNueva, String idPedido) {
        if (pedido1 != null && idPedido.equals(pedido1.getId())) pedido1.cambiaFechaEstimada(fechaNueva);
        if (pedido2 != null && idPedido.equals(pedido2.getId())) pedido2.cambiaFechaEstimada(fechaNueva);
    }

    // Metodo por el cual se pintan los datos de un trabajador
    public String pintaTrabajador () {
        String salida = "";
        salida += "=====  Trabajador: " + idTrabajador + "  =====" + "\n";
        salida += "Usuario: " + usuario + "\n";
        salida += "Nombre: " + nombre + "\n";
        salida += "Apellidos: " + apellidos + "\n";
        salida += "Correo: " + correo + "\n";
        salida += "Dirección: " + direccion + "\n";
        salida += "Teléfono: " + telefono + "\n";
        return salida;
    }

    // Metodo por el cual se pintan los datos de un trabajador para el menu de asignacion de pedidos
    public String pintaTrabajadorSeleccion  () {
        return nombre + " - " + numeroPedidos() + " pedidos en proceso";
    }

    // Metodo por el cual se pintan los pedidos asignados a un trabajador
    public String pintaPedidosAsignados () {
        if (pedidosVacios()) return "Aún no tienes pedidos asignados";
        String salida = "";
        if (pedido1 != null) salida += pedido1.pintaPedidoParaTrabajadorAdmin();
        if (pedido2 != null) salida += pedido2.pintaPedidoParaTrabajadorAdmin();
        return salida;
    }
}
