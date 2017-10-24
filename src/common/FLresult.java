package common;

public class FLresult {

	int rank;
	String sfName;
	double score;
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
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
		return "FLtable [rank=" + rank + ", sfName=" + sfName + ", score=" + score + "]";
	}
	
	
}
