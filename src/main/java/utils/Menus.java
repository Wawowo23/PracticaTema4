package utils;

import models.*;

import java.util.Scanner;

public class Menus {




    // Metodo que pinta el menu del cliente
    public static void menuCliente (Cliente cliente) {
        System.out.printf("""
                                  FERNANSHOP
               ==================================================
               = Bienvenido %s                                  =
               ==================================================
                1.- Consultar el catálogo de productos
                2.- Realizar un pedido
                3.- Ver mis pedidos realizados
                4.- Ver mis datos personales
                5.- Modificar mis datos personales
                6.- Cerrar sesión
                Introduzca la opción deseada:\s""",cliente.getUsuario());
    }


    // Metodo que pinta el menú de trabajador
    public static void menuTrabajador(Trabajador trabajador){

        System.out.printf("""
                                   FERNANSHOP
                ==================================================
                = Bienvenido %s. Tienes %s pedidos que gestionar =
                ==================================================
                1. Consultar los pedidos que tengo asignados
                2. Modificar el estado de un pedido
                3. Consultar el catálogo de productos
                4. Modificar un producto del catálogo
                5. Ver mi perfil
                6. Modificar mis datos personales
                7. Cerrar sesión
                Introduzca la opción a realizar:\s""",trabajador.getNombre(),trabajador.numeroPedidos());
    }

    // Metodo que pinta el menú de administrador
    public static void menuAdministrador(Admin admin, Tienda tienda){
        System.out.printf("""
                                   FERNANSHOP
                ==================================================
                = Bienvenido %s. Tienes %d pedido por asignar    =
                ==================================================
                1. Asignar un pedido a un trabajador
                2. Modificar el estado de un pedido
                3. Dar de alta un trabajador
                4. Ver todos los pedidos
                5. Ver todos los clientes
                6. Ver todos los trabajadores
                7. Cerrar sesión
                Introduzca la opción a realizar:\s""",admin.getUsuario(),tienda.pedidosSinAsignar());
    }








}
