package wsu.yoursql.rms;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

///////////////////////////////////////
// ���� ���̺�κ��� �ش� ���̵�� ������ ��� ���ȴ����� �˾ƺ����� Ŭ����
///////////////////////////////////////////////////////////////////
class countBook{
	private int countbook =0;
	//private Book bk;

	//countBook(Book bk){	this.bk = bk;	}
	
	/////////////////////
	// �ش� ���̵� � �����ϴ��� ����.
	////////////////////////////////////
	public int count(String customer_code){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
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
	// ������ ���� ������ ���� �������� 3�� �̻��̸� ���� �� �� ����
	// ���� �ѹ��� ���� �ϳ��� ������ ������ ���� 
	// 3�� �̻��̸� true, �ǹ̴� ���⵵���� 3�������� ����
	/////////////////////
	public boolean OverThree(String customer_code){
		countbook = count(customer_code);
		if(countbook >=3)
			return true;
		else
			return false;
	}
}
