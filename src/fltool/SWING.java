package fltool;
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
import javax.swing.JDialog;
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
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import common.Blp;
import common.BugReport;
import edu.skku.selab.blp.evaluation.EvaluatorTest;


public class SWING extends JFrame implements ActionListener{
	JPanel jp1, jp2, jpPre, jpIR, jpWeight, jpPdays, jpLevel, jpRankto, jpRankNum, jpProject;
	JTable table1, table2, table3;
	JScrollPane sp1, sp2, sp3;
	JButton[] jbSearch = new JButton[3];
    NewWindow newWindow;
    
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
	

	static ArrayList<String> bugIDList = new ArrayList<String>();
	static BugReport bugReport;
	static DB db = null;
	
	public SWING() throws Exception{		
		
		
	test = new double[1][9];
	setLayout(null);
	setTitle("FL TOOL");
	setSize(800,750);
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	String[] labelTitle = {"Commit",
			"BugReport",
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
	
	
    sp1 = new JScrollPane();
	sp1.setBounds(15, 30, 120, 380);
    jp2.add(sp1);

	sp2 = new JScrollPane();
	sp2.setBounds(160, 30, 300, 380);
    jp2.add(sp2);

    sp3 = new JScrollPane();
	sp3.setBounds(470, 30, 300, 380);
    jp2.add(sp3);
   	
    
	table1 = new JTable();
    table1.setModel(new DefaultTableModel(
    		new Object[][] { {" "},  },
            new String[] {"Bug ID"}) {
    	boolean[] columnEditables = new boolean[] {false};
    	public boolean isCellEditable(int row, int column) {
    		return columnEditables[column];
    		}
    	});
    sp1.setViewportView(table1);
    
    
	table2 = new JTable();
    table2.setModel(new DefaultTableModel(
    		new Object[][] { {" ", " "},  },
            new String[] {"Rank", "Source File Name"}) {
    	boolean[] columnEditables = new boolean[] {false, false};
    	public boolean isCellEditable(int row, int column) {
    		return columnEditables[column];
    		}
    	});
    table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table2.getColumnModel().getColumn(0).setPreferredWidth(50);
    table2.getColumnModel().getColumn(1).setPreferredWidth(245);
    sp2.setViewportView(table2);
    
    table3 = new JTable();
    table3.setModel(new DefaultTableModel( 
    		new Object[][] { {" ", " "}, }, 
    		new String[] {"Rank", "Method Name"}) {
    	boolean[] columnEditables = new boolean[] {false, false};
    	public boolean isCellEditable(int row, int column) {
    		return columnEditables[column];
    		}
    	});
    table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table3.getColumnModel().getColumn(0).setPreferredWidth(50);
    table3.getColumnModel().getColumn(1).setPreferredWidth(245);
    sp3.setViewportView(table3);
    
     
    table1.addMouseListener(new MouseAdapter(){  
	     public void mouseClicked(MouseEvent e){  
		      if (e.getClickCount() == 2){ 
		         int maxRows;
		         int[] selRows;  
		         Object bugID;
				   
			     selRows = table1.getSelectedRows();  
			     
			     if (selRows.length > 0) {
			    	 TableModel tm = table1.getModel();
			    	 bugID = tm.getValueAt(selRows[0],0);
		         
			    	 try {
			    		 if(db == null)
			    			 db = new DB();
			    		 HashMap<Integer, String> sourceFileRankMap = db.getSourceFileRank(Integer.parseInt(String.valueOf(bugID)), Integer.parseInt(String.valueOf(rankto.getText())));	
			    		 Object[][] arrAddSf = new Object[sourceFileRankMap.size()][2];
			    		 int i=0;
			    		 Iterator<Integer> iter = sourceFileRankMap.keySet().iterator();
			    		 while(iter.hasNext()) {
			    			 int rank = iter.next();
			    			 String sourceFileName = sourceFileRankMap.get(rank);
													
			    			 arrAddSf[i][0] = rank;
			    			 arrAddSf[i][1] = sourceFileName;		
			    			 i++;
			    			 }
			    		 
			    		 
			    		 HashMap<Integer, String> methodRankMap = db.getMethodRank(Integer.parseInt(String.valueOf(bugID)), Integer.parseInt(String.valueOf(rankto.getText())));	
			    		 Object[][] arrAddMth = new Object[methodRankMap.size()][2];
			    		 
			    		 i=0;
			    		 Iterator<Integer> iter2 = methodRankMap.keySet().iterator();
			    		 while(iter2.hasNext()) {
			    			 int rank = iter2.next();
			    			 String methodName = methodRankMap.get(rank);
													
			    			 arrAddMth[i][0] = rank;
			    			 arrAddMth[i][1] = methodName;		
			    			 i++;
			    			 }
			    		 	
			    		 table2.setModel(new DefaultTableModel(arrAddSf,new String[] {"Rank", "Source File Name"}) {
			    			 boolean[] columnEditables = new boolean[] {false, false};
			    			 public boolean isCellEditable(int row, int column) {
			    				 return columnEditables[column];
			    				 }
			    			 });
			    		 table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			    		 table2.getColumnModel().getColumn(0).setPreferredWidth(50);
			    		 table2.getColumnModel().getColumn(1).setPreferredWidth(500);
		    		 
			    		 
			    		 table3.setModel(new DefaultTableModel(arrAddMth,new String[] {"Rank", "Method Name"}) {
			    			 boolean[] columnEditables = new boolean[] {false, false};
			    			 public boolean isCellEditable(int row, int column) {
			    				 return columnEditables[column];
			    				 }
			    			 });
			    		 table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			    		 table3.getColumnModel().getColumn(0).setPreferredWidth(50);
			    		 table3.getColumnModel().getColumn(1).setPreferredWidth(300);
		    		 		
			    		 
			    		 } catch (Exception e1) {
			    			 // TODO Auto-generated catch block
			    			 e1.printStackTrace();
			    			 }
			    	 repaint();
			    	 } 
			     }
		      }
	     });  
    
    table2.addMouseListener(new MouseAdapter(){
    	public void mouseClicked(MouseEvent e){
    		if(e.getClickCount() == 2){
    			int maxRows;
		         int[] selRows;  
				 Object sfName;  
				 Object bugID;
				 
				 selRows = table2.getSelectedRows();  
			     
			     if (selRows.length > 0) {
			    	 TableModel tm1 = table1.getModel();
			    	 TableModel tm2 = table2.getModel();
			    	 bugID = tm1.getValueAt(selRows[0],0);
			    	 sfName = tm2.getValueAt(selRows[0],1);
			    	
			    	 try{
			    		 newWindow = new NewWindow(bugID, sfName, db);
			    		 
			    	 }
			    	 catch (Exception e1) {
		    			 // TODO Auto-generated catch block
		    			 e1.printStackTrace();
		    			 }
			     }
    		}
    	}
    });
    
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
				if(db == null)
					db = new DB();
				bugIDList = db.getBugIDs();
				String[] bugID = new String[bugIDList.size()];
				
				Object[][] arrAdd = new Object[bugIDList.size()][1];
				for(int i=0; i < bugIDList.size(); i++){
					arrAdd[i][0] = bugIDList.get(i);
		 		}
				
				table1.setModel(new DefaultTableModel(arrAdd,
						new String[] {"Bug ID"}) {
					boolean[] columnEditables = new boolean[] {false}; 
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
						}
					});

              //  db.close();
                
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

class NewWindow extends JDialog{
	JLabel jlb = new JLabel("");
    JPanel jp = new JPanel();
    JTable table4;
    JScrollPane sp4;
    
    public NewWindow(Object bugID, Object sfName, DB db){	
    	    
    	jp = new JPanel();
    	jp.setLayout(null);
    	jp.setBounds(3,3,300,380);
    	jp.setLocation(3, 15);

		 
	
		try {
		
		 HashMap<Integer, String> sfToMthMap = db.getSfToMth(Integer.parseInt(String.valueOf(bugID)), String.valueOf(sfName));	
		 
		 Object[][] arrAddSf = new Object[sfToMthMap.size()][2];
		 int i=0;
		 Iterator<Integer> iter = sfToMthMap.keySet().iterator();
		 while(iter.hasNext()) {
			 int rank = iter.next();
			 String methodName = sfToMthMap.get(rank);
									
			 arrAddSf[i][0] = rank;
			 arrAddSf[i][1] = methodName;		
			 i++;

			 }
		
		 table4 = new JTable();
		    table4.setModel(new DefaultTableModel(arrAddSf
		            ,new String[] {"Rank", "Method Name"}) {
		        	boolean[] columnEditables = new boolean[] {false, false};
		        	public boolean isCellEditable(int row, int column) {
		        		return columnEditables[column];
		        		}
		        	});	
			table4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table4.getColumnModel().getColumn(0).setPreferredWidth(50);
			table4.getColumnModel().getColumn(1).setPreferredWidth(300);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    add(jp);
	    
	    sp4 = new JScrollPane();
		sp4.setBounds(3, 3, 300, 380);
		
    	jp.add(sp4);
        sp4.setViewportView(table4);
        
        this.setSize(300,380);
        this.setModal(true);
        this.setVisible(true);
           
	    }
}

