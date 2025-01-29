package views;

public class mainPrueba {
    public static void main(String[] args) {

        String token = generaToken();
        System.out.println(token);
    }
    private static String generaToken() {
        String salida = "FS-";
        for (int i = 0; i < 5; i++) {
            salida += (int) (Math.random() * 10);
        }
        return salida;
    }
}
