package MultiTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class DownloadTask extends SwingWorker<Void, Void> {
	private static final int BUFFER_SIZE = 4096;
	private final String downloadURL;
	private final String saveDirectory;
	private final ProgressPanel progressPanel;

	private volatile boolean paused = false;
	private final Object lock = new Object();

	public void pause() {
		synchronized (lock) {
			paused = true;
		}
	}
	public void resume() {
		synchronized (lock) {
			paused = false;
			lock.notify();
		}
	}
	public void cancel() {
		cancel(true);
	}
	public DownloadTask(ProgressPanel progressPanel, String downloadURL, String saveDirectory) {
		this.progressPanel = progressPanel;
		this.downloadURL = downloadURL;
		this.saveDirectory = saveDirectory;
	}

	/**
	 * Executed in background thread
	 */
	@Override
	protected Void doInBackground() throws Exception {
        FileOutputStream outputStream;
        HTTPDownloadUtil util ;
        try {
            util = new HTTPDownloadUtil();
            util.downloadFile(downloadURL);
            // set file information on the GUI
            progressPanel.setFileInfo(util.getFileName(), util.getContentLength());
            String saveFilePath = saveDirectory + File.separator + util.getFileName();
            InputStream inputStream = util.getInputStream();
            // opens an output stream to save into file
            outputStream = new FileOutputStream(saveFilePath);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long totalBytesRead = 0;
            int percentCompleted;
            long fileSize = util.getContentLength();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (isCancelled()) {
                    inputStream.close();
                    return null;  // Return early if the task has been cancelled
                }
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
				// If the task is paused, pause the loop
                synchronized (lock) {
                    while (paused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
							System.out.println(e);
                        }
                    }
                }
				// Update the progress property. This will trigger the propertyChange() method
				// in the SwingWorker class, which will in turn update the progress bar.
                setProgress(percentCompleted);
            }
			outputStream.close();
			util.disconnect();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(progressPanel, "Error downloading file: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            setProgress(0);
            cancel(true);
        }
        return null;
    }

	/**
	 * Executed in Swing's event dispatching thread
	 */
	@Override
	protected void done() {
		try {
			// Check if the task completed or was cancelled, update the GUI accordingly
			if (!isCancelled()) {
				get(); // This will throw an exception if an error occurred during doInBackground
				// Task completed successfully
				JOptionPane.showMessageDialog(progressPanel, "Download completed", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (InterruptedException | ExecutionException e) {
			// Handle exceptions thrown by doInBackground
			JOptionPane.showMessageDialog(progressPanel, "Error occurred during download: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			progressPanel.prgsBar.setVisible(false);
			progressPanel.cancel.setVisible(false);
			progressPanel.resume.setVisible(false);
			progressPanel.pause.setVisible(false);
			progressPanel.delete.setVisible(true);
		}
	}

}
