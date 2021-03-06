/**
 * Copyright (c) 2014 by Software Engineering Lab. of Sungkyunkwan University. All Rights Reserved.
 * 
 * Permission to use, copy, modify, and distribute this software and its documentation for
 * educational, research, and not-for-profit purposes, without fee and without a signed licensing agreement,
 * is hereby granted, provided that the above copyright notice appears in all copies, modifications, and distributions.
 */
package edu.skku.selab.blp.blia.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import edu.skku.selab.blp.Property;
import edu.skku.selab.blp.common.SourceFileCorpus;
import edu.skku.selab.blp.common.FileDetector;
import edu.skku.selab.blp.common.FileParser;
import edu.skku.selab.blp.common.Method;
import edu.skku.selab.blp.db.dao.BaseDAO;
import edu.skku.selab.blp.db.dao.MethodDAO;
import edu.skku.selab.blp.db.dao.SourceFileDAO;
import edu.skku.selab.blp.utils.Splitter;

/**
 * @author Klaus Changsun Youm(klausyoum@skku.edu)
 *
 */
public class StructuredSourceFileCorpusCreator extends SourceFileCorpusCreator {
	public SourceFileCorpus create(File file) {		
		FileParser parser = new FileParser(file);
		String fileName = parser.getPackageName();
		if (fileName.trim().equals("")) {
			fileName = file.getName();
		} else {
			fileName = (new StringBuilder(String.valueOf(fileName)))
					.append(".").append(file.getName()).toString();
		}
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		
		// parser.getImportedClassed() function should be called before calling parser.getContents()
		ArrayList<String> importedClasses = parser.getImportedClasses();
		
		String classIdentifiers[] = parser.getStructuredContentWithFullyIdentifier(FileParser.CLASS_PART);		
		String classPart = stemContent(classIdentifiers);
		String classContents[] = parser.getStructuredContent(FileParser.CLASS_PART);
		classPart += " " + stemContent(classContents);

		String methodIdentifiers[] = parser.getStructuredContentWithFullyIdentifier(FileParser.METHOD_PART);
		String methodPart = stemContent(methodIdentifiers);
		String methodContents[] = parser.getStructuredContent(FileParser.METHOD_PART);
		methodPart += " " + stemContent(methodContents);

//		String variablePart = parser.getStructuredContentWithFullyIdentifier(FileParser.VARIABLE_PART);	
//		String variableContents[] = parser.getStructuredContent(FileParser.VARIABLE_PART);
		
		String variableContents[] = parser.getStructuredContent(FileParser.VARIABLE_PART);
		String variablePart = stemContent(variableContents);

		String commentContents[] = parser.getStructuredContent(FileParser.COMMENT_PART);
		String commentPart = stemContent(commentContents);
		ArrayList<Method> methodList =  parser.getAllMethodList();
		
		
		Property property = Property.getInstance();
		String product = property.getProductName();
		File f = new File("./data/api/" +product+ "_api.csv");
		FileInputStream fis;
		String apiCorpus="";
		try {
			fis = new FileInputStream(f);
			@SuppressWarnings("resource")
			BufferedReader bur = new BufferedReader(new InputStreamReader(fis));
			
			String str;
			while((str = bur.readLine()) != null){				
				if(!str.split(",")[1].toLowerCase().equals("class_url")){					

					String apiFile = str.split(",")[1].toLowerCase();
					apiFile = apiFile.substring((apiFile.lastIndexOf("/")+1),apiFile.length()).replace(".html", ".java");
//					System.out.println(apiFile.toLowerCase()+" "+file.getName());
					if(apiFile.toLowerCase().contains(file.getName().toLowerCase()) || file.getName().toLowerCase().contains(apiFile.toLowerCase()))
							apiCorpus = str.split(",")[2];
					}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String apiContents[] = Splitter.splitSourceCode(apiCorpus);
		String apiPart = stemContent(apiContents);
		
		String sourceCodeContent = classPart + " " + methodPart + " " + variablePart + " " + commentPart + " " + apiPart;
		
		
		SourceFileCorpus corpus = new SourceFileCorpus();
		corpus.setJavaFilePath(file.getAbsolutePath());
		corpus.setJavaFileFullClassName(fileName);
		corpus.setImportedClasses(importedClasses);
		corpus.setContent(sourceCodeContent);
		corpus.setClassPart(classPart);
		corpus.setMethodPart(methodPart);
		corpus.setVariablePart(variablePart);
		corpus.setCommentPart(commentPart);
		corpus.setApiPart(apiPart);
		corpus.setMethodList(methodList);		

		return corpus;
	}
	
	
	////////////////////////////////////////////////////////////////////	
	/* (non-Javadoc)
	 * @see edu.skku.selab.blia.indexer.ICorpus#create()
	 */
	public void create(String version) throws Exception {
		Property property = Property.getInstance();
		FileDetector detector = new FileDetector("java");
		File files[] = detector.detect(property.getSourceCodeDirList());
		
		SourceFileDAO sourceFileDAO = new SourceFileDAO();
		MethodDAO methodDAO = new MethodDAO();
		
		String productName = property.getProductName();
		int totalCoupusCount = SourceFileDAO.INIT_TOTAL_COUPUS_COUNT;
		double lengthScore = SourceFileDAO.INIT_LENGTH_SCORE;

		// debug code
//		System.out.printf("Source code dir: %s\n", property.getSourceCodeDir());
//		
//		FileWriter tempWriter = new FileWriter(".\\temp.txt");
//		for (int i = 0; i < files.length; i++) {
//			tempWriter.write("[" + (i+1) + "] " + files[i].getAbsolutePath() + "\n"); 
//			System.out.printf("[%d] %s\n", i + 1, files[i].getAbsolutePath());
//		}
//		tempWriter.close();

		int count = 0;
		TreeSet<String> nameSet = new TreeSet<String>();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			SourceFileCorpus corpus = create(file);

			if (corpus != null && !nameSet.contains(corpus.getJavaFileFullClassName())) {
				String className = corpus.getJavaFileFullClassName();
				if (!corpus.getJavaFileFullClassName().endsWith(".java")) {
					className += ".java";
				}
				
				String fileName = "";
				if (productName.contains(Property.ASPECTJ)) {
					String absolutePath = file.getAbsolutePath();
					String sourceCodeDirName = property.getSourceCodeDir();
					int index = absolutePath.indexOf(sourceCodeDirName);
					fileName = absolutePath.substring(index + sourceCodeDirName.length() + 1, absolutePath.length());
					fileName = fileName.replace("\\", "/");
				
//					System.out.printf("[StructuredSourceFileCorpusCreator.create()] %s, %s\n", filePath, fileName);
				} else {
					fileName = file.getAbsolutePath().replace("\\", ".");
					fileName = fileName.replace("/", ".");
					
					// Wrong file that has invalid package or path
					if (!fileName.endsWith(className)) {
						System.err.printf("[StructuredSourceFileCorpusCreator.create()] %s, %s\n", fileName, className);
						continue;
					}
					
					fileName = className;
				}
				
				int sourceFileID = sourceFileDAO.insertSourceFile(fileName, className);
				if (BaseDAO.INVALID == sourceFileID) {
					System.err.printf("[StructuredSourceFileCorpusCreator.create()] %s insertSourceFile() failed.\n", className);
					throw new Exception(); 
				}
				
				int sourceFileVersionID = sourceFileDAO.insertCorpusSet(sourceFileID, version, corpus, totalCoupusCount, lengthScore);
				if (BaseDAO.INVALID == sourceFileVersionID) {
					System.err.printf("[StructuredSourceFileCorpusCreator.create()] %s insertCorpusSet() failed.\n", className);
					throw new Exception(); 
				}
				
				ArrayList<Method> methodList = corpus.getMethodList();
				for (int j = 0; j < methodList.size(); ++j) {
					Method method = methodList.get(j);
					method.setSourceFileVersionID(sourceFileVersionID);
					methodDAO.insertMethod(method);
				}

				sourceFileDAO.insertImportedClasses(sourceFileVersionID, corpus.getImportedClasses());
				nameSet.add(corpus.getJavaFileFullClassName());
				count++;
			}
		}

		property.setFileCount(count);
	}
}
