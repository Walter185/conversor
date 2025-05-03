package service;

public interface ICurrencyConverterService {
    double convertir(String base, String destino, double cantidad) throws Exception;
}