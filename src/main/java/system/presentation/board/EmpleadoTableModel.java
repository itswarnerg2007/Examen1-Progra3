package system.presentation.board;

import system.logic.entities.Empleado;
import system.presentation.AbstractTableModel;

import java.util.List;

public class EmpleadoTableModel extends AbstractTableModel<Empleado> {

 public static final int CEDULA = 0;
 public static final int NOMBRE = 1;
 public static final int TELEFONO = 2;
 public static final int CORREO = 3;
 public static final int SALARIO_BASE = 4;
 public static final int SALARIO_BRUTO = 5;
 public static final int SALARIO_NETO = 6;

 public EmpleadoTableModel(int[] cols, List<Empleado> rows) {
  super(cols, rows);
 }

 @Override
 protected void initColNames() {
  colNames = new String[]{
          "Cédula",
          "Nombre",
          "Teléfono",
          "Correo",
          "Sal. Base",
          "Sal. Bruto",
          "Sal. Neto"
  };
 }

 @Override
 protected Object getPropertyAt(Empleado empleado, int col) {
  return switch (cols[col]) {

   case CEDULA ->
           empleado.getCedula();

   case NOMBRE ->
           empleado.getNombre();

   case TELEFONO ->
           empleado.getTelefono();

   case CORREO ->
           empleado.getCorreo();

   case SALARIO_BASE ->
           empleado.getSalarioBase();

   case SALARIO_BRUTO ->
           empleado.getSalarioBruto();

   case SALARIO_NETO ->
           empleado.getSalarioNeto();

   default -> "";
  };
 }
}