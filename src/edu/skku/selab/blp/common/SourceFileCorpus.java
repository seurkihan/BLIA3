/**
 * Copyright (c) 2014 by Software Engineering Lab. of Sungkyunkwan University. All Rights Reserved.
 * 
 * Permission to use, copy, modify, and distribute this software and its documentation for
 * educational, research, and not-for-profit purposes, without fee and without a signed licensing agreement,
 * is hereby granted, provided that the above copyright notice appears in all copies, modifications, and distributions.
 */
package edu.skku.selab.blp.common;

import java.util.ArrayList;

/**
 * @author Klaus Changsun Youm(klausyoum@skku.edu)
 *
 */
public class SourceFileCorpus {
	private String javaFileFullClassName;
	private String javaFilePath;
	@SuppressWarnings("unused")
	private String content;
	private String classPart;
	private String methodPart;
	private String variablePart;
	private String commentPart;
	private String apiPart;

	private ArrayList<String> importedClasses;
	private ArrayList<Method> methodList;
	
	private double contentNorm;
	private double classCorpusNorm;
	private double methodCorpusNorm;
	private double variableCorpusNorm;
	private double commentCorpusNorm;
	private double apiCorpusNorm;

	public SourceFileCorpus() {
		javaFileFullClassName = "";
		javaFilePath = "";
		content = "";
		classPart = "";
		methodPart = "";
		variablePart = "";
		commentPart = "";
		apiPart = "";
		importedClasses = null;
		setMethodList(null);
		
		classCorpusNorm = 0;
		methodCorpusNorm = 0;
		variableCorpusNorm = 0;
		commentCorpusNorm = 0;
		apiCorpusNorm = 0;
	}


	public double getApiCorpusNorm() {
		return apiCorpusNorm;
	}

	public void setApiCorpusNorm(double apiCorpusNorm) {
		this.apiCorpusNorm = apiCorpusNorm;
	}

	public String getApiPart() {
		return apiPart;
	}

	public void setApiPart(String apiPart) {
		this.apiPart = apiPart;
	}
	
	public String getJavaFileFullClassName() {
		return javaFileFullClassName;
	}

	public void setJavaFileFullClassName(String javaFileFullClassName) {
		this.javaFileFullClassName = javaFileFullClassName;
	}

	public String getJavaFilePath() {
		return javaFilePath;
	}

	public void setJavaFilePath(String javaFilePath) {
		this.javaFilePath = javaFilePath;
	}

	public String getContent() {
//		return content;
		String content = getApiPart() + " " + getClassPart() + " " + getMethodPart() + " " + getVariablePart() + " " + getCommentPart();
		return content.trim();
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the importedClasses
	 */
	public ArrayList<String> getImportedClasses() {
		return importedClasses;
	}

	/**
	 * @param importedClasses the importedClasses to set
	 */
	public void setImportedClasses(ArrayList<String> importedClasses) {
		this.importedClasses = importedClasses;
	}

	/**
	 * @return the classPart
	 */
	public String getClassPart() {
		return classPart;
	}

	/**
	 * @param classPart the classPart to set
	 */
	public void setClassPart(String classPart) {
		this.classPart = classPart;
	}

	/**
	 * @return the methodPart
	 */
	public String getMethodPart() {
		return methodPart;
	}

	/**
	 * @param methodPart the methodPart to set
	 */
	public void setMethodPart(String methodPart) {
		this.methodPart = methodPart;
	}

	/**
	 * @return the variablePart
	 */
	public String getVariablePart() {
		return variablePart;
	}

	/**
	 * @param variablePart the variablePart to set
	 */
	public void setVariablePart(String variablePart) {
		this.variablePart = variablePart;
	}

	/**
	 * @return the commentPart
	 */
	public String getCommentPart() {
		return commentPart;
	}

	/**
	 * @param commentPart the commentPart to set
	 */
	public void setCommentPart(String commentPart) {
		this.commentPart = commentPart;
	}

	/**
	 * @return the classCorpusNorm
	 */
	public double getClassCorpusNorm() {
		return classCorpusNorm;
	}

	/**
	 * @param classCorpusNorm the classCorpusNorm to set
	 */
	public void setClassCorpusNorm(double classCorpusNorm) {
		this.classCorpusNorm = classCorpusNorm;
	}

	/**
	 * @return the methodCorpusNorm
	 */
	public double getMethodCorpusNorm() {
		return methodCorpusNorm;
	}

	/**
	 * @param methodCorpusNorm the methodCorpusNorm to set
	 */
	public void setMethodCorpusNorm(double methodCorpusNorm) {
		this.methodCorpusNorm = methodCorpusNorm;
	}

	/**
	 * @return the variableCorpusNorm
	 */
	public double getVariableCorpusNorm() {
		return variableCorpusNorm;
	}

	/**
	 * @param variableCorpusNorm the variableCorpusNorm to set
	 */
	public void setVariableCorpusNorm(double variableCorpusNorm) {
		this.variableCorpusNorm = variableCorpusNorm;
	}

	/**
	 * @return the commentCorpusNorm
	 */
	public double getCommentCorpusNorm() {
		return commentCorpusNorm;
	}

	/**
	 * @param commentCorpusNorm the commentCorpusNorm to set
	 */
	public void setCommentCorpusNorm(double commentCorpusNorm) {
		this.commentCorpusNorm = commentCorpusNorm;
	}

	/**
	 * @return the contentNorm
	 */
	public double getContentNorm() {
		return contentNorm;
	}

	/**
	 * @param contentNorm the contentNorm to set
	 */
	public void setContentNorm(double contentNorm) {
		this.contentNorm = contentNorm;
	}

	/**
	 * @return the methodList
	 */
	public ArrayList<Method> getMethodList() {
		return methodList;
	}

	/**
	 * @param methodList the methodList to set
	 */
	public void setMethodList(ArrayList<Method> methodList) {
		this.methodList = methodList;
	}
}
