package models;

public class Tienda {
    // Atributos
    private Cliente cliente1;
    private Cliente cliente2;
    private Trabajador trabajador1;
    private Trabajador trabajador2;
    private Trabajador trabajador3;
    private Admin admin;
    private Producto p1;
    private Producto p2;
    private Producto p3;
    private Producto p4;
    private Producto p5;
    private Producto p6;
    private Producto p7;
    private Producto p8;


    // Constructor
    public Tienda() {
        cliente1 = null;
        cliente2 = null;
        trabajador1 = null;
        trabajador2 = null;
        trabajador3 = null;
        admin = new Admin();
        p1 = new Producto("Libro de Recetas", 5.75f, 15);
        p2 = new Producto("Libro de bricolaje", 6.50f, 17);
        p3 = new Producto("Libro de informática", 8.90f, 10);
        p4 = new Producto("Harry Potter y la piedra filosofal", 15.00f, 40);
        p5 = new Producto("Gerónimo Stilton en el Reino de la Fantasía", 12.15f, 30);
        p6 = new Producto("El Señor de los Anillos: El Retorno del Rey", 18.00f, 30);
        p7 = new Producto("Un Corazón por Navidad", 16.95f, 20);
        p8 = new Producto("Akira: Vol I", 19.00f, 13);
    }

    // Getters y Setters


    public Cliente getCliente1() {
        return cliente1;
    }

    public void setCliente1(Cliente cliente1) {
        this.cliente1 = cliente1;
    }

    public Cliente getCliente2() {
        return cliente2;
    }

    public void setCliente2(Cliente cliente2) {
        this.cliente2 = cliente2;
    }

    public Trabajador getTrabajador1() {
        return trabajador1;
    }

    public void setTrabajador1(Trabajador trabajador1) {
        this.trabajador1 = trabajador1;
    }

    public Trabajador getTrabajador2() {
        return trabajador2;
    }

    public void setTrabajador2(Trabajador trabajador2) {
        this.trabajador2 = trabajador2;
    }

    public Trabajador getTrabajador3() {
        return trabajador3;
    }

    public void setTrabajador3(Trabajador trabajador3) {
        this.trabajador3 = trabajador3;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Metodos

    public void mock() {
        cliente1 = new Cliente("David", "Morante", "migue", "a@", "1234", "Calle hola", 122344556, "Martos", "Jaen");

        // cliente2 = new Cliente("Lara","Camara","Lara","lara@gmail.com","0405","Calle La Cerca",4557656,"Torredonjimeno","Jaen");
        trabajador1 = new Trabajador("Rosendo", "Martos", "Rosendo", "1234", "miguelangelcamaracasado@gmail.com", "Calle amai", 898989898);

    }

    // Metodo que comprueba si el usuario y la contraseña introducidas corresponden a un cliente, trabajador o al admin
    public String login(String usuario, String clave) {
        if (cliente1 != null && cliente1.login(usuario, clave)) return "cliente";
        if (cliente2 != null && cliente2.login(usuario, clave)) return "cliente";
        if (trabajador1 != null && trabajador1.login(usuario, clave)) return "trabajador";
        if (trabajador2 != null && trabajador2.login(usuario, clave)) return "trabajador";
        if (trabajador3 != null && trabajador3.login(usuario, clave)) return "trabajador";
        if (admin.login(usuario, clave)) return "admin";
        return "error";
    }

    // Metodo que devuelve al cliente al que corresponden los datos de inicio de sesión
    public Cliente loginCliente(String usuario, String clave) {
        if (!login(usuario, clave).equals("cliente")) return null;
        if (cliente1.login(usuario, clave)) return cliente1;
        if (cliente2.login(usuario, clave)) return cliente2;
        return null;
    }

    // Metodo que cuenta la cantidad de clientes que hay
    public int cuentaClientes() {
        int salida = 0;
        if (cliente1 != null) salida++;
        if (cliente2 != null) salida++;
        return salida;
    }

    //Metodo que comprueba si hay espacio para registrar más clientes
    public boolean clientesLlenos() {
        return cuentaClientes() == 2;
    }

    // Metodo que comprueba si hay espacio libre para registrar un cliente y lo registra
    public boolean registroCliente(Cliente clienteRegistrado) {
        if (clientesLlenos()) return false;
        if (cliente1 == null) {
            cliente1 = new Cliente(clienteRegistrado);
            return true;
        }
        if (cliente2 == null) {
            cliente2 = new Cliente(clienteRegistrado);
            return true;
        }
        return false;
    }

    // Metodo que comprueba si hay espacio para registrar a un trabajador y lo registra
    public boolean registroTrabajador(Trabajador trabajadorRegistrado) {
        if (trabajadoresLlenos()) return false;
        if (trabajador1 == null) {
            trabajador1 = new Trabajador(trabajadorRegistrado);
            return true;
        }
        if (trabajador2 == null) {
            trabajador2 = new Trabajador(trabajadorRegistrado);
            return true;
        }
        trabajador3 = new Trabajador(trabajadorRegistrado);
        return true;
    }

    // Metodo que comprueba si ya hay 3 trabajadores registrados
    public boolean trabajadoresLlenos() {
        return trabajador1 != null && trabajador2 != null && trabajador3 != null;
    }

    // Metodo que registra un pedido
    public void registraPedido(Pedido pedido) {
        if (admin.pedidosCompletos()) return;
        admin.registraPedido(pedido);

    }

    // Metodo que devuelve al trabajador al que corresponden los datos de inicio de sesión
    public Trabajador loginTrabajador(String usuario, String clave) {
        if (!login(usuario, clave).equals("trabajador")) return null;
        if (trabajador1.login(usuario, clave)) return trabajador1;
        if (trabajador2.login(usuario, clave)) return trabajador2;
        if (trabajador3.login(usuario, clave)) return trabajador3;
        return null;
    }

    // Metodo que devuelve al admin una vez se comprueba que los datos de inicio de sesión le corresponden
    public Admin loginAdmin(String usuario, String clave) {
        if (login(usuario, clave).equals("admin")) return admin;
        return null;
    }

    // Metodo que calcula el número de trabajadores registrados
    private int numeroTrabajadores() {
        int salida = 0;
        if (trabajador1 != null) salida++;
        if (trabajador2 != null) salida++;
        if (trabajador3 != null) salida++;
        return salida;
    }

    // Metodo que asigna automaticamnte los pedidos a los trabajadores
    public Trabajador asignacionAutomatica(Pedido pedido) {
        if (numeroTrabajadores() == 0) return null;
        if (numeroTrabajadores() == 1) {
            trabajador1.insertaPedido(pedido);
            return trabajador1;
        }
        if (numeroTrabajadores() == 2) {
            if (trabajador1.numeroPedidos() < trabajador2.numeroPedidos()) {
                trabajador1.insertaPedido(pedido);
                return trabajador1;
            }
            if (trabajador2.numeroPedidos() < trabajador1.numeroPedidos()) {
                trabajador2.insertaPedido(pedido);
                return trabajador2;
            }
            // Si los trabajadores tienen el mismo número de pedidos asignados
            // el pedido introducido se tendrá que asignar manualmente
            return null;
        }
        if (numeroTrabajadores() == 3) {
            if (trabajador1.numeroPedidos() < trabajador2.numeroPedidos() && trabajador1.numeroPedidos() < trabajador3.numeroPedidos()) {
                trabajador1.insertaPedido(pedido);
                return trabajador1;
            }
            if (trabajador2.numeroPedidos() < trabajador1.numeroPedidos() && trabajador2.numeroPedidos() < trabajador3.numeroPedidos()) {
                trabajador2.insertaPedido(pedido);
                return trabajador2;
            }
            if (trabajador3.numeroPedidos() < trabajador2.numeroPedidos() && trabajador3.numeroPedidos() < trabajador1.numeroPedidos()) {
                trabajador3.insertaPedido(pedido);
                return trabajador3;
            }
            return null;
        }
        return null;

    }

    // Metodo que calcula cuantos pedidos registrados no han sido asignados todavía
    public int pedidosSinAsignar() {
        int salida = 0;
        salida += admin.numeroPedidos();
        if (trabajador1 != null) salida -= trabajador1.numeroPedidos();
        if (trabajador2 != null) salida -= trabajador2.numeroPedidos();
        if (trabajador3 != null) salida -= trabajador3.numeroPedidos();
        return salida;
    }

    // Metodo que pinta a los trabajadores para el menú de asignación
    public String pintaTrabajadoresParaSeleccion() {
        String salida = "";
        salida += "1.- " + ((trabajador1 == null) ? "" : trabajador1.pintaTrabajadorSeleccion()) + "\n";
        salida += "2.- " + ((trabajador2 == null) ? "" : trabajador2.pintaTrabajadorSeleccion()) + "\n";
        salida += "3.- " + ((trabajador3 == null) ? "" : trabajador3.pintaTrabajadorSeleccion()) + "\n";
        return salida;
    }

    // Metodo que asigna un pedido introducido a un trabajador según la opción indicada
    public Trabajador asignaPedidoTrabajador(int op, Pedido pedidoSeleccionado) {
        switch (op) {
            case 1:
                trabajador1.insertaPedido(pedidoSeleccionado);
                return trabajador1;
            case 2:
                trabajador2.insertaPedido(pedidoSeleccionado);
                return trabajador2;
            case 3:
                trabajador3.insertaPedido(pedidoSeleccionado);
                return trabajador3;
            default:
                return null;
        }

    }

    // Metodo que pinta los pedidos para el administrador
    public String pintaPedidosParaAdmin() {
        String salida = "";
        if (cliente1 != null && !cliente1.pedidosVacios()) salida += cliente1.pintaPedidoConCliente();
        if (cliente2 != null && !cliente2.pedidosVacios()) salida += cliente2.pintaPedidoConCliente();
        if (salida.equals("")) salida += "No hay pedidos registrados todavía";
        return salida;
    }

    // Metodo que comprueba si no hay clientes registrados
    private boolean clientesVacios() {
        return cliente1 == null && cliente2 == null;
    }

    // Metodo que pinta los datos de los trabajadores
    public String pintaTrabajadores() {
        String salida = "";
        if (numeroTrabajadores() == 0) salida += "No hay trabajadores registrados todavía";
        if (trabajador1 != null) salida += trabajador1.pintaTrabajador();
        if (trabajador2 != null) salida += trabajador2.pintaTrabajador();
        if (trabajador3 != null) salida += trabajador3.pintaTrabajador();
        return salida;
    }

    // Metodo que pinta los datos de los clientes registrados
    public String pintaClientes() {
        String salida = "";
        if (clientesVacios()) salida += "No hay clientes registrados todavía";
        if (cliente1 != null) salida += cliente1.pintaCliente();
        if (cliente2 != null) salida += cliente2.pintaCliente();
        return salida;
    }

    // Metodo que comprueba si el id de un paquete es igual al id de otro paquete
    public boolean idIguales(String id) {
        if (cliente1 != null) {
            if (cliente1.getPedido1() != null && cliente1.getPedido1().getId() == id) return true;
            if (cliente1.getPedido2() != null && cliente1.getPedido2().getId() == id) return true;
        }
        if (cliente2 != null) {
            if (cliente2.getPedido1() != null && cliente2.getPedido1().getId() == id) return true;
            if (cliente2.getPedido2() != null && cliente2.getPedido2().getId() == id) return true;
        }

        return false;
    }

    // Metodo para pintar el catálogo completo
    public String pintaCatalogo() {
        String salida = "";
        salida += "1.- " + p1.pintaProductoCatalogo() + "\n";
        salida += "2.- " + p2.pintaProductoCatalogo() + "\n";
        salida += "3.- " + p3.pintaProductoCatalogo() + "\n";
        salida += "4.- " + p4.pintaProductoCatalogo() + "\n";
        salida += "5.- " + p5.pintaProductoCatalogo() + "\n";
        salida += "6.- " + p6.pintaProductoCatalogo() + "\n";
        salida += "7.- " + p7.pintaProductoCatalogo() + "\n";
        salida += "8.- " + p8.pintaProductoCatalogo() + "\n";
        return salida;
    }

    // Metodo para que un cliente elija un producto para añadir a su pedido
    public Producto seleccionaProducto(int op) {
        return switch (op) {
            case 1 -> p1;
            case 2 -> p2;
            case 3 -> p3;
            case 4 -> p4;
            case 5 -> p5;
            case 6 -> p6;
            case 7 -> p7;
            case 8 -> p8;
            default -> null;
        };
    }

    //Metodo que comprueba si el correo introducido por un cliente ya está registrado
    public boolean correoExistente(String correo) {
        if (clientesVacios()) return false;
        if (cliente1 != null && cliente1.getCorreo().equals(correo)) return true;
        if (cliente2 != null && cliente2.getCorreo().equals(correo)) return true;
        return false;
    }

    // Metodo que localiza al dueño de un pedido mediante el id de este
    public Cliente encuentraClientePedido(String idPedido) {
        if (cliente1 != null) {
            if (cliente1.getPedido1() != null && cliente1.getPedido1().getId().equals(idPedido)) return cliente1;
            if (cliente1.getPedido2() != null && cliente1.getPedido2().getId().equals(idPedido)) return cliente1;
        }
        if (cliente2 != null) {
            if (cliente2.getPedido1() != null && cliente2.getPedido1().getId().equals(idPedido)) return cliente2;
            if (cliente2.getPedido2() != null && cliente2.getPedido2().getId().equals(idPedido)) return cliente2;
        }
        return null;
    }

    //Metodo que devuelve un pedido con el mismo id que el que ha sido introducido
    public Pedido localizaPedido(String idTemp) {
        if (admin.getPedido1() != null && admin.getPedido1().getId().equals(idTemp)) return admin.getPedido1();
        if (admin.getPedido2() != null && admin.getPedido2().getId().equals(idTemp)) return admin.getPedido2();
        if (admin.getPedido3() != null && admin.getPedido3().getId().equals(idTemp)) return admin.getPedido3();
        if (admin.getPedido4() != null && admin.getPedido4().getId().equals(idTemp)) return admin.getPedido4();
        return null;
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "cliente1=" + cliente1 +
                ", cliente2=" + cliente2 +
                ", trabajador1=" + trabajador1 +
                ", trabajador2=" + trabajador2 +
                ", trabajador3=" + trabajador3 +
                ", admin=" + admin +
                '}';
    }



}
