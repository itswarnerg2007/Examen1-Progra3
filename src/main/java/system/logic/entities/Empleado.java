package system.logic.entities;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Empleado {

    private String cedula;
    private String nombre;
    private String telefono;
    private String correo;
    private double salarioBase;

    @XmlElementWrapper(name = "bonosAplicados")
    @XmlElement(name = "bono")
    @XmlIDREF
    private List<Bonos> bonos = new ArrayList<>();

    @XmlElementWrapper(name = "deduccionesAplicadas")
    @XmlElement(name = "deduccion")
    @XmlIDREF
    private List<Deducciones> deducciones = new ArrayList<>();

    public Empleado() {
        cedula = "";
        nombre = "";
        telefono = "";
        correo = "";

        bonos = new ArrayList<>();
        deducciones = new ArrayList<>();
    }

    public Empleado(
            String cedula,
            String nombre,
            String telefono,
            String correo,
            double salarioBase) {

        this();

        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.salarioBase = salarioBase;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public List<Bonos> getBonos() {
        return bonos;
    }

    public List<Deducciones> getDeducciones() {
        return deducciones;
    }


    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setBonos(List<Bonos> bonos) {
        this.bonos = new ArrayList<>(bonos);
    }

    public void setDeducciones(List<Deducciones> deducciones) {
        this.deducciones = new ArrayList<>(deducciones);
    }

    public double getSalarioBruto() {

        double total = salarioBase;

        for (Bonos bono : bonos) {
            total += bono.aplicar(salarioBase);
        }

        return total;
    }


    public double getSalarioNeto() {

        double bruto = getSalarioBruto();
        double total = bruto;

        for (Deducciones deduccion : deducciones) {
            total -= deduccion.aplicar(bruto);
        }

        return total;
    }
}