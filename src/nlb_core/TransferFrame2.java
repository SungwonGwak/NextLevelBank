package nlb_core;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.TransferMgr;

public class TransferFrame2 extends JFrame implements ActionListener{
	final String[] buttonLabels = new String[] {
			"1", "2","3","4","5","6","7","8","9","00","0","�����",
	};
	
	JFrame frame;
	JTextField textField_account;
	JTextField textField_money;
	String number="";
	JButton buttons[];
	JButton admit,plus1,plus5,plus10,plusAll;
	public TransferFrame2() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 500, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC774\uCCB4\uD558\uAE30");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 20));
		lblNewLabel.setBounds(30, 30, 80, 32);
		frame.getContentPane().add(lblNewLabel);
		
		textField_account = new JTextField();
		textField_account.setText("���¹�ȣ");
		textField_account.setFont(new Font("����", Font.PLAIN, 17));
		textField_account.setBounds(30, 82, 425, 46);
		frame.getContentPane().add(textField_account);
		textField_account.setColumns(10);
		textField_account.addActionListener(this);
		
		textField_account.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_account.setText("");
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 290, 425, 394);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 3, 10, 10));
		
		buttons = new JButton[12];
		
		for (int i =0 ; i<12; i++) {
			JButton button = new JButton(buttonLabels[i]);
			button.addActionListener(this);
			panel.add(buttons[i] = button);
		}

		
		admit = new JButton("����");
		admit.setFont(new Font("����", Font.PLAIN, 20));
		admit.setBounds(30, 709, 425, 32);
		frame.getContentPane().add(admit);
		admit.addActionListener(this);
		
		JButton btnNewButton_1 = new JButton("+1��");
		btnNewButton_1.setBounds(30, 242, 97, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("+5��");
		btnNewButton_2.setBounds(139, 242, 97, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("+10��");
		btnNewButton_3.setBounds(248, 242, 97, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("����");
		btnNewButton_4.setBounds(358, 242, 97, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		textField_money = new JTextField();
		textField_money.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_money.setFont(new Font("����", Font.PLAIN, 17));
		textField_money.setColumns(15);
		textField_money.setBounds(30, 154, 425, 46);
		frame.getContentPane().add(textField_money);
		
		validate();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
//		System.out.println(e.getActionCommand());
		for (int i = 0; i<buttons.length; i++) {
			if (buttons[i]==obj) {
				String btnText = buttons[i].getText();
				if (obj==buttons[9] || obj==buttons[10]) {
					if (number.isEmpty())
						continue;
				}
				if (!(btnText.equals("�����"))){
					number+=btnText;
					textField_money.setText(number);
				} else {
					number="";
					textField_money.setText(number);
				}
			}
		}
		if ( obj == admit) {
			int account_num = Integer.parseInt(textField_account.getText());
			System.out.println(account_num);
			TransferMgr tMgr = new TransferMgr();
			boolean flag = false;
			
			flag = tMgr.Transaction_CheckAccount(account_num);
			
			if (flag!=false) {
				System.out.println("�´� �����Դϴ�.");
				
			} else
				System.out.println("Ʋ�� �����Դϴ�.");
			
		}
	}
	
	
	public static void main(String[] args) {
		new TransferFrame2();
	}
}
