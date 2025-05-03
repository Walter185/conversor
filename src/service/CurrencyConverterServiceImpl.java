package service;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import model.ConversionResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverterServiceImpl implements ICurrencyConverterService {
    private final Gson gson = new Gson();
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    @Override
    public double convertir(String base, String destino, double cantidad) throws Exception {
        URI direccion = URI.create(API_URL + API_KEY + "/latest/" + base);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ConversionResult resultado = gson.fromJson(response.body(), ConversionResult.class);

            if (!"success".equals(resultado.getResult())) {
                throw new Exception("No se pudo obtener la tasa de cambio.");
            }

            Double tasa = resultado.getConversion_rates().get(destino);
            if (tasa == null) {
                throw new Exception("La moneda de destino '" + destino + "' no está disponible.");
            }

            return cantidad * tasa;
        } catch (Exception e) {
            throw new Exception("Error al convertir: " + e.getMessage(), e);
        }
    }
}