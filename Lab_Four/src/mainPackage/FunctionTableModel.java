package mainPackage;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class FunctionTableModel extends AbstractTableModel {
    private Double from, to, step, p;
    public FunctionTableModel(Double from, Double to, Double step, Double p) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.p = p;
    }

    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public Double getParam(){
        return p;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        double y = (x * x) / p;
        int yInt = (int) y;

        if(col == 0) {
            return x;
        } else if(col == 1) {
            return y;
        } else {
            return Math.abs(y - yInt) <= 0.1 && Math.abs(y - yInt) > 0;
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0: return "Значение х";
            case 1: return "Значение у";
            case 2: return "Y почти целое?";
        }
        return "";
    }

    public Class<?> getColumnClass(int col) {
        if (col != 2)
            return Double.class;
        else {
            return Boolean.class;
        }
    }
}
