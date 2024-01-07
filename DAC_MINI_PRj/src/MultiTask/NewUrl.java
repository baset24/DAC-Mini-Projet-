package MultiTask;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A frame that allows users to enter a single URL for downloading.
 */
public class NewUrl extends JFrame {

	// GUI components
	JPanel panel;
	JLabel urlsLabel;
	JTextField urlField;
	JButton downloadBtn, backBtn;

	// Colors and fonts for GUI components
	Color cl = new Color(0, 22, 34);
	Color cl1 = new Color(0, 2, 34);
	Font font = new Font("System", Font.BOLD, 25);
	Font font1 = new Font("System", Font.PLAIN, 20);
	Font font2 = new Font("System", Font.BOLD, 18);

	/**
	 * Constructor for the NewUrl. Initializes the GUI components.
	 */
	public NewUrl() {
		// Set frame properties
		setSize(800, 300);
		setTitle("URLs Frame");
		getContentPane().setBackground(cl);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null); // center on screen
		setLocationRelativeTo(null);

		// Create and configure the panel
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(cl1);
		panel.setBounds(0, 0, 800, 600);

		// Create and configure the label
		urlsLabel = new JLabel("Enter The URL:");
		urlsLabel.setBounds(70, 15, 200, 30);
		urlsLabel.setFont(font);
		urlsLabel.setForeground(Color.WHITE);
		panel.add(urlsLabel);

		// Create and configure the text field
		urlField = new JTextField();
		urlField.setBounds(40, 70, 700, 40);
		urlField.setFont(font1);
		panel.add(urlField);

		// Create and configure the buttons
		downloadBtn = new JButton("Download ");
		backBtn = new JButton("Back");
		downloadBtn.setBounds(380, 160, 160, 40);
		downloadBtn.setBackground(new Color(76, 175, 80));
		backBtn.setBounds(580, 160, 160, 40);
		backBtn.setBackground(new Color(0xE85122));
		backBtn.addActionListener(event -> setVisible(false));

		// Add buttons to the panel
		JButton[] btns = { downloadBtn, backBtn };
		for (JButton btn : btns) {
			btn.setFocusable(false);
			btn.setFont(font2);
			btn.setForeground(Color.black);
			panel.add(btn);
		}

		// Add panel to the frame
		getContentPane().add(panel);
	}
}
