package wsu.yoursql.rms;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

///////////////////////////////////////
// 대출 테이블로부터 해당 아이디로 도서를 몇개나 빌렸는지를 알아보지는 클래스
///////////////////////////////////////////////////////////////////
class countBook{
	private int countbook =0;
	//private Book bk;

	//countBook(Book bk){	this.bk = bk;	}
	
	/////////////////////
	// 해당 아이디가 몇개 존재하는지 센다.
	////////////////////////////////////
	public int count(String customer_code){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
			  String sql = "select * from rental where customer_code='"+customer_code+"'";
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
	////////////////////////////////////////////////////
	// 대출을 세번 했으면 따라서 도서수가 3개 이상이면 대출 할 수 없음
	// 대출 한번에 도수 하나를 빌리는 것으로 전제 
	// 3개 이상이면 true, 의미는 대출도서는 3개까지만 가능
	/////////////////////
	public boolean OverThree(String customer_code){
		countbook = count(customer_code);
		if(countbook >=3)
			return true;
		else
			return false;
	}
}
