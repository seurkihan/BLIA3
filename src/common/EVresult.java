package common;

public class EVresult {

	int bugID;
	String sfName;
	double score;
	
	
	public int getBugID() {
		return bugID;
	}
	public void setBugID(int bugID) {
		this.bugID = bugID;
	}
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "EVresult [bugID=" + bugID + ", sfName=" + sfName + ", score=" + score + "]";
	}

}
