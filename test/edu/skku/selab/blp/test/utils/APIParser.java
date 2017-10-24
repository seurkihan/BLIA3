package edu.skku.selab.blp.test.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.jsoup.Jsoup;

public class APIParser {
	static String path = "C:\\Users\\rose\\Desktop\\zxing-api\\";
	
	public static void main(String[] args) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter("./zxing-api.csv"));
		File dir = new File(path);
		File[] fileArray = dir.listFiles();
		int num = 1;
		bw.write("index,fileName,description\n");
		for(int i = 0 ; i<fileArray.length; i++){
			BufferedReader br = new BufferedReader(new FileReader(fileArray[i]));
			String str;
			bw.write(num+","+fileArray[i].getName().toLowerCase().replace(".html", "")+",");
			String data = "";
			while((str=br.readLine())!=null){
				data = data + str.replace(",", "_")+" ";
			}
			bw.write(Jsoup.parse(data).text());
			bw.write("\n");
			num++;
		}
		bw.close();
	}
}