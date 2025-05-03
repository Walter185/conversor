package service;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import model.ConversionResult;
import util.HttpClientUtil;

public class CurrencyConverterServiceImpl implements ICurrencyConverterService {
    private final Gson gson = new Gson();
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");

    private static final String API_URL = "https://api.currencyapi.com/v3/latest";

    @Override
    public double convertir(String base, String destino, double cantidad) throws Exception {
        String url = API_URL +
                "?apikey=" + API_KEY +
                "&base_currency=" + base +
                "&currencies=" + destino;

        String response = HttpClientUtil.get(url);

        ConversionResult resultado = gson.fromJson(response, ConversionResult.class);

        if (!resultado.success()) {
            throw new Exception("No se pudo obtener la tasa de cambio.");
        }

        Double tasa = resultado.rates().get(destino);
        return cantidad * tasa;
    }
}
