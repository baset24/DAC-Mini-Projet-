package MultiTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MultiDownload extends JFrame {

    JButton newUrlBtn, newUrlsBtn;
    JPanel panel;
    JScrollPane jsp;

    Color clBtn = new Color(0x191c27);

    Font font2 = new Font("System", Font.BOLD, 16);
    NewUrl newUrl;
    UrlsFrame newUrls;

    private List<URL> urlsList;

    public MultiDownload() {
        // TODO Auto-generated constructor

        setSize(800, 700);
        setTitle("DownLoad Manager");
        getContentPane().setBackground(clBtn);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);// center on screen
        setLocationRelativeTo(null);

        newUrlBtn = new JButton("New Url");
        newUrlBtn.setBounds(0, 0, 120, 40);
        newUrlBtn.setFont(font2);
        newUrlBtn.setForeground(Color.BLACK);
        newUrlBtn.setFocusable(false);
        newUrlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Open the NewUrl frame when the button is clicked
                newUrl = new NewUrl();
                newUrl.setVisible(true);
                newUrl.downloadBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        urlsList = new ArrayList<>();
                        if (newUrl.urlField.getText().isEmpty()|| !newUrl.urlField.getText().contains("://")) {
                            JOptionPane.showMessageDialog(newUrl.urlField, "Please enter download URL!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            newUrl.urlField.requestFocus();
                            return;
                        } else {
                            try {
                                String urlFile = newUrl.urlField.getText();
                                URI uri = new URI(urlFile);
                                URL urlT2 = uri.toURL();
                                // Check if the URL exists
                                HttpURLConnection huc = (HttpURLConnection) urlT2.openConnection();
                                huc.setRequestMethod("HEAD");
                                int responseCode = huc.getResponseCode();
                                if (responseCode != HttpURLConnection.HTTP_OK) {
                                    JOptionPane.showMessageDialog(newUrl.urlField, "Invalid URL file: URL does not exist",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    newUrl.urlField.requestFocus();
                                    return;
                                }
                                // Add the entered URL to the list when the Download button is clicked
                                urlsList.add(urlT2);
                            } catch (IOException | URISyntaxException ex) {
                                JOptionPane.showMessageDialog(newUrl.urlField, "invalid URL file",
                                        ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                                newUrl.urlField.requestFocus();
                                return;
                            }
                        }
                        newUrl.setVisible(false);
                        createProgressPanels(getUrlsList());
                    }
                });
            }
        });
        getContentPane().add(newUrlBtn);

//		======================== add all ===================
        newUrlsBtn = new JButton("New URLs");
        newUrlsBtn.setBounds(120, 0, 150, 40);
        newUrlsBtn.setFont(font2);
        newUrlsBtn.setForeground(Color.BLACK);
        newUrlsBtn.setFocusable(false);

// 		========== URLs Frame to get List of URLs ==============
        newUrlsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Open the NewUrls frame when the button is clicked
                newUrls = new UrlsFrame();
                newUrls.setVisible(true);
//          =========== logic of download button ======================
                newUrls.downloadBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event1) {
                        urlsList = new ArrayList<>();
                        for (JTextField url : newUrls.urlsField) {
                            // in this parte of code, it checks any error about the url entered
                            if (url.getText().isEmpty()|| !url.getText().contains("://")) {
                                JOptionPane.showMessageDialog(url, "Please enter download URL!", "Empty input",
                                        JOptionPane.ERROR_MESSAGE);
                                url.requestFocus();
                                return;
                            } else {
                                try {
                                    String urlFile = url.getText();
                                    URI uri = new URI(urlFile);
                                    URL urlT2 = uri.toURL();
                                    // Check if the URL exists
                                    HttpURLConnection huc = (HttpURLConnection) urlT2.openConnection();
                                    huc.setRequestMethod("HEAD");
                                    int responseCode = huc.getResponseCode();
                                    if (responseCode != HttpURLConnection.HTTP_OK) {
                                        JOptionPane.showMessageDialog(newUrl.urlField, "Invalid URL file: URL does not exist",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        newUrl.urlField.requestFocus();
                                        return;
                                    }
                                    // add the url to the urlList if there is the url correct
                                    urlsList.add(urlT2);
                                }catch (IOException | URISyntaxException ex) {
                                    // If the URL is not valid, show an error message
                                    JOptionPane.showMessageDialog(url, "Invalid URL file: " + ex.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    url.requestFocus();
                                    return;
                                }
                            }
                        }
                        newUrls.setVisible(false);
                        // call method that create progress panels
                        createProgressPanels(getUrlsList());
                    }
                });
            }
        });

        getContentPane().add(newUrlsBtn);
//		====  Create and configure the panel and scroll pane  =====
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(clBtn);
        panel.setBounds(0, 40, 800, 650);
        jsp = new JScrollPane(panel);
        jsp.setVisible(false);
        jsp.setBounds(0, 40, 800, 650);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(jsp, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

       /* */try {
            // Set look and feel and open the MultiDownload frame
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new MultiDownload().setVisible(true));
    }


     // Returns the list of URLs to be downloaded.

    public List<URL> getUrlsList() {
        return urlsList;
    }

    /**
     * Creates ProgressPanel instances for each URL in the list and adds them to the panel.
     */
    public void createProgressPanels(List<URL> progressPanel) {
        for (URL urlPanel : progressPanel) {
            jsp.setVisible(true);
            ProgressPanel panelProgress = new ProgressPanel( urlPanel);
            panelProgress.setPreferredSize(new Dimension(750, 200));
            panelProgress.buttonDownloadActionPerformed();
            panel.add(panelProgress);
            panel.revalidate();
            panel.repaint();
        }
    }
}