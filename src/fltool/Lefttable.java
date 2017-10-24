package fltool;
import javax.swing.table.AbstractTableModel;

public class Lefttable extends AbstractTableModel{

	String[] leftColNames = {"순위", "SF", "점수"};
	Object[][] leftData = {{"", "", ""}};
	
	public Lefttable(){
		
	}
	
	public Lefttable(Object[][] leftData) {
		this.leftData = leftData;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return leftColNames.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return leftData.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return leftData[arg0][arg1];
	}

	public String getColumnName(int arg0){
		return leftColNames[arg0];
	}
}
