package MultiTask;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class ProgressPanel extends JPanel implements PropertyChangeListener {
	// GUI components
	JButton cancel, resume, pause,delete;
	JLabel fileName, fileSize, idLabel;
	JTextField sizeField, filaNameField;
	JProgressBar prgsBar;

	// Colors and fonts for GUI components
	Color cl1 = new Color(0xD9D9D9);
	Color clBtn = new Color(0x2271E8);
	Font font2 = new Font("System", Font.BOLD, 16);
	Font headerFont = new Font("System", Font.BOLD, 18);

	// The download task this panel is monitoring
	DownloadTask task;
	static int id = 0;

	// The URL of the file to be downloaded
	private final String urlT;



	ProgressPanel( URL url) {
		this.urlT = url.toString();
		// ============== progress Panel ==================
		setSize(750, 200);
		setBackground(cl1);
		setLayout(null);
//		================= margin Panel =======================
		JPanel panelMargin = new JPanel();
		panelMargin.setBounds(0, 190, 800, 10);
		panelMargin.setBackground(clBtn);
		add(panelMargin);

//		================= file ID Label =======================
		JPanel idPanel = new JPanel();
		idPanel.setBounds(600, 20, 160, 40);
		idPanel.setBackground(new Color(255, 152, 0));

		idLabel = new JLabel("Task n° : " + id++);
		idLabel.setForeground(new Color(0xC02494));
		idLabel.setFont(new Font("Jokerman", Font.BOLD, 20));
		idPanel.add(idLabel);
		add(idPanel);
//		================= file name Label =======================
		fileName = new JLabel("File Name: ");
		fileName.setBounds(20, 10, 200, 30);
		fileName.setForeground(Color.BLACK);
		fileName.setFont(headerFont);
		add(fileName);

		filaNameField = new JTextField();
		filaNameField.setBounds(180, 10, 300, 30);
		filaNameField.setForeground(Color.BLACK);
		filaNameField.setBackground(Color.LIGHT_GRAY);
		filaNameField.setFont(headerFont);
		add(filaNameField);

//		=========== file size =================
		fileSize = new JLabel("Size :");
		fileSize.setBounds(20, 50, 200, 30);
		fileSize.setForeground(Color.BLACK);
		fileSize.setFont(headerFont);
		add(fileSize);

		sizeField = new JTextField(null);
		sizeField.setBounds(180, 55, 300, 30);
		sizeField.setForeground(Color.BLACK);
		sizeField.setBackground(Color.LIGHT_GRAY);
		sizeField.setFont(headerFont);
		add(sizeField);
		// ====== progress Bar ===============
		prgsBar = new JProgressBar(1, 100);
		prgsBar.setBounds(80, 100, 500, 40);
		prgsBar.setForeground(new Color(21, 206, 38));
		prgsBar.setBackground(new Color(253, 253, 255));
		prgsBar.setFont(font2);
		prgsBar.setStringPainted(true);
		add(prgsBar);
		// ====== cancel Btn ===============
		cancel = new JButton("Cancel");
		cancel.setBackground(new Color(244, 44, 5));
		cancel.setBounds(650, 145, 100, 35);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				task.cancel();
			}
		});
		// ====== Delete Btn ===============
		delete = new JButton("Delete");
		delete.setBackground(new Color(234, 134, 93, 194));
		delete.setBounds(650, 145, 100, 35);
		delete.setVisible(false); // this button is shown only if the download is completed
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		// ====== resume Btn ===============
		resume = new JButton("Resume");
		resume.setBackground(new Color(74, 225, 90));
		resume.setBounds(650, 100, 100, 35);
		resume.setVisible(false);
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resume.setVisible(false);
				pause.setVisible(true);
				task.resume();
			}
		});
		// ====== Pause Btn ===============
		pause = new JButton("Pause");
		pause.setBackground(new Color(243, 176, 31));
		pause.setBounds(650, 100, 100, 35);
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pause.setVisible(false);
				resume.setVisible(true);
				task.pause();

			}
		});

		JButton[] btn = { cancel, resume, pause,delete };
		for (JButton jButton : btn) {
			jButton.setFont(font2);
			add(jButton);
		}
	}


	/**
	 * Starts a new download task.
	 */
	public void buttonDownloadActionPerformed() {
        String downloadURL;
		downloadURL = urlT;
		String saveDir = "/D://download MANGER mini projet DAC/";
		try {
			prgsBar.setValue(0);
			task = new DownloadTask(this, downloadURL, saveDir);
			task.addPropertyChangeListener(this);
			task.execute();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error executing upload task: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Sets the file information displayed on this panel.
	 * @param name the file name
	 * @param size the file size in bytes
	 */
	void setFileInfo(String name, int size) {
		filaNameField.setText(name);
		sizeField.setText((double) (size / (1024 * 1024)) + "M.Byte");
	}
	/**
	 * Updates the progress bar when the progress of the download changes.
	 * @param evt the property change event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")) {
			// Update the progress bar on the EDT
			int progress = (Integer) evt.getNewValue();
			prgsBar.setValue(progress);
		}
	}
}
