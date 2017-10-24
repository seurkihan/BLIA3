package common;

import java.util.ArrayList;

public class StructuredBugReport {
	
	int bugID;
	String buildInfo;
	String observedBehavior;
	String expectedBehavior;
	String stepToReproduce;
	String stackTrace;
	String codeExample;
	String errorReport;
	String patch;
	String testCase;
	String otherInformation;
	String otherType;
	
	
	public int getBugID() {
		return bugID;
	}
	

	public String getPatch() {
		return patch;
	}
	public void setPatch(String patch) {
		this.patch = patch;
	}
	
	public void setBugID(int bugID) {
		this.bugID = bugID;
	}
	public String getBuildInfo() {
		return buildInfo;
	}
	public void setBuildInfo(String buildInfo) {
		this.buildInfo = buildInfo;
	}
	public String getObservedBehavior() {
		return observedBehavior;
	}
	public void setObservedBehavior(String observedBehavior) {
		this.observedBehavior = observedBehavior;
	}
	public String getExpectedBehavior() {
		return expectedBehavior;
	}
	public void setExpectedBehavior(String expectedBehavior) {
		this.expectedBehavior = expectedBehavior;
	}
	public String getStepToReproduce() {
		return stepToReproduce;
	}
	public void setStepToReproduce(String stepToReproduce) {
		this.stepToReproduce = stepToReproduce;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getCodeExample() {
		return codeExample;
	}
	public void setCodeExample(String codeExample) {
		this.codeExample = codeExample;
	}
	public String getErrorReport() {
		return errorReport;
	}
	public void setErrorReport(String errorReport) {
		this.errorReport = errorReport;
	}
	public String getTestCase() {
		return testCase;
	}
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}
	public String getOtherInformation() {
		return otherInformation;
	}
	public void setOtherInformation(String otherInformation) {
		this.otherInformation = otherInformation;
	}
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	

}
