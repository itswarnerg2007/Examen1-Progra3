package system.presentation.board;

import system.logic.entities.Deducciones;
import system.presentation.AbstractTableModel;

import java.util.List;

public class DeduccionTableModel extends AbstractTableModel<Deducciones> {

    public static final int NOMBRE = 0;
    public static final int VALOR = 1;

    public DeduccionTableModel(int[] cols, List<Deducciones> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[]{
                "Nombre",
                "Valor"
        };
    }

    @Override
    protected Object getPropertyAt(Deducciones deduccion, int col) {
        return cols[col] == NOMBRE
                ? deduccion.getNombre()
                : deduccion.valorFormateado();
    }
}