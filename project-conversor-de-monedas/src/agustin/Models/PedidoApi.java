package agustin.Models;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PedidoApi {
    private String direccion;
    private String monedaAConvertir;
    private String monedaDeConversion;


    public PedidoApi(String monedaAConvertir, String monedaDeConversion) {
        this.monedaAConvertir = monedaAConvertir;
        this.monedaDeConversion = monedaDeConversion;
    }

    public String PairConversion(double cantidad) throws IOException, InterruptedException {
        this.direccion = "https://v6.exchangerate-api.com/v6/4d6718a7c5037fffd76dd94f/pair/"+
                monedaAConvertir+ "/" + monedaDeConversion + "/" + cantidad;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().
                    uri(URI.create(direccion)).
                    build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        }catch (Exception e){
            return "Oh no. Ha sucedido un error en la conexión. Inténtelo de nuevo más tarde.\n" +
                    e.getMessage();
        }


    }
}
