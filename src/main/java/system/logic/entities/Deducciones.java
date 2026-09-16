package system.logic.entities;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Deducciones {

    @XmlID
    private String id;

    private String tipo;
    private String nombre;
    private double valor;

    // =========================
    // CONSTRUCTORES
    // =========================

    public Deducciones() {
    }

    public Deducciones(
            String id,
            String tipo,
            String nombre,
            double valor) {

        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }


    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }


    public double aplicar(double bruto) {

        if ("P".equalsIgnoreCase(tipo)) {
            return bruto * valor / 100.0;
        }

        return valor;
    }

    public String valorFormateado() {

        if ("P".equalsIgnoreCase(tipo)) {
            return String.format(
                    "%.2f %%",
                    valor
            );
        }

        return String.format(
                "₡ %.2f",
                valor
        );
    }

    @Override
    public String toString() {
        return nombre;
    }
}
