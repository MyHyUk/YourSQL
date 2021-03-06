package wsu.yoursql.rms;
import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

class searchBook extends JDialog implements ActionListener, MouseListener{
	private JPanel pnTop, pnBottom;
	private JTable tblList;
	private JButton btnOk, btnCancel;
	private JComboBox cbbList;
	private Label lblSrh;
	private JTextField tfSearch;

	private Book bk;
	private ExtendedDefaultTableModel Model;
	private String colName[] = {"도서코드", "도서명","바코드번호"};
	private String searchList[] = {"도서코드", "도서명","바코드번호"};
	private String srhData[][] = {};
	private String srhStr=""; // 찾을단어
	private String strCombo=""; // 선택한 리스트
	private int intCombo=0; //선택한 리스트의 인덱스값
	private int size=0 ; // DB에서 불러온 String 배열 크기
	private int len=0; //Table List에 보여주기 위해 크기구함
	private String[] bCode, bName, bBarcode;// DB에서 불러온 데이타를 저장하기 위해서
	
	
	searchBook(Book bk, JFrame f){
		super(f,"도서 목록 검색" , true);
		this.bk = bk;
		

		Container c = getContentPane();
		c.setLayout(new BorderLayout(3,2));
		
		Model = new ExtendedDefaultTableModel(srhData, colName);
		tblList = new JTable(Model);
		JScrollPane scrollPane = new JScrollPane(tblList);
		tblList.getTableHeader().setReorderingAllowed(false);
		tblList.getTableHeader().setResizingAllowed(false);
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "검색된 도서정보",
															TitledBorder.LEFT,
															TitledBorder.BELOW_TOP, MyFont.sans1,new Color(53,57,72)));

		btnOk = new JButton(" O    K ");
		btnCancel = new JButton("Cancel");
		
		lblSrh = new Label("Search");
		tfSearch = new JTextField(20);
		
		cbbList = new JComboBox(searchList);
		cbbList.setSelectedIndex(0);
		cbbList.setFont(MyFont.sans1);
		
		btnOk.setBackground(new Color(170,181,221));
		btnCancel.setBackground(new Color(170,181,221));
		tfSearch.setBackground(new Color(244,252,243));
		cbbList.setBackground(new Color(254,252,243));

		pnTop = new JPanel(new BorderLayout(5,3));
		pnBottom = new JPanel(new GridBagLayout());

		pnTop.setBackground(new Color(217,230,237));
		pnBottom.setBackground(new Color(217,230,237));
		scrollPane.setBackground(new Color(217,230,237));
		tblList.setBackground(new Color(217,230,237));
		tblList.getTableHeader().setBackground(new Color(136,146,139));
		tblList.getTableHeader().setForeground(Color.white);

		tblList.getColumnModel().getColumn(0).setPreferredWidth(5);
		tblList.getColumnModel().getColumn(1).setPreferredWidth(20); 
		tblList.getColumnModel().getColumn(2).setPreferredWidth(10); 

		tblList.setPreferredScrollableViewportSize(new Dimension(400, 70));
	
		pnBottom.add(lblSrh, new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),3,5));
		pnBottom.add(cbbList, new GridBagConstraints(1,0,2,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,0,0,0),3,5));
		pnBottom.add(tfSearch, new GridBagConstraints(3,0,5,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE, new Insets(0,0,0,0),3,5));
		pnBottom.add(btnOk, new GridBagConstraints(2,1,2,1,0,0,GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(20,10,15,0),3,5));
		pnBottom.add(btnCancel, new GridBagConstraints(4,1,2,1,0,0,GridBagConstraints.EAST,GridBagConstraints.NONE, new Insets(20,10,15,0),3,5));
	
		pnTop.add(scrollPane, BorderLayout.CENTER);
		c.add(pnTop, BorderLayout.CENTER);
		c.add(pnBottom, BorderLayout.SOUTH);
		
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		tfSearch.addActionListener(this);
		cbbList.addActionListener(this);
		tblList.addMouseListener(this);

		setBounds(150,300,600,300);
		setVisible(true);
	
	}
	/*// index - 0: 도서코드, 1: 도서명, 3:바코드번호 로 찾는 갯수*/
	public int countTuple(int index, String str){ // 해당 도서 튜플갯수를 알아내기 위한 메서드
		int count = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		try{  
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String	sql="";
			Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
			switch(index){
				case 0:
					sql = "select * from book where book_code like '%"+str+"%'";
					break;
				case 1:
					sql = "select * from book where book_name like '%"+str+"%'";
					break;
				case 2:
					sql = "select * from book where book_barcode like '%"+str+"%'";
					break;
				default:
					break;
			}
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
				
			while(rs1.next()){
				count++;
			}
			rs1.close();
			stat.close();
			conn.close();
		}catch(java.lang.Exception ea){ea.printStackTrace();}  
		return count;
	}
	

	/*// index - 0: 도서코드, 1: 도서명, 3:바코드번호 */
	public void searchList(int index, String str){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		try{  
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String	sql="";
			Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
		
			switch(index){
				case 0:
					size = countTuple(0,str);
					/*DB에서 불러들일 데이타들의 배열 선언*/
					bCode = new String[size];
					bName = new String[size];
					bBarcode = new String[size];
					bk.setView("찾은 튜플의 갯수: "+size);
					sql = "select * from book where book_code like '%"+str+"%'";
					break;
				case 1:
					size = countTuple(1,str);
					/*DB에서 불러들일 데이타들의 배열 선언*/
					bCode = new String[size];
					bName = new String[size];
					bBarcode = new String[size];
					bk.setView("찾은 튜플의 갯수: "+size);
					sql = "select * from book where book_name like '%"+str+"%'";
					break;
			
				case 2://수량에서 바코드번호로
					size = countTuple(2,str);
					/*DB에서 불러들일 데이타들의 배열 선언*/
					bCode = new String[size];
					bName = new String[size];
					bBarcode = new String[size];
					bk.setView("찾은 튜플의 갯수: "+size);
					sql = "select * from book where book_barcode like '%"+str+"%'";
				
					break;
					
				default:
					break;
		}
			
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
			int tnum = 0;
			//DB로부터 데이터 얻어 저장하기
			while(rs1.next()){
				bCode[tnum] = rs1.getString(1);
				bName[tnum] = rs1.getString(2);
				bBarcode[tnum] = rs1.getString(3);
				tnum++;
			}
			
			// JTable 보여주기 위한 설정
			len = bCode.length; // 테이블에 보여주기 위해 크기 구함		
			srhData = new String[len][3];
			for(int i=0;i<len;i++){
				srhData[i][0] = bCode[i];
				srhData[i][1] = bName[i];
				srhData[i][2] = bBarcode[i];
			}
			Model = new ExtendedDefaultTableModel(srhData,colName);
			tblList.setModel(Model);
			tblList.setShowGrid(true);
			tblList.getColumnModel().getColumn(0).setPreferredWidth(5);
			tblList.getColumnModel().getColumn(1).setPreferredWidth(50); 
			tblList.getColumnModel().getColumn(2).setPreferredWidth(10); 


			bk.setView("  도서코드\t도서명\t바코드번호");
			for(int i=0;i<size;i++){
				bk.setView("  "+bCode[i]+"\t"+bName[i]+"\t"+bBarcode[i]+"\t");		
			}
			bk.setView("  "+srhStr+" 로 검색한 결과 "+ size +" 개를 찾았습니다!");
			
			rs1.close();
			stat.close();
			conn.close();
		}catch(java.lang.Exception ea){ea.printStackTrace();}  
		
	
	}

	public void actionPerformed( ActionEvent e ){
		Object obj = e.getSource();
		
		if(obj == btnOk || obj == tfSearch){
			if(tfSearch.getText().equals("")){
				Label msg = new Label("검색할 단어를 입력해 주세여");
				msg.setFont(MyFont.sans1);
				JOptionPane.showMessageDialog(this, msg, "알 림!!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			srhStr = tfSearch.getText().trim();
			strCombo = (String)cbbList.getSelectedItem();
			strCombo = strCombo.trim();
			searchList(intCombo, srhStr);
		
		}
		else if(obj == cbbList){ // 선택된 리스트 인덱스
			intCombo = (int)cbbList.getSelectedIndex();
		}
		else if(obj == btnCancel){
			dispose();
		}


	}
	public void mouseClicked(MouseEvent me){
			
		Object obj = me.getSource();
			
		
		if(obj == tblList){
			int row=0;
			String code,name,barcode;
			row = tblList.getSelectedRow(); // 테이블 행번호를 넘겨줌
			TableModel model = tblList.getModel();
			
			code = ""+ model.getValueAt(row, 0);
			name = ""+ model.getValueAt(row, 1);
			barcode = ""+ model.getValueAt(row, 2);
			

			bk.setData(code,name,barcode);

		}
		
	}
	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
