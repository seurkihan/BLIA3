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

public class DB {
private Connection conn = null;
	
	DB() throws Exception
	{
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:./DB/"+Main.project,"sa","");
		System.out.println("-------- CONNECT WITH "+Main.project+" DB ----------");;

	}		
	
	public Connection getConn()
	{
		return conn;
	}

	//public void close() throws SQLException{
		//rconn.close();
	//}
	

	public ArrayList<BugReport> getBugReports() {
		ArrayList<BugReport> bugRepository = new ArrayList<BugReport>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("SELECT * FROM BUG_INFO");
			while(rs.next()){
				BugReport bugReport = new BugReport();
				bugReport.setBugID(rs.getInt("BUG_ID"));
				bugReport.setReporter(rs.getString("BUG_AUT"));
				bugReport.setProduct(rs.getString("PRD_NAME"));
				bugReport.setComponent(rs.getString("COMP_NAME"));
				bugReport.setProductVer(rs.getString("PRD_VER"));
				bugReport.setHardware(rs.getString("BUG_HW"));
				bugReport.setAssignee(rs.getString("BUG_ASSIGNEE"));
				bugReport.setOpenDate(String.valueOf(rs.getDate("BUG_OPEN_DATE")));
				bugReport.setModifiedDate(String.valueOf(rs.getDate("BUG_MODIFY_DATE")));
				bugReport.setStatus(rs.getString("BUG_STATUS"));
				bugReport.setPriority(rs.getString("BUG_PRIOR"));
				bugReport.setSever(rs.getString("BUG_SEVER"));
				bugReport.setSummury(rs.getString("BUG_SUM"));
				bugReport.setDescription(rs.getString("BUG_DES"));
				bugRepository.add(bugReport);
			}
			
		}
		catch(Exception e)
		{
			//System.out.println("ERROR: GET DUP BUG ID LIST");
		}
		return (ArrayList<BugReport>) bugRepository.clone();
	}
	public BugReport getBugReport(String bugID) {
		BugReport bugReport = new BugReport();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("SELECT * FROM BUG_INFO WHERE BUG_ID = "+bugID);
			while(rs.next()){
				bugReport.setBugID(rs.getInt("BUG_ID"));
				bugReport.setReporter(rs.getString("BUG_AUT"));
				bugReport.setProduct(rs.getString("PRD_NAME"));
				bugReport.setComponent(rs.getString("COMP_NAME"));
				bugReport.setProductVer(rs.getString("PRD_VER"));
				bugReport.setHardware(rs.getString("BUG_HW"));
				bugReport.setAssignee(rs.getString("BUG_ASSIGNEE"));
				bugReport.setOpenDate(String.valueOf(rs.getDate("BUG_OPEN_DATE")));
				bugReport.setModifiedDate(String.valueOf(rs.getDate("BUG_MODIFY_DATE")));
				bugReport.setStatus(rs.getString("BUG_STATUS"));
				bugReport.setPriority(rs.getString("BUG_PRIOR"));
				bugReport.setSever(rs.getString("BUG_SEVER"));
				bugReport.setSummury(rs.getString("BUG_SUM"));
				bugReport.setDescription(rs.getString("BUG_DES"));
			}
			
		}
		catch(Exception e)
		{
			//System.out.println("ERROR: GET DUP BUG ID LIST");
		}
		return bugReport; 
	}
	
	
	public ArrayList<String> getBugIDs() {
		ArrayList<String> bugIdList = new ArrayList<String>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("SELECT BUG_ID FROM BUG_INFO");
			while(rs.next()){
				bugIdList.add(String.valueOf(rs.getInt("BUG_ID")));
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: GET  BUG ID LIST");
		}
		return (ArrayList<String>) bugIdList.clone();
	}
	
	public HashMap<Integer, String> getSourceFileRank(int bugID, int rankTo) {
		HashMap<Integer, String> sourceFileRankMap = new HashMap<Integer, String>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery(
					"select * from (select rownum as rank, * from "
					+ "(SELECT * FROM INT_ANALYSIS where bug_ID=" +bugID
					+ "order by blia_sf_score desc) where rownum <=" + rankTo 
					+ ") a, sf_info b where a.sf_ver_id =  b.sf_id");
			while(rs.next()){
				sourceFileRankMap.put(rs.getInt("Rank"), rs.getString("SF_NAME"));
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: SOURCE FILE RANK MAP");
		}
		return (HashMap<Integer, String>) sourceFileRankMap.clone();
	}
	
	public HashMap<Integer, String> getMethodRank(int bugID, int rankTo){
		HashMap<Integer, String> methodRankMap = new HashMap<Integer, String>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("select e.*, f.sf_name from (select c.*, d.sf_id from "
						+ "(select a.rank, a.bug_id, a.mth_id, b.mth_name, b.sf_ver_id from "
						+ "(select rownum as rank, * from (SELECT * FROM INT_MTH_ANALYSIS where bug_id="
						+ bugID + "order by blia_mth_score desc) where rownum<="
						+ rankTo + ") a, mth_info b where a.mth_id=b.mth_id) c, sf_ver_info d "
						+ "where c.sf_ver_id=d.sf_ver_id) e, sf_info f where e.sf_id=f.sf_id");
			while(rs.next()){
				methodRankMap.put(rs.getInt("Rank"), rs.getString("mth_name"));
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: GET RANK MAP");
		}
		return (HashMap<Integer, String>) methodRankMap.clone();
	}
	
	public HashMap<Integer, String> getSfToMth(int bugID, String sfName){
		HashMap<Integer, String> sfToMthMap = new HashMap<Integer, String>();
		try
		{
			System.out.print(bugID + " " + sfName);
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("select rank, bug_id, a.mth_id, blia_mth_score, mth_name  from "
					+ "(select rownum as rank, * from (select * from int_mth_analysis where bug_id="
					+ bugID + " order by blia_mth_score desc)) a, "
					+ "(select mth_id, mth_name from mth_info where sf_ver_id = "
					+ "(SELECT sf_id FROM SF_INFO where sf_name = '"
					+ sfName + "')) b where a.mth_id=b.mth_id");
			while(rs.next()){
				sfToMthMap.put(rs.getInt("Rank"), rs.getString("mth_name"));
			}
			System.out.print(sfToMthMap);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: GET SOURCE FILE TO METHOD RANK MAP");
		}
		return (HashMap<Integer, String>) sfToMthMap.clone();
	}
	
}
