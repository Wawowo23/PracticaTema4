package views;


import models.*;
import utils.Comunicaciones;
import utils.Menus;
import utils.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class main {
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        Tienda tienda = new Tienda();
        String tipoUsuario, opMenusUsuarios, quiereSeguir = "";


        // Iniciamos el programa teniendo ya un cliente y un trabajador creados
        tienda.mock();

        do { // Creamos un bucle para que no se termine el programa
            Utils.limpiaPantalla();
            inicio();
            String op = s.nextLine();
            switch (op) {
                case "1": // Damos la opción de que un cliente se registre
                    if (tienda.clientesLlenos()) System.out.println("No se pueden registrar más clientes");
                    else {
                        System.out.println((tienda.registroCliente(menuRegistro(tienda))) ?
                                "Se ha registrado correctamente"
                                : "Ha ocurrido un error al registrarse");
                    }
                    Utils.pulsaParaContinuar();
                    break;
                case "2": // Damos la opción de que el usuario inicie sesión
                    Utils.limpiaPantalla();
                    System.out.print("Introduzca su usuario: ");
                    String usuarioTemp = s.nextLine();
                    System.out.print("Introduzca su contraseña: ");
                    String claveTemp = s.nextLine();
                    tipoUsuario = tienda.login(usuarioTemp, claveTemp);

                    Utils.limpiaPantalla();
                    switch (tipoUsuario) { // Según el tipo de usuario que inicie sesión mostramos distintos menús
                        case "cliente": // Menú para clientes
                            Cliente clienteTemporal = tienda.loginCliente(usuarioTemp, claveTemp);
                            if (!clienteTemporal.isActivado()) {
                                boolean verificado = false;
                                do {
                                    String token = generaToken();
                                    Comunicaciones.enviarCorreoVerificacion(clienteTemporal.getCorreo(), token);
                                    System.out.print("Introduzca el código que le ha sido enviado: ");
                                    String codigoVerificacionTeclado = s.nextLine();
                                    if (codigoVerificacionTeclado.equals(token)) {
                                        clienteTemporal.setActivado(true);
                                        verificado = true;
                                        System.out.println("Su correo ha sido verificado");
                                    } else
                                        System.out.println("El código introducido es incorrecto. Se volverá a enviar un nuevo correo.");
                                    Utils.pulsaParaContinuar();
                                } while (!verificado);

                            }
                            do {
                                Menus.menuCliente(clienteTemporal);
                                opMenusUsuarios = s.nextLine();
                                switch (opMenusUsuarios) {
                                    case "1": // Pintamos el catálogo
                                        tienda.pintaCatalogo();
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "2": // El cliente realiza un pedido
                                        // Comprobamos si el cliente puede realizar más pedidos
                                        if (clienteTemporal.pedidosCompletos())
                                            System.out.println("No puede realizar más pedidos");
                                        else {
                                            boolean unico = false;
                                            Pedido pedidoAgregado;
                                            do {
                                                pedidoAgregado = new Pedido();
                                                if (!tienda.idIguales(pedidoAgregado.getId())) unico = true;
                                            } while (!unico);

                                            do { // Creamos un bucle para que el cliente añada productos a su pedido
                                                Producto productoAgregado = seleccionaProducto(tienda);
                                                if (productoAgregado == null)
                                                    System.out.println("El producto que ha introducido no existe");
                                                    // Comprobamos si el producto seleccionado existe
                                                else {
                                                    int cantidad = -1;
                                                    do {
                                                        try {

                                                            System.out.print("Introduzca la cantidad de este producto que quiere comprar: ");
                                                            cantidad = Integer.parseInt(s.nextLine());

                                                        } catch (NumberFormatException e) {
                                                            System.out.println("ERROR. El formato introducido es incorrecto.");
                                                        }
                                                    } while (cantidad == -1);
                                                    System.out.println(((pedidoAgregado.insertaProducto(productoAgregado, cantidad)) ?
                                                            "Producto agregado correctamente"
                                                            : "ERROR: Se ha producido un error al agregar el producto al pedido"));
                                                    if (pedidoAgregado.pedidoLleno())
                                                        System.out.println("Su pedido está completo. No puede agregar más de 3 productos a su pedido");
                                                    else { //Preguntamos si el cliente quiere seguir añadiendo productos a su pedido
                                                        do {
                                                            System.out.print("Quiere introducir otro producto al pedido (S/N): ");
                                                            quiereSeguir = s.nextLine();
                                                        } while (!quiereSeguir.equalsIgnoreCase("s") && !quiereSeguir.equalsIgnoreCase("n"));
                                                    }
                                                }
                                            } while (!quiereSeguir.equalsIgnoreCase("n") && !pedidoAgregado.pedidoLleno());
                                            // Tras haber completado el pedido lo agregamos al perfil del cliente,
                                            // lo registramos y lo asignamos a un trabajador
                                            clienteTemporal.insertaPedidos(pedidoAgregado);
                                            tienda.registraPedido(pedidoAgregado);
                                            Trabajador trabajadorParaMensaje = tienda.asignacionAutomatica(pedidoAgregado);
                                            if (trabajadorParaMensaje != null) {
                                                Comunicaciones.enviaMensajeTelegram("Se le ha sido asignado un pedido", trabajadorParaMensaje.getIdTelegram());
                                                Comunicaciones.enviaCorreoPedidoAsignado(pedidoAgregado,trabajadorParaMensaje,clienteTemporal);
                                            }
                                            Utils.pulsaParaContinuar();
                                        }
                                        break;
                                    case "3": // Pintamos los pedidos realizados por un cliente
                                        if (clienteTemporal.pedidosVacios())
                                            System.out.println("Aun no ha realizado pedidos");
                                        else {
                                            System.out.println(clienteTemporal.pintaPedidoConCliente());
                                        }
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "4": // Pintamos los datos del cliente
                                        System.out.println(clienteTemporal.pintaCliente());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "5": // Permitimos cambiar los datos de un cliente
                                        System.out.println("Estos son tus datos personales registrados: ");
                                        System.out.println(clienteTemporal.pintaCliente());
                                        System.out.print("Que dato personal quieres cambiar(nombre/dirección/teléfono...): ");
                                        String datoCambiado = s.nextLine();
                                        cambiaDato(clienteTemporal, datoCambiado);

                                        break;
                                    case "6": // Cerramos la sesión del cliente
                                        Utils.cerrarSesion();
                                        break;
                                    default:
                                        System.out.println("Opción introducida no válida");
                                        break;
                                }
                            } while (!opMenusUsuarios.equals("6"));

                            break;
                        case "trabajador": // Menú para trabajadores
                            Trabajador trabajadorTemporal = tienda.loginTrabajador(usuarioTemp, claveTemp);
                            do {
                                Utils.limpiaPantalla();
                                Menus.menuTrabajador(trabajadorTemporal);
                                opMenusUsuarios = s.nextLine();
                                switch (opMenusUsuarios) {
                                    case "1": // Pintamos los pedidos que tiene asignado un trabajador
                                        System.out.println("Bienvenido al apartado de visualización de pedidos");
                                        System.out.println(trabajadorTemporal.pintaPedidosAsignados());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "2": // Permitimos el cambio de estado, fecha estimada y comentario del pedido
                                        System.out.println("Bienvenido al apartado de cambio de estados de los pedidos");
                                        cambioEstadoPedidoTrabajador(trabajadorTemporal,tienda);

                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "3": // Pintamos el catálogo
                                        System.out.println("Bienvenido al catálogo de nuestra tienda");
                                        tienda.pintaCatalogo();
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "4": // Permitimos cambiar los datos de los productos del catálogo
                                        System.out.println("Bienvenido al apartado de cambio de productos");
                                        cambioProducto(tienda);
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "5": // Pintamos los datos del trabajador
                                        System.out.println("Bienvenido al apartado de visualización del perfil");
                                        System.out.println(trabajadorTemporal.pintaTrabajador());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "6": // Permitimos cambiar los datos del trabajador
                                        System.out.println("Bienvenido al apartado de cambio de datos personales");
                                        System.out.println("Estos son tus datos personales");
                                        System.out.println(trabajadorTemporal.pintaTrabajador());
                                        System.out.print("Que dato personal quieres cambiar(nombre/dirección/teléfono...): ");
                                        String datoCambiado = s.nextLine();
                                        cambiaDatoTrabajador(trabajadorTemporal, datoCambiado);
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "7": // Cerramos la sesión del trabajador
                                        Utils.cerrarSesion();
                                        break;
                                    default:
                                        System.out.println("Opción no válida");
                                        break;

                                }
                            } while (!opMenusUsuarios.equals("7"));
                            break;
                        case "admin": // Menú para administrador
                            Admin adminTemporal = tienda.loginAdmin(usuarioTemp, claveTemp);
                            do {
                                Utils.limpiaPantalla();
                                Menus.menuAdministrador(adminTemporal, tienda);
                                opMenusUsuarios = s.nextLine();
                                switch (opMenusUsuarios) {
                                    case "1": // Asignación manual de pedidos a trabajadores
                                        Pedido pedidoSeleccionado = null;
                                        System.out.println("Bienvenido al apartado de asignación de pedidos");
                                        do { // Creamos un bucle que asegure de que no se asigne un pedido que no existe
                                            // Pintamos los pedidos
                                            try {
                                                System.out.println(adminTemporal.pintaSeleccionPedido());
                                                System.out.print("Seleccione el pedido a asignar: ");
                                                int opPedido = Integer.parseInt(s.nextLine());
                                                pedidoSeleccionado = adminTemporal.seleccionaPedido(opPedido);
                                                if (pedidoSeleccionado == null)
                                                    System.out.println("El pedido que ha seleccionado no existe");
                                            } catch (NumberFormatException e) {
                                                System.out.println("ERROR. El formato introducido es incorrecto.");
                                            }
                                        } while (pedidoSeleccionado == null);
                                        // Pintamos los trabajadores

                                        int opTrabajador = -1;
                                        do {
                                            try {
                                                System.out.println(tienda.pintaTrabajadoresParaSeleccion());
                                                System.out.print("Seleccione el trabajador: ");
                                                opTrabajador = Integer.parseInt(s.nextLine());
                                                // Comprobamos si el pedido ha sido asignado correctamente
                                                Trabajador trabajadorTemp = tienda.asignaPedidoTrabajador(opTrabajador, pedidoSeleccionado);
                                                if (trabajadorTemp != null) {
                                                    System.out.println("El pedido ha sido asignado correctamente");
                                                    Comunicaciones.enviaMensajeTelegram("Se le ha asignado un pedido", trabajadorTemp.getIdTelegram());
                                                    Cliente clienteParaCorreo = tienda.encuentraClientePedido(pedidoSeleccionado.getId());
                                                    Comunicaciones.enviaCorreoPedidoAsignado(pedidoSeleccionado,trabajadorTemp,clienteParaCorreo);


                                                } else
                                                    System.out.println("Ha ocurrido un problema al asignar el pedido");
                                            } catch (NumberFormatException e) {
                                                System.out.println("ERROR. El formato introducido es incorrecto.");
                                            }
                                        } while (opTrabajador == -1);
                                        break;
                                    case "2": // Permitimos el cambio de estado, fecha estimada y comentario del pedido
                                        System.out.println("Bienvenido al apartado de cambio de estados de pedidos");
                                        cambioEstadoPedidoAdmin(adminTemporal,tienda);
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "3": // Permitimos dar de alta a un trabajador
                                        System.out.println("Bienvenido al apartado de registro de trabajadores");
                                        Utils.pulsaParaContinuar();
                                        // Comprobamos si se puede dar de alta a más trabajadores
                                        if (tienda.trabajadoresLlenos())
                                            System.out.println("No se puede dar de alta a más trabajadores");
                                        else {
                                            System.out.println(((tienda.registroTrabajador(registroTrabajador())) ?
                                                    "El trabajador ha sido registrado correctamente"
                                                    : "Ha habido un problema en el registro del trabajador"));
                                        }
                                        break;
                                    case "4": // Pintamos todos los pedidos registrados
                                        System.out.println("Bienvenido al apartado de visualización de pedidos");
                                        System.out.println(tienda.pintaPedidosParaAdmin());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "5": // Pintamos todos los clientes registrados
                                        System.out.println("Bienvenido al apartado de visualización de clientes");
                                        System.out.println(tienda.pintaClientes());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "6": // Pintamos todos los trabajadores registrados
                                        System.out.println("Bienvenido al apartado de visualización de trabajadores");
                                        System.out.println(tienda.pintaTrabajadores());
                                        Utils.pulsaParaContinuar();
                                        break;
                                    case "7": // Cerramos la sesión del administrador
                                        Utils.cerrarSesion();
                                        break;
                                    default:
                                        System.out.println("Opción introducida incorrecta");
                                        break;

                                }
                            } while (!opMenusUsuarios.equals("7"));
                            break;
                        case "error":
                            System.out.println("Usuario y/o contraseña introducidos incorrectos");
                            Utils.pulsaParaContinuar();
                            break;
                    }
                    break;
                default:

                    System.out.println("Opción introducida incorrecta.");
                    Utils.pulsaParaContinuar();
                    break;
            }

        } while (true);

    }

    private static String generaToken() {
        String salida = "FS-";
        for (int i = 0; i < 5; i++) {
            salida += (int) (Math.random() * 10);
        }
        return salida;
    }

    private static void cambioEstadoPedidoAdmin(Admin adminTemporal, Tienda tienda) {
        System.out.println(adminTemporal.pintaEstadosPedidos());
        System.out.print("Introduce el id del pedido que quieres modificar: ");
        String idTemp = s.nextLine();
        Pedido pedidoCorreo = tienda.localizaPedido(idTemp);
        if (pedidoCorreo == null) System.out.println("El id introducido es incorrecto");
        {
            Cliente clienteCorreos = tienda.encuentraClientePedido(idTemp);


            int estadoNuevo = seleccionaEstado(idTemp);
            adminTemporal.cambiaEstadoPedido(estadoNuevo, idTemp);
            System.out.println("Estado del pedido cambiado correctamente");
            System.out.print("¿Quiere indicar una nueva fecha de entrega?(S/N): ");
            String opCambiaFecha = s.nextLine();
            if (!opCambiaFecha.equalsIgnoreCase("n")) {
                LocalDate fechaNueva = LocalDate.parse("1666-10-10");
                do {
                    try {
                        System.out.print("Introduzca la nueva fecha de entrega (yyyy-mm-dd): ");
                        fechaNueva = LocalDate.parse(s.nextLine());
                        adminTemporal.cambiaFechaPedido(fechaNueva, idTemp);
                        System.out.println("Fecha guardada correctamente");

                    } catch (DateTimeParseException e) {
                        System.out.println("ERROR. El formato introducido es incorrecto.");
                    }
                } while (fechaNueva.equals(LocalDate.parse("1666-10-10")));

            }
            System.out.println("¿Quiere añadir un comentario al pedido?(S/N)");
            String opInsertaComentario = s.nextLine();
            if (opInsertaComentario.equalsIgnoreCase("s")) {
                System.out.print("Introduzca el nuevo comentario: ");
                String comentarioNuevo = s.nextLine();
                adminTemporal.insertaComentarioPedido(comentarioNuevo, idTemp);
                System.out.println("Comentario guardado correctamente");
            }
            if (clienteCorreos != null) Comunicaciones.enviaCorreoCambioEstadoPedidoCliente(pedidoCorreo,clienteCorreos);
        }


    }

    private static void cambioEstadoPedidoTrabajador(Trabajador trabajadorTemporal,Tienda tienda) {
        System.out.println(trabajadorTemporal.pintaPedidosAsignados());
        if (trabajadorTemporal.numeroPedidos() != 0) { // Comprobamos si el trabajador tiene pedidos asignados
            System.out.print("Introduce el id del pedido que quieres modificar: ");
            String idTemp = s.nextLine();
            Pedido pedidoCorreo = tienda.localizaPedido(idTemp);
            if (pedidoCorreo == null) System.out.println("El id introducido es incorrecto");
            {
                Cliente clienteCorreos = tienda.encuentraClientePedido(idTemp);


                int estadoNuevo = seleccionaEstado(idTemp);
                trabajadorTemporal.cambiaEstadoPedido(estadoNuevo, idTemp);
                System.out.println("Estado del pedido cambiado correctamente");
                System.out.print("¿Quiere indicar una nueva fecha de entrega?(S/N): ");
                String opCambiaFecha = s.nextLine();
                if (!opCambiaFecha.equalsIgnoreCase("n")) {
                    LocalDate fechaNueva = LocalDate.parse("1666-10-10");
                    do {
                        try {
                            System.out.print("Introduzca la nueva fecha de entrega (yyyy-mm-dd): ");
                            fechaNueva = LocalDate.parse(s.nextLine());
                            trabajadorTemporal.cambiaFechaPedido(fechaNueva, idTemp);
                            System.out.println("Fecha guardada correctamente");

                        } catch (DateTimeParseException e) {
                            System.out.println("ERROR. El formato introducido es incorrecto.");
                        }
                    } while (fechaNueva.equals(LocalDate.parse("1666-10-10")));

                }
                System.out.println("¿Quiere añadir un comentario al pedido?(S/N)");
                String opInsertaComentario = s.nextLine();
                if (opInsertaComentario.equalsIgnoreCase("s")) {
                    System.out.print("Introduzca el nuevo comentario: ");
                    String comentarioNuevo = s.nextLine();
                    trabajadorTemporal.insertaComentarioPedido(comentarioNuevo, idTemp);
                    System.out.println("Comentario guardado correctamente");
                }
                if (clienteCorreos != null) Comunicaciones.enviaCorreoCambioEstadoPedidoCliente(pedidoCorreo,clienteCorreos);
            }

        }
    }

    // Función que pinta el inicio de la empresa
    public static void inicio() {

        System.out.println("""
                ██████╗ ██╗███████╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗██╗██████╗  ██████╗      █████╗     ███████╗███████╗██████╗ ███╗   ██╗ █████╗ ███╗   ██╗███████╗██╗  ██╗ ██████╗ ██████╗\s
                ██╔══██╗██║██╔════╝████╗  ██║██║   ██║██╔════╝████╗  ██║██║██╔══██╗██╔═══██╗    ██╔══██╗    ██╔════╝██╔════╝██╔══██╗████╗  ██║██╔══██╗████╗  ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗
                ██████╔╝██║█████╗  ██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║██║██║  ██║██║   ██║    ███████║    █████╗  █████╗  ██████╔╝██╔██╗ ██║███████║██╔██╗ ██║███████╗███████║██║   ██║██████╔╝
                ██╔══██╗██║██╔══╝  ██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║██║██║  ██║██║   ██║    ██╔══██║    ██╔══╝  ██╔══╝  ██╔══██╗██║╚██╗██║██╔══██║██║╚██╗██║╚════██║██╔══██║██║   ██║██╔═══╝\s
                ██████╔╝██║███████╗██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║██║██████╔╝╚██████╔╝    ██║  ██║    ██║     ███████╗██║  ██║██║ ╚████║██║  ██║██║ ╚████║███████║██║  ██║╚██████╔╝██║    \s
                ╚═════╝ ╚═╝╚══════╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝╚═╝╚═════╝  ╚═════╝     ╚═╝  ╚═╝    ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝    \s
                """);

        System.out.print("""
                1.- Registrarse como cliente
                2.- Iniciar Sesión
                Introduzca la opción deseada:\s""");

    }

    // Metodo que devuelve un cliente tras reunir sus datos de registro
    public static Cliente menuRegistro(Tienda tienda) {
        var s = new Scanner(System.in);
        String nombre, apellidos, usuario, correo, clave, direccion, localidad, provincia;
        int telefono = -1;
        boolean correoExistente;
        System.out.print("Introduzca su usuario: ");
        usuario = s.nextLine();
        System.out.print("Introduzca su contraseña: ");
        clave = s.nextLine();
        System.out.println("Usuario y clave registrado correctamente");
        Utils.pulsaParaContinuar();
        System.out.println("A continuación deberá introducir sus datos personales");
        System.out.print("Introduzca su nombre: ");
        nombre = s.nextLine();
        System.out.print("Introduzca su apellido: ");
        apellidos = s.nextLine();
        do { // Bucle que comprueba que el correo contenga un @
            correoExistente = false;
            System.out.print("Introduzca su correo: ");
            correo = s.nextLine();
            if (!correo.contains("@")) System.out.println("El correo introducido no cumple los requisitos");
            if (tienda.correoExistente(correo)) {
                System.out.println("El correo que ha introducido ya existe");
                correoExistente = true;
            }
        } while (!correo.contains("@") || correoExistente);
        System.out.print("Introduzca su dirección: ");
        direccion = s.nextLine();
        System.out.print("Introduzca su provincia: ");
        provincia = s.nextLine();
        System.out.print("Introduzca su localidad: ");
        localidad = s.nextLine();
        do { //Bucle que comprueba que el teléfono tiene 9 dígitos
            System.out.print("Introduzca su número de teléfono: ");
            try {
                telefono = Integer.parseInt(s.nextLine());
                if (telefono < 100000000 || telefono > 999999999)
                    System.out.println("El número introducido no cumple con los requisitos establecidos");
            } catch (NumberFormatException e) {
                System.out.println("ERROR. El formato introducido es incorrecto");
            }
        } while (telefono < 100000000 || telefono > 999999999);
        return new Cliente(nombre, apellidos, usuario, correo, clave, direccion, telefono, localidad, provincia);

    }


    // Metodo que se encarga de cambiar un dato personal del cliente que sea introducido
    public static void cambiaDato(Cliente clienteTemporal, String datoCambiado) {
        var s = new Scanner(System.in);
        switch (datoCambiado.toLowerCase()) {
            case "nombre":
                System.out.print("Introduzca su nuevo nombre: ");
                clienteTemporal.setNombre(s.nextLine());
                break;
            case "apellidos":
                System.out.print("Introduzca sus nuevos apellidos: ");
                clienteTemporal.setApellidos(s.nextLine());
                break;
            case "direccion":
                System.out.print("Introduzca su nueva direccion: ");
                clienteTemporal.setDireccion(s.nextLine());
                break;
            case "localidad":
                System.out.print("Introduzca su nueva localidad: ");
                clienteTemporal.setLocalidad(s.nextLine());
                break;
            case "provincia":
                System.out.print("Introduzca su nueva provincia: ");
                clienteTemporal.setProvincia(s.nextLine());
                break;
            case "telefono":
                int telefonoNuevo = -1;
                do {
                    System.out.print("Introduzca su número de teléfono: ");
                    try {
                        telefonoNuevo = Integer.parseInt(s.nextLine());
                        if (telefonoNuevo < 100000000 || telefonoNuevo > 999999999)
                            System.out.println("El número introducido no cumple con los requisitos establecidos");
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR. El formato introducido es incorrecto");
                    }
                } while (telefonoNuevo < 100000000 || telefonoNuevo > 999999999);
                clienteTemporal.setTelefono(telefonoNuevo);

                break;
            case "correo":
                String correoNuevo;
                do {
                    System.out.print("Introduzca su nuevo correo: ");
                    correoNuevo = s.nextLine();
                    if (!correoNuevo.contains("@")) System.out.println("El correo que introduzca debe contener un @");
                } while (!correoNuevo.contains("@"));
                clienteTemporal.setCorreo(correoNuevo);
                clienteTemporal.setActivado(false);
                break;
            default:
                System.out.print("Dato introducido incorrecto");
                break;


        }
    }

    public static Producto seleccionaProducto(Tienda tienda) {
        var s = new Scanner(System.in);
        int op = -1;
        System.out.println(tienda.pintaCatalogo());
        do { // Bucle que se asegura de que se seleccione un producto que existe
            System.out.print("Introduce el producto que quieres seleccionar: ");
            try {
                op = Integer.parseInt(s.nextLine());
                if (op < 1 || op > 8) System.out.println("El producto seleccionado no existe");
                else {
                    return tienda.seleccionaProducto(op);
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR. El formato introducido no es correcto");
            }
        } while (op < 1 || op > 8);

        return null;
    }

    // Metodo que se utiliza para cambiar los datos de un producto
    public static void cambioProducto(Tienda tienda) {
        var s = new Scanner(System.in);
        Producto productoCambio = seleccionaProducto(tienda);
        System.out.print("Introduce lo que quieres cambiar de este producto (nombre/precio/cantidad): ");
        String opcion = s.nextLine();
        switch (opcion.toLowerCase()) {
            case "nombre":
                System.out.print("Introduce el nuevo nombre para el producto: ");
                productoCambio.setNombre(s.nextLine());
                break;
            case "precio":
                float nuevoPrecio;
                do { // Comprobamos que el nuevo precio introducido no sea menor de 0
                    System.out.print("Introduce el nuevo precio para el producto: ");
                    nuevoPrecio = Float.parseFloat(s.nextLine());
                    if (nuevoPrecio < 0) System.out.println("No se puede cambiar el precio establecido al introducido");
                } while (nuevoPrecio < 0);
                productoCambio.setPrecio((nuevoPrecio));
                break;
            case "cantidad":
                int cantidadNueva;
                do { // Comprobamos que la nueva cantidad introducida no sea menor de 0
                    System.out.print("Introduce la nueva cantidad para el producto: ");
                    cantidadNueva = Integer.parseInt(s.nextLine());
                    if (cantidadNueva < 0) System.out.println("No se puede cambiar la cantidad a la introducida");
                } while (cantidadNueva < 0);
                productoCambio.setCantidad(cantidadNueva);
                break;
            default:
                System.out.println("La opción que ha introducido no existe");
                break;
        }

    }

    // Metodo que muestra los distintos estados que se le pueden dar a un pedido y devuelve una de las opciones
    public static int seleccionaEstado(String id) {
        var s = new Scanner(System.in);
        int opcion = -1;
        do {
            try {
                System.out.printf("""
                        === Actualización del pedido %s ===
                        
                        Nuevo estado:
                        
                        1.- Recibido
                        2.- En preparación
                        3.- Retrasado
                        4.- Cancelado
                        5.- Enviado
                        Selecciona el nuevo estado:\s""", id);
                opcion = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR. El formato introducido es incorrecto");
            }
        } while (opcion == -1);

        return opcion;
    }

    // Metodo que se encarga de cambiar un dato personal del trabajador que sea introducido
    public static void cambiaDatoTrabajador(Trabajador trabajadorTemporal, String datoCambiado) {
        var s = new Scanner(System.in);
        switch (datoCambiado.toLowerCase()) {
            case "nombre":
                System.out.print("Introduzca su nuevo nombre: ");
                trabajadorTemporal.setNombre(s.nextLine());
                break;
            case "apellidos":
                System.out.print("Introduzca sus nuevos apellidos: ");
                trabajadorTemporal.setApellidos(s.nextLine());
                break;
            case "direccion":
                System.out.print("Introduzca su nueva direccion: ");
                trabajadorTemporal.setDireccion(s.nextLine());
                break;
            case "telefono":
                int telefonoNuevo = -1;
                do {
                    try {
                        System.out.print("Introduzca su número de teléfono: ");
                        telefonoNuevo = Integer.parseInt(s.nextLine());
                        if (telefonoNuevo < 100000000 || telefonoNuevo > 999999999) {
                            System.out.println("El número introducido no cumple con los requisitos establecidos");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR. El formato introducido es incorrecto");
                    }
                } while (telefonoNuevo < 100000000 || telefonoNuevo > 999999999);
                trabajadorTemporal.setTelefono(telefonoNuevo);
                break;
            case "correo":
                String correoNuevo;
                do {
                    System.out.print("Introduzca su nuevo correo: ");
                    correoNuevo = s.nextLine();
                    if (!correoNuevo.contains("@")) System.out.println("El correo que introduzca debe contener un @");
                } while (!correoNuevo.contains("@"));
                trabajadorTemporal.setCorreo(correoNuevo);
                break;
            default:
                System.out.print("Dato introducido incorrecto");
                break;


        }
    }


    // Metodo que devuelve un trabajador tras reunir los datos necesarios para darlo de alta
    public static Trabajador registroTrabajador() {
        var s = new Scanner(System.in);
        String usuario, clave, nombre, apellidos, correo, direccion;
        int telefono = -1;
        System.out.print("Introduzca el usuario: ");
        usuario = s.nextLine();
        System.out.print("Introduzca la contraseña: ");
        clave = s.nextLine();
        System.out.print("Introduzca el nombre: ");
        nombre = s.nextLine();
        System.out.print("Introduzca los apellidos: ");
        apellidos = s.nextLine();
        do {
            System.out.print("Introduzca su correo: ");
            correo = s.nextLine();
            if (!correo.contains("@")) System.out.println("El correo introducido no cumple los requisitos");
        } while (!correo.contains("@"));
        System.out.print("Introduzca la dirección: ");
        direccion = s.nextLine();
        do {
            System.out.print("Introduzca el número de teléfono: ");
            try {
                telefono = Integer.parseInt(s.nextLine());
                if (telefono < 100000000 || telefono > 999999999)
                    System.out.println("El número introducido no cumple con los requisitos establecidos");

            } catch (NumberFormatException e) {
                System.out.println("ERROR. El formato introducido es incorrecto");
            }
        } while (telefono < 100000000 || telefono > 999999999);

        return new Trabajador(nombre, apellidos, usuario, clave, correo, direccion, telefono);

    }
}
