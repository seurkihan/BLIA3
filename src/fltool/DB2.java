package fltool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import common.BugReport;
import common.Property;
import common.StructuredBugReport;

public class DB2 {
private Connection conn = null;
	
	DB2() throws Exception
	{
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:./DB/EVALUATION","sa","");
//		System.out.println("-------- CONNECT WITH "+Property.getInstance().getTargetProduct()+" DB ----------");;

	}		
	
	public Connection getConn()
	{
		return conn;
	}

	public void close() throws SQLException{
		conn.close();
	}
	
	public String getEvalResultFile() {
		String evalResultFile = new String();
	
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("SELECT top1_rate, top5_rate, top10_rate, map, mrr "
					+ "FROM EXP_INFO where upper(prod_name) =upper('" + Main.project + "') and alg_name='BLIA_File' order by exp_date desc");
			if(rs.next()){
				evalResultFile = (rs.getDouble("top1_rate") + " " + rs.getDouble("top5_rate") + " "
						+ rs.getDouble("top10_rate") + " " + rs.getDouble("map") + " " + rs.getDouble("mrr"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("ERROR: GET EVAL RESULT");
		}
		return evalResultFile;
	}
	
	public String getEvalResultMethod() {
		String evalResultMethod = new String();
	
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("SELECT top1_rate, top5_rate, top10_rate, map, mrr "
					+ "FROM EXP_INFO where upper(prod_name) =upper('" + Main.project + "') and alg_name='BLIA_Method' order by exp_date desc");
			if(rs.next()){
				evalResultMethod = (rs.getDouble("top1_rate") + " " + rs.getDouble("top5_rate") + " "
						+ rs.getDouble("top10_rate") + " " + rs.getDouble("map") + " " + rs.getDouble("mrr"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("ERROR: GET EVAL RESULT");
		}
		return evalResultMethod;
	}
	
	
	
}
