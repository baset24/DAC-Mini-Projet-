package MultiTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utility class for downloading files over HTTP.
 */
public class HTTPDownloadUtil {

	// HttpURLConnection instance
	private HttpURLConnection httpConn;

	/**
	 * InputStream of HttpURLConnection
	 */
	private InputStream inputStream;

	// Name of the file to be downloaded
	private String fileName;
	// Size of the file to be downloaded
	private int contentLength;

	/**
	 * Downloads a file from a URL
	 *
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void downloadFile(String fileURL) throws IOException, URISyntaxException {
		// Create a URI and open a connection to it
		URI uri = new URI(fileURL);
		URL url = uri.toURL();
		httpConn = (HttpURLConnection) url.openConnection();
			String disposition = httpConn.getHeaderField("Content-Disposition");
			contentLength = httpConn.getContentLength();
			// Extract the file name from the header field
			if (disposition != null) {
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// If not found in the header, extract it from the URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}
			// Open the input stream
			inputStream = httpConn.getInputStream();

	}

	/**
	 * Disconnects from the server and closes the input stream.
	 *
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		inputStream.close();
		httpConn.disconnect();
	}

	/**
	 * Returns the file name of the downloaded file.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Returns the size of the downloaded file.
	 */
	public int getContentLength() {
		return this.contentLength;
	}

	/**
	 * Returns the input stream of the HttpURLConnection.
	 */
	public InputStream getInputStream() {
		return this.inputStream;
	}
}
