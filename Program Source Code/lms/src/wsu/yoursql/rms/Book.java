package wsu.yoursql.rms;

import java.io.*;
import java.text.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

class Book extends JFrame implements ActionListener {
	public String ipaddress = "210.207.88.57";
	
	

	//////// book 데이터
	private JLabel lblbook_code, lblbookname, lblbookrental, lblbookback, lblrental_id, lblbookbarcode;
	private JButton btnBuy, btnBookDel, btnrental, btnReturn, btnBookSearch, btnInsert, btnInit;
	private JTextField tfbook_code, tfBookName, tfBookBarcode, tfrental, tfReturn, tfrentalId;
	//////// lbrMember 클래스에서 쓰임
	protected JLabel lblid, lblname, lblcode, lblphone, lbladdress, lblcash;
	protected JButton btnMember, btnDel, btnSearch, btnMod, btnClear;

	/////// 업체 데이터
	protected JButton btnProAdd, btnProDel,btnProMod,btnProSearch,btnDeal;
	protected JTextField tfpro_Code, tfpro_name, tfpro_bank, tfpro_acc, tfpro_manager, tfpro_phone;
	private Label lblpro_Code, lblpro_name, lblpro_bank, lblpro_acc, lblpro_manager, lblpro_phone;
	
	
	protected JTextField tfId, tfName, tfbirthday, tfphone, tfaddress, tfcash;
	protected JTextArea taStatus;
	protected JPanel pnCenter, pnBottom, pntab1, pntab2, pntab3, pntab4, pntab5;
	private JTabbedPane tabpane;
	private ImageIcon icon;
	private JMenuBar mnbBar;
	private JMenu mnAbout;
	private JMenuItem itAbout;
	private ExtendedDefaultTableModel tblModel, ComModel;
	protected ExtendedDefaultTableModel rentalModel, returnModel;
	private JTable table, ComTable;
	protected JTable rentalTable, returnTable;
	private JScrollPane scrollPane;
	protected JScrollPane rentalScrollPane, returnScrollPane;
	private String lbrList[] = { "도서코드", "도서명", "도서 바코드", "도서상태" };
	private String lbrData[][] = {};

	private String ComList[] = { "업체코드", "업체명", "주거래 은행", "계좌번호", "담당자 이름", "담당자 연락처" };
	private String ComData[][] = {};
	private String[] aCode, aName, aBarcode;

	private lbrMember mem;
	private lbrProvider prov;
	
	
	private DBView dbv;


	Book() {

		super("빅뱅 도서 대여 시스템");
		Container c = getContentPane();
		c.setLayout(new BorderLayout(2, 1));
		c.setBackground(Color.white);

		/////////////////////
		// 각종 컴포넌트 생성
		//////////////////
		pnCenter = new JPanel(new BorderLayout());
		pnBottom = new JPanel(new BorderLayout());
		pnCenter.setBackground(new Color(249, 242, 205));
		pnBottom.setBackground(new Color(249, 242, 205));
		tabpane = new JTabbedPane();

		pntab1 = new JPanel(new GridLayout(1, 0, 5, 3));
		pntab2 = new JPanel(new GridBagLayout());
		pntab3 = new JPanel(new GridBagLayout());
		pntab4 = new JPanel(new GridLayout(2, 0, 5, 3));
		pntab5 = new JPanel(new GridBagLayout());

		pntab1.setBackground(new Color(249, 242, 205));
		pntab2.setBackground(new Color(249, 242, 205));
		pntab3.setBackground(new Color(249, 242, 205));
		pntab4.setBackground(new Color(249, 242, 205));
		pntab5.setBackground(new Color(249, 242, 205));

		/////////////////////////
		/// 도서목록 탭 시작

		tblModel = new ExtendedDefaultTableModel(lbrData, lbrList);
		table = new JTable(tblModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBackground(new Color(249, 242, 205));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setBackground(new Color(217, 230, 237));
		table.getTableHeader().setBackground(new Color(207, 203, 171));
		// table.getTableHeader().setForeground(Color.white);

		pntab1.add(scrollPane);

		////////////////
		// 도서목록 탭 끝
		////////////////////
		// 도서관리 시작
		/////////////
		lblbook_code = new JLabel("도서코드 :        ");
		lblbookname = new JLabel("도 서 명 :          ");
		lblbookbarcode = new JLabel("바코드번호 :     ");
		lblbookrental = new JLabel("대 여 일 :          ");
		lblbookback = new JLabel("반 납 일 :          ");
		lblrental_id = new JLabel("대 여 자 코 드 :          ");

		lblbook_code.setFont(MyFont.sans1);
		lblbookname.setFont(MyFont.sans1);
		lblbookbarcode.setFont(MyFont.sans1);
		lblbookrental.setFont(MyFont.sans1);
		lblbookback.setFont(MyFont.sans1);
		lblrental_id.setFont(MyFont.sans1);

		/////////////////////
		// 텍스트 필드 생성
		//////////////////////////////
		tfbook_code = new JTextField(40);
		tfBookName = new JTextField(40);
		tfBookBarcode = new JTextField(40);
		tfrental = new JTextField("반납시만 이용", 40);
		tfrental.setEditable(false); // 시스템이 알아서 하게끔
		tfReturn = new JTextField("반납시만 이용", 40);
		tfReturn.setEditable(false);
		tfrentalId = new JTextField(40);

		tfbook_code.setBackground(new Color(252, 251, 217));
		tfBookName.setBackground(new Color(252, 251, 217));
		tfBookBarcode.setBackground(new Color(252, 251, 217));
		tfrental.setBackground(new Color(252, 251, 200));
		tfReturn.setBackground(new Color(252, 251, 200));
		tfrentalId.setBackground(new Color(252, 251, 217));

		/////////////////
		// 버튼 생성
		//////////////////////////
		btnBuy = new JButton("구  입");
		btnBookDel = new JButton("삭  제");
		btnrental = new JButton("대  출");
		btnReturn = new JButton("반  납");
		btnBookSearch = new JButton("검  색");
		btnInsert = new JButton("수  정");
		btnInit = new JButton("리  셋");

		btnBuy.setBackground(new Color(239, 232, 194));
		btnBookDel.setBackground(new Color(239, 232, 194));
		btnrental.setBackground(new Color(239, 232, 194));
		btnReturn.setBackground(new Color(239, 232, 194));
		btnBookSearch.setBackground(new Color(239, 232, 194));
		btnInsert.setBackground(new Color(239, 232, 194));
		btnInit.setBackground(new Color(239, 232, 194));

		btnBuy.setFont(MyFont.sans1);
		btnBookDel.setFont(MyFont.sans1);
		btnrental.setFont(MyFont.sans1);
		btnReturn.setFont(MyFont.sans1);
		btnBookSearch.setFont(MyFont.sans1);
		btnInsert.setFont(MyFont.sans1);
		btnInit.setFont(MyFont.sans1);

		/////////////////////////////////////
		// 페널에 등록
		////////////////////////////////////
		pntab2.add(lblbook_code, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfbook_code, new GridBagConstraints(1, 0, 3, 1, 3, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(lblbookname, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfBookName, new GridBagConstraints(1, 1, 3, 1, 3, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(lblbookbarcode, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfBookBarcode, new GridBagConstraints(1, 2, 3, 1, 3, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(lblbookrental, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfrental, new GridBagConstraints(1, 3, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(lblbookback, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfReturn, new GridBagConstraints(1, 4, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(lblrental_id, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab2.add(tfrentalId, new GridBagConstraints(1, 5, 3, 1, 3, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab2.add(btnBuy, new GridBagConstraints(5, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnBookDel, new GridBagConstraints(5, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnrental, new GridBagConstraints(5, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnReturn, new GridBagConstraints(5, 3, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnBookSearch, new GridBagConstraints(5, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnInsert, new GridBagConstraints(5, 5, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab2.add(btnInit, new GridBagConstraints(5, 6, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 70), 3, 5));

		////////////////
		// 리스너에 등록
		//////////////////
		btnBuy.addActionListener(this);
		btnBookDel.addActionListener(this);
		btnrental.addActionListener(this);
		btnReturn.addActionListener(this);
		btnBookSearch.addActionListener(this);
		btnInsert.addActionListener(this);
		btnInit.addActionListener(this);
		/// 도서 관리 끝

		////////////////////////////////////////////////
		// 회원관리 시작
		lblid = new JLabel("\t 회 원 코 드 :        ");
		lblname = new JLabel("\t 회 원 이 름 :        ");
		lblcode = new JLabel("\t 생 년 월 일 :        ");
		lblphone = new JLabel("\t 연   락   처 :        ");
		lbladdress = new JLabel("\t 주         소 :        ");
		lblcash = new JLabel("\t 선 불 금 액 :        ");

		lblid.setFont(MyFont.sans1);
		lblname.setFont(MyFont.sans1);
		lblcode.setFont(MyFont.sans1);
		lblphone.setFont(MyFont.sans1);
		lbladdress.setFont(MyFont.sans1);
		lblcash.setFont(MyFont.sans1);

		tfId = new JTextField("가입시엔 안적어도됨", 40);
		tfName = new JTextField(40);
		tfbirthday = new JTextField(40);
		tfphone = new JTextField(40);
		tfaddress = new JTextField(40);
		tfcash = new JTextField(40);

		tfId.setBackground(new Color(252, 251, 200));
		tfName.setBackground(new Color(252, 251, 217));
		tfbirthday.setBackground(new Color(252, 251, 217));
		tfphone.setBackground(new Color(252, 251, 217));
		tfaddress.setBackground(new Color(252, 251, 217));
		tfcash.setBackground(new Color(252, 251, 217));

		btnMember = new JButton("회원가입");
		btnDel = new JButton("회원삭제");
		btnSearch = new JButton("회원검색");
		btnMod = new JButton("회원수정");
		btnClear = new JButton("입력리셋");

		btnMember.setBackground(new Color(239, 232, 194));
		btnDel.setBackground(new Color(239, 232, 194));
		btnSearch.setBackground(new Color(239, 232, 194));
		btnMod.setBackground(new Color(239, 232, 194));
		btnClear.setBackground(new Color(239, 232, 194));

		btnMember.setFont(MyFont.sans1);
		btnDel.setFont(MyFont.sans1);
		btnSearch.setFont(MyFont.sans1);
		btnMod.setFont(MyFont.sans1);
		btnClear.setFont(MyFont.sans1);

		btnMember.addActionListener(this);
		btnDel.addActionListener(this);
		btnSearch.addActionListener(this);
		btnMod.addActionListener(this);
		btnClear.addActionListener(this);

		/////////////////////
		// 패널에 컴포넌트 등록
		/////////////////////
		pntab3.add(lblid, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));
		pntab3.add(tfId, new GridBagConstraints(1, 0, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab3.add(lblname, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));
		pntab3.add(tfName, new GridBagConstraints(1, 1, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab3.add(lblcode, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));
		pntab3.add(tfbirthday, new GridBagConstraints(1, 2, 3, 1, 3, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab3.add(lblphone, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab3.add(tfphone, new GridBagConstraints(1, 3, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab3.add(lbladdress, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab3.add(tfaddress, new GridBagConstraints(1, 4, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pntab3.add(lblcash, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5)); // 새로 생성
		pntab3.add(tfcash, new GridBagConstraints(1, 5, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5)); // 새로 생성

		pntab3.add(btnMember, new GridBagConstraints(5, 0, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab3.add(btnDel, new GridBagConstraints(5, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 70), 3, 5));
		pntab3.add(btnSearch, new GridBagConstraints(5, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab3.add(btnMod, new GridBagConstraints(5, 3, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 70), 3, 5));
		pntab3.add(btnClear, new GridBagConstraints(5, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));

		// 회원관리 끝
		//////////////////////////////
		// 업체관리 시작
		lblpro_Code = new Label("업 체 코 드 : ");
		lblpro_name = new Label("업   체   명 : ");
		lblpro_bank = new Label("주 거 래 은 행 : ");
		lblpro_acc = new Label("계 좌 번 호 : ");
		lblpro_manager = new Label("담당자 이름 : ");
		lblpro_phone = new Label("담당자 연락처 : ");

		tfpro_Code = new JTextField("가입시엔 안적어도됨",40);
		tfpro_name = new JTextField(40);
		tfpro_bank = new JTextField(40);
		tfpro_acc = new JTextField(40);
		tfpro_manager = new JTextField(40);
		tfpro_phone = new JTextField(40);

		btnProAdd = new JButton("업체추가");
		btnProDel = new JButton("업체삭제");
		btnProMod = new JButton("업체수정");
		btnProSearch = new JButton("업체검색");
		btnDeal = new JButton("매출현황");

		btnProAdd.setBackground(new Color(239, 232, 194));
		btnProDel.setBackground(new Color(239, 232, 194));
		btnProMod.setBackground(new Color(239, 232, 194));
		btnProSearch.setBackground(new Color(239, 232, 194));
		btnDeal.setBackground(new Color(210, 232, 250));
		
		btnProAdd.setFont(MyFont.sans1);
		btnProDel.setFont(MyFont.sans1);
		btnProMod.setFont(MyFont.sans1);
		btnProSearch.setFont(MyFont.sans1);
		btnDeal.setFont(MyFont.sans1);
		
		btnProAdd.addActionListener(this);
		btnProDel.addActionListener(this);
		btnProMod.addActionListener(this);
		btnProSearch.addActionListener(this);
		btnDeal.addActionListener(this);
		
		tfpro_Code.setBackground(new Color(244, 252, 200));
		tfpro_name.setBackground(new Color(244, 252, 243));
		tfpro_bank.setBackground(new Color(244, 252, 243));
		tfpro_acc.setBackground(new Color(244, 252, 243));
		tfpro_manager.setBackground(new Color(244, 252, 243));
		tfpro_phone.setBackground(new Color(244, 252, 243));

		pntab5.add(lblpro_Code, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_Code, new GridBagConstraints(1, 0, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(lblpro_name, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_name, new GridBagConstraints(1, 1, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(lblpro_bank, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_bank, new GridBagConstraints(1, 2, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(lblpro_acc, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_acc, new GridBagConstraints(1, 3, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(lblpro_manager, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_manager, new GridBagConstraints(1, 4, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(lblpro_phone, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pntab5.add(tfpro_phone, new GridBagConstraints(1, 5, 3, 1, 3, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pntab5.add(btnProAdd, new GridBagConstraints(5, 0, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab5.add(btnProDel, new GridBagConstraints(5, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab5.add(btnProMod, new GridBagConstraints(5, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab5.add(btnProSearch, new GridBagConstraints(5, 3, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));
		pntab5.add(btnDeal, new GridBagConstraints(5, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 70), 3, 5));

		
		
		
		//업체관리끝
		/////////////////////////////////////////
		icon = new ImageIcon("./image/room.gif");
		tabpane.addTab("도서 목록", icon, pntab1);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/with.gif");
		tabpane.addTab("도서 관리", icon, pntab2);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/one.gif");
		tabpane.addTab("회원 관리", icon, pntab3);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/view.gif");
		tabpane.addTab("대출 현황", icon, pntab4);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/view.gif");
		tabpane.addTab("업체 관리", icon, pntab5);
		tabpane.setFont(MyFont.sans1);

		taStatus = new JTextArea(10, 10);
		taStatus.setBackground(new Color(252, 251, 217));
		taStatus.setEditable(false);
		taStatus.setLineWrap(true);
		taStatus.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(taStatus);
		pnBottom.add(sp);
		pnBottom.setBorder(
				new TitledBorder(new EtchedBorder(), "작업 상태", TitledBorder.LEFT, TitledBorder.TOP, MyFont.sans1));

		mnbBar = new JMenuBar();
		setJMenuBar(mnbBar);
		mnAbout = new JMenu("  About  LMS   ");
		// mnbBar.setBackground(new Color(249,242,205));
		// mnAbout.setBackground(new Color(249,242,205));
		mnbBar.add(mnAbout);
		itAbout = new JMenuItem("  About  ");
		mnbBar.setBackground(new Color(249, 242, 205));
		mnAbout.setBackground(new Color(249, 242, 205));
		mnAbout.add(itAbout);
		itAbout.addActionListener(this);
		////////////////////////////////////////////
		/////////////////////////
		/// 대출예약 탭 시작
		DBView dbv = new DBView(this); // DBView 클래스의 인스턴스 생성
		this.dbv = dbv;

		rentalModel = new ExtendedDefaultTableModel(dbv.rentalData, dbv.rentalList);
		rentalTable = new JTable(rentalModel);
		rentalScrollPane = new JScrollPane(rentalTable);
		rentalScrollPane.setBackground(new Color(249, 242, 205));
		rentalTable.getTableHeader().setReorderingAllowed(false);
		rentalTable.getTableHeader().setResizingAllowed(false);
		rentalTable.setBackground(new Color(217, 230, 237));
		rentalTable.getTableHeader().setBackground(new Color(207, 203, 171));

		returnModel = new ExtendedDefaultTableModel(dbv.returnData, dbv.returnList);
		returnTable = new JTable(returnModel);
		returnScrollPane = new JScrollPane(returnTable);
		returnScrollPane.setBackground(new Color(249, 242, 205));
		returnTable.getTableHeader().setReorderingAllowed(false);
		returnTable.getTableHeader().setResizingAllowed(false);
		returnTable.setBackground(new Color(217, 230, 237));
		returnTable.getTableHeader().setBackground(new Color(207, 203, 171));

		rentalScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "대출 현황정보", TitledBorder.LEFT,
				TitledBorder.BELOW_TOP, MyFont.sans1, new Color(53, 57, 72)));
		returnScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "반납 현황정보", TitledBorder.LEFT,
				TitledBorder.BELOW_TOP, MyFont.sans1, new Color(53, 57, 72)));

		pntab4.add(rentalScrollPane);
		pntab4.add(returnScrollPane);

		initViewList(); // 초기 도서목록을 보여준다.
		//////////////////////////
		// 대출/예약 탭에 대출과 예약 리스트를 로드한다.
		//////////////////////////////
		dbv.initList(1);
		dbv.initList(2);

		pnCenter.add(tabpane, BorderLayout.CENTER);
		// c.add(pnTop, BorderLayout.NORTH);
		//////////////////
		// 컨테이너에 패널 등록
		///////////////////////
		c.add(pnCenter, BorderLayout.CENTER);
		c.add(pnBottom, BorderLayout.SOUTH);

		////////////
		// 프레임 설정
		/////////////////////
		setBounds(80, 50, 800, 600);
		setResizable(false);
		setVisible(true);
		// setLocation(200,0);
		// setSize(500,660);

	} // 폼 구성 완료

	//////////////////////////////////////////
	// 도서목록의 튜플갯수와 DB내용을 읽어들이기 위한 메서드
	//////////////////////////////////////////////////////
	public int initBookList() {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "select book_code from book order by book_code asc";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002"); // 연결을
																					// 한다.ID는
																					// 자신의
																					// 것,Passwd도
																					// 마찬가지

			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);

			while (rs1.next()) {
				count++;
			}
			rs1.close();

			aCode = new String[count];
			aName = new String[count];
			aBarcode = new String[count];

			sql = "select * from book order by book_code asc";
			rs1 = stat.executeQuery(sql);
			int cnum = 0;
			while (rs1.next()) {
				aCode[cnum] = rs1.getString(1);
				aName[cnum] = rs1.getString(2);
				aBarcode[cnum] = rs1.getString(3);

				cnum++;
			}

			rs1.close();
			stat.close();
			conn.close();
		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}

		return count;
	}

	///////////////////////////////////////////////////////////////////
	// 도서 목록을 보기 위한 메서드, 자료 갱신때마다 로드시켜 새로운값을 보여줌
	////////////////////////////////////////////////////////////////////
	public void initViewList() {

		int len = initBookList();
		lbrData = new String[len][4];
		for (int i = 0; i < len; i++) {
			lbrData[i][0] = aCode[i];
			lbrData[i][1] = aName[i];
			lbrData[i][2] = aBarcode[i];
			lbrData[i][3] = getBookState(aCode[i]);
		}
		tblModel = new ExtendedDefaultTableModel(lbrData, lbrList);
		table.setModel(tblModel);
		table.setShowGrid(true);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(1);

		// setView("도서 목록을 로드하였습니다.");

	}

	////////////////////////////////
	/// 현재 작업한 내용을 보여주는 메서드
	/////////////////////////////////////
	public void setView(String str) {
		taStatus.append(str);
		taStatus.append("\n================================================================"
				+ "==========================================\n");
	}

	//////////////////////////////////////
	// 도서 텍스트필드 데이타 셋팅
	///////////////////////////////////////////
	public void setData(String a, String b, String c) {
		tfbook_code.setText(a);
		tfBookName.setText(b);
		tfBookBarcode.setText(c);
	}

	////////////////////////////////////
	// DB연결 확인을 위한 테스트 메서드
	///////////////////////////////////////////
	public void connectDBTest() {
		// DB에 연결하는 구간

		try { // 드라이버를 load한다.
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버가 연결되지 않습니다." + e.getMessage());
		}
		try {
			java.util.Properties pro = new java.util.Properties();
			pro.put("encoding", "KSC5601");// 한글 처리가 가능하겠끔
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			// 연결을 한다.
			Connection con;
			con = DriverManager.getConnection(url, "root", "gkdl002");// 아이디와
																		// 패스워드는
																		// 설정하지
																		// 않음

			System.out.println("DATABASE에 연결 되었습니다.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	} // DB연결 확인 구간 종료

	//////////////////////////////////////////////////////////////////////////
	// 도서 추가나 도서코드의 중복성을 검사하는 메서드
	//
	///////////////////////////////////////////////////////////////////////////
	public boolean checkbook_code(String str) {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			if (!str.equals("")) {

				String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
				String sql = "";
				Connection conn = DriverManager.getConnection(url, "root", "gkdl002");

				sql = "select book_code from book where book_code ='" + str + "'";

				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while (rs1.next()) {
					count++;
				}
				setView("도서 코드 중복 갯수: " + count);

				rs1.close();
				stat.close();
				conn.close();
			}

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}
		if (count > 0) { // 아이디나 도서코드가 존재하면
			return true;
		} else // 아이디나 도서코드가 존재하지 않으면
			return false;
	}

	///////////////////////////////
	// 도서코드 인증 도서코드가 맞으면 true
	//////////////////////////////////
	public boolean lbrCodeauth(String str) {
		String id = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			if (!str.equals("")) {

				String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
				String sql = "";
				Connection conn = DriverManager.getConnection(url, "root", "gkdl002");
				sql = "select book_code from book where book_code ='" + str + "'";
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while (rs1.next()) {
					id = rs1.getString("book_code");
				}
				rs1.close();
				stat.close();
				conn.close();
			}

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}

		if (id.equals(str)) // 도서코드가 있음
			return true;
		else // 도서코드가 없음
			return false;
	}

	///////////////////////////////
	// 회원 인증 회원이 맞으면 true
	//////////////////////////////////
	public boolean auth(String str) {
		String customer_code = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			if (!str.equals("")) {

				String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
				String sql = "";
				Connection conn = DriverManager.getConnection(url, "root", "gkdl002");
				sql = "select customer_code from customer where customer_code ='" + str + "'";
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while (rs1.next()) {
					customer_code = rs1.getString("customer_code");
				}
				rs1.close();
				stat.close();
				conn.close();
			}

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}

		if (customer_code.equals(str)) // 회원임
			return true;
		else // 회원이 아님
			return false;
	}

	////////////////////////////////////////////////////
	// 도서부분에서 재대로 값이 입력되었는지 체크하는 메서드
	// codeNum - 도서추가:1, 삭제:2, 대출:3, 반납:4, 수정:5,
	//////////////////////////////////////////////////
	public boolean checkLibrary(int codeNum) {
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if (codeNum == 1) {
			if (checkbook_code(tfbook_code.getText())) { // 동일한 도서코드가 존재하면
				lblstr.setText("이미 중복된 도서코드가 존재합니다! 유일한 도서코드를 입력해주세요");
				JOptionPane.showMessageDialog(this, lblstr, "아이디 중복", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")) {
				lblstr.setText("빈 곳이 있습니다! 도서코드, 도서명, 바코드번호를 입력해주세요");
				JOptionPane.showMessageDialog(this, lblstr, "도서 추가 경고", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (tfbook_code.getText().length() != 4) {
				lblstr.setText("도서 코드는 책장번호 2자리 + 장르번호 2자리 총 4자리 입니다");
				JOptionPane.showMessageDialog(this, lblstr, "도서 추가 경고", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		else if (codeNum == 2) {
			if (tfbook_code.getText().equals("")) {
				lblstr.setText("도서코드를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(this, lblstr, "도서 코드 입력", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!lbrCodeauth(tfbook_code.getText().trim())) {
				lblstr.setText("도서코드가 정확하지 않습니다. 확인하시고, 시도해주세요");
				JOptionPane.showMessageDialog(this, lblstr, "도서코드 인증 실패", JOptionPane.INFORMATION_MESSAGE);
				setView("도서코드가 정확하지 않습니다. 확인하시고, 다시 시도해주세요!");
				return false;
			}
		} else if (codeNum == 3) {

			countBook cb = new countBook(); // countBook 클래스 인스턴스, 대출 도서 갯수를
											// 알아본다

			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")
					|| tfBookBarcode.getText().equals("") || tfrentalId.getText().equals("")) {
				lblstr.setText("도서코드, 도서명 , 바코드 번호 ,대여자 코드를 입력해주세요! 검색을 통해서 하시면 편리합니다");
				JOptionPane.showMessageDialog(this, lblstr, "도서 대출", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!auth(tfrentalId.getText().trim())) {
				lblstr.setText("인증에 실패하였습니다. 회원가입을 하시고, 이용해 주십시오!");
				JOptionPane.showMessageDialog(this, lblstr, "인증 실패", JOptionPane.INFORMATION_MESSAGE);
				setView("당신 누구야! 인증에 실패하였습니다.");
				return false;
			}

			else if (cb.OverThree(tfrentalId.getText())) { // 도서 대출갯수가 3개를 초과
															// 했으면
				lblstr.setText(tfrentalId.getText() + " 님은 이미 대출 도서 3개를 초과하셨습니다. 더이상 대출하실수 없습니다.");
				JOptionPane.showMessageDialog(this, lblstr, "해당 아이디 대출 불가능 ", JOptionPane.INFORMATION_MESSAGE);
				setView(tfrentalId.getText() + " 님은 이미 대출 도서 3개를 초과하셨습니다. 더이상 대출하실수 없습니다.");
				return false;
			}
		} else if (codeNum == 4) {
			if (tfbook_code.getText().equals("")) {
				lblstr.setText("도서코드를 입력해주세요! 검색을 통해 하시면 편리합니다.");
				JOptionPane.showMessageDialog(this, lblstr, "도서 반납", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!auth(tfrentalId.getText().trim())) {
				lblstr.setText("아이디가 잘못됐습니다. 그런 아이디는 존재하지 않습니다.");
				JOptionPane.showMessageDialog(this, lblstr, "대여자 아이디가 잘못됐습니다", JOptionPane.INFORMATION_MESSAGE);
				setView("아이디가 잘못됐습니다. 그런 아이디는 존재하지 않습니다.");
				return false;
			}
		} else if (codeNum == 5) {
			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")) {
				lblstr.setText("빈 곳이 있습니다! 도서코드, 도서명, 바코드번호를 입력해주세요");
				JOptionPane.showMessageDialog(this, lblstr, "도서 추가 경고", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}

	/////////////////////////////////////////
	// 대출 가능 여부 판단 거짓이면 대출할수 없음
	/////////////////////////////////////////////
	public boolean availablerentalId(String str) {

		// String id="", stop="", state="";
		// Label lblstr = new Label();
		// lblstr.setFont(MyFont.sans1);
		int count = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {

			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002");
			sql = "select id, stopday from policy where id ='" + str + " ' and state='불가능'";
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
			while (rs1.next()) {
				count++;
			}
			rs1.close();
			stat.close();
			conn.close();

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}
		if (count > 0) {
			return false;
		} else
			return true;
	}

	////////////////////////////////////
	// 이벤트처리 시작
	//////////////////////////////////////////////////
	public void actionPerformed(ActionEvent e) {

		// String command = e.getActionCommand();
		Object ob = e.getSource();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {

			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			Connection con = DriverManager.getConnection(url, "root", "gkdl002"); // 연결을
																					// 한다.ID는
																					// 자신의
																					// 것,Passwd도
																					// 마찬가지
			// System.out.println("DATABASE에 연결 되었습니다.") ;

			Statement stmt;

			lbrMember mem = new lbrMember(this); // 멤버 인스턴스 생성
			this.mem = mem;
			
			lbrProvider prov = new lbrProvider(this); // 멤버 인스턴스 생성
			this.prov = prov;
			
			
			// 회원 가입부분
			if (ob == btnMember) {
				mem.memberRegister();
			}

			// 회원 삭제부분
			else if (ob == btnDel) {
				mem.memberUnregister();
			}

			// 회원 검색부분
			else if (ob == btnSearch) {
				mem.memberSearch();
			}

			// 회원 수정
			else if (ob == btnMod) {
				mem.memberModify();
			}
			// 회원 초기화 부분
			else if (ob == btnClear) {

				tfId.setText("");
				tfName.setText("");
				tfbirthday.setText("");
				tfphone.setText("");
				tfaddress.setText("");
				tfcash.setText("");
				taStatus.setText("");
				setView("  모든 작업 그룹을 초기화 하였습니다.");
			}
			
			
			
			// 업체 입력부분
			else if( ob == btnProAdd)
			{
				prov.providerRegister();
				
			}
			else if( ob == btnProDel){
				prov.providerUnregister();
				
			}
			else if( ob == btnProSearch){
				prov.providerSearch();
			}
			else if( ob == btnProMod){
				prov.providerModify();
				
			}
			else if( ob == btnDeal){
				Provdeal pd = new Provdeal(this,this);
				
			}

			// 도서 입력 부분

			else if (ob == btnBuy) {
				BuyBook buybook = new BuyBook(this, this);
				
			}

			// 도서 삭제 부분
			else if (ob == btnBookDel) {
				String book_code = tfbook_code.getText();

				if (checkLibrary(2)) {
					stmt = con.createStatement();
					String sql = "delete from book where book_code = '" + book_code + "'";
					stmt.executeUpdate(sql);

					setView("도서 코드 : " + book_code + " 가 삭제 되었습니다.");
					tfbook_code.setText("");
					tfBookName.setText("");
					tfBookBarcode.setText("");

					tfrental.setText("반납시만 이용");
					tfReturn.setText("반납시만 이용");

					stmt.close();
					con.close();
				}
				initViewList(); // 도서목록에 다시 보여줌

			} // 도서 삭제 완료

			// 도서 대출 부분
			else if (ob == btnrental) {

				if (checkLibrary(3)) {
					// 현재 날짜 얻기
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date currentTime = new java.util.Date();
					String dateStr = formatter.format(currentTime);

					String rentaldate = dateStr; // 대출 날짜

					// 일주일후의 날짜 얻기
					Calendar rightNow = Calendar.getInstance();
					currentTime = rightNow.getTime();
					rightNow.add(rightNow.DATE, +7); // 일주일간의 대출기한
					currentTime = rightNow.getTime();
					dateStr = formatter.format(currentTime);

					String returndate = dateStr; // 반납 날짜

					String book_code = tfbook_code.getText();
					String book_name = tfBookName.getText();
					String rentalid = tfrentalId.getText();
					int rentcost = 2000;
					String hasbook = new String("");
					stmt = con.createStatement();
					String sql = "select book_code from book where book_code ='" + book_code + "'";
					ResultSet rs = stmt.executeQuery(sql);

					while (rs.next())
						hasbook = rs.getString(1);
					if (hasbook.equals("")) {
						Label lblstr = new Label();
						lblstr.setFont(MyFont.sans1);
						lblstr.setText("해당하는 도서코드의 도서가 없습니다.");
						JOptionPane.showMessageDialog(this, lblstr, "구비 도서가 없습니다.", JOptionPane.INFORMATION_MESSAGE);
						setView("  대출 이용불가 - 구비 도서가 없습니다. 후에 다시 이용해 주십시오!");
						return;
					} else {
						sql = "insert into rental values ('" + rentalid + "','" + book_code + "','" + rentaldate + "','"
								+ returndate + "','" + rentcost + "')";
						stmt.executeUpdate(sql);

						setView("  대여자 : " + rentalid + "\t도서명 : " + book_name + "\t" + "\t도서코드 : " + book_code + "\t"
								+ "대여일자 : " + rentaldate + "\t반납일자 : " + returndate + "\t연체료: " + rentcost
								+ "\n  해당 도서가 성공적으로 대여 되었습니다!");

						setView("  도서 목록의 해당 도서를 대출중으로 표기하였습니다.");

					}

					tfbook_code.setText(book_code);
					tfBookName.setText(book_name);

					tfrental.setText(rentaldate);
					tfReturn.setText(returndate);
					stmt.close();
					con.close();
				}
				initViewList(); // 도서목록에 다시 보여줌
				dbv.initList(1); // 대출 현황 재로드
			} // 도서 대출 완료

			// 도서 반납 부분
			else if (ob == btnReturn) {

				if (checkLibrary(4)) {
					String book_code = tfbook_code.getText();
					String rentalid = "";
					String rentaldate = ""; // 대출일
					String returndate = ""; // 예정 반납일
					String creturndate; // 반납일자
					String bookname = "";

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date currentTime = new java.util.Date();
					String dateStr = formatter.format(currentTime);

					creturndate = dateStr; // 반납일자

					stmt = con.createStatement();
					String sql = "select book_code, rental_date, rental_returndate,customer_code from rental where book_code = '"
							+ book_code + "'";
					ResultSet rs = stmt.executeQuery(sql);

					while (rs.next()) {
						book_code = rs.getString(1);
						rentaldate = rs.getString(2);
						returndate = rs.getString(3);
						rentalid = rs.getString("customer_code");

					}
					rs.close();

					sql = "delete from rental where book_code = '" + book_code + "' and customer_code= '" + rentalid
							+ "'";
					stmt.executeUpdate(sql);

					sql = "insert into `return` values ('" + rentalid + "','" + book_code + "'," + creturndate + ")";
					stmt.executeUpdate(sql);

					setView("도서 코드 : " + book_code + "\t" + "도서명 : " + bookname + "\t대출일자: " + rentaldate + "\t예정반납일자: "
							+ returndate + "\t반납일자 : " + creturndate + "\t" + "대여자 : " + rentalid
							+ "\n  위와 같이 반납 되었습니다!");

					tfbook_code.setText(book_code);
					tfBookName.setText(bookname);// tfWriter.setText("");

					tfrental.setText(rentaldate);
					tfReturn.setText(returndate);

					// rs.close();
					stmt.close();
					con.close();
				}
				initViewList(); // 도서목록에 다시 보여줌
				dbv.initList(1); // 대출 현황 재로드

			} // 도서 반납 완료
				// 대해서
			else if (ob == itAbout) {
				Label[] lblAbout;

				Box b = Box.createVerticalBox();
				lblAbout = new Label[2];

				lblAbout[0] = new Label("     빅뱅 도서대여 프로그램           ");
				lblAbout[1] = new Label("     YourSQL");

				for (int i = 0; i < lblAbout.length; i++) {
					b.add(lblAbout[i]);
				}
				JOptionPane.showMessageDialog(this, b, "About", JOptionPane.INFORMATION_MESSAGE);

			}

			// 도서 검색 부분

			else if (ob == btnBookSearch) {

				searchBook srhbook = new searchBook(this, this);
			}
			/// 도서 수정부분
			else if (ob == btnInsert) {

				String code = tfbook_code.getText();
				String name = tfBookName.getText();
				String barcode = tfBookBarcode.getText();

				if (checkLibrary(5)) {

					stmt = con.createStatement();
					String sql = "update book set book_name='" + name + "'  where book_code='" + code + "'";
					stmt.executeUpdate(sql);
					sql = "update book set book_barcode='" + barcode + "'  where book_code='" + code + "'";
					stmt.executeUpdate(sql);

					setView("  도서코드\t도서명\t바코드번호");
					setView("  " + code + "\t" + name + "\t" + barcode + "\n  성공적으로 위와 같이 수정하였습니다");
					tfbook_code.setText("");
					tfBookName.setText("");
					tfBookBarcode.setText("");

					tfrental.setText("반납시만 이용");
					tfReturn.setText("반납시만 이용");
					stmt.close();
					con.close();
				}
				initViewList(); // 도서목록에 다시 보여줌
			} // 도서 수정 완료

			// 도서 부분 초기화 버튼
			else if (ob == btnInit) {
				tfbook_code.setText("");
				tfBookName.setText("");

				tfrental.setText("반납시만 이용");
				tfReturn.setText("반납시만 이용");
				tfrentalId.setText("");
				taStatus.setText("");
				setView("모든 작업 그룹을 초기화 하였습니다.");
				// 테스트

			}

		} catch (Exception ea) {
			ea.printStackTrace();
			setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  " + ea);
		}
	}

	public String getBookName(String book_code) {
		String book_name = new String();
		int len = initBookList();
		for (int i = 0; i < len; i++) {
			if (lbrData[i][0].equals(book_code)) {
				book_name = lbrData[i][1];
			}
		}

		return book_name;
	}

	public String getBookState(String book_code) {
		String book_state = new String("");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002");

			sql = "select book_code from rental";

			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);

			while (rs1.next()) {
				if (rs1.getString(1).equals(book_code)) {
					book_state = "대출중";
				}
			}
			if (book_state.equals("")) {
				book_state = "비치중";
			}
			rs1.close();
			stat.close();
			conn.close();

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}
		return book_state;
	}

}
