package system.presentation.board;

import system.logic.entities.Bonos;
import system.logic.entities.Deducciones;
import system.logic.entities.Empleado;
import system.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {

 public static final String CURRENT_EMPLOYEE = "currentEmployee";
 public static final String EMPLOYEES = "employees";
 public static final String BONUSES = "bonuses";
 public static final String DEDUCTIONS = "deductions";

 private Empleado currentEmployee = new Empleado();

 private List<Empleado> employees = new ArrayList<>();
 private List<Bonos> bonuses = new ArrayList<>();
 private List<Deducciones> deductions = new ArrayList<>();

 @Override
 public void addPropertyChangeListener(PropertyChangeListener listener) {
  super.addPropertyChangeListener(listener);

  firePropertyChange(CURRENT_EMPLOYEE);
  firePropertyChange(EMPLOYEES);
  firePropertyChange(BONUSES);
  firePropertyChange(DEDUCTIONS);
 }

 // =========================
 // GETTERS
 // =========================

 public Empleado getCurrentEmployee() {
  return currentEmployee;
 }

 public List<Empleado> getEmployees() {
  return employees;
 }

 public List<Bonos> getBonuses() {
  return bonuses;
 }

 public List<Deducciones> getDeductions() {
  return deductions;
 }

 // =========================
 // SETTERS
 // =========================

 public void setCurrentEmployee(Empleado empleado) {
  currentEmployee = empleado;

  bonuses = new ArrayList<>(empleado.getBonos());
  deductions = new ArrayList<>(empleado.getDeducciones());

  firePropertyChange(CURRENT_EMPLOYEE);
  firePropertyChange(BONUSES);
  firePropertyChange(DEDUCTIONS);
 }

 public void setEmployees(List<Empleado> employees) {
  this.employees = employees;

  firePropertyChange(EMPLOYEES);
 }

 // =========================
 // BONOS
 // =========================

 public void addBonus(Bonos bono) {
  if (bono != null && !bonuses.contains(bono)) {
   bonuses.add(bono);

   firePropertyChange(BONUSES);
  }
 }

 public void removeBonus(Bonos bono) {
  if (bonuses.remove(bono)) {
   firePropertyChange(BONUSES);
  }
 }

 // =========================
 // DEDUCCIONES
 // =========================

 public void addDeduction(Deducciones deduccion) {
  if (deduccion != null && !deductions.contains(deduccion)) {
   deductions.add(deduccion);

   firePropertyChange(DEDUCTIONS);
  }
 }

 public void removeDeduction(Deducciones deduccion) {
  if (deductions.remove(deduccion)) {
   firePropertyChange(DEDUCTIONS);
  }
 }
}