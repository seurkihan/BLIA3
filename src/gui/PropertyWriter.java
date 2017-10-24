package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import common.Blp;

public class PropertyWriter {
	public void writePropertyFile(Blp info) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
		BufferedReader br = new BufferedReader(new FileReader("blp.properties"));
		String str;
		int index=0;
		while((str = br.readLine())!=null){
			//System.out.println(index + " " + str);
			if(str.toLowerCase().contains("target_product")){
				bw.write("TARGET_PRODUCT=" + info.getProject() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("source_dir")){
				bw.write(str.split("=")[0] + "=" + info.getSourceFile() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("repo_dir")){
				bw.write(str.split("=")[0] + "=" + info.getCommitPath() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("bug_repo_file")){
				bw.write(str.split("=")[0] + "=" + info.getBugReportSet() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("alpha")){
				bw.write(str.split("=")[0] + "=" + info.getAlpha() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("beta")){
				bw.write(str.split("=")[0] + "=" + info.getBeta() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("gamma")){
				bw.write(str.split("=")[0] + "=" + info.getGamma() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("delta")){
				bw.write(str.split("=")[0] + "=" + info.getDelta() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("eta")){
				bw.write(str.split("=")[0] + "=" + info.getEta() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("file_rank_limit")){
				bw.write(str.split("=")[0] + "=" + info.getRankNum() + "\n");
			}else if(str.toLowerCase().contains(info.getProject().toLowerCase())
					&&str.toLowerCase().contains("past_days")){
				bw.write(str.split("=")[0] + "=" + info.getPdays() + "\n");
			}else if(str.toLowerCase().contains("structured_info")){
				bw.write(str.split("=")[0] + "=" + info.isStructure() + "\n");
			}else if(str.toLowerCase().contains("strace_score")){
				bw.write(str.split("=")[0] + "=" + info.isStackTrace() + "\n");
			}else if(str.toLowerCase().contains("new_bug_comments")){
				bw.write(str.split("=")[0] + "=" + info.isComments() + "\n");
			}else if(str.toLowerCase().contains("run_level")
					&&info.isMethodLevel()){
				bw.write(str.split("=")[0] + "=" + "METHOD" + "\n");
			}else if(str.toLowerCase().contains("run_level")
					&&!info.isMethodLevel()){
				bw.write(str.split("=")[0] + "=" + "FILE" + "\n");
			}else{
				bw.write(str+"\n");
			}
			index++;
		}
		bw.close();
		
		//파일 지우기
		String filename = "blp.properties";
		File f = new File(filename);
		if(f.delete()){
			System.out.println("ok!");
		}else
			System.out.println("no!");
		
		//파일 이름 바꾸기
		File oldFile = new File("test.txt");
		if(oldFile.renameTo(new File("blp.properties"))){
			System.out.println("ok!");
		}else
			System.out.println("no!");
		
	}
	
	public static void main(String args[]) throws Exception{
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
		
		BufferedReader br = new BufferedReader(new FileReader("blp.properties"));
		String str;
		int index=0;
		while((str = br.readLine())!=null){
			System.out.println(index + " " + str);
			if(str.toLowerCase().contains("target_product")){
				bw.write("target_product=aspectj\n");
			}
			else{
				bw.write(str+"\n");
			}
			index++;
		}
		bw.close();
		
		String filename = "blp.properties";
		File f = new File(filename);
		if(f.delete()){
			System.out.println("ok!");
		}else
			System.out.println("no!");
		
		File oldFile = new File("test.txt");
		if(oldFile.renameTo(new File("blp.properties"))){
			System.out.println("ok!");
		}else
			System.out.println("no!");
		
	}
}
