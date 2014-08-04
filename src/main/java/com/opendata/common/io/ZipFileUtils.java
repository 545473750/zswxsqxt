package com.opendata.common.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 功能:zip压缩、解压(支持中文文件名) 说明:使用Apache Ant提供的zip工具org.apache.tools.zip实现zip压缩和解压功能.
 * 解决了由于java.util.zip包不支持汉字的问题。 使用java.util.zip包时,当zip文件中有名字为中文的文件时, 就会出现异常:
 * "Exception in thread "main " java.lang.IllegalArgumentException at
 * java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:285) <p/> <p/>
 * 注意: 1、使用时把ant.jar放到classpath中,程序中使用 import org.apache.tools.zip.*; 2、本程序使用Ant
 * 1.7.1 中的ant.jar部分类，ant-zip-1.7.1只保留Ant的zip压缩功能，以减小ant.jar的大小。
 * 4、ZipEntry的isDirectory()方法中,目录以"/"结尾。 <p/> <p/>
 * ------------------------------------------------ 可将主函数注释去掉以单独测试ZipFileUtils类。
 * Compile: javac -cp Ant.jar ZipFileUtils.java <p/> Usage:(将ant.jar直接放在当前目录)
 * 压缩: java -cp Ant.jar;. ZipFileUtils -zip [directoryName | fileName]... 解压:
 * java -cp Ant.jar;. ZipFileUtils -unzip "fileName.zip" <p/>
 * ------------------------------------------------
 * @author 付威
 */
public class ZipFileUtils {

    private static int bufSize = 8096; // size of bytes

    /**
     * 压缩文件夹内的所有文件和目录(不支持有中文目录或文件名)。
     *
     * @param zipDirectory
     *            需要压缩的文件夹名
     * @return 成功返回null，否则返回失败信息
     */
    public static String zip(String zipDirectory) {
        File zipDir = new File(zipDirectory);
        return zip(zipDirectory, zipDir.getPath(), false);
    }

    /**
     * 压缩文件夹内的所有文件和目录(不支持有中文目录或文件名)。
     *
     * @param zipDirectory
     *            需要压缩的文件夹名
     * @param zipFileName
     *            压缩后的zip文件名，如果后缀不是".zip, .jar, .war"， 自动添加后缀".zip"。
     * @param includeSelfDir
     *            是否包含自身文件夹
     * @return 成功返回null，否则返回失败信息
     */
    public static String zip(String zipDirectory, String zipFileName,
            boolean includeSelfDir) {
        File zipDir = new File(zipDirectory);
        File[] willZipFileArr;
        if (includeSelfDir || zipDir.isFile()) {
            willZipFileArr = new File[]{zipDir};
        } else {
            willZipFileArr = zipDir.listFiles();
        }
        return zip(willZipFileArr, zipFileName);
    }

    /**
     * 压缩多个文件或目录。可以指定多个单独的文件或目录。
     *
     * @param files
     *            要压缩的文件或目录组成的<code>File</code>数组。
     * @param zipFileName
     *            压缩后的zip文件名，如果后缀不是".zip, .jar, .war"，自动添加后缀".zip"。
     * @return 成功返回null，否则返回失败信息
     */
    public static String zip(File[] files, String zipFileName) {
        if (files == null) {
            return "待压缩的文件不存在。";
        }

        // 未指定压缩文件名，默认为"ZipFile"
        if (zipFileName == null || zipFileName.equals("")) {
            zipFileName = "ZipFile";
        }

        // 添加".zip"后缀
        if (!zipFileName.toLowerCase().endsWith(".zip")
                && !zipFileName.toLowerCase().endsWith(".jar")
                && !zipFileName.toLowerCase().endsWith(".war")) {
            zipFileName += ".zip";
        }

        JarOutputStream jarOutput = null;
        try {
            jarOutput = new JarOutputStream(new FileOutputStream(zipFileName),
                    new Manifest());

            for (File file : files) {
                zipFiles(file, jarOutput, "");
            }
            System.out.println("压缩文件成功：" + zipFileName);
        } catch (Exception e) {
            System.out.println("异常：" + e);
        } finally {
            if (jarOutput != null) {
                try {
                    jarOutput.close();
                } catch (IOException e) {
                    System.out.println("异常：" + e);
                }
            }
        }
        return null;
    }

    /**
     * @param file
     *            压缩文件
     * @param jos
     *            JarOutputStream
     * @param pathName
     *            相对路径
     * @throws Exception
     *             异常
     */
    private static void zipFiles(File file, JarOutputStream jos, String pathName)
            throws Exception {
        String fileName = pathName + file.getName();
        if (file.isDirectory()) {
            fileName = fileName + "/";
            jos.putNextEntry(new JarEntry(fileName));
            String fileNames[] = file.list();
            if (fileNames != null) {
                for (int i = 0; i < fileNames.length; i++) {
                    zipFiles(new File(file, fileNames[i]), jos, fileName);
                }
                jos.closeEntry();
            }
        } else {
            JarEntry jarEntry = new JarEntry(fileName);
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(file));
            jos.putNextEntry(jarEntry);

            byte[] buf = new byte[bufSize];
            int len;
            while ((len = in.read(buf)) >= 0) {
                jos.write(buf, 0, len);
            }
            in.close();
            jos.closeEntry();
        }
    }

    /**
     * 压缩文件夹内的所有文件和目录。
     *
     * @param zipDirectory
     *            需要压缩的文件夹名
     * @return 成功返回null，否则返回失败信息
     */
    public static String antzip(String zipDirectory) {
        File zipDir = new File(zipDirectory);
        return antzip(zipDirectory, zipDir.getPath(), false);
    }

    /**
     * 压缩文件夹内的所有文件和目录。
     *
     * @param zipDirectory
     *            需要压缩的文件夹名
     * @param zipFileName
     *            压缩后的zip文件名，如果后缀不是".zip, .jar, .war"， 自动添加后缀".zip"。
     * @param includeSelfDir
     *            是否包含自身文件夹
     * @return 成功返回null，否则返回失败信息
     */
    public static String antzip(String zipDirectory, String zipFileName,
            boolean includeSelfDir) {
        File zipDir = new File(zipDirectory);
        File[] willZipFileArr;
        if (includeSelfDir || zipDir.isFile()) {
            willZipFileArr = new File[]{zipDir};
        } else {
            willZipFileArr = zipDir.listFiles();
        }
        return antzip(willZipFileArr, zipFileName);
    }

    /**
     * 压缩多个文件或目录。可以指定多个单独的文件或目录。
     *
     * @param files
     *            要压缩的文件或目录组成的<code>File</code>数组。
     * @param zipFileName
     *            压缩后的zip文件名，如果后缀不是".zip, .jar, .war"，自动添加后缀".zip"。
     * @return 成功返回null，否则返回失败信息
     */
    public static String antzip(File[] files, String zipFileName) {
        // 未指定压缩文件名，默认为"ZipFile"
        if (zipFileName == null || zipFileName.equals("")) {
            zipFileName = "ZipFile";
        }

        // 添加".zip"后缀
        if (!zipFileName.toLowerCase().endsWith(".zip")
                && !zipFileName.toLowerCase().endsWith(".jar")
                && !zipFileName.toLowerCase().endsWith(".war")) {
            zipFileName += ".zip";
        }

        ZipOutputStream zipOutput = null;
        try {
            zipOutput = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(zipFileName)));
            zipOutput.setEncoding("GBK");

            for (File file : files) {
                antzipFiles(file, zipOutput, "");
            }

            System.out.println("压缩文件成功：" + zipFileName);
        } catch (Exception e) {
            System.out.println("异常：" + e);
            return e.getMessage();
        } finally {
            try {
                assert zipOutput != null;
                zipOutput.close();
            } catch (Exception e) {
                System.out.println("异常：" + e);
            }
        }
        return null;
    }

    /**
     * @param file
     *            压缩文件
     * @param zipOutput
     *            ZipOutputStream
     * @param pathName
     *            相对路径
     * @throws Exception
     *             异常
     */
    private static void antzipFiles(File file, ZipOutputStream zipOutput,
            String pathName) throws Exception {
        String fileName = pathName + file.getName();
        if (file.isDirectory()) {
            fileName = fileName + "/";
            zipOutput.putNextEntry(new ZipEntry(fileName));
            String fileNames[] = file.list();
            if (fileNames != null) {
                for (int i = 0; i < fileNames.length; i++) {
                    antzipFiles(new File(file, fileNames[i]), zipOutput,
                            fileName);
                }
                zipOutput.closeEntry();
            }
        } else {
            ZipEntry jarEntry = new ZipEntry(fileName);
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(file));
            zipOutput.putNextEntry(jarEntry);

            byte[] buf = new byte[bufSize];
            int len;
            while ((len = in.read(buf)) >= 0) {
                zipOutput.write(buf, 0, len);
            }
            in.close();
            zipOutput.closeEntry();
        }
    }

    /**
     * 解压指定zip文件。
     *
     * @param unZipFile
     *            需要解压的zip文件对象
     * @return 成功返回null，否则返回失败信息
     */
    public static String unZip(File unZipFile) throws Exception {
        return unZip(unZipFile.getPath(), null);
    }

    /**
     * 解压指定zip文件到指定的目录。
     *
     * @param unZipFile
     *            需要解压的zip文件对象
     * @param destFileName
     *            解压目的目录
     * @return 成功返回null，否则返回失败信息
     */
    public static String unZip(File unZipFile, String destFileName) throws Exception {
        return unZip(unZipFile.getPath(), destFileName);
    }

    /**
     * 解压指定zip文件。
     *
     * @param unZipFileName
     *            需要解压的zip文件名
     * @return 成功返回null，否则返回失败信息
     */
    public static String unZip(String unZipFileName) throws Exception{
        return unZip(unZipFileName, null);
    }

    /**
     * 解压指定zip文件到指定的目录。
     *
     * @param unZipFileName
     *            需要解压的zip文件名
     * @param destFileName
     *            解压目的目录，如果为null则为当前zip文件所有目录
     * @return 成功返回null，否则返回失败信息
     */
    public static String unZip(String unZipFileName, String destFileName) throws Exception{
        File unzipFile = new File(unZipFileName);

        if (destFileName == null || destFileName.trim().length() == 0) {
            destFileName = unzipFile.getParent();
        }

        File destFile;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(unzipFile, "GBK");
            for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                destFile = new File(destFileName, entry.getName());

                unZipFile(destFile, zipFile, entry); // 执行解压
            }
        } catch (Exception e) {
            System.out.println("异常：" + e);
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                assert zipFile != null;
                zipFile.close();
            } catch (Exception e) {
                System.out.println("异常：" + e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /* 执行解压 */
    private static void unZipFile(File destFile, ZipFile zipFile, ZipEntry entry)
            throws IOException {
        InputStream inputStream;
        FileOutputStream fileOut;
        if (entry.isDirectory()) // 是目录，则创建之
        {
            destFile.mkdirs();
        } else // 是文件
        {
            // 如果指定文件的父目录不存在,则创建之.
            File parent = destFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            inputStream = zipFile.getInputStream(entry);

            fileOut = new FileOutputStream(destFile);
            byte[] buf = new byte[bufSize];
            int readedBytes;
            while ((readedBytes = inputStream.read(buf)) > 0) {
                fileOut.write(buf, 0, readedBytes);
            }
            fileOut.close();

            inputStream.close();
        }
    }

    /**
     * 设置压缩或解压时缓冲区大小，单位字节。
     *
     * @param bufSize
     *            缓冲区大小
     */
    public void setBufSize(int bufSize) {
        ZipFileUtils.bufSize = bufSize;
    }

// 主函数，用于测试ZipFileUtils类
    public static void main(String[] args) throws Exception {
//        InsParm parm = new InsParm();
//        parm.setFtpIP("192.168.0.242");
//        parm.setFtpPort("21");
//        parm.setUsername("admin");
//        parm.setPassword("admin");
//        parm.setFtpDic("/UE_FTP/");
//        parm.setFtpFile("upload.zip");
//
//        new Thread(new Main(parm)).start();

    }
}

