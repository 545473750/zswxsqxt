package cn.com.opendata.attachment;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClientAttachmentApplication
{
	public static final String Bean="Client_Attachment";
	// 资源文件目录名
	public static final String DEST_FOLDER_NAME = "/cn.com.opendata.attachments/";

	// 资源文件在JAR包内的路径
	private static final String SOURCES_PATH = "/cn/com/opendata/attachment/images/";

	// 所有图片名称
	private static final List IMAGES_NAME = Arrays.asList(new String[] {
			"atch_doc.gif", "atch_html.gif", "atch_none.gif", "atch_ppt.gif",
			"atch_rar.gif", "atch_txt.gif", "atch_wmv.gif", "atch_xls.gif",
			"arrow.gif", "loading.gif", "ImageDialog.js" });

	// 下载Servlet访问路径
	private String downloadPath;

	// 上传Servlet访问路径
	private String uploadPath;

	// 上传界面访问路径
	private String uploadUIPath;

	// 错误提示界面访问路径
	private String errorMessageUIPath;
	
	//webservices访问地址
	private String webservicesUrl;
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.opendata.platform.Application#deploy()
	 */
	public boolean deploy() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.opendata.platform.Application#init()
	 */
	public boolean init() {
		String sourceDir = this.getRootRealPath();
		this.createDitchnetDir(sourceDir + DEST_FOLDER_NAME);
		String imgName, sourcePath, destPath;
		URL sourceURL;
		for (Iterator iter = IMAGES_NAME.iterator(); iter.hasNext();) {
			imgName = (String) iter.next();
			sourcePath = SOURCES_PATH + imgName;
			sourceURL = getClass().getResource(sourcePath);
			destPath = sourceDir + DEST_FOLDER_NAME + imgName;
			writeFile(sourceURL, destPath);
		}
		return true;
	}

	/**
	 * 构造方法初始化配置<br>
	 * 必须的参数:
	 * <ul>
	 * <li>download-url：下载servlet的访问路径，需要与web.xml中的配置相同。</li>
	 * <li>upload-url：附件上传servlet的访问地址，需要与web.xml配置相同。</li>
	 * <li>upload-ui-url：选择被上传附件的页面。本应用已提供也可自定义，具体实现方式将在扩展方法中提到。</li>
	 * <li>error-message-url：出现错误时错误提示页面。本应用已提供也可自定义。</li>
	 * </ul>
	 * 
	 * @param args
	 *            初始化参数的HashMap
	 */
	public ClientAttachmentApplication(Map<String, String> args) {
		this.downloadPath = args.get("download-url");
		this.uploadPath = args.get("upload-url");
		this.uploadUIPath = args.get("upload-ui-url");
		this.errorMessageUIPath = args.get("error-message-url");
		this.webservicesUrl = args.get("webservices-url");
		//this.init();
	}

	/**
	 * @return the downloadPath
	 */
	public String getDownloadPath() {
		return downloadPath;
	}

	/**
	 * @return the uploadPath
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	/**
	 * @return the uploadUIPath
	 */
	public String getUploadUIPath() {
		return uploadUIPath;
	}

	
	/**
	 * @return the errorMessageUIPath
	 */
	public String getErrorMessageUIPath() {
		return errorMessageUIPath;
	}


	private String getRootRealPath() {
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();
		String[] realPath = classPath.split("WEB-INF/classes/");
		return realPath[0].substring(1).replaceAll("/", "\\\\");
	}

	/**
	 * 输出一个文件
	 * 
	 * @param fromURL
	 *            文件来自的URL
	 * @param toPath
	 *            文件被写到的完整路径
	 */
	private void writeFile(final URL fromURL, final String toPath) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(fromURL.openStream());
			out = new BufferedOutputStream(new FileOutputStream(toPath));
			int len;
			byte[] buffer = new byte[4096];
			while ((len = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in!=null)
				{
					in.close();
				}
				if(out!=null)
				{
					out.close();
				}
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建资源根目录
	 * 
	 * @param dirPath
	 *            目录名
	 */
	private void createDitchnetDir(final String dirPath) {
		File dir = null;
		try {
			dir = new File(dirPath);
			dir.mkdir();
		} catch (Exception e) {
//			e.printStackTrace();
			// log.error("Error creating Ditchnet dir");
		}
	}

	public String getWebservicesUrl()
	{
		return webservicesUrl;
	}

	public void setWebservicesUrl(String webservicesUrl)
	{
		this.webservicesUrl = webservicesUrl;
	}
}
