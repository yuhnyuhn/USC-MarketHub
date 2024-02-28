package marketplaceusc;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author 1bestcsharp.blogspot.com
 */

public class TheModel extends AbstractTableModel {

    private String[] columns;
    private Object[][] rows;
    
    public TheModel(){}
    
    public TheModel(Object[][] data, String[] columnName){
    
        this.rows = data;
        this.columns = columnName;
    }

    
public Class getColumnClass(int column) {
    // 4 is the index of the column image
    if (column == 5) {
        return Icon.class;
    } else {
        // Add a null check before accessing the class
        Object value = getValueAt(0, column);
        return (value != null) ? value.getClass() : Object.class;
    }
}
    
    
    public int getRowCount() {
     return this.rows.length;
    }

    public int getColumnCount() {
     return this.columns.length;
    }

    
    public Object getValueAt(int rowIndex, int columnIndex) {
    
    return this.rows[rowIndex][columnIndex];
    }
    public String getColumnName(int col){
        return this.columns[col];
    }


}