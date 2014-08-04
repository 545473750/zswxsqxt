package cn.com.opendata.attachment.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 文件转换成base64工具
 * @author Administrator
 *
 */
public class File2Base64Code
{

	/**
	 * 指定文件转换成base64字符串
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception
	{
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);

	}

	/**
	 * base64字符串转换成文件
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) throws Exception
	{
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();

	}

	/**
	 * base64字符串保存为文本文件
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void toFile(String base64Code, String targetPath) throws Exception
	{
		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}
	
	/**
	 * base64字符串转输入流
	 * @param content
	 * @return
	 */
	public static InputStream base64ToInputStream(String base64Code)throws Exception
	{
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		InputStream is = new ByteArrayInputStream(buffer);  
	    return is;  
	}
	
	/**
	 * 字符串转输入流
	 * @param content
	 * @return
	 */
	public static InputStream StringToInputStream(String content)
	{
		InputStream is = new ByteArrayInputStream(content.getBytes());  
	     return is;  
	}
	
	/**
	 * 将byte数组转为输入流
	 * @param content
	 * @return
	 */
	public static InputStream byteToInputStream(byte[] content)
	{
		InputStream is = new ByteArrayInputStream(content);
		 return is;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			String base64Code = encodeBase64File("D:\\1.jpg");
			System.out.println(base64Code);
			decoderBase64File(base64Code, "D:\\2.jpg");
			toFile(base64Code, "D:\\three.txt");
		} catch (Exception e)
		{

			e.printStackTrace();
		}
	}

}
