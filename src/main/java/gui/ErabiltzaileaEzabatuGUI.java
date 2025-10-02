package gui;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bidaiaria;
import domain.Driver;
import domain.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class ErabiltzaileaEzabatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public ErabiltzaileaEzabatuGUI(User u) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatuGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(u instanceof Bidaiaria) {
			setBounds(100, 100, 450, 300);
		} else {
			setBounds(800, 100, 450, 300);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEz = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatuGUI.Ez"));
		btnEz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if(u instanceof Bidaiaria) {
					JFrame a = new BidaiariGUI((Bidaiaria)u);
					a.setVisible(true);
				} else {
					JFrame a = new GidariGUI((Driver)u);
					a.setVisible(true);
				}
			}
			
		});
		btnEz.setBounds(65, 188, 121, 43);
		contentPane.add(btnEz);
		
		JButton btnBai = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatuGUI.Bai"));
		btnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.erabiltzaileaEzabatu(u);
				for (Window window : Window.getWindows()) {
                    window.dispose();
				}
			}
		});
		
		
		btnBai.setBounds(244, 188, 106, 43);
		contentPane.add(btnBai);
		
		JLabel JlabelZiur = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatuGUI.Ziur"));
		JlabelZiur.setHorizontalAlignment(SwingConstants.CENTER);
		JlabelZiur.setBounds(65, 57, 285, 13);
		contentPane.add(JlabelZiur);
	}
}