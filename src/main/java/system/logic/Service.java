package system.logic;

import system.data.Data;
import system.data.XMLPersister;
import system.logic.entities.Bonos;
import system.logic.entities.Deducciones;
import system.logic.entities.Empleado;

import java.util.List;

public class Service {
    private static Service instance;
    private Data data;

    public static Service instance() {
        if (instance == null) instance = new Service();
        return instance;
    }

    private Service() {
        try {
            data = XMLPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }

        ensureExamRubros();
        store();
    }

    public List<Empleado> getEmpleados() {
        return data.getEmpleados();
    }

    public List<Bonos> getBonos() {
        return data.getBonos();
    }

    public List<Deducciones> getDeducciones() {
        return data.getDeducciones();
    }

    public void agregarEmpleado(Empleado empleado) throws Exception {
        validar(empleado, null);
        data.getEmpleados().add(empleado);
        store();
    }

    public void modificarEmpleado(Empleado original, Empleado editado) throws Exception {
        if (original == null || original.getCedula() == null || original.getCedula().isBlank()) {
            throw new Exception("Seleccione un empleado para modificar");
        }

        validar(editado, original);
        int index = data.getEmpleados().indexOf(original);
        if (index < 0) throw new Exception("Empleado no encontrado");

        data.getEmpleados().set(index, editado);
        store();
    }

    private void validar(Empleado empleado, Empleado ignorar) throws Exception {
        if (empleado.getCedula().isBlank()) throw new Exception("La cédula es requerida");
        if (empleado.getNombre().isBlank()) throw new Exception("El nombre es requerido");
        if (empleado.getTelefono().isBlank()) throw new Exception("El teléfono es requerido");
        if (empleado.getCorreo().isBlank() || !empleado.getCorreo().contains("@")) {
            throw new Exception("El correo no es válido");
        }
        if (empleado.getSalarioBase() < 0) throw new Exception("El salario base no puede ser negativo");

        for (Empleado otro : data.getEmpleados()) {
            if (otro != ignorar && otro.getCedula().equalsIgnoreCase(empleado.getCedula())) {
                throw new Exception("Ya existe un empleado con esa cédula");
            }
        }
    }

    private void ensureExamRubros() {
        addBonusIfMissing(new Bonos("BON-001", "P", "Dedicación exclusiva", 35));
        addBonusIfMissing(new Bonos("BON-002", "F", "Grado de Maestría", 20000));
        addBonusIfMissing(new Bonos("BON-003", "F", "Grado de Doctorado", 30000));

        addDeductionIfMissing(new Deducciones("DED-001", "P", "Seguro de salud", 5.5));
        addDeductionIfMissing(new Deducciones("DED-002", "F", "Seguro de vida del Magisterio", 19970));
        addDeductionIfMissing(new Deducciones("DED-003", "P", "Régimen de pensiones", 4.33));
    }

    private void addBonusIfMissing(Bonos bonus) {
        boolean exists = data.getBonos().stream().anyMatch(b -> b.getId().equalsIgnoreCase(bonus.getId()));
        if (!exists) data.getBonos().add(bonus);
    }

    private void addDeductionIfMissing(Deducciones deduction) {
        boolean exists = data.getDeducciones().stream().anyMatch(d -> d.getId().equalsIgnoreCase(deduction.getId()));
        if (!exists) data.getDeducciones().add(deduction);
    }

    public void store() {
        try {
            XMLPersister.instance().store(data);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo guardar XML: " + e.getMessage(), e);
        }
    }

    public void stop() {
        store();
    }
}
