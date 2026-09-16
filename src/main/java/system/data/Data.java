package system.data;

import jakarta.xml.bind.annotation.*;

import system.logic.entities.Bonos;
import system.logic.entities.Deducciones;
import system.logic.entities.Empleado;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    private int empleadoBonoConsecutivo = 1;
    private int empleadoDeduccionConsecutivo = 1;

    @XmlElementWrapper(name = "bonos")
    @XmlElement(name = "rubro")
    private List<Bonos> bonos = new ArrayList<>();

    @XmlElementWrapper(name = "deducciones")
    @XmlElement(name = "rubro")
    private List<Deducciones> deducciones = new ArrayList<>();

    @XmlElementWrapper(name = "empleados")
    @XmlElement(name = "empleado")
    private List<Empleado> empleados = new ArrayList<>();

    public Data() {
    }

    public List<Bonos> getBonos() {
        return bonos;
    }

    public List<Deducciones> getDeducciones() {
        return deducciones;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public int getEmpleadoBonoConsecutivo() {
        return empleadoBonoConsecutivo;
    }

    public void setEmpleadoBonoConsecutivo(int empleadoBonoConsecutivo) {
        this.empleadoBonoConsecutivo = empleadoBonoConsecutivo;
    }


    public int getEmpleadoDeduccionConsecutivo() {
        return empleadoDeduccionConsecutivo;
    }

    public void setEmpleadoDeduccionConsecutivo(int empleadoDeduccionConsecutivo) {
        this.empleadoDeduccionConsecutivo = empleadoDeduccionConsecutivo;
    }
}