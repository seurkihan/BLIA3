package gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import common.Blp;
import common.BugReport;
import edu.skku.selab.blp.evaluation.EvaluatorTest;



public class SWING extends JFrame implements ActionListener{
	JPanel jp1, jp2, jpPre, jpIR, jpWeight, jpPdays, jpBottom, jpBottom2, jpLevel, jpRankto, jpRankNum, jpProject;
	JTable table1, table2;
	JScrollPane sp1, sp2;
	JButton[] jbSearch = new JButton[3];
	
	private double[][] test;
	private JTextField[] jtf;

	String stem[] = {"stemming", "none"};
	String token[] = {"tokenization", "none"};
	String sword[] = {"stop word", "none"};
	String IR[] = {"IR", "none"};
	String level[] = {"File Level", "Method Level"};
	String project[] = {"ASPECTJ", "SWT", "ZXING"};
	
	
	JLabel jl[] = new JLabel[9];
	JTextArea jt[] = new JTextArea[9];
	JScrollPane jsp[] = new JScrollPane[9];
	
	String[] weightTitle = {"alpha", "beta", "gamma", "delta", "eta", "rankNum"};	
	JLabel[] weightLabel = new JLabel[weightTitle.length];
	JTextField alpha = new JTextField(10);
	JTextField beta = new JTextField(10);
	JTextField gamma = new JTextField(10);
	JTextField delta = new JTextField(10);
	JTextField eta = new JTextField(10);
	JTextField rankNum = new JTextField(10);

	JLabel pdaysLabel = new JLabel("past days");
	JTextField pdays = new JTextField(10);
	
	JLabel ranktoLabel = new JLabel("* show rank to");
	JTextField rankto = new JTextField(10);
	
	String[] etc = {"구조적 정보", "Stack trace", "comments"};
	JCheckBox[] checkbox = new JCheckBox[3];
	
	JComboBox projectCombo = new JComboBox(project);
	JComboBox stemCombo = new JComboBox(stem);
	JComboBox tokenCombo = new JComboBox(token);
	JComboBox swordCombo = new JComboBox(sword);
	JComboBox levelCombo = new JComboBox(level);
	
	String[] bottomTitle = {"Top1", "Top5", "Top10", "MAP", "MRR"};
	JTextField jtfTop1 = new JTextField(10);
	JTextField jtfTop5 = new JTextField(10);
	JTextField jtfTop10 = new JTextField(10);
	JTextField jtfMap = new JTextField(10);
	JTextField jtfMrr = new JTextField(10);
	
	String[] bottom2Title = {"Top1", "Top5", "Top10", "MAP", "MRR"};
	JTextField jtfTop1_r = new JTextField(10);
	JTextField jtfTop5_r = new JTextField(10);
	JTextField jtfTop10_r = new JTextField(10);
	JTextField jtfMap_r = new JTextField(10);
	JTextField jtfMrr_r = new JTextField(10);
	
	JLabel clickedSC;
	JTextField jtClicked = new JTextField(10);


	static ArrayList<String> bugIDList = new ArrayList<String>();
	static BugReport bugReport;

	
	public SWING() throws Exception{		
		
		
	test = new double[1][9];
	setLayout(null);
	setTitle("Eval TOOL");
	setSize(800,750);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	String[] labelTitle = {"Commit",
			"BugReport Set",
			"SourceFile",
			"Options"};	

	JLabel[] label = new JLabel[labelTitle.length];
	
	for(int i=0; i<labelTitle.length;i++){
		label[i] = new JLabel(labelTitle[i]);
	}
		
	jtf = new JTextField[3];
	
	for(int i=0;i<jtf.length;i++){
		jtf[i] = new JTextField();
		jtf[i].setHorizontalAlignment(JTextField.RIGHT);
		jtf[i].setText("");
	}
	
	jp1 = new JPanel();
	jp1.setLayout(null);
	jp1.setBounds(3,3,785,250);
	jp1.setLocation(3, 15);
	
	for(int i=0;i<4;i++){
		label[i].setBounds(10, 30+i*25, 120, 20);
		label[i].setOpaque(true);
		jp1.add(label[i]);
	}
	
	jtf[0].setBounds(130, 30, 570, 20);
	jtf[0].setOpaque(true);
	jtf[0].setHorizontalAlignment(JTextField.LEFT);
	jp1.add(jtf[0]);
	
	for(int i=1;i<3;i++){
		jtf[i].setBounds(130, 30+i*25, 570, 20);
		jtf[i].setOpaque(true);
		jtf[i].setHorizontalAlignment(JTextField.LEFT);
		jp1.add(jtf[i]);
	}
	

	for(int i=0; i<jbSearch.length; i++){
		jbSearch[i] = new JButton();
		jbSearch[i].setText("Search");
		jbSearch[i].setBounds(700, 40+i*25, 80, 30);
		jbSearch[i].addActionListener(this);
		add(jbSearch[i]);
	}

	JButton jbRun = new JButton("run");
	jbRun.setBounds(700,230,80,30);
	jbRun.addActionListener(this);
	add(jbRun);
	//textfield입력값 출력
	jbRun.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("alpha: " +alpha.getText());
			System.out.println("beta: " +beta.getText());
			System.out.println("gamma: " +gamma.getText());
			System.out.println("days: " +pdays.getText());
		}
		
	});
	//끝
	

	TitledBorder tb1 = new TitledBorder(new LineBorder(Color.black),"Information");
	jp1.setBorder(tb1);
	add(jp1);

	
	//project 선택 시작
	jpProject = new JPanel();
	jpProject.setLayout(null);
	jpProject.setBounds(130, 105, 120, 50);
	jpProject.setLocation(130, 105);
	
	TitledBorder tbProject = new TitledBorder(new LineBorder(Color.black),"Project");
	jpProject.setBorder(tbProject);
	jp1.add(jpProject);
	
	
	add(projectCombo);
	setLocationRelativeTo(null);
	projectCombo.setBounds(3, 8, 110, 50);
	projectCombo.setLocation(3, 8);
	jpProject.add(projectCombo);
	projectCombo.addItemListener(new ItemHandler());
	//project 선택 끝
	
	
	//전처리 시작
	jpPre = new JPanel();
	jpPre.setLayout(null);
	jpPre.setBounds(130, 160, 400, 50);
	jpPre.setLocation(130, 160);
	
	TitledBorder tbPre = new TitledBorder(new LineBorder(Color.black),"전처리");
	jpPre.setBorder(tbPre);
	jp1.add(jpPre);
	
	
	add(stemCombo);
	setLocationRelativeTo(null);
	stemCombo.setBounds(3, 8, 130, 50);
	stemCombo.setLocation(3, 8);
	jpPre.add(stemCombo);
	stemCombo.addItemListener(new ItemHandler());
	
	add(tokenCombo);
	setLocationRelativeTo(null);
	tokenCombo.setBounds(130, 8, 130, 50);
	tokenCombo.setLocation(130, 8);
	jpPre.add(tokenCombo);
	tokenCombo.addItemListener(new ItemHandler());

	add(swordCombo);
	setLocationRelativeTo(null);
	swordCombo.setBounds(260, 8, 130, 50);
	swordCombo.setLocation(260, 8);
	jpPre.add(swordCombo);
	swordCombo.addItemListener(new ItemHandler());
	//전처리 끝
	
	//IR 시작
	jpIR = new JPanel();
	jpIR.setLayout(null);
	jpIR.setBounds(540, 160, 80, 50);
	jpIR.setLocation(540, 160);
	
	TitledBorder tbIR = new TitledBorder(new LineBorder(Color.black),"IR");
	jpIR.setBorder(tbIR);
	jp1.add(jpIR);
	
	JComboBox IRCombo = new JComboBox(IR);
	add(IRCombo);
	setLocationRelativeTo(null);
	IRCombo.setBounds(3, 8, 70, 50);
	IRCombo.setLocation(3, 8);
	jpIR.add(IRCombo);
	IRCombo.addItemListener(new ItemHandler());
	//IR 끝
	
	//level 시작
	jpLevel = new JPanel();
	jpLevel.setLayout(null);
	jpLevel.setBounds(630, 160, 145, 50);
	jpLevel.setLocation(630, 160);
	
	TitledBorder tbLevel = new TitledBorder(new LineBorder(Color.black),"Level");
	jpLevel.setBorder(tbLevel);
	jp1.add(jpLevel);
	
	add(levelCombo);
	setLocationRelativeTo(null);
	levelCombo.setBounds(3, 13, 140, 40);
	levelCombo.setLocation(3, 13);
	jpLevel.add(levelCombo);
	levelCombo.addItemListener(new ItemHandler());
	//level 끝
		
	
	//가중치 시작
	jpWeight = new JPanel();
	jpWeight.setLayout(null);
	jpWeight.setBounds(290, 105, 485, 50);
	jpWeight.setLocation(290, 105);
	
	TitledBorder tbWeight = new TitledBorder(new LineBorder(Color.black),"가중치");
	jpWeight.setBorder(tbWeight);
	jp1.add(jpWeight);
	
	
	for(int i=0; i<weightTitle.length;i++){
		weightLabel[i] = new JLabel(weightTitle[i]);
	}
	
	weightLabel[0].setBounds(10, 20, 40, 20);
	weightLabel[0].setOpaque(false);
	jpWeight.add(weightLabel[0]);
	alpha.setBounds(45, 18, 35, 25);
	alpha.setLocation(45, 18);
	jpWeight.add(alpha);
	
	weightLabel[1].setBounds(85, 20, 40, 20);
	weightLabel[1].setOpaque(false);
	jpWeight.add(weightLabel[1]);
	beta.setBounds(112, 18, 35, 25);
	beta.setLocation(112, 18);
	jpWeight.add(beta);
	
	weightLabel[2].setBounds(152, 20, 47, 20);
	weightLabel[2].setOpaque(false);
	jpWeight.add(weightLabel[2]);
	gamma.setBounds(198, 18, 35, 25);
	gamma.setLocation(198, 18);
	jpWeight.add(gamma);
	
	weightLabel[3].setBounds(240, 20, 47, 20);
	weightLabel[3].setOpaque(false);
	jpWeight.add(weightLabel[3]);
	delta.setBounds(272, 18, 35, 25);
	delta.setLocation(272, 18);
	jpWeight.add(delta);
	
	weightLabel[4].setBounds(317, 20, 40, 20);
	weightLabel[4].setOpaque(false);
	jpWeight.add(weightLabel[4]);
	eta.setBounds(338, 18, 35, 25);
	eta.setLocation(338, 18);
	jpWeight.add(eta);
	
	weightLabel[5].setBounds(380, 20, 70, 20);
	weightLabel[5].setOpaque(false);
	jpWeight.add(weightLabel[5]);
	rankNum.setBounds(440, 18, 35, 25);
	rankNum.setLocation(440, 18);
	jpWeight.add(rankNum);
	//가중치 끝
	
	//past days 시작
	jpPdays = new JPanel();
	jpPdays.setLayout(null);
	jpPdays.setBounds(450, 200, 110, 45);
	jpPdays.setLocation(450, 200);
	jp1.add(jpPdays);
	
	pdaysLabel.setBounds(0, 20, 100, 20);
	pdaysLabel.setOpaque(false);
	jpPdays.add(pdaysLabel);
	pdays.setBounds(60, 20, 35, 20);
	pdays.setLocation(60, 20);
	jpPdays.add(pdays);
	//past days 끝

	//check box 시작
	JLabel[] etcLabel = new JLabel[etc.length];
	
	for(int i=0; i<etc.length;i++){
		etcLabel[i] = new JLabel(etc[i]);
	}

	for(int i=0; i<3; i++){
		checkbox[i] = new JCheckBox(etc[i]);
		checkbox[i].addActionListener(this);
		checkbox[i].setBounds(130+i*100, 200, 110, 60);
		checkbox[i].setLocation(130+i*100, 200);
		jp1.add(checkbox[i]);
	}
	//check box 끝
	
	//show rank to
	jpRankto = new JPanel();
	jpRankto.setLayout(null);
	jpRankto.setBounds(560, 200, 210, 45);
	jpRankto.setLocation(560, 200);
	jp1.add(jpRankto);
	
	ranktoLabel.setBounds(0, 20, 100, 20);
	ranktoLabel.setOpaque(false);
	jpRankto.add(ranktoLabel);
	rankto.setBounds(95, 20, 35, 20);
	rankto.setLocation(95, 20);
	jpRankto.add(rankto);
	//show rank to 끝
	
////////////////////////////////jp2	start
	jp2 = new JPanel();
	jp2.setLayout(null);
	jp2.setBounds(5,270,785,430);
	
	TitledBorder tb2 = new TitledBorder(new LineBorder(Color.black),"Result");
	jp2.setBorder(tb2);
	add(jp2);
	
	//왼쪽 bottom 시작
	jpBottom = new JPanel();
	jpBottom.setLayout(null);
	jpBottom.setBounds(12, 360, 370, 60);
	jpBottom.setLocation(12, 360);
	
	TitledBorder tbBottom = new TitledBorder(new LineBorder(Color.black));
	jpBottom.setBorder(tbBottom);
	
	jp2.add(jpBottom);
	
	

	JLabel[] bottomLabel = new JLabel[bottomTitle.length];
	
	for(int i=0; i<bottomTitle.length;i++){
		bottomLabel[i] = new JLabel(bottomTitle[i]);
	}
	
	bottomLabel[0].setBounds(5, 7, 35, 20);
	bottomLabel[0].setOpaque(false);
	jpBottom.add(bottomLabel[0]);
	jtfTop1.setBounds(40, 7, 60, 20);
	jtfTop1.setLocation(40, 7);
	jpBottom.add(jtfTop1);
	
	bottomLabel[1].setBounds(130, 7, 40, 20);
	bottomLabel[1].setOpaque(false);
	jpBottom.add(bottomLabel[1]);
	jtfTop5.setBounds(170, 7, 60, 20);
	jtfTop5.setLocation(170, 7);
	jpBottom.add(jtfTop5);
	
	bottomLabel[2].setBounds(260, 7, 40, 20);
	bottomLabel[2].setOpaque(false);
	jpBottom.add(bottomLabel[2]);
	jtfTop10.setBounds(300, 7, 60, 20);
	jtfTop10.setLocation(300, 7);
	jpBottom.add(jtfTop10);
	
	bottomLabel[3].setBounds(5, 35, 40, 20);
	bottomLabel[3].setOpaque(false);
	jpBottom.add(bottomLabel[3]);
	jtfMap.setBounds(40, 35, 60, 20);
	jtfMap.setLocation(40, 35);
	jpBottom.add(jtfMap);
	
	bottomLabel[4].setBounds(130, 35, 40, 20);
	bottomLabel[4].setOpaque(false);
	jpBottom.add(bottomLabel[4]);
	jtfMrr.setBounds(170, 35, 60, 20);
	jtfMrr.setLocation(170, 35);
	jpBottom.add(jtfMrr);
	//왼쪽 bottom 끝
	
	//오른쪽 bottom 시작
	jpBottom2 = new JPanel();
	jpBottom2.setLayout(null);
	jpBottom2.setBounds(400, 360, 370, 60);
	jpBottom2.setLocation(400, 360);
	
	TitledBorder tbBottom2 = new TitledBorder(new LineBorder(Color.black));
	jpBottom2.setBorder(tbBottom2);
	
	jp2.add(jpBottom);
	
	jp2.add(jpBottom2);
	
	JLabel[] bottom2Label = new JLabel[bottomTitle.length];
	
	for(int i=0; i<bottomTitle.length;i++){
		bottom2Label[i] = new JLabel(bottomTitle[i]);
	}
	
	bottom2Label[0].setBounds(5, 7, 35, 20);
	bottom2Label[0].setOpaque(false);
	jpBottom2.add(bottom2Label[0]);
	jtfTop1_r.setBounds(40, 7, 60, 20);
	jtfTop1_r.setLocation(40, 7);
	jpBottom2.add(jtfTop1_r);
	
	bottom2Label[1].setBounds(130, 7, 40, 20);
	bottom2Label[1].setOpaque(false);
	jpBottom2.add(bottom2Label[1]);
	jtfTop5_r.setBounds(170, 7, 60, 20);
	jtfTop5_r.setLocation(170, 7);
	jpBottom2.add(jtfTop5_r);
	
	bottom2Label[2].setBounds(260, 7, 40, 20);
	bottom2Label[2].setOpaque(false);
	jpBottom2.add(bottom2Label[2]);
	jtfTop10_r.setBounds(300, 7, 60, 20);
	jtfTop10_r.setLocation(300, 7);
	jpBottom2.add(jtfTop10_r);
	
	bottom2Label[3].setBounds(5, 35, 40, 20);
	bottom2Label[3].setOpaque(false);
	jpBottom2.add(bottom2Label[3]);
	jtfMap_r.setBounds(40, 35, 60, 20);
	jtfMap_r.setLocation(40, 35);
	jpBottom2.add(jtfMap_r);
	
	bottom2Label[4].setBounds(130, 35, 40, 20);
	bottom2Label[4].setOpaque(false);
	jpBottom2.add(bottom2Label[4]);
	jtfMrr_r.setBounds(170, 35, 60, 20);
	jtfMrr_r.setLocation(170, 35);
	jpBottom2.add(jtfMrr_r);
	
	//오른쪽 bottom 끝	
	sp1 = new JScrollPane();
	sp1.setBounds(12, 30, 370, 320);
    jp2.add(sp1);

    sp2 = new JScrollPane();
	sp2.setBounds(400, 60, 370, 290);
    jp2.add(sp2);

    clickedSC = new JLabel("Source Code : ");
    clickedSC.setBounds(400, 30, 90, 20);
	clickedSC.setOpaque(true);
	jp2.add(clickedSC);
	
	jtClicked.setBounds(490, 30, 280, 20);
	jtClicked.setLocation(490, 30);
	jp2.add(jtClicked);

	
	table1 = new JTable();
    table1.setModel(new DefaultTableModel(
    		new Object[][] { {" ", " ", " "},  },
            new String[] {"Bug Report", "Source Code", "순위" }) {
    	boolean[] columnEditables = new boolean[] {false, false, false};
    	public boolean isCellEditable(int row, int column) {
    		return columnEditables[column];
    		}
    	});
    
    
    sp1.setViewportView(table1);
    table1.addMouseListener(new MouseAdapter(){  
	     public void mouseClicked(MouseEvent e){  
		      if (e.getClickCount() == 2){ 
		         int maxRows;
		         int[] selRows;  
				 Object sfName;  
				 Object bugID;
				   
			     selRows = table1.getSelectedRows();  
			  
			     if (selRows.length > 0) {
			    	 TableModel tm = table1.getModel();
			    	 sfName = tm.getValueAt(selRows[0],1); 
			    	 bugID = tm.getValueAt(selRows[0],0);
			    	 jtClicked.setText(String.valueOf(sfName));
		         
			    	 try {
			    		 DB db = new DB();
			    		 HashMap<Integer, String> bugMethodMap = db.getMethodRank(Integer.parseInt(String.valueOf(bugID)), String.valueOf(sfName));	
			    		 Object[][] arrAdd = new Object[bugMethodMap.size()][3];
			    		 int i=0;
			    		 
			    		 Iterator<Integer> iter = bugMethodMap.keySet().iterator();
			    		 while(iter.hasNext()) {
			    			 int rank = iter.next();
			    			 String methodName = bugMethodMap.get(rank);
													
			    			 arrAdd[i][0] = rank;
			    			 arrAdd[i][1] = methodName;
			    			 arrAdd[i][2] = "";				
			    			 i++;
			    			 }
			    		 
			    		 table2.setModel(new DefaultTableModel(arrAdd,new String[] {"순위", "Method", "점수"}) {
			    			 boolean[] columnEditables = new boolean[] {false, false, false};
			    			 public boolean isCellEditable(int row, int column) {
			    				 return columnEditables[column];
			    				 }
			    			 });
			    		 
			    		 } catch (Exception e1) {
			    			 // TODO Auto-generated catch block
			    			 e1.printStackTrace();
			    			 }
			    	 repaint();
			    	 } 
			     }
		      }
	     });  
    
    
    table2 = new JTable();
    table2.setModel(new DefaultTableModel( 
    		new Object[][] { {" ", " ", " "}, }, 
    		new String[] {"순위", "Method", "점수"}) {
    	boolean[] columnEditables = new boolean[] {false, false, false};
    	public boolean isCellEditable(int row, int column) {
    		return columnEditables[column];
    		}
    	});
    
    sp2.setViewportView(table2);
////////////////////////////////jp2 end	
    
    setVisible(true);
	}
	
	static HashMap <String, HashMap<Integer, String>> bugRankMap = new HashMap <String, HashMap<Integer, String>>();
	static String evalResultFile = new String();

	static PropertyWriter pw = new PropertyWriter();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = new JButton();
		try{
			b = (JButton) e.getSource();
		}catch(ClassCastException ex){
			System.out.println("It is not button");
		}
		
		if(checkbox[0].isSelected())
			System.out.println("구조적 정보 is selected");
		if(checkbox[1].isSelected())
			System.out.println("Stack trace is selected");
		if(checkbox[2].isSelected())
			System.out.println("Comments is selected");
		
		int index = 0;
		if(b.getText().equals("run")){
			try {
				Blp blp = new Blp();
				blp.setProject((String)projectCombo.getSelectedItem());
				blp.setSourceFile(jtf[2].getText());
				blp.setCommitPath(jtf[0].getText());
				blp.setBugReportSet(jtf[1].getText());
				blp.setPdays(pdays.getText());
				blp.setAlpha(alpha.getText());
				blp.setBeta(beta.getText());
				blp.setGamma(gamma.getText());
				blp.setDelta(delta.getText());
				blp.setEta(eta.getText());
				blp.setRankNum(rankNum.getText());
				blp.setStructure(checkbox[0].isSelected());
				blp.setStackTrace(checkbox[1].isSelected());
				blp.setComments(checkbox[2].isSelected());
				blp.setMethodLevel(levelCombo.getSelectedItem()=="Method Level");
				pw.writePropertyFile(blp);
				
				EvaluatorTest test = new EvaluatorTest();
				test.verifyEvaluateBLIAOnce();
				
				Main.project = (String)projectCombo.getSelectedItem();
				System.out.println(Main.project);
				DB db = new DB();
				bugIDList = db.getBugIDs();
				String[] bugID = new String[bugIDList.size()];
				bugRankMap = new HashMap<String, HashMap<Integer, String>>();
				int cnt = 0;
				
				for(int i=0; i < bugIDList.size(); i++){
		 			bugID[i] = bugIDList.get(i);
		 			HashMap<Integer, String> data = db.getBugFileRank(bugID[i]);
		 			cnt += data.size();
		 			bugRankMap.put(bugID[i], data);
		 		}

				//System.out.println(bugRankMap);
				
				Object[][] arrAdd = new Object[cnt][3];
				int i=0;
				
				Iterator iter = bugRankMap.keySet().iterator();
				while(iter.hasNext()){
					String key = (String) iter.next();
					HashMap<Integer, String> data = bugRankMap.get(key);
					
					Iterator iter2 = data.keySet().iterator();
					while(iter2.hasNext()){
						int key2 = (int) iter2.next();
						String sf = data.get(key2);
						
						String sfID = sf.split(" ")[0];
						String sfName = sf.split(" ")[1];
						
						//System.out.println(arrAdd[i][0] + " " + key + " " + sfName);
						arrAdd[i][0] = key;
						arrAdd[i][1] = sfName;
						arrAdd[i][2] = key2;
						i++;
						}
					}
				
				table1.setModel(new DefaultTableModel(arrAdd,
						new String[] {"Bug Report", "Source Code", "순위"}) {
					boolean[] columnEditables = new boolean[] {false, false, false}; 
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
						}
					});
				
				DB2 db2 = new DB2();
		 		
                evalResultFile = db2.getEvalResultFile();
  
                String top1 = evalResultFile.split(" ")[0];
                String top5 = evalResultFile.split(" ")[1];
                String top10 = evalResultFile.split(" ")[2];
                String map = evalResultFile.split(" ")[3];
                String mrr = evalResultFile.split(" ")[4];            
                
                String top1Text = String.format("%.3f", Double.parseDouble(top1));
                String top5Text = String.format("%.3f", Double.parseDouble(top5));
                String top10Text = String.format("%.3f", Double.parseDouble(top10));
                String mapText = String.format("%.3f", Double.parseDouble(map));
                String mrrText = String.format("%.3f", Double.parseDouble(mrr));
        
                jtfMap.setText(mapText);
                jtfMrr.setText(mrrText);
                jtfTop1.setText(top1Text);
                jtfTop5.setText(top5Text);
                jtfTop10.setText(top10Text);
                
                if(levelCombo.getSelectedItem()=="Method Level"){
               	String evalResultMethod = db2.getEvalResultMethod();
  
                String top1_r = evalResultMethod.split(" ")[0];
                String top5_r = evalResultMethod.split(" ")[1];
                String top10_r = evalResultMethod.split(" ")[2];
                String map_r = evalResultMethod.split(" ")[3];
                String mrr_r = evalResultMethod.split(" ")[4];

                
                String top1Text_r = String.format("%.3f", Double.parseDouble(top1_r));
                String top5Text_r = String.format("%.3f", Double.parseDouble(top5_r));
                String top10Text_r = String.format("%.3f", Double.parseDouble(top10_r));
                String mapText_r = String.format("%.3f", Double.parseDouble(map_r));
                String mrrText_r = String.format("%.3f", Double.parseDouble(mrr_r));
        
                jtfTop1_r.setText(top1Text_r);
                jtfTop5_r.setText(top5Text_r);
                jtfTop10_r.setText(top10Text_r);
                jtfMap_r.setText(mapText_r);
                jtfMrr_r.setText(mrrText_r); 
                }
                
//                db.close();
//                db2.close();
                
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			for(int i=0; i<3; i++){
				index = i;
				if(b.equals(jbSearch[index])){
					try {
	
					JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); //
					chooser.setCurrentDirectory(new File("/")); 
			        chooser.setAcceptAllFileFilterUsed(true);    
			        chooser.setDialogTitle("search"); 
			        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
			        
//			        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "cd11"); 
//			        chooser.setFileFilter(filter); 
			        
			        int returnVal = chooser.showOpenDialog(null); 
			        
			        if(returnVal == JFileChooser.APPROVE_OPTION) {  
			            jtf[index].setText(chooser.getSelectedFile().toString());
			            }
			        else if(returnVal == JFileChooser.CANCEL_OPTION){ 
			        	System.out.println("cancel");
			        	}
			        repaint();
			        }
				catch (Exception e1) {
					e1.printStackTrace();
					}
				}
			}
		}
	}
	public double[][] getTest(){
		return test;
	}
}


class ItemHandler implements ItemListener{

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED){
			System.out.println("Selected Item: " + e.getItem());
			}
	}
	
}


