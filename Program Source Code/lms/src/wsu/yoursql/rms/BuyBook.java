package wsu.yoursql.rms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class BuyBook extends JDialog implements ActionListener, MouseListener {
	private JPanel pnTop, pnBottom;
	private JTable tblList;
	private JButton btnOk, btnCancel;
	private Label lblcom,  lblname, lblbarcode, lblprice ,lblbooknum;
	private JTextField tfcom,  tfname, tfbarcode, tfprice,tfbooknum;

	private Book bk;
	private ExtendedDefaultTableModel Model;
	private String colName[] = { "������","��ü�ڵ�","�����ڵ�", "������", "���ڵ��ȣ", "���űݾ�" };
	private String srhData[][] = {};
	private int size = 0; // DB���� �ҷ��� String �迭 ũ��
	private int len = 0; // Table List�� �����ֱ� ���� ũ�ⱸ��
	private String[] bDate,bProv_code,bBook_code, bBook_name, bBarcode,bPrice;// DB���� �ҷ��� ����Ÿ�� �����ϱ� ���ؼ�

	BuyBook(Book bk, JFrame f) {
		
		super(f, "���� ���� �޴�", true);
		this.bk = bk;

		Container c = getContentPane();
		c.setLayout(new BorderLayout(3, 2));

		Model = new ExtendedDefaultTableModel(srhData, colName);
		tblList = new JTable(Model);
		JScrollPane scrollPane = new JScrollPane(tblList);
		tblList.getTableHeader().setReorderingAllowed(false);
		tblList.getTableHeader().setResizingAllowed(false);
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "���Ե� ��������", TitledBorder.LEFT, TitledBorder.BELOW_TOP,
				MyFont.sans1, new Color(53, 57, 72)));

		btnOk = new JButton("��  ��");
		btnCancel = new JButton("��  ��");

		lblcom = new Label("�� ü �� ��");
		lblname = new Label("��   ��   ��");
		lblbooknum = new Label("�����帣��ȣ");
		lblbarcode = new Label("�� �� �� �� ȣ");
		lblprice = new Label("�� �� �� ��");

		tfcom = new JTextField(20);
		tfname = new JTextField(20);
		tfbooknum= new JTextField(20);
		tfbarcode = new JTextField(20);
		tfprice = new JTextField(20);

		btnOk.setBackground(new Color(170, 181, 221));
		btnCancel.setBackground(new Color(170, 181, 221));

		tfcom.setBackground(new Color(244, 252, 243));
		tfname.setBackground(new Color(244, 252, 243));
		tfbooknum.setBackground(new Color(244, 252, 243));
		tfbarcode.setBackground(new Color(244, 252, 243));
		tfprice.setBackground(new Color(244, 252, 243));

		pnTop = new JPanel(new BorderLayout(5, 3));
		pnBottom = new JPanel(new GridBagLayout());

		pnTop.setBackground(new Color(217, 230, 237));
		pnBottom.setBackground(new Color(217, 230, 237));
		scrollPane.setBackground(new Color(217, 230, 237));
		tblList.setBackground(new Color(217, 230, 237));
		tblList.getTableHeader().setBackground(new Color(136, 146, 139));
		tblList.getTableHeader().setForeground(Color.white);

		tblList.getColumnModel().getColumn(0).setPreferredWidth(20);
		tblList.getColumnModel().getColumn(1).setPreferredWidth(30);
		tblList.getColumnModel().getColumn(2).setPreferredWidth(30);
		tblList.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblList.getColumnModel().getColumn(4).setPreferredWidth(20);
		tblList.getColumnModel().getColumn(5).setPreferredWidth(20);
		

		tblList.setPreferredScrollableViewportSize(new Dimension(400, 70));

		pnBottom.add(lblcom, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pnBottom.add(tfcom, new GridBagConstraints(3, 0, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));
		
		pnBottom.add(lblbooknum, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pnBottom.add(tfbooknum, new GridBagConstraints(3, 1, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));
		
		
		pnBottom.add(lblname, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pnBottom.add(tfname, new GridBagConstraints(3, 2, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pnBottom.add(lblbarcode, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pnBottom.add(tfbarcode, new GridBagConstraints(3, 3, 5, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));

		pnBottom.add(lblprice, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 3, 5));
		pnBottom.add(tfprice, new GridBagConstraints(3, 4, 5, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 3, 5));

		pnBottom.add(btnOk, new GridBagConstraints(2, 5, 2, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(20, 10, 15, 0), 3, 5));
		pnBottom.add(btnCancel, new GridBagConstraints(4, 5, 2, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(20, 10, 15, 0), 3, 5));

		pnTop.add(scrollPane, BorderLayout.CENTER);
		c.add(pnTop, BorderLayout.CENTER);
		c.add(pnBottom, BorderLayout.SOUTH);

		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);

		PrintList();
		
		setBounds(150, 300, 600, 600);
		setVisible(true);

	}

	

	public void PrintList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("����̹��� ������� �ʽ��ϴ�." + ex.getMessage());
		}

		try {
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql="",sql2 ="";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002"); // ������
																					// �Ѵ�.ID��
																					// �ڽ���
																					// ��,Passwd��
																					// ��������

			size = bk.initBookList();
			
			/* DB���� �ҷ����� ����Ÿ���� �迭 ���� */
			bDate = new String[size];
			bProv_code = new String[size];
			bBook_code = new String[size];
			bBook_name = new String[size];
			bBarcode = new String[size];
			bPrice = new String[size];
			
			
			
			sql = "select * from book ";
			sql2 = "select * from buy ";
			
		

			Statement stat = conn.createStatement();
			Statement stat2 = conn.createStatement();
			
			ResultSet rs1 = stat.executeQuery(sql);
			ResultSet rs2 = stat2.executeQuery(sql2);
			
			int tnum = 0;
			// book DB�κ��� ������ ��� �����ϱ�
			while (rs1.next()) {
				bBook_code[tnum] = rs1.getString(1);
				bBook_name[tnum] = rs1.getString(2);
				bBarcode[tnum] = rs1.getString(3);
				
				
				tnum++;
			}

			
			tnum = 0;
			while(rs2.next()){
				if(rs2.getString(3).equals(bBook_code[tnum])){
					bDate[tnum] = rs2.getString(1);
					bPrice[tnum] = rs2.getString(2);
					bProv_code[tnum] = rs2.getString(4);
				}
				tnum++;
			}
			
			
			
			// buy DB�κ��� ������ ��� �����ϱ�
			while (rs1.next()) {
				
				tnum++;
			}
			
			// JTable �����ֱ� ���� ����
			len = bBook_code.length;; // ���̺��� �����ֱ� ���� ũ�� ����
			srhData = new String[len][6];
			for (int i = 0; i < len; i++) {
				srhData[i][0] = bDate[i];
				srhData[i][1] = bProv_code[i];
				srhData[i][2] = bBook_code[i];
				srhData[i][3] = bBook_name[i];
				srhData[i][4] = bBarcode[i];
				srhData[i][5] = bPrice[i];
				
			}
			Model = new ExtendedDefaultTableModel(srhData, colName);
			tblList.setModel(Model);
			tblList.setShowGrid(true);
			tblList.getColumnModel().getColumn(0).setPreferredWidth(20);
			tblList.getColumnModel().getColumn(1).setPreferredWidth(30);
			tblList.getColumnModel().getColumn(2).setPreferredWidth(30);
			tblList.getColumnModel().getColumn(3).setPreferredWidth(100);
			tblList.getColumnModel().getColumn(4).setPreferredWidth(20);
			tblList.getColumnModel().getColumn(5).setPreferredWidth(20);

			
			rs1.close();
			rs2.close();
			stat.close();
			stat2.close();
			
			conn.close();
		} catch (java.lang.Exception ea) {
			ea.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		Statement stmt;
		Object obj = e.getSource();
		// ���� ��¥ ���
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentTime = new java.util.Date();
		String dateStr = formatter.format(currentTime);

		String buydate = dateStr; // ���� ��¥

		
	
			if (obj == btnOk) {

				String book_code = tfbooknum.getText();
				int count = bk.initBookList();
				String str_count = new String();
				if (count < 10) {
					str_count = "000";
				} else if (count < 100) {
					str_count = "00";
				} else if (count < 1000) {
					str_count = "0";
				} else if (count < 10000) {

					str_count = "";
				} else {
					str_count = null;
				}
				str_count += String.valueOf(count);
				book_code = "B" + book_code + str_count;

				
				String prov_code = tfcom.getText();
				String book_name = tfname.getText();
				String book_barcode = tfbarcode.getText();
				String price = tfprice.getText();
				
				if (str_count != null) {
					if (checkBuy()) {
						try {

							String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
							Connection con = DriverManager.getConnection(url, "root", "gkdl002");

						String sql = "insert into book values ('" + book_code + "','" + book_name + "','" + book_barcode +"')";
						stmt = con.createStatement();
						stmt.executeUpdate(sql);
						
						String sql2 = "insert into buy values ('" + buydate + "','" + price + "','" + book_code + "','" + prov_code + "')";
						stmt = con.createStatement();
						stmt.executeUpdate(sql2);

						bk.setView("    ��ü �ڵ�\t�����ڵ�\t������\t���ڵ��ȣ\t��������");
						bk.setView("    " + prov_code + "\t" + book_code + "\t" + book_name + "\t"+ book_barcode + "\t" + price + "\t");
						bk.setView("    ���Ͱ��� ������ �߰��Ͽ����ϴ�!");

						tfcom.setText("");
						tfname.setText("");
						tfbarcode.setText("");
						tfprice.setText("");

						stmt.close();
						con.close();
						} catch (Exception ea) {
							ea.printStackTrace();
							bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  " + ea);
						}

					}
					PrintList(); // ������Ͽ� �ٽ� ������
				} // ���� �Է¿Ϸ�

			}
			else if(obj == btnCancel){
				dispose();
				
			}
		
	

	}

	public void mouseClicked(MouseEvent me) {

		Object obj = me.getSource();

		if (obj == tblList) {
			int row = 0;
			String code, name, barcode;
			row = tblList.getSelectedRow(); // ���̺� ���ȣ�� �Ѱ���
			TableModel model = tblList.getModel();

			code = "" + model.getValueAt(row, 0);
			name = "" + model.getValueAt(row, 1);
			barcode = "" + model.getValueAt(row, 2);

			bk.setData(code, name, barcode);

		}

	}

	public boolean checkBuy() {
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if (tfcom.getText().equals("") || tfname.getText().equals("") || tfprice.getText().equals("")) {
			lblstr.setText("�� ���� �ֽ��ϴ�! ��ü�ڵ�, ������, ���ڵ��ȣ,���԰����� �Է����ּ���");
			JOptionPane.showMessageDialog(this, lblstr, "���� �߰� ���", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if (tfbooknum.getText().length() != 4 ){
			lblstr.setText("�����帣��ȣ�� 2�ڸ��� 4�ڸ��Դϴ�.");
			JOptionPane.showMessageDialog(this, lblstr, "���� �߰� ���", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}