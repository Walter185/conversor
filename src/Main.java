import service.CurrencyConverterServiceImpl;
import service.ICurrencyConverterService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ICurrencyConverterService servicio = new CurrencyConverterServiceImpl();
        int opcion;

        do {
            System.out.println("*******************1" +
                    "********************************");
            System.out.println("Bienvenidos al Conversor de Monedas");
            System.out.println("***************************************************");
            System.out.println("1) Dólar => Peso argentino");
            System.out.println("2) Peso argentino => Dólar");
            System.out.println("3) Dólar => Real brasileño");
            System.out.println("4) Real brasileño => Dólar");
            System.out.println("5) Dólar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.println("***************************************************");
            System.out.print("Elija una opción válida: ");
            opcion = teclado.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.println("***************************************************");
                System.out.print("Ingrese el valor que deseas convertir: ");
                double cantidad = teclado.nextDouble();

                String base = "", destino = "";
                switch (opcion) {
                    case 1 -> { base = "USD"; destino = "ARS"; }
                    case 2 -> { base = "ARS"; destino = "USD"; }
                    case 3 -> { base = "USD"; destino = "BRL"; }
                    case 4 -> { base = "BRL"; destino = "USD"; }
                    case 5 -> { base = "USD"; destino = "COP"; }
                    case 6 -> { base = "COP"; destino = "USD"; }
                }

                try {
                    double resultado = servicio.convertir(base, destino, cantidad);
                    System.out.printf(
                            "%nEl valor %.2f [%s] corresponde al valor final de >>> %.2f [%s]%n%n",
                            cantidad, base, resultado, destino
                    );
                } catch (Exception e) {
                    System.out.println("Error al convertir: " + e.getMessage());
                }
            } else if (opcion != 7) {
                System.out.println("Opción inválida. Intente nuevamente.\n");
            }

        } while (opcion != 7);

        System.out.println("Gracias por usar el conversor.");
        teclado.close();
    }
}