package model;

import java.util.Map;

public record ConversionResult(
    boolean success,
    String base,
    String date,
    Map<String, Double> rates
) {}