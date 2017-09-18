package edu.skku.selab.blp.evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.skku.selab.blp.Property;

public class PropertiesWriter {
	public void writePropertyFile(double eta) throws Exception{
		Property prop = Property.loadInstance();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
		BufferedReader br = new BufferedReader(new FileReader("blp.properties"));
		String str;
		int index=0;
			
		prop.setEta(eta);
			while((str = br.readLine())!=null){
				//System.out.println(index + " " + str);
				if(str.toLowerCase().contains(prop.getProductName().toLowerCase())
						&&str.toLowerCase().contains("eta")){
							bw.write(str.split("=")[0] + "=" + prop.getEta() + "\n");
				}else{
					bw.write(str+"\n");
				}
				index++;
			}
		bw.close();
		
		//파일 지우기
		String filename = "blp.properties";
		File f = new File(filename);
		f.delete();
		
		//파일 이름 바꾸기
		File oldFile = new File("test.txt");
		oldFile.renameTo(new File("blp.properties"));
		
	}
}
