package system.presentation.board;

import system.logic.Service;
import system.logic.entities.*;

public class Controller {

 private final View view;
 private final Model model;

 public Controller(View view, Model model) {
  this.view = view;
  this.model = model;

  view.setController(this);
  view.setModel(model);

  view.loadRubros(
          Service.instance().getBonos(),
          Service.instance().getDeducciones()
  );

  model.setEmployees(
          Service.instance().getEmpleados()
  );
 }

 // =========================
 // EMPLEADOS
 // =========================

 public void addEmployee(Empleado empleado) throws Exception {
  Service.instance().agregarEmpleado(empleado);

  model.setEmployees(
          Service.instance().getEmpleados()
  );

  model.setCurrentEmployee(new Empleado());
 }

 public void modifyEmployee(Empleado empleado) throws Exception {
  Empleado old = model.getCurrentEmployee();

  Service.instance().modificarEmpleado(old, empleado);

  model.setEmployees(
          Service.instance().getEmpleados()
  );

  model.setCurrentEmployee(new Empleado());
 }

 public void selectEmployee(Empleado empleado) {
  model.setCurrentEmployee(empleado);
 }

 public void clear() {
  model.setCurrentEmployee(new Empleado());
 }

 // =========================
 // BONOS
 // =========================

 public void addBonus(Bonos bono) {
  model.addBonus(bono);
 }

 public void removeBonus(Bonos bono) {
  model.removeBonus(bono);
 }

 // =========================
 // DEDUCCIONES
 // =========================

 public void addDeduction(Deducciones deduccion) {
  model.addDeduction(deduccion);
 }

 public void removeDeduction(Deducciones deduccion) {
  model.removeDeduction(deduccion);
 }
}