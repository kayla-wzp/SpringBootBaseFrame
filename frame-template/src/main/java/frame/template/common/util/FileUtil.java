package frame.template.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import frame.template.common.CharsetConst;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public static final String SUFFIX_XLS = "xls";
	public static final String SUFFIX_XLSX = "xlsx";

	public static final String SUFFIX_JPG = "jpg";
	public static final String SUFFIX_JPEG = "jpeg";
	public static final String SUFFIX_PNG = "png";
	public static final String SUFFIX_GIF = "gif";
	public static final String SUFFIX_BMP = "bmp";

	public static final String SUFFIX_PDF = "pdf";
	public static final String SUFFIX_CSV = "csv";
	public static final String SUFFIX_TXT = "txt";
	public static final String SUFFIX_XML = "xml";
	public static final String SUFFIX_DOC = "doc";
	public static final String SUFFIX_DOCX = "docx";
	public static final String SUFFIX_HTML = "html";

	public static final String SUFFIX_ZIP = "zip";
	public static final String SUFFIX_RAR = "rar";


	public static final String[] SUFFIX_ARR_IMAGE = {SUFFIX_JPG, SUFFIX_PNG, SUFFIX_JPEG, SUFFIX_GIF};
	public static final String[] SUFFIX_ARR_EXCEL = {SUFFIX_XLS, SUFFIX_XLSX};

	private static final String ERR_TEXT = "转存文件出错";

	public static void createDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static File createFileWithDir(String filePath) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}

	public static String getResourceFilePath(String fileName) {
		String path = FileUtil.class.getResource(fileName).getPath();
		return path;
	}

	public static void save(MultipartFile multipartFile, File file) {
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			log.error(ERR_TEXT, e);
		}
	}

	public static String getFileNameSuffix(String fileName) {
		if (fileName.lastIndexOf(StrUtil.DOT) != -1) {
			return fileName.substring(fileName.lastIndexOf(StrUtil.DOT) + 1).toLowerCase();
		}
		return "";
	}

	public static void validateFileNameSuffix(String fileName, String[] suffixArr) {
		String suffix = getFileNameSuffix(fileName);
		if (CollectionUtil.notContain(suffixArr, suffix)) {
			log.error(ERR_TEXT, "不支持改格式文件上传");
		}
	}

	public static void stringToFile(String str, String filePath) {
		File distFile = createFileWithDir(filePath);
		try (BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
				BufferedWriter bufferedWriter =
						new BufferedWriter(new OutputStreamWriter(new FileOutputStream(distFile), CharsetConst.UTF8))) {

			char buf[] = new char[1024];
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			log.error(ERR_TEXT, e);
		}
	}

	public static File streamToFile(OutputStream outputStream, String filePath) {
		File distFile = createFileWithDir(filePath);
		try (FileOutputStream out = new FileOutputStream(distFile);
				ByteArrayOutputStream stream = (ByteArrayOutputStream) outputStream;) {
			out.write(stream.toByteArray());
			return distFile;
		} catch (Exception e) {
			log.error(filePath + ERR_TEXT, e);
			return null;
		}
	}
}
