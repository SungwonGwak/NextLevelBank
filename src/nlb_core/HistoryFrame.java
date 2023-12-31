package nlb_core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import beans.AccountsBean;
import beans.HistoryBean;
import database.HistoryMgr;
import database.MemberMgr;
import nlb_core.MainFrame.SharedData;
import beans.MemberBean;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.net.ssl.SSLContext;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.List;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.sql.*;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class HistoryFrame extends JFrame {

	private JPanel Account_Histroy;
	private JLabel Account_Balance;
	private String Member_ID;
	private int days = 3;
	HistoryMgr th;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Vector<HistoryBean> vlist;
	int doAccount = 0;
	MemberBean bean;
	List list;
	JTable historyTable;
	DefaultTableModel tableModel;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HistoryFrame frame = new HistoryFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * 
	 * @param do_account
	 */
	public void getTransferList() {
		HistoryMgr transferHistory = new HistoryMgr();
		vlist = transferHistory.getHistoryList(doAccount, days);
		for (int i = 0; i < vlist.size(); i++) {
			HistoryBean bean = vlist.get(i);
			String[] rowData = {
					String.valueOf(bean.getTransfer_No()), 
					bean.getTransfer_Date(), 
					bean.getTransfer_Memo(),
					bean.getTransfer_Category(),
					String.valueOf(bean.getTransfer_Balance()),
					String.valueOf(bean.getTransfer_Take_Account()),
					String.valueOf(bean.getTransfer_Do_Balance())
					};
			tableModel.addRow(rowData);
		}
	}
	
	public HistoryFrame(int do_account, MemberBean mBean, AccountsBean aBean) {
		this.setResizable(false);
		this.doAccount = do_account;
		this.bean = mBean;
		setBounds(100, 100, 500, 800);
		Account_Histroy = new JPanel();
		Account_Histroy.setBackground(new Color(255, 255, 255));
		Account_Histroy.setBorder(new EmptyBorder(5, 5, 5, 5));
		int centerX = (screenSize.width -this.getWidth()) / 2; // 창 중앙에 frame
	    int centerY = (screenSize.height - this.getHeight()) / 2;
	    this.setLocation(centerX,centerY);
		
		setContentPane(Account_Histroy);
		Account_Histroy.setLayout(null);

		JPanel History_Value = new JPanel();
		History_Value.setBackground(new Color(255, 228, 0));
		History_Value.setBounds(0, 0, 484, 254);
		Account_Histroy.add(History_Value);
		History_Value.setLayout(null);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainFrame mf = new MainFrame(mBean);
				SharedData.setFlag(0);
				mf.getFrame().setVisible(true);
			}
		});

		JButton transferbtn = new JButton("이체하기");
		transferbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TransferFrame tf = new TransferFrame(do_account, aBean, mBean);
				
			}
		});
		transferbtn.setBackground(new Color(221, 199, 0));
		transferbtn.setBorderPainted(false);
		transferbtn.setBounds(100, 170, 300, 35);
		transferbtn.setFont(new Font("나눔바른고딕", Font.BOLD, 20));
		History_Value.add(transferbtn);

		JLabel Account_Master = new JLabel(bean.getMEMBER_Name() + "의 통장");
		Account_Master.setFont(new Font("굴림", Font.BOLD, 20));
		Account_Master.setBounds(173, 21, 134, 35);
		History_Value.add(Account_Master);

		JLabel Account_Num = new JLabel("" + do_account);
		Account_Num.setForeground(Color.LIGHT_GRAY);
		Account_Num.setHorizontalAlignment(SwingConstants.CENTER);
		Account_Num.setFont(new Font("나눔바른고딕", Font.PLAIN, 20));
		Account_Num.setBounds(139, 80, 200, 25);
		History_Value.add(Account_Num);

		JLabel Account_Balance = new JLabel();
		Account_Balance.setFont(new Font("나눔바른고딕", Font.BOLD, 35));
		Account_Balance.setBounds(100, 100, 300, 70);
		Account_Balance.setHorizontalAlignment(SwingConstants.CENTER);
		History_Value.add(Account_Balance);
		HistoryMgr transferHistory = new HistoryMgr();
		HistoryBean historybean = transferHistory.getHistoryList1(doAccount);
		Account_Balance.setText(String.valueOf(historybean.getAccount_Balance()) + "원"); // historybean 사용

		JPanel History_Value2 = new JPanel();
		History_Value2.setBackground(new Color(255, 255, 255));
		History_Value2.setBounds(0, 248, 484, 49);
		Account_Histroy.add(History_Value2);

		String a[] = {"선택","3일", "1주일", "1개월" };
		History_Value2.setLayout(null);
		JComboBox comboBox = new JComboBox(a);
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(427, 10, 57, 21);
		History_Value2.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex()==0) {
	
		    	}else if (comboBox.getSelectedIndex()==1) {
		    		// 리스트 갱신하는 메소드
		    		days = 3;
		    		tableModel.setRowCount(0);
		    		transferHistory.getHistoryList(do_account, days);
		    		getTransferList();
		    	} else if (comboBox.getSelectedIndex()==2) {
		    		days = 7;
		    		tableModel.setRowCount(0);
		    		transferHistory.getHistoryList(do_account, days);
		    		getTransferList();
		    	}else if (comboBox.getSelectedIndex()==3) {
		    		// 리스트 갱신하는 메소드
		    		days = 30;
		    		tableModel.setRowCount(0);
		    		transferHistory.getHistoryList(do_account, days);
		    		getTransferList();
			}
			}
		});

		JPanel History_Value3 = new JPanel();
		History_Value3.setBackground(new Color(255, 255, 255));
		History_Value3.setBounds(0, 296, 484, 465);
		vlist = transferHistory.getHistoryList(doAccount, days);
		tableModel = new DefaultTableModel();
		historyTable = new JTable(tableModel);
		tableModel.addColumn("이체번호");
		tableModel.addColumn("이체날짜");
		tableModel.addColumn("메모");
		tableModel.addColumn("이체유형");
		tableModel.addColumn("이체금액");
		tableModel.addColumn("보내는곳");
		tableModel.addColumn("계좌잔액");

		// 데이터를 테이블 모델에 추가
		for (int i = 0; i < vlist.size(); i++) {
			HistoryBean bean = vlist.get(i);
			String[] rowData = {
					String.valueOf(bean.getTransfer_No()), 
					bean.getTransfer_Date(),
					bean.getTransfer_Memo(),
					bean.getTransfer_Category(), 
					String.valueOf(bean.getTransfer_Balance()),
					String.valueOf(bean.getTransfer_Take_Account()),
					String.valueOf(bean.getTransfer_Do_Balance())};
			tableModel.addRow(rowData);
		}
		JScrollPane tableScrollPane = new JScrollPane(historyTable);
		tableScrollPane.setBounds(0, 0, 484, 465);
		History_Value3.setLayout(new BorderLayout()); // 세로로 정렬되도록 설정
		History_Value3.add(tableScrollPane, BorderLayout.CENTER); // JScrollPane를 프레임에 추가
		Account_Histroy.add(History_Value3);
		setVisible(true);
	}
}