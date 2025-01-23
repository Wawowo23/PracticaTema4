package models;

public class Cliente {
    // Atributos
    private String nombre;
    private String apellidos;
    private String usuario;
    private String correo;
    private String clave;
    private String direccion;
    private int telefono;
    private String localidad;
    private String provincia;
    private Pedido pedido1;
    private Pedido pedido2;
    private boolean activado;

    // Constructor

    public Cliente(String nombre,String apellidos,String usuario, String correo, String clave, String direccion, int telefono, String localidad, String provincia) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.correo = correo;
        this.clave = clave;
        this.direccion = direccion;
        this.telefono = telefono;
        this.localidad = localidad;
        this.provincia = provincia;
        pedido1 = null;
        pedido2 = null;
        activado = false;
    }

    // Constructor copia

    public Cliente (Cliente clienteCopiado) {
        nombre = clienteCopiado.nombre;
        apellidos = clienteCopiado.apellidos;
        usuario = clienteCopiado.usuario;
        correo = clienteCopiado.correo;
        clave = clienteCopiado.clave;
        direccion = clienteCopiado.direccion;
        telefono = clienteCopiado.telefono;
        localidad = clienteCopiado.localidad;
        provincia = clienteCopiado.provincia;
        pedido1 = clienteCopiado.pedido1;
        pedido2 = clienteCopiado.pedido2;
        activado = clienteCopiado.activado;
    }

    // Getters y Setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    // Metodos

    // Metodo que comprueba si el usuario y contraseña introducidos corresponden a los del cliente
    public boolean login(String usuario, String clave) {
        return this.usuario.equals(usuario) && this.clave.equals(clave);
    }

    // Metodo que comprueba si un cliente ya ha realizado todos sus pedidos
    public boolean pedidosCompletos () {
        return pedido2 != null && pedido1 != null;
    }

    // Metodo que comprueba si un cliente no ha realizado ningún pedido
    public boolean pedidosVacios () {
        return pedido2 == null && pedido1 == null;
    }

    // Metodo por el cual un cliente realiza un pedido
    public boolean insertaPedidos (Pedido pedido) {
        if (pedidosCompletos()) return false;
        if (pedido1 == null) {
            pedido1 = new Pedido(pedido);
            return true;
        }
        pedido2 = new Pedido(pedido);
        return true;
    }


    // Metodo que pinta los datos de un cliente
    public String pintaCliente() {
        return "Cliente: " + nombre + " " + apellidos + "\n" +
                "Usuario: " + usuario + "\n" +
                "Dirección: " + direccion +  "\n" +
                "Localidad: " + localidad +  "\n" +
                "Provincia: " + provincia +  "\n" +
                "Teléfono: " + telefono +  "\n" +
                "Correo: " + correo +  "\n";
    }

    // Metodo que pinta los distintos datos de un cliente y sus pedidos
    public String pintaPedidoConCliente () {
        String salida = "";
        // Comprobamos si el cliente ha realizado algún pedido
        if (pedidosVacios()) return "No hay pedidos todavía";
        if (pedido1 != null) {
            salida += "============   " + pedido1.getId() + "   ============\n";
            salida += "Estado: " + pedido1.getEstado() + "\n";
            salida += pintaCliente();
            salida += pedido1.pintaPedidoParaCliente();
        }
        if (pedido2 != null) {
            salida += "============   " + pedido2.getId() + "   ============\n";
            salida += "Estado: " + pedido2.getEstado() + "\n";
            salida += pintaCliente();
            salida += pedido2.pintaPedidoParaCliente();
        }
        return salida;
    }


}
