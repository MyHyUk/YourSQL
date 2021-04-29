package wsu.yoursql.rms;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//////////////////////////////////////////////////////
// Main 함수
///////////////////////////////////////////////////////
public class LMS{
	public static void main(String args[]){
		try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        Book f = new Book();//생성자 호출
        f.setVisible(true);
		f.connectDBTest();// DB연결 테스트
                  
		f.addWindowListener(new WindowAdapter() {   //윈도우 창 닫는 메소드 
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                } 
		});
      }
}


