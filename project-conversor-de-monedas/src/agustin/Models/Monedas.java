package agustin.Models;

public class Monedas {
    private String monedaBase;
    private String monedaAConvertir;
    private double conversion;
    private double cantidad;
    private String  horaDeConversion;

    public String getHoraDeConversion() {
        return horaDeConversion;
    }

    public Monedas(ConversorAPI conversorAPI, double cantidad, String  horaDeConversion) {
        this.monedaBase = conversorAPI.base_code();
        this.monedaAConvertir = conversorAPI.target_code();
        this.conversion = conversorAPI.conversion_result();
        this.cantidad = cantidad;
        this.horaDeConversion = horaDeConversion;
    }

    @Override
    public String toString() {
        return cantidad +
                " [" + monedaBase + "] " +
                "equivalen a " +
                conversion +
                " [" + monedaAConvertir + "] ";
    }
}
