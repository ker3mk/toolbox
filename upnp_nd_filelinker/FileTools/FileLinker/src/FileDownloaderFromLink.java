import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileDownloaderFromLink {
	private FileDownloaderFromLink() {
	}

	public static List<String> LinkerExtract(String url) throws IOException {
		final ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.connect(url).get();
		Elements linkelements = doc.select("a[href]");
		// Takes links which include files.
		for (Element link : linkelements) {
			result.add(link.attr("abs:href"));
		}
		return result;
	}

	public static void downloadFromUrl(URL url, String localFilename)
			throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URLConnection urlConn = url.openConnection();// connect
			is = urlConn.getInputStream();
			fos = new FileOutputStream(localFilename); // open outputstream to
														// local file
			byte[] buffer = new byte[4096]; // declare 4KB buffer
			int len;
			// while we have available data, continue downloading and storing to
			// local file

			while ((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
	}

	public final static void main(String[] args) throws IOException {
		String site = "ENTER URL HERE";
		List<String> links = FileDownloaderFromLink.LinkerExtract(site);

		for (String link : links) {
			link = link.replace(" ", "%20");
			URL url = new URL(link);
			try {
				FileDownloaderFromLink.downloadFromUrl(url, "DownloadedFiles/"
						+ (link.substring(60)));
			} catch (Exception e) {
				System.out.println(" File Not Found !!!!!" + e.getMessage()
						);
				continue;
			}

			System.out.println(link);
		}
	}
}