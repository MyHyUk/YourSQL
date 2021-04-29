package wsu.yoursql.rms;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.*;
import javax.swing.table.*;

class DBView{
	private String[] rentcode,rentname,rentid,rentstart,rentend,rentcost; // 대출배열
	private String[] recode,rename,reid,restart,reover,recost; // 반납배열
	protected String rentalList[] = {"대출자","도서코드","도서명","대출일","대출 만료일","대여료"};
	protected String returnList[] = {"대출자","도서코드","도서명","반납일","반납 경과일","연체료"};
	protected String rentalData[][] ={};
	protected String returnData[][] ={};
	
	private Book bk;

	DBView(Book bk){
		this.bk =bk;
	}
	
	//////////////////////////////////////////
	// 도서목록의 튜플갯수와 DB내용을 읽어들이기 위한 메서드
	// flag - 1: 대출 , 2: 반납
	//////////////////////////////////////////////////////
	public int lengthList(int flag){ 
		int count = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		try{
			String sql="";
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			if(flag == 1)
				sql="select book_code from rental";
			else if(flag == 2)
				sql="select book_code from `return`";
			
			Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
			
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
				
			while(rs1.next()){
				count++;
			}
			rs1.close();

			if(flag ==1){
				rentcode = new String[count];
				rentname = new String[count];
				rentid = new String[count];
				rentstart = new String[count];
				rentend = new String[count];
				rentcost = new String[count];
			}
			else if(flag == 2){
				recode = new String[count];
				rename = new String[count];
				reid = new String[count];
				restart = new String[count];
				reover = new String[count];
				recost = new String[count];
			}
			if(flag == 1){
				sql="select * from rental order by book_code asc";
			}
			else if(flag == 2){
				sql="select * from `return` order by book_code asc";
			}
			rs1 = stat.executeQuery(sql);
			int cnum=0;
			if(flag == 1){
				while(rs1.next()){
					rentid[cnum] = rs1.getString(1);
					rentcode[cnum] = rs1.getString(2);
					rentstart[cnum] = rs1.getString(3);
					rentend[cnum] = rs1.getString(4);
					rentcost[cnum] =  rs1.getString(5);
					rentname[cnum] = bk.getBookName(rentcode[cnum]);
					cnum++;
				}
			}
			
			else if(flag == 2){
				while(rs1.next()){
					reid[cnum] = rs1.getString(1);
					recode[cnum] = rs1.getString(2);
					restart[cnum] = rs1.getString(3);
					
					rename[cnum] =  bk.getBookName(recode[cnum]);
					
					reover[cnum] = "";
					recost[cnum] = "";
					cnum++;
				}
			}
			rs1.close();
			stat.close();
			conn.close();
		}catch(java.lang.Exception ea){ea.printStackTrace();} 
		
		return count;
	}

	///////////////////////////////////////////////////////////////////
	// 대출, 반납 현황 보기 flag - 1: 대출, 2: 반납
	////////////////////////////////////////////////////////////////////
	public void initList(int flag){
		 int len =0;
		 if(flag == 1){
			  len = lengthList(1);
			  rentalData = new String[len][6];
			  for(int i=0;i<len;i++){
				   rentalData[i][0] = rentid[i];
				   rentalData[i][1] = rentcode[i];
				   rentalData[i][2] = rentname[i];
				   rentalData[i][3] = rentstart[i];
				   rentalData[i][4] = rentend[i];
				   rentalData[i][5] = rentcost[i];
				 
			  }
			  bk.rentalModel = new ExtendedDefaultTableModel(rentalData,rentalList);
			  bk.rentalTable.setModel(bk.rentalModel);
			  bk.rentalTable.setShowGrid(true);

			  bk.rentalTable.getColumnModel().getColumn(0).setPreferredWidth(10);
			  bk.rentalTable.getColumnModel().getColumn(1).setPreferredWidth(5); 
			  bk.rentalTable.getColumnModel().getColumn(2).setPreferredWidth(100); 
			  bk.rentalTable.getColumnModel().getColumn(3).setPreferredWidth(15); 
			  bk.rentalTable.getColumnModel().getColumn(4).setPreferredWidth(15);
			  bk.rentalTable.getColumnModel().getColumn(5).setPreferredWidth(15);

			 // bk.setView("대출/예약현황 탭에 대출정보를 로드하였습니다!");
		 }
		 else if(flag == 2){
			  len = lengthList(2);
			  returnData = new String[len][6];
			  for(int i=0;i<len;i++){
				   returnData[i][0] = reid[i];
				   returnData[i][1] = recode[i];
				   returnData[i][2] = rename[i];
				   returnData[i][3] = restart[i];
				   returnData[i][4] = reover[i];
				   returnData[i][5] = recost[i];
			  }
			  bk.returnModel = new ExtendedDefaultTableModel(returnData,returnList);
			  bk.returnTable.setModel(bk.returnModel);
			  bk.returnTable.setShowGrid(true);

			  bk.returnTable.getColumnModel().getColumn(0).setPreferredWidth(10);
			  bk.returnTable.getColumnModel().getColumn(1).setPreferredWidth(5); 
			  bk.returnTable.getColumnModel().getColumn(2).setPreferredWidth(100); 
			  bk.returnTable.getColumnModel().getColumn(3).setPreferredWidth(15); 
			  bk.returnTable.getColumnModel().getColumn(4).setPreferredWidth(15); 
			  bk.returnTable.getColumnModel().getColumn(5).setPreferredWidth(10);
				 
			 
			  //bk.setView("대출/예약현황 탭에 예약정보를 로드하였습니다!");
		 }
		 
    }
}
