package com.opendata.common.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author 付威
 */
public class FileUtils {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * 复制文件目录
     * @param srcDir 源文件目录
     * @param destDir 被复制文件目录
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    /**
     * 复制文件目录
     * @param srcDir  源文件目录
     * @param destDir 被复制文件目录
     * @param preserveFileDate 是否记录保存文件日期
     * @throws IOException 
     */
    public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    /**
     * 复制文件目录
     * @param srcDir 源文件目录
     * @param destDir 被复制文件目录
     * @param filter 文件过滤器
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File destDir, FileFilter filter) throws IOException {
        copyDirectory(srcDir, destDir, filter, true);
    }

    /**
     * 复制文件目录
     * @param srcDir 源文件目录
     * @param destDir 被复制文件目录
     * @param filter 文件过滤器
     * @param preserveFileDate 是否记录保存文件日期
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (srcDir.exists() == false) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (srcDir.isDirectory() == false) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        // Cater for destination being directory within the source directory (see IO-141)
        List exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList(srcFiles.length);
                for (int i = 0; i < srcFiles.length; i++) {
                    File copiedFile = new File(destDir, srcFiles[i].getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
    }

    /**
     * 复制文件目录
     * @param srcDir 源文件目录
     * @param destDir 被复制文件目录
     * @param filter 文件过滤器
     * @param preserveFileDate 是否记录保存文件日期
     * @param exclusionList 文件集合
     * @throws IOException
     */
    private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List exclusionList) throws IOException {
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (destDir.mkdirs() == false) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
            if (preserveFileDate) {
                destDir.setLastModified(srcDir.lastModified());
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        // recurse
        File[] files = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + srcDir);
        }
        for (int i = 0; i < files.length; i++) {
            File copiedFile = new File(destDir, files[i].getName());
            if (exclusionList == null || !exclusionList.contains(files[i].getCanonicalPath())) {
                if (files[i].isDirectory()) {
                    doCopyDirectory(files[i], copiedFile, filter, preserveFileDate, exclusionList);
                } else {
                    doCopyFile(files[i], copiedFile, preserveFileDate);
                }
            }
        }
    }
    
    /**
     * 复制文件
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile)throws IOException{
    	doCopyFile(srcFile, destFile, true);
    }

    /**
     * 复制文件
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @param preserveFileDate 是否记录保存文件日期
     * @throws IOException
     */
    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }
        //先判断此文件目录是不是存在 如果不存在则创建
        if(!destFile.getParentFile().exists()){
        	destFile.mkdirs();
        }
        if(!destFile.exists()){
        	destFile.createNewFile();
        }

        FileInputStream input = new FileInputStream(srcFile);
        try {
            FileOutputStream output = new FileOutputStream(destFile);
            try {
                copy(input, output);
            } finally {
                try {
                    output.close();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        } finally {
            try {
                input.close();
            } catch (IOException ioe) {
                // do nothing
            }
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    /**
     * 复制文件流
     * @param input 输入流
     * @param output 输出流
     * @return 返回文件大小
     * @throws IOException
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 复制大文件 
     * @param input 输入流
     * @param output 输出流
     * @return 返回文件大小
     * @throws IOException
     */
    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    /**   
   * 删除文件，可以是单个文件或文件夹   
    * @param   fileName    待删除的文件名   
    * @return 文件删除成功返回true,否则返回false   
    */    
    public static boolean delete(String fileName){     
        File file = new File(fileName);     
        if(!file.exists()){     
           return false;     
        }else{     
           if(file.isFile()){     
                    
                return deleteFile(fileName);     
            }else{     
                return deleteDirectory(fileName);     
           }     
        }     
   }    
    
    /**   
    * 删除单个文件   
    * @param   fileName    被删除文件的文件名   
    * @return 单个文件删除成功返回true,否则返回false   
    */    
   public static boolean deleteFile(String fileName){     
        File file = new File(fileName);     
        if(file.isFile() && file.exists()){     
            file.delete();     
           return true;     
        }else{     
           return false;     
       }     
    }     
         
   /**   
    * 删除目录（文件夹）以及目录下的文件   
    * @param   dir 被删除目录的文件路径   
    * @return  目录删除成功返回true,否则返回false   
    */    
   public static boolean deleteDirectory(String dir){     
        //如果dir不以文件分隔符结尾，自动添加文件分隔符      
        if(!dir.endsWith(File.separator)){     
           dir = dir+File.separator;     
        }     
       File dirFile = new File(dir);     
       //如果dir对应的文件不存在，或者不是一个目录，则退出      
       if(!dirFile.exists() || !dirFile.isDirectory()){     
           return false;     
       }     
        boolean flag = true;     
        //删除文件夹下的所有文件(包括子目录)      
       File[] files = dirFile.listFiles();     
       for(int i=0;i<files.length;i++){     
            //删除子文件      
            if(files[i].isFile()){     
              flag = deleteFile(files[i].getAbsolutePath());     
               if(!flag){     
                   break;     
               }     
           }     
           //删除子目录      
            else{     
               flag = deleteDirectory(files[i].getAbsolutePath());     
              if(!flag){     
                   break;     
               }     
           }     
       }     
             
        if(!flag){     
           return false;     
        }     
             
       //删除当前目录      
        if(dirFile.delete()){     
            return true;     
       }else{     
            return false;     
        }     
   }     

}
