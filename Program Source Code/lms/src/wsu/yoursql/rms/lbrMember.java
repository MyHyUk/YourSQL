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
	// 회원아이디가 존재하는지 검사
	///////////////////////////////////
	public boolean existId(String str){
		String customer_code="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			java.util.Properties pro = new java.util.Properties();
		}
		catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
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
		
		if(customer_code.equals(str)) //아이디가 있음
			return true;
		else // 아이디가 없음
			return false;
	}
	
	//////////////////////////////////////////////////////////////////////////
	// 회원가입시 프라이머리 키의 중복성을 검사하는 메서드
	///////////////////////////////////////////////////////////////////////////
	public boolean checkId(String str){
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
				
				
				sql = "select customer_code from customer where customer_code ='"+str+"'";
			
				
				Statement stat = conn.createStatement();
				ResultSet rs1 = stat.executeQuery(sql);
				while(rs1.next()){
					count++;
				}
				
				bk.setView("아이디 중복 갯수: "+count);
				
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
	// 회원 인증 회원이 맞으면 true
	//////////////////////////////////
	public boolean auth(String str){
		String customer_code="";
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
		
		if(customer_code.equals(str)) //회원임
			return true;
		else // 회원이 아님
			return false;
	}

	////////////////////////////////////////////////////
	// 회원부분에서 재대로 값이 입력되었는지 체크하는 메서드
	// flag - 가입:1, 삭제:2, 검색:3, 수정: 4
	//////////////////////////////////////////////////
	public boolean checkMember(int flag){ 
		Label lblstr = new Label();
		lblstr.setFont(MyFont.sans1);

		if(flag == 1){
			
			
			if( bk.tfName.getText().equals("") || 
				bk.tfbirthday.getText().equals("") || bk.tfphone.getText().equals("") || bk.tfaddress.getText().equals("")|| bk.tfcash.getText().equals("")){
				lblstr.setText("빈 곳이 있습니다! 모두 입력해 주십시오!!");
				JOptionPane.showMessageDialog(bk,lblstr,"회원가입!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag ==2){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("아이디를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"회원 삭제",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 3){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("아이디를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"회원 검색",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		else if(flag == 4){
			if(bk.tfId.getText().equals("")){
				lblstr.setText("아이디를 꼭 입력해 주십시오!");
				JOptionPane.showMessageDialog(bk,lblstr,"회원 수정!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(bk.tfId.getText().equals("") || bk.tfName.getText().equals("") || 
				bk.tfbirthday.getText().equals("") || bk.tfphone.getText().equals("") || bk.tfaddress.getText().equals("")|| bk.tfcash.getText().equals("")){
				lblstr.setText("빈 곳이 있습니다! 모두 입력해 주십시오!!");
				JOptionPane.showMessageDialog(bk,lblstr,"회원 수정!",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			else if(!existId(bk.tfId.getText())){
				lblstr.setText("해당 아이디가 존재하지 않습니다. 회원가입을 해 주십시오");
				JOptionPane.showMessageDialog(bk,lblstr,"회원수정",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}

		return true;
	}
	////////////////////////////////////
	// 회원 등록
	/////////////////////////////////
	public void memberRegister(){
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
					bk.setView("  회원가입을 선택하셨습니다.\n  회원코드: "+customer_code+"  성명: "+name+"  생년월일:"+birthday+"  연락처: "+phone+"  주소: "+address+" 선불금액: "+cash+"을 추가 하였습니다.");

					stmt.close();
					con.close();
			  }



		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 회원삭제
	/////////////////////////////////
	public void memberUnregister(){
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
			  
			  String customer_code    = bk.tfId.getText();
              String name  = bk.tfName.getText();

				   if(checkMember(2)){
						stmt = con.createStatement();
						String sql = "delete from customer where customer_code = '" +customer_code+"'";
						stmt.executeUpdate(sql);

						bk.setView("  아이디: "+customer_code+" 님이 삭제 되었습니다."+"\n");

                        bk.tfId.setText("");    bk.tfName.setText("");    bk.tfbirthday.setText("");      
						bk.tfphone.setText("");	bk.tfaddress.setText(""); bk.tfcash.setText("");                
						stmt.close();
						con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 회원 검색
	/////////////////////////////////
	public void memberSearch(){
		try{
			  Class.forName("com.mysql.jdbc.Driver");
		      java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
      
		 try{  

              String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection con=DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
              
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
						bk.setView("  "+"회원코드"+"\t" +"이름"+"\t"+"생년월일"+"\t"+"연락처\t"+"주소"+"\t"+"선불금액");
						bk.setView("  "+id+"\t"+name+"\t"+birthday+"\t"+phone+"\t"+address+"\t"+cash);
				   }
                                    
				   stmt.close();
				   con.close();
				   }

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	////////////////////////////////////
	// 회원 수정
	/////////////////////////////////
	public void memberModify(){
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

				   bk.setView("  "+"I D"+"\t" +"이름"+"\t"+"생년월일"+"\t"+"연락처\t"+"주소\t"+"선불금액");
				   bk.setView("  "+customer_code+"\t"+name+"\t"+birthday+"\t"+phone+"\t"+address+"\t"+cash+"\n  위와 같이 회원정보를 수정하였습니다.");
                                    
				   stmt.close();
				   con.close();
			}

		}catch(Exception ea){ea.printStackTrace();
	     bk.setView("DATABASE 처리 하는중 에러가 발생했습니다. \n  "+ea); 
      }
	}
	public int member_count(){
		 
		 int count=0;

		 try{
			  Class.forName("com.mysql.jdbc.Driver");
			  java.util.Properties pro = new java.util.Properties();
		 }
		 catch(ClassNotFoundException ex){System.out.println("드라이버가 연결되지 않습니다."+ex.getMessage());}
			
		 try{

			  String url = "jdbc:mysql://"+ipaddress+":3306/yoursql?useSSL=false";
			  Connection conn = DriverManager.getConnection(url,"root","gkdl002"); //연결을 한다.ID는 자신의 것,Passwd도 마찬가지
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
