import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImageModifier {

	private String path;
	private String courseId;

	public ImageModifier(String path, String courseId) {
		this.path = path;
		this.courseId = courseId;
	}

	public void modifyHTML() throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String str = new String(encoded, Charset.forName("UTF-8"));
		String newStr = new String(str.getBytes(), Charset.forName("UTF-8"));
		ArrayList<Integer> courseImageLocs = new ArrayList<Integer>();
		ArrayList<Integer> parentImageLocs = new ArrayList<Integer>();
		int endIndex;
		// Find all img srcs
		for (int index = str.indexOf("img src=\"images"); index >= 0; index = str
				.indexOf("img src=\"images", index + 1)) {
			courseImageLocs.add(index);
			int urlStartIndex = index + 16;
			endIndex = str.indexOf("\"", urlStartIndex);
			String imgUrl = str.substring(urlStartIndex, endIndex);
			StringBuilder builder = new StringBuilder();
			builder.append("th:src");
			builder.append("=");
			builder.append("\"@{~/resources/images/courses/");
			builder.append(courseId + "/");
			builder.append(imgUrl);
			builder.append("}\"");
			//String srcString ="th:src=\"@{~/resources/images/sidebar-thumbnail.png}\";
			System.out.println(builder.toString());
			//System.out.println("start: " + index + " end: " + endIndex);
			System.out.println(str.substring(urlStartIndex, endIndex));
		}
		for (int index = str.indexOf("img src=\"../../"); index >= 0; index = str
				.indexOf("img src=\"../../", index + 1)) {
			parentImageLocs.add(index);
			int urlStartIndex = index + 22;
			endIndex = str.indexOf("\"", urlStartIndex);
			String imgUrl = str.substring(urlStartIndex, endIndex);
			StringBuilder builder = new StringBuilder();
			builder.append("th:src");
			builder.append("=");
			builder.append("\"@{~/resources/images/");
			builder.append(imgUrl);
			builder.append("}\"");
			//String srcString ="th:src=\"@{~/resources/images/sidebar-thumbnail.png}\";
			System.out.println(builder.toString());
			//System.out.println("start: " + index + " end: " + endIndex);
			System.out.println(str.substring(urlStartIndex, endIndex));
		}
		System.out.println("Course specifi images hit: " + courseImageLocs.size());
		System.out.println("Parent folder images hit: " + parentImageLocs.size());
		//System.out.println(newStr);
	}
}
