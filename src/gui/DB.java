package gui;


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

//	public void close() throws SQLException{
//		conn.close();
//	}
	

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
	
	public HashMap<Integer, String> getBugFileRank(String bugID) {
		HashMap<Integer, String> bugRankMap = new HashMap<Integer, String>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("select * from (select a.bug_id, sf_ver_id, rank, blia_sf_score "
					+ "from (select rownum as rank, bug_id, sf_Ver_id, blia_sf_score "
					+ "from (select * from int_analysis where bug_id = " + bugID +" order by blia_Sf_score desc)) a,"
					+ "bug_fix_sf_info b where a.bug_id = b.bug_id and a.sf_Ver_id = b.fixed_sf_Ver_id) x, "
					+ "sf_info y where x.sf_ver_id = y.sf_id");
			while(rs.next()){
				bugRankMap.put(rs.getInt("Rank"), rs.getString("SF_ID")+" "+rs.getString("SF_NAME"));
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: GET RANK MAP");
		}
		return (HashMap<Integer, String>) bugRankMap.clone();
	}
	
	public HashMap<Integer, String> getMethodRank(int bugID, String sfName){
		HashMap<Integer, String> methodRankMap = new HashMap<Integer, String>();
		try
		{
			Statement q = conn.createStatement();
			ResultSet rs = q.executeQuery("select rank, x.mth_id, x.mth_name from "
					+ "(select mth_id, mth_name from mth_info where sf_ver_id = "
					+ "(SELECT sf_id FROM SF_INFO where sf_name = '" + sfName +"' )) x, "
					+ "(select a.bug_id, rank, a.mth_id from (select rownum as rank, * from "
					+ "(SELECT * FROM INT_MTH_ANALYSIS where bug_id = "+ bugID +")) a, "
					+ "	bug_fix_mth_info b where a.bug_id = b.bug_id and a.mth_id = b.fixed_mth_id) y "
					+ "where x.mth_id = y.mth_id order by rank asc");
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
	
	
}
