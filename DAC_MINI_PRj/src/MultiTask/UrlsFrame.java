package MultiTask;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * A frame that allows users to enter URLs for downloading.
 */
public class UrlsFrame extends JFrame implements ActionListener {

	// GUI components
	JPanel panel;
	JLabel urlsLabel;
	JTextField url1, url2, url3, url4, url5;
	JButton downloadBtn, backBtn;

	// Colors and fonts for GUI components
	Color cl = new Color(0, 22, 34);
	Color cl1 = new Color(0, 2, 34);
	Font font = new Font("System", Font.BOLD, 25);
	Font font1 = new Font("System", Font.PLAIN, 20);
	Font font2 = new Font("System", Font.BOLD, 18);

	// Array of URL text fields
	JTextField[] urls = { url1, url2, url3, url4, url5 };

	// List to store the text fields
	protected List<JTextField> urlsField = new ArrayList<>();

	/**
	 * Constructor for the UrlsFrame. Initializes the GUI components.
	 */
	public UrlsFrame() {
		// Set frame properties
		setSize(800, 600);
		setTitle("URLs Frame");
		getContentPane().setBackground(cl);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);// center on screen
		setLocationRelativeTo(null);

		// Create and configure the panel
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(cl1);
		panel.setBounds(0, 0, 800, 600);
//		=====================================
		urlsLabel = new JLabel("Enter URLs");
		urlsLabel.setBounds(70, 15, 150, 30);
		urlsLabel.setFont(font);
		urlsLabel.setForeground(Color.WHITE);
		panel.add(urlsLabel);

		// Create and configure the text fields
		int boundY = 70;
		for (JTextField url : urls) {
			url = new JTextField();
			url.setBounds(40, boundY, 700, 40);
			boundY += 70;
			url.setFont(font1);
			url.addActionListener(this);
			urlsField.add(url);
			panel.add(url);
		}

		// Create and configure the buttons
		downloadBtn = new JButton("Download All");
		backBtn = new JButton("Back");
		downloadBtn.setBounds(380, 450, 160, 40);
		downloadBtn.setBackground(new Color(76, 175, 80));
		backBtn.setBounds(580, 450, 160, 40);
		backBtn.setBackground(new Color(244, 67, 54));
		backBtn.addActionListener(e -> setVisible(false));
		JButton[] btns = { downloadBtn, backBtn };
		for (JButton btn : btns) {
			btn.setFocusable(false);
			btn.setFont(font2);
			btn.setForeground(Color.black);
			panel.add(btn);
		}
		getContentPane().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(backBtn)){
			setVisible(false);
		}
	}

}
