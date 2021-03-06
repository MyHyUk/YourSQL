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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Provdeal extends JDialog implements ActionListener, MouseListener {
	private JPanel pnTop;
	private JTable tblList;
	private JButton btnCheck, btnCancel;

	private Book bk;
	private ExtendedDefaultTableModel Model;
	private String colName[] = { "날짜", "매출 금액", "지출 금액" };
	private String srhData[][] = {};
	private int size = 0; // DB에서 불러온 String 배열 크기
	private int len = 0; // Table List에 보여주기 위해 크기구함
	private int sum_cost = 0, sum_price = 0;
	private String[] dDate, dPrice, dCost;// DB에서 불러온 데이타를 저장하기 위해서

	Provdeal(Book bk, JFrame f) {

		super(f, "매출 현황", true);
		this.bk = bk;

		Container c = getContentPane();
		c.setLayout(new BorderLayout(3, 2));

		Model = new ExtendedDefaultTableModel(srhData, colName);
		tblList = new JTable(Model);
		JScrollPane scrollPane = new JScrollPane(tblList);
		
		JPanel Pane = new JPanel(new GridBagLayout());
		Pane.setBackground(new Color(217, 230, 237));
		
		
		tblList.getTableHeader().setReorderingAllowed(false);
		tblList.getTableHeader().setResizingAllowed(false);
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "매출 현황 정보", TitledBorder.LEFT, TitledBorder.BELOW_TOP,
				MyFont.sans1, new Color(53, 57, 72)));

		btnCheck = new JButton("총 매출확인");
		btnCancel = new JButton("닫 기");

		btnCheck.setBackground(new Color(170, 181, 221));
		btnCancel.setBackground(new Color(170, 181, 221));

		pnTop = new JPanel(new BorderLayout(5, 3));

		pnTop.setBackground(new Color(217, 230, 237));
		scrollPane.setBackground(new Color(217, 230, 237));
		tblList.setBackground(new Color(217, 230, 237));
		tblList.getTableHeader().setBackground(new Color(136, 146, 139));
		tblList.getTableHeader().setForeground(Color.white);

		tblList.getColumnModel().getColumn(0).setPreferredWidth(20);
		tblList.getColumnModel().getColumn(1).setPreferredWidth(30);
		tblList.getColumnModel().getColumn(2).setPreferredWidth(30);

		tblList.setPreferredScrollableViewportSize(new Dimension(400, 70));

		Pane.add(btnCheck, new GridBagConstraints(2, 5, 2, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(20, 10, 15, 0), 3, 5));
		Pane.add(btnCancel, new GridBagConstraints(4, 5, 2, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(20, 10, 15, 0), 3, 5));


		pnTop.add(scrollPane, BorderLayout.CENTER);
		c.add(pnTop, BorderLayout.CENTER);
		c.add(Pane, BorderLayout.SOUTH);


		btnCheck.addActionListener(this);
		btnCancel.addActionListener(this);

		PrintList();

		setBounds(150, 300, 600, 600);
		setVisible(true);

	}

	public void PrintList() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버가 연결되지 않습니다." + ex.getMessage());
		}

		try {
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			String sql = "", sql2 = "";
			Connection conn = DriverManager.getConnection(url, "root", "gkdl002"); // 연결을
																					// 한다.ID는
																					// 자신의
																					// 것,Passwd도
																					// 마찬가지

			sql = "select * from rental,buy ";
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
			while (rs1.next()) {
				size++;
			}

			/* DB에서 불러들일 데이타들의 배열 선언 */
			dDate = new String[size];
			dCost = new String[size];
			dPrice = new String[size];
			for (int i = 0; i < size; i++) {
				dDate[i] = "0";
				dCost[i] = "0";
				dPrice[i] = "0";
			}

			sql = "select rental_date,rental_cost from rental ";
			sql2 = "select buy_date,buy_price from buy ";

			stat = conn.createStatement();
			Statement stat2 = conn.createStatement();

			rs1 = stat.executeQuery(sql);
			ResultSet rs2 = stat2.executeQuery(sql2);

			

			int tnum = 0;
			// 대여료 수입계산
			while (rs1.next()) {
				dDate[tnum] = rs1.getString(1);
				dCost[tnum] = rs1.getString(2);
				sum_cost += Integer.valueOf(dCost[tnum]);

				tnum++;
			}
			tnum = 0;
			while (rs2.next()) {
				if (dDate[tnum].equals(rs2.getString(1))) {
					dPrice[tnum] = rs2.getString(2);
				} else {
					dDate[tnum] = rs2.getString(1);
					dPrice[tnum] = rs2.getString(2);

				}
				sum_price += Integer.valueOf(dPrice[tnum]);
				tnum++;

			}

			// buy DB로부터 데이터 얻어 저장하기
			while (rs1.next()) {

				tnum++;
			}

			// JTable 보여주기 위한 설정
			len = dDate.length;
			; // 테이블에 보여주기 위해 크기 구함
			srhData = new String[len][3];
			for (int i = 0; i < len; i++) {
				srhData[i][0] = dDate[i];
				srhData[i][1] = dCost[i];
				srhData[i][2] = dPrice[i];

			}
			Model = new ExtendedDefaultTableModel(srhData, colName);
			tblList.setModel(Model);
			tblList.setShowGrid(true);
			tblList.getColumnModel().getColumn(0).setPreferredWidth(20);
			tblList.getColumnModel().getColumn(1).setPreferredWidth(30);
			tblList.getColumnModel().getColumn(2).setPreferredWidth(30);

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

		if (e.getSource() == btnCheck) {
			JOptionPane.showMessageDialog(null,
					"[총 합산 금액]     매출:￦" + String.valueOf(sum_cost) + "   지출:￦" + String.valueOf(sum_price));

		} else if (e.getSource() == btnCancel) {
			dispose();

		}

	}

	public void mouseClicked(MouseEvent me) {

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
