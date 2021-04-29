package wsu.yoursql.rms;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

class lbrMember{
	
	Book bk;

	lbrMember(Book bk){
		this.bk = bk;
	}

	//////////////////////////////
	// ȸ�����̵� �����ϴ��� �˻�
	///////////////////////////////////
	public boolean existId(String str){
		String customer_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		try{
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			Connection conn = DriverManager.getConnection(url,"root","gkdl002"); 
			String sql = "select customer_code from customer where customer_code ='"+str+"'";
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
			while(rs1.next()){
				customer_code = rs1.getString("customer_code");
			}
			rs1.close();
			stat.close();
			conn.close();
			
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}
		
		if(customer_code.equals(str)) //���̵� ����
			return true;
		else // ���̵� ����
			return false;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// ȸ�����Խ� �����̸Ӹ� Ű�� �ߺ����� �˻��ϴ� �޼���
	///////////////////////////////////////////////////////////////////////////
	public boolean checkId(String str){
		int count = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		try{
			if(!str.equals("")){
				
				String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
				String	sql="";
				Connection conn = DriverManager.getConnection(url,"root","gkdl002"); 
				
				
				sql = "select customer_code from customer where customer_code ='"+str+"'";
			
				
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while(rs1.next()){
					count++;
				}
				
				bk.setView("���̵� �ߺ� ����: "+count);
				
				rs1.close();
				stat.close();
				conn.close();
			}
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}  
		if(count > 0){ // ���̵� �����ڵ尡 �����ϸ� 
			return true;
		}
		else // ���̵� �����ڵ尡 �������� ������
			return false;
	}
	///////////////////////////////
	// ȸ�� ���� ȸ���� ������ true
	//////////////////////////////////
	public boolean auth(String str){
		String customer_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		try{
			if(!str.equals("")){

				String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
				String sql="";
				Connection conn = DriverManager.getConnection(url,"root","gkdl002"); 
				sql = "select customer_code from customer where customer_code ='"+str+"'";
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while(rs1.next()){
					customer_code = rs1.getString("customer_code");
				}
				rs1.close();
				stat.close();
				conn.close();
			}
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}
		
		if(customer_code.equals(str)) //ȸ����
			return true;
		else // ȸ���� �ƴ�
			return false;
	}

	////////////////////////////////////////////////////
	// ȸ���κп��� ���� ���� �ԷµǾ����� üũ�ϴ� �޼���
	// flag - ����:1, ����:2, �˻�:3, ����: 4
	//////////////////////////////////////////////////
	public boolean checkMember(int flag){ 
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if(flag == 1){
			
			
			if( bk.tfName.getText().equals("") || 
				bk.tfbirthday.getText().equals("") || bk.tfphone.getText().equals("") || bk.tfaddress.getText().equals("")|| bk.tfcash.getText().equals("")){
				lblstr.setText("�� ���� �ֽ��ϴ�! ��� �Է��� �ֽʽÿ�!!");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ������!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag ==2){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("���̵� �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ�� ����",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 3){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("���̵� �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ�� �˻�",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 4){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("���̵� �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ�� ����!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(bk.tfId.getText().equals("") || bk.tfName.getText().equals("") || 
				bk.tfbirthday.getText().equals("") || bk.tfphone.getText().equals("") || bk.tfaddress.getText().equals("")|| bk.tfcash.getText().equals("")){
				lblstr.setText("�� ���� �ֽ��ϴ�! ��� �Է��� �ֽʽÿ�!!");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ�� ����!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(!existId(bk.tfId.getText())){
				lblstr.setText("�ش� ���̵� �������� �ʽ��ϴ�. ȸ�������� �� �ֽʽÿ�");
				JOptionPane.showMessageDialog(bk,lblstr,"ȸ������",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}
	////////////////////////////////////
	// ȸ�� ���
	/////////////////////////////////
	public void memberRegister(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
              //System.out.println("DATABASE�� ���� �Ǿ����ϴ�.") ;
              
              Statement stmt; 
			  
			  
			  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			  java.util.Date currentTime = new java.util.Date(); 
			  String dateStr= formatter.format(currentTime);
			  dateStr = dateStr.substring(0, 4);
			  int count = member_count();
			  String str_count = new String();
			  if(count<10){
				  str_count ="000";
			  }else if(count<100){
				  str_count ="00";
			  }else if(count<1000){
				  str_count ="0";
			  }else if(count<10000){
				  str_count ="";
			  }else{
				  str_count = null;
			  }
			  
			  
			  String customer_code = "M"+dateStr+str_count+count;
			  String name  = bk.tfName.getText();
              String birthday  = bk.tfbirthday.getText();
              String phone = bk.tfphone.getText();
			  String address = bk.tfaddress.getText();
			  String cash  = bk.tfcash.getText();
                    
			  if(checkMember(1)){
				   String sql = "insert into customer values ('"+customer_code+"','"+name+"','"+birthday+"','"+phone+"','"+address+"','"+cash+"')";
				   stmt = con.createStatement();
				   stmt.executeUpdate(sql);
                                              
					bk.tfId.setText("");bk.tfName.setText("");bk.tfbirthday.setText("");bk.tfphone.setText("");bk.tfaddress.setText("");bk.tfcash.setText("");	
					bk.setView("  ȸ�������� �����ϼ̽��ϴ�.\n  ȸ���ڵ�: "+customer_code+"  ����: "+name+"  �������:"+birthday+"  ����ó: "+phone+"  �ּ�: "+address+" ���ұݾ�: "+cash+"�� �߰� �Ͽ����ϴ�.");

					stmt.close();
					con.close();
			  }



		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ȸ������
	/////////////////////////////////
	public void memberUnregister(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
              //System.out.println("DATABASE�� ���� �Ǿ����ϴ�.") ;
              
              Statement stmt; 
			  
			  String customer_code    = bk.tfId.getText();
              String name  = bk.tfName.getText();

				   if(checkMember(2)){
						stmt = con.createStatement();
						String sql = "delete from customer where customer_code = '" +customer_code+"'";
						stmt.executeUpdate(sql);

						bk.setView("  ���̵�: "+customer_code+" ���� ���� �Ǿ����ϴ�."+"\n");

                        bk.tfId.setText("");    bk.tfName.setText("");    bk.tfbirthday.setText("");      
						bk.tfphone.setText("");	bk.tfaddress.setText(""); bk.tfcash.setText("");                
						stmt.close();
						con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ȸ�� �˻�
	/////////////////////////////////
	public void memberSearch(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
              
              Statement stmt; 
			  
			  if(checkMember(3)){
				   stmt = con.createStatement();
				   ResultSet rs = stmt.executeQuery("select * from customer where customer_code ='" +bk.tfId.getText()+ "'");
				   while(rs.next()){
                                                          
						String id   = rs.getString(1);
						String name = rs.getString(2);
						String birthday = rs.getString(3);
						String phone= rs.getString(4);
						String address= rs.getString(5);
						String cash = rs.getString(6);
                                                                          
						bk.tfId.setText(id);
						bk.tfName.setText(name);
						bk.tfbirthday.setText(birthday);
						bk.tfphone.setText(phone);
						bk.tfaddress.setText(address);
						bk.tfcash.setText(cash);
						bk.setView("  "+"ȸ���ڵ�"+"\t" +"�̸�"+"\t"+"�������"+"\t"+"����ó\t"+"�ּ�"+"\t"+"���ұݾ�");
						bk.setView("  "+id+"\t"+name+"\t"+birthday+"\t"+phone+"\t"+address+"\t"+cash);
				   }
                                    
				   stmt.close();
				   con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ȸ�� ����
	/////////////////////////////////
	public void memberModify(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
              //System.out.println("DATABASE�� ���� �Ǿ����ϴ�.") ;
              
              Statement stmt; 
			  
			  if(checkMember(4)){
				   stmt = con.createStatement();
								
				   String customer_code = bk.tfId.getText();
				   String name = bk.tfName.getText();
				   String birthday = bk.tfbirthday.getText();
				   String phone = bk.tfphone.getText();
				   String address = bk.tfaddress.getText();
				   String cash = bk.tfcash.getText();
				
				   String sql = "update customer set customer_name='"+name+"',customer_birthday='"+birthday+"',customer_phone='"+phone+"',customer_address='"+address+"',customer_cash='"+cash+"' where customer_code ='" +customer_code+ "'";
				   stmt.executeUpdate(sql);

				   bk.setView("  "+"I D"+"\t" +"�̸�"+"\t"+"�������"+"\t"+"����ó\t"+"�ּ�\t"+"���ұݾ�");
				   bk.setView("  "+customer_code+"\t"+name+"\t"+birthday+"\t"+phone+"\t"+address+"\t"+cash+"\n  ���� ���� ȸ�������� �����Ͽ����ϴ�.");
                                    
				   stmt.close();
				   con.close();
			}

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	public int member_count(){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
			  String sql = "select * from customer ";
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

}