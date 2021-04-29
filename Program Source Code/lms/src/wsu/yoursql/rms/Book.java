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
	
	

	//////// book ������
	private JLabel lblbook_code, lblbookname, lblbookrental, lblbookback, lblrental_id, lblbookbarcode;
	private JButton btnBuy, btnBookDel, btnrental, btnReturn, btnBookSearch, btnInsert, btnInit;
	private JTextField tfbook_code, tfBookName, tfBookBarcode, tfrental, tfReturn, tfrentalId;
	//////// lbrMember Ŭ�������� ����
	protected JLabel lblid, lblname, lblcode, lblphone, lbladdress, lblcash;
	protected JButton btnMember, btnDel, btnSearch, btnMod, btnClear;

	/////// ��ü ������
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
	private String lbrList[] = { "�����ڵ�", "������", "���� ���ڵ�", "��������" };
	private String lbrData[][] = {};

	private String ComList[] = { "��ü�ڵ�", "��ü��", "�ְŷ� ����", "���¹�ȣ", "����� �̸�", "����� ����ó" };
	private String ComData[][] = {};
	private String[] aCode, aName, aBarcode;

	private lbrMember mem;
	private lbrProvider prov;
	
	
	private DBView dbv;


	Book() {

		super("��� ���� �뿩 �ý���");
		Container c = getContentPane();
		c.setLayout(new BorderLayout(2, 1));
		c.setBackground(Color.white);

		/////////////////////
		// ���� ������Ʈ ����
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
		/// ������� �� ����

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
		// ������� �� ��
		////////////////////
		// �������� ����
		/////////////
		lblbook_code = new JLabel("�����ڵ� :        ");
		lblbookname = new JLabel("�� �� �� :          ");
		lblbookbarcode = new JLabel("���ڵ��ȣ :     ");
		lblbookrental = new JLabel("�� �� �� :          ");
		lblbookback = new JLabel("�� �� �� :          ");
		lblrental_id = new JLabel("�� �� �� �� �� :          ");

		lblbook_code.setFont(MyFont.sans1);
		lblbookname.setFont(MyFont.sans1);
		lblbookbarcode.setFont(MyFont.sans1);
		lblbookrental.setFont(MyFont.sans1);
		lblbookback.setFont(MyFont.sans1);
		lblrental_id.setFont(MyFont.sans1);

		/////////////////////
		// �ؽ�Ʈ �ʵ� ����
		//////////////////////////////
		tfbook_code = new JTextField(40);
		tfBookName = new JTextField(40);
		tfBookBarcode = new JTextField(40);
		tfrental = new JTextField("�ݳ��ø� �̿�", 40);
		tfrental.setEditable(false); // �ý����� �˾Ƽ� �ϰԲ�
		tfReturn = new JTextField("�ݳ��ø� �̿�", 40);
		tfReturn.setEditable(false);
		tfrentalId = new JTextField(40);

		tfbook_code.setBackground(new Color(252, 251, 217));
		tfBookName.setBackground(new Color(252, 251, 217));
		tfBookBarcode.setBackground(new Color(252, 251, 217));
		tfrental.setBackground(new Color(252, 251, 200));
		tfReturn.setBackground(new Color(252, 251, 200));
		tfrentalId.setBackground(new Color(252, 251, 217));

		/////////////////
		// ��ư ����
		//////////////////////////
		btnBuy = new JButton("��  ��");
		btnBookDel = new JButton("��  ��");
		btnrental = new JButton("��  ��");
		btnReturn = new JButton("��  ��");
		btnBookSearch = new JButton("��  ��");
		btnInsert = new JButton("��  ��");
		btnInit = new JButton("��  ��");

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
		// ��ο� ���
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
		// �����ʿ� ���
		//////////////////
		btnBuy.addActionListener(this);
		btnBookDel.addActionListener(this);
		btnrental.addActionListener(this);
		btnReturn.addActionListener(this);
		btnBookSearch.addActionListener(this);
		btnInsert.addActionListener(this);
		btnInit.addActionListener(this);
		/// ���� ���� ��

		////////////////////////////////////////////////
		// ȸ������ ����
		lblid = new JLabel("\t ȸ �� �� �� :        ");
		lblname = new JLabel("\t ȸ �� �� �� :        ");
		lblcode = new JLabel("\t �� �� �� �� :        ");
		lblphone = new JLabel("\t ��   ��   ó :        ");
		lbladdress = new JLabel("\t ��         �� :        ");
		lblcash = new JLabel("\t �� �� �� �� :        ");

		lblid.setFont(MyFont.sans1);
		lblname.setFont(MyFont.sans1);
		lblcode.setFont(MyFont.sans1);
		lblphone.setFont(MyFont.sans1);
		lbladdress.setFont(MyFont.sans1);
		lblcash.setFont(MyFont.sans1);

		tfId = new JTextField("���Խÿ� �������", 40);
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

		btnMember = new JButton("ȸ������");
		btnDel = new JButton("ȸ������");
		btnSearch = new JButton("ȸ���˻�");
		btnMod = new JButton("ȸ������");
		btnClear = new JButton("�Է¸���");

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
		// �гο� ������Ʈ ���
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
				new Insets(0, 0, 0, 0), 3, 5)); // ���� ����
		pntab3.add(tfcash, new GridBagConstraints(1, 5, 3, 1, 3, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5)); // ���� ����

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

		// ȸ������ ��
		//////////////////////////////
		// ��ü���� ����
		lblpro_Code = new Label("�� ü �� �� : ");
		lblpro_name = new Label("��   ü   �� : ");
		lblpro_bank = new Label("�� �� �� �� �� : ");
		lblpro_acc = new Label("�� �� �� ȣ : ");
		lblpro_manager = new Label("����� �̸� : ");
		lblpro_phone = new Label("����� ����ó : ");

		tfpro_Code = new JTextField("���Խÿ� �������",40);
		tfpro_name = new JTextField(40);
		tfpro_bank = new JTextField(40);
		tfpro_acc = new JTextField(40);
		tfpro_manager = new JTextField(40);
		tfpro_phone = new JTextField(40);

		btnProAdd = new JButton("��ü�߰�");
		btnProDel = new JButton("��ü����");
		btnProMod = new JButton("��ü����");
		btnProSearch = new JButton("��ü�˻�");
		btnDeal = new JButton("������Ȳ");

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

		
		
		
		//��ü������
		/////////////////////////////////////////
		icon = new ImageIcon("./image/room.gif");
		tabpane.addTab("���� ���", icon, pntab1);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/with.gif");
		tabpane.addTab("���� ����", icon, pntab2);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/one.gif");
		tabpane.addTab("ȸ�� ����", icon, pntab3);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/view.gif");
		tabpane.addTab("���� ��Ȳ", icon, pntab4);
		tabpane.setFont(MyFont.sans1);

		icon = new ImageIcon("./image/view.gif");
		tabpane.addTab("��ü ����", icon, pntab5);
		tabpane.setFont(MyFont.sans1);

		taStatus = new JTextArea(10, 10);
		taStatus.setBackground(new Color(252, 251, 217));
		taStatus.setEditable(false);
		taStatus.setLineWrap(true);
		taStatus.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(taStatus);
		pnBottom.add(sp);
		pnBottom.setBorder(
				new TitledBorder(new EtchedBorder(), "�۾� ����", TitledBorder.LEFT, TitledBorder.TOP, MyFont.sans1));

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
		/// ���⿹�� �� ����
		DBView dbv = new DBView(this); // DBView Ŭ������ �ν��Ͻ� ����
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

		rentalScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "���� ��Ȳ����", TitledBorder.LEFT,
				TitledBorder.BELOW_TOP, MyFont.sans1, new Color(53, 57, 72)));
		returnScrollPane.setBorder(new TitledBorder(new EtchedBorder(), "�ݳ� ��Ȳ����", TitledBorder.LEFT,
				TitledBorder.BELOW_TOP, MyFont.sans1, new Color(53, 57, 72)));

		pntab4.add(rentalScrollPane);
		pntab4.add(returnScrollPane);

		initViewList(); // �ʱ� ��������� �����ش�.
		//////////////////////////
		// ����/���� �ǿ� ����� ���� ����Ʈ�� �ε��Ѵ�.
		//////////////////////////////
		dbv.initList(1);
		dbv.initList(2);

		pnCenter.add(tabpane, BorderLayout.CENTER);
		// c.add(pnTop, BorderLayout.NORTH);
		//////////////////
		// �����̳ʿ� �г� ���
		///////////////////////
		c.add(pnCenter, BorderLayout.CENTER);
		c.add(pnBottom, BorderLayout.SOUTH);

		////////////
		// ������ ����
		/////////////////////
		setBounds(80, 50, 800, 600);
		setResizable(false);
		setVisible(true);
		// setLocation(200,0);
		// setSize(500,660);

	} // �� ���� �Ϸ�

	//////////////////////////////////////////
	// ��������� Ʃ�ð����� DB������ �о���̱� ���� �޼���
	//////////////////////////////////////////////////////
	public int initBookList() {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
		}

		try {
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "select book_code from book order by book_code asc";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002"); // ������
																					// �Ѵ�.ID��
																					// �ڽ���
																					// ��,Passwd��
																					// ��������

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
	// ���� ����� ���� ���� �޼���, �ڷ� ���Ŷ����� �ε���� ���ο�� ������
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

		// setView("���� ����� �ε��Ͽ����ϴ�.");

	}

	////////////////////////////////
	/// ���� �۾��� ������ �����ִ� �޼���
	/////////////////////////////////////
	public void setView(String str) {
		taStatus.append(str);
		taStatus.append("\n================================================================"
				+ "==========================================\n");
	}

	//////////////////////////////////////
	// ���� �ؽ�Ʈ�ʵ� ����Ÿ ����
	///////////////////////////////////////////
	public void setData(String a, String b, String c) {
		tfbook_code.setText(a);
		tfBookName.setText(b);
		tfBookBarcode.setText(c);
	}

	////////////////////////////////////
	// DB���� Ȯ���� ���� �׽�Ʈ �޼���
	///////////////////////////////////////////
	public void connectDBTest() {
		// DB�� �����ϴ� ����

		try { // ����̹��� load�Ѵ�.
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + e.getMessage());
		}
		try {
			java.util.Properties pro = new java.util.Properties();
			pro.put("encoding", "KSC5601");// �ѱ� ó���� �����ϰڲ�
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			// ������ �Ѵ�.
			Connection con;
			con = DriverManager.getConnection(url, "root", "gkdl002");// ���̵��
																		// �н������
																		// ��������
																		// ����

			System.out.println("DATABASE�� ���� �Ǿ����ϴ�.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	} // DB���� Ȯ�� ���� ����

	//////////////////////////////////////////////////////////////////////////
	// ���� �߰��� �����ڵ��� �ߺ����� �˻��ϴ� �޼���
	//
	///////////////////////////////////////////////////////////////////////////
	public boolean checkbook_code(String str) {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
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
				setView("���� �ڵ� �ߺ� ����: " + count);

				rs1.close();
				stat.close();
				conn.close();
			}

		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}
		if (count > 0) { // ���̵� �����ڵ尡 �����ϸ�
			return true;
		} else // ���̵� �����ڵ尡 �������� ������
			return false;
	}

	///////////////////////////////
	// �����ڵ� ���� �����ڵ尡 ������ true
	//////////////////////////////////
	public boolean lbrCodeauth(String str) {
		String id = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
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

		if (id.equals(str)) // �����ڵ尡 ����
			return true;
		else // �����ڵ尡 ����
			return false;
	}

	///////////////////////////////
	// ȸ�� ���� ȸ���� ������ true
	//////////////////////////////////
	public boolean auth(String str) {
		String customer_code = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
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

		if (customer_code.equals(str)) // ȸ����
			return true;
		else // ȸ���� �ƴ�
			return false;
	}

	////////////////////////////////////////////////////
	// �����κп��� ���� ���� �ԷµǾ����� üũ�ϴ� �޼���
	// codeNum - �����߰�:1, ����:2, ����:3, �ݳ�:4, ����:5,
	//////////////////////////////////////////////////
	public boolean checkLibrary(int codeNum) {
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if (codeNum == 1) {
			if (checkbook_code(tfbook_code.getText())) { // ������ �����ڵ尡 �����ϸ�
				lblstr.setText("�̹� �ߺ��� �����ڵ尡 �����մϴ�! ������ �����ڵ带 �Է����ּ���");
				JOptionPane.showMessageDialog(this, lblstr, "���̵� �ߺ�", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")) {
				lblstr.setText("�� ���� �ֽ��ϴ�! �����ڵ�, ������, ���ڵ��ȣ�� �Է����ּ���");
				JOptionPane.showMessageDialog(this, lblstr, "���� �߰� ���", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (tfbook_code.getText().length() != 4) {
				lblstr.setText("���� �ڵ�� å���ȣ 2�ڸ� + �帣��ȣ 2�ڸ� �� 4�ڸ� �Դϴ�");
				JOptionPane.showMessageDialog(this, lblstr, "���� �߰� ���", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		else if (codeNum == 2) {
			if (tfbook_code.getText().equals("")) {
				lblstr.setText("�����ڵ带 �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(this, lblstr, "���� �ڵ� �Է�", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!lbrCodeauth(tfbook_code.getText().trim())) {
				lblstr.setText("�����ڵ尡 ��Ȯ���� �ʽ��ϴ�. Ȯ���Ͻð�, �õ����ּ���");
				JOptionPane.showMessageDialog(this, lblstr, "�����ڵ� ���� ����", JOptionPane.INFORMATION_MESSAGE);
				setView("�����ڵ尡 ��Ȯ���� �ʽ��ϴ�. Ȯ���Ͻð�, �ٽ� �õ����ּ���!");
				return false;
			}
		} else if (codeNum == 3) {

			countBook cb = new countBook(); // countBook Ŭ���� �ν��Ͻ�, ���� ���� ������
											// �˾ƺ���

			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")
					|| tfBookBarcode.getText().equals("") || tfrentalId.getText().equals("")) {
				lblstr.setText("�����ڵ�, ������ , ���ڵ� ��ȣ ,�뿩�� �ڵ带 �Է����ּ���! �˻��� ���ؼ� �Ͻø� �����մϴ�");
				JOptionPane.showMessageDialog(this, lblstr, "���� ����", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!auth(tfrentalId.getText().trim())) {
				lblstr.setText("������ �����Ͽ����ϴ�. ȸ�������� �Ͻð�, �̿��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(this, lblstr, "���� ����", JOptionPane.INFORMATION_MESSAGE);
				setView("��� ������! ������ �����Ͽ����ϴ�.");
				return false;
			}

			else if (cb.OverThree(tfrentalId.getText())) { // ���� ���ⰹ���� 3���� �ʰ�
															// ������
				lblstr.setText(tfrentalId.getText() + " ���� �̹� ���� ���� 3���� �ʰ��ϼ̽��ϴ�. ���̻� �����ϽǼ� �����ϴ�.");
				JOptionPane.showMessageDialog(this, lblstr, "�ش� ���̵� ���� �Ұ��� ", JOptionPane.INFORMATION_MESSAGE);
				setView(tfrentalId.getText() + " ���� �̹� ���� ���� 3���� �ʰ��ϼ̽��ϴ�. ���̻� �����ϽǼ� �����ϴ�.");
				return false;
			}
		} else if (codeNum == 4) {
			if (tfbook_code.getText().equals("")) {
				lblstr.setText("�����ڵ带 �Է����ּ���! �˻��� ���� �Ͻø� �����մϴ�.");
				JOptionPane.showMessageDialog(this, lblstr, "���� �ݳ�", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (!auth(tfrentalId.getText().trim())) {
				lblstr.setText("���̵� �߸��ƽ��ϴ�. �׷� ���̵�� �������� �ʽ��ϴ�.");
				JOptionPane.showMessageDialog(this, lblstr, "�뿩�� ���̵� �߸��ƽ��ϴ�", JOptionPane.INFORMATION_MESSAGE);
				setView("���̵� �߸��ƽ��ϴ�. �׷� ���̵�� �������� �ʽ��ϴ�.");
				return false;
			}
		} else if (codeNum == 5) {
			if (tfbook_code.getText().equals("") || tfBookName.getText().equals("")) {
				lblstr.setText("�� ���� �ֽ��ϴ�! �����ڵ�, ������, ���ڵ��ȣ�� �Է����ּ���");
				JOptionPane.showMessageDialog(this, lblstr, "���� �߰� ���", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}

	/////////////////////////////////////////
	// ���� ���� ���� �Ǵ� �����̸� �����Ҽ� ����
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
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
		}

		try {

			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002");
			sql = "select id, stopday from policy where id ='" + str + " ' and state='�Ұ���'";
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
	// �̺�Ʈó�� ����
	//////////////////////////////////////////////////
	public void actionPerformed(ActionEvent e) {

		// String command = e.getActionCommand();
		Object ob = e.getSource();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
		}

		try {

			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			Connection con = DriverManager.getConnection(url, "root", "gkdl002"); // ������
																					// �Ѵ�.ID��
																					// �ڽ���
																					// ��,Passwd��
																					// ��������
			// System.out.println("DATABASE�� ���� �Ǿ����ϴ�.") ;

			Statement stmt;

			lbrMember mem = new lbrMember(this); // ��� �ν��Ͻ� ����
			this.mem = mem;
			
			lbrProvider prov = new lbrProvider(this); // ��� �ν��Ͻ� ����
			this.prov = prov;
			
			
			// ȸ�� ���Ժκ�
			if (ob == btnMember) {
				mem.memberRegister();
			}

			// ȸ�� �����κ�
			else if (ob == btnDel) {
				mem.memberUnregister();
			}

			// ȸ�� �˻��κ�
			else if (ob == btnSearch) {
				mem.memberSearch();
			}

			// ȸ�� ����
			else if (ob == btnMod) {
				mem.memberModify();
			}
			// ȸ�� �ʱ�ȭ �κ�
			else if (ob == btnClear) {

				tfId.setText("");
				tfName.setText("");
				tfbirthday.setText("");
				tfphone.setText("");
				tfaddress.setText("");
				tfcash.setText("");
				taStatus.setText("");
				setView("  ��� �۾� �׷��� �ʱ�ȭ �Ͽ����ϴ�.");
			}
			
			
			
			// ��ü �Էºκ�
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

			// ���� �Է� �κ�

			else if (ob == btnBuy) {
				BuyBook buybook = new BuyBook(this, this);
				
			}

			// ���� ���� �κ�
			else if (ob == btnBookDel) {
				String book_code = tfbook_code.getText();

				if (checkLibrary(2)) {
					stmt = con.createStatement();
					String sql = "delete from book where book_code = '" + book_code + "'";
					stmt.executeUpdate(sql);

					setView("���� �ڵ� : " + book_code + " �� ���� �Ǿ����ϴ�.");
					tfbook_code.setText("");
					tfBookName.setText("");
					tfBookBarcode.setText("");

					tfrental.setText("�ݳ��ø� �̿�");
					tfReturn.setText("�ݳ��ø� �̿�");

					stmt.close();
					con.close();
				}
				initViewList(); // ������Ͽ� �ٽ� ������

			} // ���� ���� �Ϸ�

			// ���� ���� �κ�
			else if (ob == btnrental) {

				if (checkLibrary(3)) {
					// ���� ��¥ ���
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date currentTime = new java.util.Date();
					String dateStr = formatter.format(currentTime);

					String rentaldate = dateStr; // ���� ��¥

					// ���������� ��¥ ���
					Calendar rightNow = Calendar.getInstance();
					currentTime = rightNow.getTime();
					rightNow.add(rightNow.DATE, +7); // �����ϰ��� �������
					currentTime = rightNow.getTime();
					dateStr = formatter.format(currentTime);

					String returndate = dateStr; // �ݳ� ��¥

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
						lblstr.setText("�ش��ϴ� �����ڵ��� ������ �����ϴ�.");
						JOptionPane.showMessageDialog(this, lblstr, "���� ������ �����ϴ�.", JOptionPane.INFORMATION_MESSAGE);
						setView("  ���� �̿�Ұ� - ���� ������ �����ϴ�. �Ŀ� �ٽ� �̿��� �ֽʽÿ�!");
						return;
					} else {
						sql = "insert into rental values ('" + rentalid + "','" + book_code + "','" + rentaldate + "','"
								+ returndate + "','" + rentcost + "')";
						stmt.executeUpdate(sql);

						setView("  �뿩�� : " + rentalid + "\t������ : " + book_name + "\t" + "\t�����ڵ� : " + book_code + "\t"
								+ "�뿩���� : " + rentaldate + "\t�ݳ����� : " + returndate + "\t��ü��: " + rentcost
								+ "\n  �ش� ������ ���������� �뿩 �Ǿ����ϴ�!");

						setView("  ���� ����� �ش� ������ ���������� ǥ���Ͽ����ϴ�.");

					}

					tfbook_code.setText(book_code);
					tfBookName.setText(book_name);

					tfrental.setText(rentaldate);
					tfReturn.setText(returndate);
					stmt.close();
					con.close();
				}
				initViewList(); // ������Ͽ� �ٽ� ������
				dbv.initList(1); // ���� ��Ȳ ��ε�
			} // ���� ���� �Ϸ�

			// ���� �ݳ� �κ�
			else if (ob == btnReturn) {

				if (checkLibrary(4)) {
					String book_code = tfbook_code.getText();
					String rentalid = "";
					String rentaldate = ""; // ������
					String returndate = ""; // ���� �ݳ���
					String creturndate; // �ݳ�����
					String bookname = "";

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date currentTime = new java.util.Date();
					String dateStr = formatter.format(currentTime);

					creturndate = dateStr; // �ݳ�����

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

					setView("���� �ڵ� : " + book_code + "\t" + "������ : " + bookname + "\t��������: " + rentaldate + "\t�����ݳ�����: "
							+ returndate + "\t�ݳ����� : " + creturndate + "\t" + "�뿩�� : " + rentalid
							+ "\n  ���� ���� �ݳ� �Ǿ����ϴ�!");

					tfbook_code.setText(book_code);
					tfBookName.setText(bookname);// tfWriter.setText("");

					tfrental.setText(rentaldate);
					tfReturn.setText(returndate);

					// rs.close();
					stmt.close();
					con.close();
				}
				initViewList(); // ������Ͽ� �ٽ� ������
				dbv.initList(1); // ���� ��Ȳ ��ε�

			} // ���� �ݳ� �Ϸ�
				// ���ؼ�
			else if (ob == itAbout) {
				Label[] lblAbout;

				Box b = Box.createVerticalBox();
				lblAbout = new Label[2];

				lblAbout[0] = new Label("     ��� �����뿩 ���α׷�           ");
				lblAbout[1] = new Label("     YourSQL");

				for (int i = 0; i < lblAbout.length; i++) {
					b.add(lblAbout[i]);
				}
				JOptionPane.showMessageDialog(this, b, "About", JOptionPane.INFORMATION_MESSAGE);

			}

			// ���� �˻� �κ�

			else if (ob == btnBookSearch) {

				searchBook srhbook = new searchBook(this, this);
			}
			/// ���� �����κ�
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

					setView("  �����ڵ�\t������\t���ڵ��ȣ");
					setView("  " + code + "\t" + name + "\t" + barcode + "\n  ���������� ���� ���� �����Ͽ����ϴ�");
					tfbook_code.setText("");
					tfBookName.setText("");
					tfBookBarcode.setText("");

					tfrental.setText("�ݳ��ø� �̿�");
					tfReturn.setText("�ݳ��ø� �̿�");
					stmt.close();
					con.close();
				}
				initViewList(); // ������Ͽ� �ٽ� ������
			} // ���� ���� �Ϸ�

			// ���� �κ� �ʱ�ȭ ��ư
			else if (ob == btnInit) {
				tfbook_code.setText("");
				tfBookName.setText("");

				tfrental.setText("�ݳ��ø� �̿�");
				tfReturn.setText("�ݳ��ø� �̿�");
				tfrentalId.setText("");
				taStatus.setText("");
				setView("��� �۾� �׷��� �ʱ�ȭ �Ͽ����ϴ�.");
				// �׽�Ʈ

			}

		} catch (Exception ea) {
			ea.printStackTrace();
			setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  " + ea);
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
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
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
					book_state = "������";
				}
			}
			if (book_state.equals("")) {
				book_state = "��ġ��";
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