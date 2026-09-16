package system.presentation.board;

import system.logic.entities.Bonos;
import system.presentation.AbstractTableModel;

import java.util.List;

public class BonoTableModel extends AbstractTableModel<Bonos> {

    public static final int NOMBRE = 0;
    public static final int VALOR = 1;

    public BonoTableModel(int[] cols, List<Bonos> rows) {
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
    protected Object getPropertyAt(Bonos bono, int col) {
        return cols[col] == NOMBRE
                ? bono.getNombre()
                : bono.valorFormateado();
    }
}