package wsu.yoursql.rms;

import javax.swing.table.*;

public class ExtendedDefaultTableModel extends DefaultTableModel
{
    public ExtendedDefaultTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

