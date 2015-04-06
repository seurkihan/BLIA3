// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Property.java

package edu.skku.selab.blp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Property {
	final static public String ASPECTJ = "aspectj";
	final static public String ECLIPSE = "eclipse";
	final static public String SWT = "swt";
	final static public String ZXING = "zxing";
	final static public String DEFAULT = "default";
	
	final static public String ASPECTJ_SOURCE_DIR_NAME = "org.aspectj";
	final static public String ECLIPSE_SOURCE_DIR_NAME = "eclipse-3.1";
	final static public String SWT_SOURCE_DIR_NAME = "swt-3.1";
	final static public String ZXING_SOURCE_DIR_NAME = "ZXing-1.6";
	
	final static public String ASPECTJ_REPO_DIR = Property.readProperty("ASPECTJ_REPO_DIR");
	final static public String ECLIPSE_REPO_DIR = Property.readProperty("ECLIPSE_REPO_DIR");
	final static public String SWT_REPO_DIR = Property.readProperty("SWT_REPO_DIR");
	final static public String ZXING_REPO_DIR = Property.readProperty("ZXING_REPO_DIR");
	
	private String bugFilePath;
	private String[] sourceCodeDirList;
	private String workDir;
	private int fileCount;
	private int wordCount;
	private int bugReportCount;
	private int bugTermCount;
	private double alpha;
	private double beta;
	private String outputFile;
	private String separator = System.getProperty("file.separator");
	private String lineSeparator = System.getProperty("line.separator");
	private static Property p = null;
	private String productName;
	private int pastDays;
	private String repoDir;

	public int getBugTermCount() {
		return bugTermCount;
	}

	public void setBugTermCount(int bugTermCount) {
		this.bugTermCount = bugTermCount;
	}

	public int getBugReportCount() {
		return bugReportCount;
	}

	public void setBugReportCount(int bugReportCount) {
		this.bugReportCount = bugReportCount;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public String getWorkDir() {
		return workDir;
	}
	
	private static String readProperty(String key) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("blp.properties"));
		} catch (IOException e) {
		}

		return properties.getProperty(key);
	}

	public static void createInstance(String productName, String bugFilePath, String sourceCodeDir, String workDir, double alpha, double beta, int pastDays, String repoDir, String outputFile) {
		if (null == p) {
			p = new Property(productName, bugFilePath, sourceCodeDir, workDir, alpha, beta, pastDays, repoDir, outputFile);
		} else {
			p.setValues(productName, bugFilePath, sourceCodeDir, workDir, alpha,
					beta, pastDays, repoDir, outputFile);
		}
		
	}

	public static Property getInstance() {
		return p;
	}
	
	private Property(String productName, String bugFilePath, String sourceCodeDir, String workDir, double alpha, double beta, int pastDays, String repoDir, String outputFile) {
		setValues(productName, bugFilePath, sourceCodeDir, workDir, alpha,
				beta, pastDays, repoDir, outputFile);
	}

	private void setValues(String productName, String bugFilePath,
			String sourceCodeDir, String workDir, double alpha, double beta,
			int pastDays, String repoDir, String outputFile) {
		this.setProductName(productName);
		this.bugFilePath = bugFilePath;
		this.workDir = workDir;
		this.setAlpha(alpha);
		this.setBeta(beta);
		this.setPastDays(pastDays);
		this.setRepoDir(repoDir);
		this.outputFile = outputFile;
		
		String osName = System.getProperty("os.name");
		switch (productName) {
		case Property.ASPECTJ:
//			this.sourceCodeDirList = new String[1];
			this.sourceCodeDirList = new String[22];
			if (osName.equals("Mac OS X")) {
//				this.sourceCodeDirList[0] = sourceCodeDir;
				
				this.sourceCodeDirList[0] = sourceCodeDir + "/ajbrowser/src";
				this.sourceCodeDirList[1] = sourceCodeDir + "/ajde/src";
				this.sourceCodeDirList[2] = sourceCodeDir + "/ajde.core/src";
				this.sourceCodeDirList[3] = sourceCodeDir + "/ajdoc/src";
				this.sourceCodeDirList[4] = sourceCodeDir + "/asm/src";
				this.sourceCodeDirList[5] = sourceCodeDir + "/aspectj5rt/java5-src";
				this.sourceCodeDirList[6] = sourceCodeDir + "/aspectj-attic/ajdoc-src";
				this.sourceCodeDirList[7] = sourceCodeDir + "/bcel-builder/src";
				this.sourceCodeDirList[8] = sourceCodeDir + "/bridge/src";
				this.sourceCodeDirList[9] = sourceCodeDir + "/build/src";
				this.sourceCodeDirList[10] = sourceCodeDir + "/loadtime/src";
				this.sourceCodeDirList[11] = sourceCodeDir + "/loadtime5/java5-src";
				this.sourceCodeDirList[12] = sourceCodeDir + "/org.aspectj.ajdt.core/src";
				this.sourceCodeDirList[13] = sourceCodeDir + "/org.aspectj.matcher/src";
				this.sourceCodeDirList[14] = sourceCodeDir + "/runtime/src";
				this.sourceCodeDirList[15] = sourceCodeDir + "/taskdefs/src";
				this.sourceCodeDirList[16] = sourceCodeDir + "/util/src";
				this.sourceCodeDirList[17] = sourceCodeDir + "/weaver/src";
				this.sourceCodeDirList[18] = sourceCodeDir + "/weaver5/java5-src";
				this.sourceCodeDirList[19] = sourceCodeDir + "/testing/src";
				this.sourceCodeDirList[20] = sourceCodeDir + "/testing/newsrc";
				this.sourceCodeDirList[21] = sourceCodeDir + "/tests/src";
			} else {
//				this.sourceCodeDirList[0] = sourceCodeDir;
				
				this.sourceCodeDirList[0] = sourceCodeDir + "\\ajbrowser\\src";
				this.sourceCodeDirList[1] = sourceCodeDir + "\\ajde\\src";
				this.sourceCodeDirList[2] = sourceCodeDir + "\\ajde.core\\src";
				this.sourceCodeDirList[3] = sourceCodeDir + "\\ajdoc\\src";
				this.sourceCodeDirList[4] = sourceCodeDir + "\\asm\\src";
				this.sourceCodeDirList[5] = sourceCodeDir + "\\aspectj5rt\\java5-src";
				this.sourceCodeDirList[6] = sourceCodeDir + "\\aspectj-attic\\ajdoc-src";
				this.sourceCodeDirList[7] = sourceCodeDir + "\\bcel-builder\\src";
				this.sourceCodeDirList[8] = sourceCodeDir + "\\bridge\\src";
				this.sourceCodeDirList[9] = sourceCodeDir + "\\build\\src";
				this.sourceCodeDirList[10] = sourceCodeDir + "\\loadtime\\src";
				this.sourceCodeDirList[11] = sourceCodeDir + "\\loadtime5\\java5-src";
				this.sourceCodeDirList[12] = sourceCodeDir + "\\org.aspectj.ajdt.core\\src";
				this.sourceCodeDirList[13] = sourceCodeDir + "\\org.aspectj.matcher\\src";
				this.sourceCodeDirList[14] = sourceCodeDir + "\\runtime\\src";
				this.sourceCodeDirList[15] = sourceCodeDir + "\\taskdefs\\src";
				this.sourceCodeDirList[16] = sourceCodeDir + "\\util\\src";
				this.sourceCodeDirList[17] = sourceCodeDir + "\\weaver\\src";
				this.sourceCodeDirList[18] = sourceCodeDir + "\\weaver5\\java5-src";
				this.sourceCodeDirList[19] = sourceCodeDir + "\\testing\\src";
				this.sourceCodeDirList[20] = sourceCodeDir + "\\testing\\newsrc";
				this.sourceCodeDirList[21] = sourceCodeDir + "\\tests\\src";
			}
			break;
		case Property.ECLIPSE:
			this.sourceCodeDirList = new String[1];
			this.sourceCodeDirList[0] = sourceCodeDir;
			break;
		case Property.SWT:
			this.sourceCodeDirList = new String[1];
			if (osName.equals("Mac OS X")) {
				this.sourceCodeDirList[0] = sourceCodeDir + "/src";
			} else {
				this.sourceCodeDirList[0] = sourceCodeDir + "\\src";
			}
			break;
		case Property.ZXING:
			this.sourceCodeDirList = new String[8];
			if (osName.equals("Mac OS X")) {
				this.sourceCodeDirList[0] = sourceCodeDir + "/android/src";
				this.sourceCodeDirList[1] = sourceCodeDir + "/android-integration/src";
				this.sourceCodeDirList[2] = sourceCodeDir + "/core/src";
				this.sourceCodeDirList[3] = sourceCodeDir + "/javame/src";
				this.sourceCodeDirList[4] = sourceCodeDir + "/javase/src";
				this.sourceCodeDirList[5] = sourceCodeDir + "/rim/src";
				this.sourceCodeDirList[6] = sourceCodeDir + "/zxing.appspot.com/generator/src";
				this.sourceCodeDirList[7] = sourceCodeDir + "/zxingorg/src";
			} else {
				this.sourceCodeDirList[0] = sourceCodeDir + "\\android\\src";
				this.sourceCodeDirList[1] = sourceCodeDir + "\\android-integration\\src";
				this.sourceCodeDirList[2] = sourceCodeDir + "\\core\\src";
				this.sourceCodeDirList[3] = sourceCodeDir + "\\javame\\src";
				this.sourceCodeDirList[4] = sourceCodeDir + "\\javase\\src";
				this.sourceCodeDirList[5] = sourceCodeDir + "\\rim\\src";
				this.sourceCodeDirList[6] = sourceCodeDir + "\\zxing.appspot.com\\generator\\src";
				this.sourceCodeDirList[7] = sourceCodeDir + "\\zxingorg\\src";
			}
			break;
		default:
			break;
		}
	}
	
	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getBugFilePath() {
		return bugFilePath;
	}

	public String[] getSourceCodeDirList() {
		return sourceCodeDirList;
	}

	public String getSeparator() {
		return separator;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @param beta the beta to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * @return the pastDays
	 */
	public int getPastDays() {
		return pastDays;
	}

	/**
	 * @param pastDate the pastDays to set
	 */
	public void setPastDays(int pastDays) {
		this.pastDays = pastDays;
	}

	/**
	 * @return the repoDir
	 */
	public String getRepoDir() {
		return repoDir;
	}

	/**
	 * @param repoDir the repoDir to set
	 */
	public void setRepoDir(String repoDir) {
		this.repoDir = repoDir;
	}
}
