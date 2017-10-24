package common;

public class FLsubTable {

	String sfName;
	int rank;
	String methodName;
	double score;
	
	public String getSfName() {
		return sfName;
	}
	public void setSfName(String sfName) {
		this.sfName = sfName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "FLsubTable [sfName=" + sfName + ", rank=" + rank + ", methodName=" + methodName + ", score=" + score
				+ "]";
	}
	
	
}
