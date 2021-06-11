package 计算器实训终;

import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class mainJFrame extends JFrame {
//	private 
	boolean flag = false;
	static Connection con = null;//数据库?
	Button btn[] = new Button[] {

			new Button("x²"), new Button("√"), new Button("mod"), new Button("log"), new Button("("), new Button(")"),
			new Button("CE"), new Button("Del"), new Button("7"), new Button("8"), new Button("9"), new Button("+"),
			new Button("4"), new Button("5"), new Button("6"), new Button("-"), new Button("1"), new Button("2"),
			new Button("3"), new Button("*"), new Button("0"), new Button("."), new Button("="), new Button("/"),

	};
	private JPanel content1 = new JPanel();
	private JPanel content = new JPanel();
	private JPanel jbt = new JPanel();
	private JPanel jpb = new JPanel();
	private JPanel jpa = new JPanel();
	private JTextField jtf = new JTextField();
	private JTextArea jta = new JTextArea("历史记录");
	

	public mainJFrame() {

		content.setLayout(new BorderLayout());
		content1.setLayout(new BorderLayout());

		jta.setEditable(false);
		jta.setBackground(Color.white);
		jta.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 20));
		jpa.setLayout(new BorderLayout());

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		jta.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		jta.setLineWrap(true);   
		  JScrollPane scrollPane = new JScrollPane(
	                jta,
	                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
	        );

	       add(scrollPane);
	      
		content1.add(jpa);
		
		this.add(content1, BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();

	        //初始化菜�?
	    JMenu menu1 = new JMenu("清空历史");
	    JMenuItem copyMenuItem = new JMenuItem("确定");
//		JMenuItem pasteMenuItem = new JMenuItem("粘贴");
		// 子菜单添加到�?级菜�?
	    copyMenuItem.addActionListener(new meunlistent());
	    
		menu1.add(copyMenuItem);
//	    menu1.addActionListener(new meunlistent());
	    menu1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    menu1.setAlignmentX(Box.RIGHT_ALIGNMENT);
		menuBar.add(menu1);
		
		setJMenuBar(menuBar);
		
		jtf.setPreferredSize(new Dimension(300, 100));
		jtf.setEditable(false);
		jtf.setBackground(Color.white);
		jtf.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 50));
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		jpb.setLayout(new BorderLayout());
		jpb.add(jtf);
//		content.add(jpb);
		content.add(jpb, BorderLayout.NORTH);
//		this.add(jpb, BorderLayout.NORTH);

		jbt.setLayout(new GridLayout(6, 4));
		for (int i = 0; i < btn.length; i++) {
			jbt.add(btn[i]);
			if (i != 22 && i != 7 && i != 6 && i != 0)
				btn[i].addActionListener(new listen());
			else
				btn[i].addActionListener(new result());

		}
		content.add(jbt, BorderLayout.CENTER);
//		this.add(jbt, BorderLayout.CENTER);
		this.add(content, BorderLayout.WEST);
		setTitle("计算器");
		setSize(500, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		new mainJFrame().init1();
		
	}

	  void init1() {
		init();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from mycal";
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				jta.append("\r\n");
				jta.append(rs.getString("history"));
			} 
			System.out.println("查询成功");
		} catch (SQLException e1) {
			System.out.println("查询数据库出错");
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e1) {
				System.out.println("关闭资源出错");

				e1.printStackTrace();
				rs = null;
				stmt = null;
				con = null;
			}
		}
		
	}

	class listen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (flag) {
				jtf.setText("");
				flag = false;
			}
			Button temped = (Button) e.getSource();
			jtf.setText(jtf.getText() + temped.getLabel());
		}

	}

	class result implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn[22]) {
				String check = jtf.getText();

				String pp = String.valueOf(new Solution().calculate(check));
				jtf.setText(pp);

//				数据库?
				
				init();
				Statement stmt = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					String sql = "select * from mycal";
					String sqlinsert = "insert into mycal(history) value(?)";

					String mycount = check + "=" + pp;

					stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//					rs=(ResultSet) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 

					ps = con.prepareStatement(sqlinsert);

					ps.setString(1, mycount);

					ps.executeUpdate();

					rs = stmt.executeQuery(sql);
					rs.last();
					System.out.println("列数" + rs.getRow());
//					jta.setText("历史记录");
					do {
						jta.append("\r\n");
						jta.append(rs.getString("history"));
					} while (rs.next());
					System.out.println("查询成功");
				} catch (SQLException e1) {
					System.out.println("查询数据库出错?");
					e1.printStackTrace();
				} finally {
					try {
						rs.close();
						stmt.close();
						con.close();
					} catch (SQLException e1) {
						System.out.println("关闭资源出错");

						e1.printStackTrace();
						rs = null;
						stmt = null;
						con = null;
					}
				}

				
				flag = true;
			} else if (e.getSource() == btn[6]) {
				jtf.setText("");
			} else if (e.getSource() == btn[7]) {
				String tempStr = jtf.getText();
				jtf.setText(tempStr.substring(0, tempStr.length() - 1));
			} else if (e.getSource() == btn[0]) {
				jtf.setText(String.valueOf(new Solution().calculate(jtf.getText() + "*" + jtf.getText())));
			}
		}

	}
	
	class meunlistent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jta.setText("历史记录");
			init();
			Statement stmt = null;
			PreparedStatement ps = null;
			try {
				String sqldelect = "DELETE FROM mycal";

				stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				ps = con.prepareStatement(sqldelect);

				ps.executeUpdate();


//				jta.setText("历史记录");
				
				System.out.println("删除成功");
			} catch (SQLException e1) {
				System.out.println("查询数据库出错");
				e1.printStackTrace();
			} finally {
				try {
//					rs.close();
					stmt.close();
					con.close();
				} catch (SQLException e1) {
					System.out.println("关闭资源出错");

					e1.printStackTrace();
//					rs = null;
					stmt = null;
					con = null;
				}
			}

		}
		
	}
	
	public static void init()
	{
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/counter?useSSL=false&serverTimezone=UTC";
			String dbUserName = "root";
			String dbPassword = "123456";
			String jdbcName = "com.mysql.cj.jdbc.Driver";
			Class.forName(jdbcName);
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("测试连接成功");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	
}
