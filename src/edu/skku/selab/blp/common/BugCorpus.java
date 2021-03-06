/**
 * Copyright (c) 2014 by Software Engineering Lab. of Sungkyunkwan University. All Rights Reserved.
 * 
 * Permission to use, copy, modify, and distribute this software and its documentation for
 * educational, research, and not-for-profit purposes, without fee and without a signed licensing agreement,
 * is hereby granted, provided that the above copyright notice appears in all copies, modifications, and distributions.
 */
package edu.skku.selab.blp.common;

/**
 * @author Klaus Changsun Youm(klausyoum@skku.edu)
 *
 */
public class BugCorpus {
	private String summaryPart;
	private String descriptionPart;
	private String commentPart;
	private double contentNorm;
	private double summaryCorpusNorm;
	private double decriptionCorpusNorm;
	private String descriptionPartEx; // including comments

	/**
	 * 
	 */
	public BugCorpus() {
		summaryPart = "";
		descriptionPart = "";
		setCommentPart("");
		contentNorm = 0;
		summaryCorpusNorm = 0;
		decriptionCorpusNorm = 0;
		descriptionPartEx = "";
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return summaryPart + " " + descriptionPart;
	}
	
	/**
	 * @return the content extended
	 */
	public String getContentEx() {
		return summaryPart + " " + descriptionPartEx;
	}

	/**
	 * @return the summaryPart
	 */
	public String getSummaryPart() {
		return summaryPart;
	}

	/**
	 * @param summaryPart the summaryPart to set
	 */
	public void setSummaryPart(String summaryPart) {
		this.summaryPart = summaryPart;
	}

	/**
	 * @return the descriptionPart
	 */
	public String getDescriptionPart() {
		return descriptionPart;
	}

	/**
	 * @param descriptionPart the descriptionPart to set
	 */
	public void setDescriptionPart(String descriptionPart) {
		this.descriptionPart = descriptionPart;
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
	 * @return the summaryCorpusNorm
	 */
	public double getSummaryCorpusNorm() {
		return summaryCorpusNorm;
	}

	/**
	 * @param summaryCorpusNorm the summaryCorpusNorm to set
	 */
	public void setSummaryCorpusNorm(double summaryCorpusNorm) {
		this.summaryCorpusNorm = summaryCorpusNorm;
	}

	/**
	 * @return the decriptionCorpusNorm
	 */
	public double getDecriptionCorpusNorm() {
		return decriptionCorpusNorm;
	}

	/**
	 * @param decriptionCorpusNorm the decriptionCorpusNorm to set
	 */
	public void setDecriptionCorpusNorm(double decriptionCorpusNorm) {
		this.decriptionCorpusNorm = decriptionCorpusNorm;
	}

	/**
	 * @return the descriptionPartEx
	 */
	public String getDescriptionPartEx() {
		return descriptionPartEx;
	}

	/**
	 * @param descriptionPartEx the descriptionPartEx to set
	 */
	public void setDescriptionPartEx(String descriptionPartEx) {
		this.descriptionPartEx = descriptionPartEx;
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
}
