package models;

public class Producto {
    // Atributos
    private String nombre;
    private float precio;
    private int cantidad;
    private String id;

    // Atributo de la clase
    private static int productosRegistrados;

    // Constructor

    public Producto(String nombre, float precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.id = generaId();
        productosRegistrados++;
    }

    // Constructor copia

    public Producto(Producto producto){
        nombre = producto.nombre;
        precio = producto.precio;
        cantidad = producto.cantidad;
        id = producto.id;
    }



    // Getters Y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductosRegistrados() {
        return productosRegistrados;
    }

    public void setProductosRegistrados(int productosRegistrados) {
        this.productosRegistrados = productosRegistrados;
    }
// Metodos

    // Con este metodo generamos el id de cada producto basándonos en la cantidad de productos registrados que hay
    private String generaId () {
        String codigo = "";
        codigo += String.valueOf(productosRegistrados);
        while (codigo.length() < 8) {
            codigo = "0" + codigo;
        }
        return codigo;
    }

    // Metodo que se utiliza cuando se meten productos en un pedido
    public boolean salidaProducto (int cantidad) {
        // Validamos si la cantidad que nos pasan es mayor que el stock que queda de ese producto o es menor que 1
        if (cantidad > this.cantidad || cantidad < 1) return false;
        this.cantidad -= cantidad;
        return true;
    }

    // Metodo para pintar todos los datos de un producto
    public String pintaProducto () {
        return "Id del producto: " + id + "\n" +
                "Nombre: " + nombre + "\n" +
                "Precio: " + precio + "€" + "\n" +
                "Cantidad restante: " + cantidad + "\n";
    }

    // Metodo para pintar los datos que se deseen saber de un producto al pintar el catálogo
    public String pintaProductoCatalogo () {
        return nombre + ": " + precio + "E. " + ((cantidad < 1) ? "Agotado" : cantidad + " restantes");
    }

    // Metodo para pintar los datos que se deseen saber de un producto al pintar un pedido
    public String pintaProductoPedido() {
        return "\t - " + nombre + "(" + precio + "E)\n";
    }
}
