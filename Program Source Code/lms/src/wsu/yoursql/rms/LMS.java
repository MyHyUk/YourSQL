package wsu.yoursql.rms;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//////////////////////////////////////////////////////
// Main �Լ�
///////////////////////////////////////////////////////
public class LMS{
	public static void main(String args[]){
		try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        Book f = new Book();//������ ȣ��
        f.setVisible(true);
		f.connectDBTest();// DB���� �׽�Ʈ
                  
		f.addWindowListener(new WindowAdapter() {   //������ â �ݴ� �޼ҵ� 
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                } 
		});
      }
}


