package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.FLresult;

public class Control {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void connection() {
		try{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:./DB/ASPECTJ","sa","");
		}catch(ClassNotFoundException e){
		}catch(SQLException e){
		}
	}
	
	public void disconnection() {
		try {
	        if(pstmt != null) pstmt.close();
	        if(rs != null) rs.close();
	        if(conn != null) conn.close();
	} catch (SQLException e) {
	}
}

//소스파일 데이터
	public ArrayList<FLresult> searchSf(){
		ArrayList<FLresult> sfList = new ArrayList<>();
		
			String query = "SELECT SF_NAME FROM SF_INFO";
			try {
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				while(rs.next()){
					FLresult flTable = new FLresult();
					flTable.setSfName(rs.getString("SF_NAME"));
					sfList.add(flTable);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	return sfList;		
	}
	
	

}