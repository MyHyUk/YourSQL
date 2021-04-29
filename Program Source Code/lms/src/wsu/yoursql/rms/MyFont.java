package wsu.yoursql.rms;

import java.awt.*;
import javax.swing.*;

public class MyFont
{	
	public static Font sans1 = new Font("Sansserif", Font.PLAIN, 12);
	public static Font sans2 = new Font("Sansserif", Font.PLAIN, 11);

	//
	//	getSans()
	//	기본글꼴을 산스세리프체로 바꾸어서 리턴시킨다.
	//
	public static JLabel getSans(String str)
	{
		JLabel label = new JLabel(str);
		label.setFont(sans1);
		label.setForeground(new Color(116,92,92));
		return label;
	}
 //  함수 추가
	public static JLabel getSans1(String str)
	{
		JLabel label = new JLabel(str);
		label.setFont(sans1);
		label.setForeground(new Color(116,92,92));
		return label;
	}
	
	public static JLabel getGraySans(String str)
	{
		JLabel label = new JLabel(str);
		label.setFont(sans1);
		label.setForeground(new Color(116,92,92));
		label.setAlignmentX(SwingConstants.LEFT);
		return label;
	}
	
	public static JLabel getGraySans(String str, ImageIcon icon)
	{		
		JLabel label = new JLabel(str, icon, SwingConstants.LEFT);
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setFont(sans1);
		label.setForeground(new Color(116,92,92));
		return label;
	}
}
