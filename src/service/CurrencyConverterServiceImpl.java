package service;

import com.google.gson.Gson;
import model.ConversionResult;
import util.HttpClientUtil;

public class CurrencyConverterServiceImpl implements ICurrencyConverterService {
    private static final String API_URL = "https://api.exchangerate.host/latest";
    private final Gson gson = new Gson();

    @Override
    public double convertir(String base, String destino, double cantidad) throws Exception {
        String url = API_URL + "?base=" + base + "&symbols=" + destino;
        String response = HttpClientUtil.get(url);

        ConversionResult resultado = gson.fromJson(response, ConversionResult.class);

        if (!resultado.success()) {
            throw new Exception("No se pudo obtener la tasa de cambio.");
        }

        Double tasa = resultado.rates().get(destino);
        return cantidad * tasa;
    }
}
