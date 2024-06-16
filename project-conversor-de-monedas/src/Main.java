import agustin.Models.ConversorAPI;
import agustin.Models.MiGson;
import agustin.Models.Monedas;
import agustin.Models.PedidoApi;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> listaDeMonedas = Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "MXN", "USD");
    static Scanner entrada =  new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        PedidoApi pedidoApi;
        Monedas monedas;
        MiGson miGson;
        ConversorAPI conversorAPI;
        double cantidad;
        String resultadoConexion;
        String monedaBase = "";
        String modedaAConvertir = "";
        List<Monedas> historialConversiones = new ArrayList<>();

        System.out.println("***** BIENVENIDO AL CONVERSOR DE MONEDAS *****\n");

        while (true){
            monedaBase = Moneda("base");
            if(monedaBase.equals("0")){break;}
            System.out.println("Elige la cantidad a convertir:\n");
            cantidad = Double.parseDouble(entrada.nextLine());
            System.out.println("----- SIGUIENTE: ");

            modedaAConvertir = Moneda("aConverir");
            if(modedaAConvertir.equals("0")){break;}
            System.out.println("----- RESULTADO: ");

            //Conexión a la API
            pedidoApi = new PedidoApi(monedaBase, modedaAConvertir);
            resultadoConexion = pedidoApi.PairConversion(cantidad);
            if(resultadoConexion.contains("Oh no. Ha sucedido un error en la conexión. Inténtelo de nuevo más tarde.")){
                break;
            }

            //Serialización Json a Objetos
            miGson = new MiGson();
            conversorAPI = miGson.ExtraerDatosConversion(resultadoConexion);
            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String horaConMinutos = horaActual.format(formatter);
            monedas = new Monedas(conversorAPI, cantidad, horaConMinutos);

            System.out.println(monedas+"\n");
            historialConversiones.add(monedas);

        }

        System.out.println("Historial de conversiones:\n");
        for(Monedas historial : historialConversiones){
            System.out.println(historial.getHoraDeConversion() + "hrs - " + historial);
        }
    }

    static String Moneda(String tipoMoneda){
        String monedaSeleccionada = "";
        String mensaje = tipoMoneda.equals("base")? "Elige las siglas de la moneda a convertir (PARA SALIR PRESIONE 0)" :
                "Elige las siglas de la moneda a la cual convertir (PARA SALIR PRESIONE 0)";
        while (!listaDeMonedas.contains(monedaSeleccionada) && !monedaSeleccionada.equals("0")){
            System.out.println(mensaje);
            System.out.println("ARS - Peso argentino\n" +
                    "BOB - Boliviano boliviano\n" +
                    "BRL - Real brasileño\n" +
                    "CLP - Peso chileno\n" +
                    "COP - Peso colombiano\n" +
                    "MXN - Peso mexicano\n" +
                    "USD - Dólar estadounidense");
            monedaSeleccionada = entrada.nextLine();
            if(!listaDeMonedas.contains(monedaSeleccionada.toUpperCase().trim()) && !monedaSeleccionada.equals("0") ){
                System.out.println("Esa NO es una opción viable.");
            }
            else if (monedaSeleccionada.equals("0")){
                System.out.println("HAS ELEGIDO SALIR");
            }
            else{
                break;
            }
        }
        return monedaSeleccionada;
    }
}