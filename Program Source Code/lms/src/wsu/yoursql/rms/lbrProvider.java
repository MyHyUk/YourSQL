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
	// 업체코드가 존재하는지 검사
	///////////////////////////////////
	public boolean existCode(String str){
		String provider_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
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
		
		if(provider_code.equals(str)) //아이디가 있음
			return true;
		else // 아이디가 없음
			return false;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// 업체가입시 프라이머리 키의 중복성을 검사하는 메서드
	///////////////////////////////////////////////////////////////////////////
	public boolean checkCode(String str){
		int count = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
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
				
				bk.setView("코드 중복 갯수: "+count);
				
				rs1.close();
				stat.close();
				conn.close();
			}
			
		}catch(java.lang.Exception ea){ea.printStackTrace();}  
		if(count > 0){ // 아이디나 도서코드가 존재하면 
			return true;
		}
		else // 아이디나 도서코드가 존재하지 않으면
			return false;
	}
	///////////////////////////////
	// 인증 업체가 맞으면 true
	//////////////////////////////////
	public boolean auth(String str){
		String provider_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
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
		
		if(provider_code.equals(str)) //업체임
			return true;
		else // 업체이 아님
			return false;
	}

	////////////////////////////////////////////////////
	// 업체부분에서 재대로 값이 입력되었는지 체크하는 메서드
	// flag - 가입:1, 삭제:2, 검색:3, 수정: 4
	//////////////////////////////////////////////////
	public boolean checkprovider(int flag){ 
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if(flag == 1){
			
			
			if( bk.tfpro_Code.getText().equals("") || 
				bk.tfpro_bank.getText().equals("") || bk.tfpro_acc.getText().equals("") || bk.tfpro_manager.getText().equals("")|| bk.tfpro_phone.getText().equals("")){
				lblstr.setText("빈 곳이 있습니다! 모두 입력해 주십시오!!");
				JOptionPane.showMessageDialog(bk,lblstr,"업체 등록",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag ==2){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("업체코드를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"업체 삭제",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 3){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("업체코드를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"업체 검색",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 4){
			if(bk.tfpro_Code.getText().equals("")){
				lblstr.setText("업체코드를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"업체 수정!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(bk.tfpro_Code.getText().equals("") || bk.tfpro_name.getText().equals("") || 
				bk.tfpro_bank.getText().equals("") || bk.tfpro_acc.getText().equals("") || bk.tfpro_manager.getText().equals("")|| bk.tfpro_phone.getText().equals("")){
				lblstr.setText("빈 곳이 있습니다! 모두 입력해 주십시오!!");
				JOptionPane.showMessageDialog(bk,lblstr,"업체 수정!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(!existCode(bk.tfpro_Code.getText())){
				lblstr.setText("해당 업체코드가 존재하지 않습니다. 업체가입을 해 주십시오");
				JOptionPane.showMessageDialog(bk,lblstr,"업체수정",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}
	////////////////////////////////////
	// 업체 등록
	/////////////////////////////////
	public void providerRegister(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
              //System.out.println("DATABASE에 연결 되었습니다.") ;
              
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
					bk.setView("  업체가입을 선택하셨습니다.\n  업체코드: "+provider_code+"  업체명: "+provider_name+"  주거래은행:"+provider_bank+"  계좌번호: "+provider_acc+"  담당자명: "+provider_manager+" 담당자 연락처: "+provider_phone+"을 추가 하였습니다.");

					stmt.close();
					con.close();
			  }



		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 업체삭제
	/////////////////////////////////
	public void providerUnregister(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
              //System.out.println("DATABASE에 연결 되었습니다.") ;
              
              Statement stmt; 
			  
			  String provider_code  = bk.tfpro_Code.getText();
              String provider_name  = bk.tfpro_name.getText();

				   if(checkprovider(2)){
						stmt = con.createStatement();
						String sql = "delete from provider where provider_code = '" +provider_code+"'";
						stmt.executeUpdate(sql);

						bk.setView("  업체명: "+provider_name+" 님이 삭제 되었습니다."+"\n");

                        bk.tfpro_Code.setText("");    bk.tfpro_name.setText("");    bk.tfpro_bank.setText("");      
						bk.tfpro_acc.setText("");	bk.tfpro_manager.setText(""); bk.tfpro_phone.setText("");                
						stmt.close();
						con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 업체 검색
	/////////////////////////////////
	public void providerSearch(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
              
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
						bk.setView("  "+"업체코드"+"\t" +"업체명"+"\t"+"주거래은행"+"\t"+"계좌번호\t"+"담당자명"+"\t"+"담당자연락처");
						bk.setView("  "+provider_code+"\t"+provider_name+"\t"+provider_bank+"\t"+provider_account+"\t"+provider_manager+"\t"+provider_phone);
				   }
                                    
				   stmt.close();
				   con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 업체 수정
	/////////////////////////////////
	public void providerModify(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
              //System.out.println("DATABASE에 연결 되었습니다.") ;
              
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

				   bk.setView("  "+"I D"+"\t" +"업체명"+"\t"+"주거래은행"+"\t"+"계좌번호\t"+"담당자명\t"+"담당자연락처");
				   bk.setView("  "+provider_code+"\t"+provider_name+"\t"+provider_bank+"\t"+provider_account+"\t"+provider_manager+"\t"+provider_phone+"\n  위와 같이 업체정보를 수정하였습니다.");
                                    
				   stmt.close();
				   con.close();
			}

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	public int provider_count(){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
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
