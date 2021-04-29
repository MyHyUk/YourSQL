package wsu.yoursql.rms;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

class lbrProvider{
	
	Book bk;

	lbrProvider(Book bk){
		this.bk = bk;
	}

	//////////////////////////////
	// ��ü�ڵ尡 �����ϴ��� �˻�
	///////////////////////////////////
	public boolean existCode(String str){
		String provider_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		try{
			String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			Connection conn = DriverManager.getConnection(url,"root","gkdl002"); 
			String sql = "select provider_code from provider where provider_code ='"+str+"'";
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery(sql);
			while(rs1.next()){
				provider_code = rs1.getString("provider_code");
			}
			rs1.close();
			stat.close();
			conn.close();
			
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}
		
		if(provider_code.equals(str)) //���̵� ����
			return true;
		else // ���̵� ����
			return false;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// ��ü���Խ� �����̸Ӹ� Ű�� �ߺ����� �˻��ϴ� �޼���
	///////////////////////////////////////////////////////////////////////////
	public boolean checkCode(String str){
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
				
				
				sql = "select provider_code from provider where provider_code ='"+str+"'";
			
				
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while(rs1.next()){
					count++;
				}
				
				bk.setView("�ڵ� �ߺ� ����: "+count);
				
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
	// ���� ��ü�� ������ true
	//////////////////////////////////
	public boolean auth(String str){
		String provider_code="";
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
				sql = "select provider_code from provider where provider_code ='"+str+"'";
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while(rs1.next()){
					provider_code = rs1.getString("provider_code");
				}
				rs1.close();
				stat.close();
				conn.close();
			}
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}
		
		if(provider_code.equals(str)) //��ü��
			return true;
		else // ��ü�� �ƴ�
			return false;
	}

	////////////////////////////////////////////////////
	// ��ü�κп��� ���� ���� �ԷµǾ����� üũ�ϴ� �޼���
	// flag - ����:1, ����:2, �˻�:3, ����: 4
	//////////////////////////////////////////////////
	public boolean checkprovider(int flag){ 
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if(flag == 1){
			
			
			if( bk.tfpro_Code.getText().equals("") || 
				bk.tfpro_bank.getText().equals("") || bk.tfpro_acc.getText().equals("") || bk.tfpro_manager.getText().equals("")|| bk.tfpro_phone.getText().equals("")){
				lblstr.setText("�� ���� �ֽ��ϴ�! ��� �Է��� �ֽʽÿ�!!");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü ���",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag ==2){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("��ü�ڵ带 �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü ����",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 3){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("��ü�ڵ带 �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü �˻�",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 4){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("��ü�ڵ带 �� �Է��� �ֽʽÿ�!");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü ����!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(bk.tfpro_Code.getText().equals("") || bk.tfpro_name.getText().equals("") || 
				bk.tfpro_bank.getText().equals("") || bk.tfpro_acc.getText().equals("") || bk.tfpro_manager.getText().equals("")|| bk.tfpro_phone.getText().equals("")){
				lblstr.setText("�� ���� �ֽ��ϴ�! ��� �Է��� �ֽʽÿ�!!");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü ����!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(!existCode(bk.tfpro_Code.getText())){
				lblstr.setText("�ش� ��ü�ڵ尡 �������� �ʽ��ϴ�. ��ü������ �� �ֽʽÿ�");
				JOptionPane.showMessageDialog(bk,lblstr,"��ü����",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}
	////////////////////////////////////
	// ��ü ���
	/////////////////////////////////
	public void providerRegister(){
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
			  int count = provider_count();
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
			  
			  
			  String provider_code = "C"+dateStr+str_count+count;
			  String provider_name  = bk.tfpro_name.getText();
              String provider_bank  = bk.tfpro_bank.getText();
              String provider_acc = bk.tfpro_acc.getText();
			  String provider_manager = bk.tfpro_manager.getText();
			  String provider_phone  = bk.tfpro_phone.getText();
                    
			  if(checkprovider(1)){
				   String sql = "insert into provider values ('"+provider_code+"','"+provider_name+"','"+provider_bank+"','"+provider_acc+"','"+provider_manager+"','"+provider_phone+"')";
				   stmt = con.createStatement();
				   stmt.executeUpdate(sql);
                                              
					bk.tfpro_Code.setText("");bk.tfpro_name.setText("");bk.tfpro_bank.setText("");bk.tfpro_acc.setText("");bk.tfpro_manager.setText("");bk.tfpro_phone.setText("");	
					bk.setView("  ��ü������ �����ϼ̽��ϴ�.\n  ��ü�ڵ�: "+provider_code+"  ��ü��: "+provider_name+"  �ְŷ�����:"+provider_bank+"  ���¹�ȣ: "+provider_acc+"  ����ڸ�: "+provider_manager+" ����� ����ó: "+provider_phone+"�� �߰� �Ͽ����ϴ�.");

					stmt.close();
					con.close();
			  }



		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ��ü����
	/////////////////////////////////
	public void providerUnregister(){
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
			  
			  String provider_code  = bk.tfpro_Code.getText();
              String provider_name  = bk.tfpro_name.getText();

				   if(checkprovider(2)){
						stmt = con.createStatement();
						String sql = "delete from provider where provider_code = '" +provider_code+"'";
						stmt.executeUpdate(sql);

						bk.setView("  ��ü��: "+provider_name+" ���� ���� �Ǿ����ϴ�."+"\n");

                        bk.tfpro_Code.setText("");    bk.tfpro_name.setText("");    bk.tfpro_bank.setText("");      
						bk.tfpro_acc.setText("");	bk.tfpro_manager.setText(""); bk.tfpro_phone.setText("");                
						stmt.close();
						con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ��ü �˻�
	/////////////////////////////////
	public void providerSearch(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
              
              Statement stmt; 
			  
			  if(checkprovider(3)){
				   stmt = con.createStatement();
				   ResultSet rs = stmt.executeQuery("select * from provider where provider_code ='" +bk.tfpro_Code.getText()+ "'");
				   while(rs.next()){
					   
					   
                                                          
						String provider_code   = rs.getString(1);
						String provider_name = rs.getString(2);
						String provider_bank = rs.getString(3);
						String provider_account= rs.getString(4);
						String provider_manager= rs.getString(5);
						String provider_phone = rs.getString(6);
                                                                          
						bk.tfpro_Code.setText(provider_code);
						bk.tfpro_name.setText(provider_name);
						bk.tfpro_bank.setText(provider_bank);
						bk.tfpro_acc.setText(provider_account);
						bk.tfpro_manager.setText(provider_manager);
						bk.tfpro_phone.setText(provider_phone);
						bk.setView("  "+"��ü�ڵ�"+"\t" +"��ü��"+"\t"+"�ְŷ�����"+"\t"+"���¹�ȣ\t"+"����ڸ�"+"\t"+"����ڿ���ó");
						bk.setView("  "+provider_code+"\t"+provider_name+"\t"+provider_bank+"\t"+provider_account+"\t"+provider_manager+"\t"+provider_phone);
				   }
                                    
				   stmt.close();
				   con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// ��ü ����
	/////////////////////////////////
	public void providerModify(){
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
			  
			  if(checkprovider(4)){
				   stmt = con.createStatement();
								
				   String provider_code = bk.tfpro_Code.getText();
				   String provider_name = bk.tfpro_name.getText();
				   String provider_bank = bk.tfpro_bank.getText();
				   String provider_account = bk.tfpro_acc.getText();
				   String provider_manager = bk.tfpro_manager.getText();
				   String provider_phone = bk.tfpro_phone.getText();
				
				   String sql = "update provider set provider_name='"+provider_name+"',provider_bank='"+provider_bank+"',provider_account='"+provider_account+"',provider_manager='"+provider_manager+"',provider_phone='"+provider_phone+"' where provider_code ='" +provider_code+ "'";
				   stmt.executeUpdate(sql);

				   bk.setView("  "+"I D"+"\t" +"��ü��"+"\t"+"�ְŷ�����"+"\t"+"���¹�ȣ\t"+"����ڸ�\t"+"����ڿ���ó");
				   bk.setView("  "+provider_code+"\t"+provider_name+"\t"+provider_bank+"\t"+provider_account+"\t"+provider_manager+"\t"+provider_phone+"\n  ���� ���� ��ü������ �����Ͽ����ϴ�.");
                                    
				   stmt.close();
				   con.close();
			}

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE ó�� �ϴ��� ������ �߻��߽��ϴ�. \n  "+ea); 
      }
	}
	public int provider_count(){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("����̹��� ������� �ʽ��ϴ�."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //������ �Ѵ�.ID�� �ڽ��� ��,Passwd�� ��������
			  String sql = "select * from provider ";
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