package com.opendata.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件工具类
 * @author 付威
 */
public class FileUtil
{

    /**
     * 创建新文件并且保存
     * 
     * @param src
     * @return
     */
    public static int createNewFile(File src, String newFileName, String uploadPath)
    {
        File dir = new File(uploadPath);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        File dst = new File(dir, newFileName);
        InputStream is = null;
        OutputStream os = null;
        int fileSize = 0;
        try
        {
            is = new BufferedInputStream(new FileInputStream(src));
            os = new BufferedOutputStream(new FileOutputStream(dst));
            fileSize = is.available();
            byte buffer[] = new byte[8192];
            int len = 0;
            while ((len = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                if (os != null)
                {
                    os.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return fileSize;
    }

    /**
     * 获取文件扩张名
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName)
    {
        if (fileName == null)
        {
            return "";
        }
        else
        {
            int dotindex = fileName.lastIndexOf(".");
            String extName = fileName.substring(dotindex + 1, fileName.length());
            extName = extName.toLowerCase();
            return extName;
        }
    }

    /**
     * 获取文件名称(不包含扩展名)
     * @param fileName
     * @return
     */
    public static String getFileNameWithoutExt(String fileName)
    {
        int dotindex = fileName.lastIndexOf(".");
        String fName = fileName.substring(0, dotindex);
        fName = fName.toLowerCase();
        return fName;
    }

    /**
     * 拷贝文件
     * @param filePathSrc
     * @param filePathDes
     * @return
     */
    public static boolean CopyFile(String filePathSrc, String filePathDes)
    {
        boolean re = false;
        File fSrc = new File(filePathSrc);
        if (!fSrc.exists())
            return false;
        try
        {
            if (fSrc.isFile())
            {
                FileInputStream input = new FileInputStream(fSrc);
                FileOutputStream output = new FileOutputStream(filePathDes);
                byte b[] = new byte[5120];
                int len;
                while ((len = input.read(b)) != -1)
                    output.write(b, 0, len);
                output.flush();
                output.close();
                input.close();
                re = true;
            }
            else
            {
                System.out.print("Error:" + filePathSrc + " is not found\uFF01");
            }
        }
        catch (IOException e)
        {
            System.out.print(e.getMessage());
        }
        return re;
    }

    /**
     * 删除物理文件
     * 
     * @param filePath
     */
    public static void deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (file.exists())
        {
            file.delete();
        }
    }

    /**
     * 删除文件夹下文件
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) throws IOException
    {
        File f = new File(filepath);
        if (f.exists())
        {
            if (f.isDirectory())
            {
                if (f.listFiles().length == 0)
                {
                    f.delete();
                }
                else
                {
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++)
                    {
                        if (delFile[j].isDirectory())
                            del(delFile[j].getAbsolutePath());
                        delFile[j].delete();
                    }

                }
                del(filepath);
            }
        }
        else
        {
            f.delete();
        }
    }

    /**
     * 获取附件原名陈
     * 
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName)
    {
        if (fileName.indexOf(".") >= 0)
        {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public static void downLoad(String filePath, HttpServletResponse response, boolean isOnLine)
    {
        try
        {
            File f = new File(filePath);
            if (!f.exists())
            {
                response.sendError(404, "File not found!");
                return;
            }
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;

            response.reset(); //非常重要
            if (isOnLine)
            { //在线打开方式
                URL u = new URL("file:///" + filePath);
                response.setContentType(u.openConnection().getContentType());
                response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
                //文件名应该编码成UTF-8
                response.setCharacterEncoding("UTF-8");
            }
            else
            { //纯下载方式
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
                //解决中文乱码问题
                response.setHeader("Content-Disposition","attachment;filename="+new String
                		(f.getName().getBytes("gbk"),"iso-8859-1"));   
                //response.setCharacterEncoding("UTF-8");
            }
            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                while ((len = br.read(buf)) != -1)
                {
                    out.write(buf, 0, len);
                }
            }
            catch (IOException e)
            {

            }

            finally
            {
                if (br != null)
                {
                    try
                    {
                        br.close();
                        out.flush();
                        out.close();
                    }
                    catch (IOException e)
                    {
                    }
                }

            }
        }
        catch (IOException e)
        {
        }
    }
    public static void downLoad(String filePath,String fileName, HttpServletResponse response, boolean isOnLine)
    {
        try
        {
            File f = new File(filePath);
            if (!f.exists())
            {
                response.sendError(404, "File not found!");
                return;
            }
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;

            response.reset(); //非常重要
            if (isOnLine)
            { //在线打开方式
                URL u = new URL("file:///" + filePath);
                response.setContentType(u.openConnection().getContentType());
                response.setHeader("Content-Disposition", "inline; filename=" + fileName);
                //文件名应该编码成UTF-8
                response.setCharacterEncoding("UTF-8");
            }
            else
            { //纯下载方式
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                //解决中文乱码问题
                response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gbk"),"iso-8859-1"));   
                //response.setCharacterEncoding("UTF-8");
            }
            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                while ((len = br.read(buf)) != -1)
                {
                    out.write(buf, 0, len);
                }
            }
            catch (IOException e)
            {

            }

            finally
            {
                if (br != null)
                {
                    try
                    {
                        br.close();
                        out.flush();
                        out.close();
                    }
                    catch (IOException e)
                    {
                    }
                }

            }
        }
        catch (IOException e)
        {
        }
    }
    /**
     * 创建新名称
     * 
     * @param seq
     * @param fileNames
     * @return
     */
    @SuppressWarnings("static-access")
    public static String createNewFileName(Long seq, String fileNames)
    {
        Calendar now = Calendar.getInstance();
        String newFileName = String.valueOf(now.get(now.YEAR)) + String.valueOf(now.get(now.MONTH) + 1) + String.valueOf(now.get(now.DATE));
        newFileName += String.valueOf(now.get(now.HOUR)) + String.valueOf(now.get(now.MINUTE)) + String.valueOf(now.get(now.SECOND));
        newFileName = newFileName + "-" + seq + "-" + fileNames;
        return newFileName;
    }
    
    /**
     * 创建文件全路径路径
     * @param filePath
     * @param request
     * @param fullName
     * @return
     */
    public static String createFilePath(String filePath,HttpServletRequest request,String fullName){
    	String targetpath = request.getSession().getServletContext().getRealPath("/upload")+File.separator+filePath;//设定目标文件位置
    	String fileFullPath = targetpath+File.separator+fullName;	//设置目标文件（路径和文件的名称）
    	return fileFullPath;
    }
    /**
     * 创建名称带时间
     * @param fileName
     * @param num
     * @return
     */
    public static String creatFullName(String fileName,int num){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//给文件名加上时间，拼成文件全称
		String date = sdf.format(new Date());
		int index = fileName.lastIndexOf(".");	
		String name = fileName.substring(0, index)+date;
		String houzhui = fileName.substring(index);
		String fullName = name +"_"+ num +houzhui;
    	return fullName;
    }
}
