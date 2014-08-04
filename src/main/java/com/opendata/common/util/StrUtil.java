package com.opendata.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串工具类
 * @author 付威
 */
public class StrUtil
{

    public static int INT_HELPER = 0;

    public static String UrlEncode(String str, String charset)
    {
        if (str == null)
            return "";

        String s = str;
        try
        {
            s = URLEncoder.encode(str, charset);
        }
        catch (Exception e)
        {
        }
        return s;
    }

    /**
     * 如果对象为空，则返回空字符串
     * 
     * @param str
     * @return
     */
    public static String isNullToBank(Object str)
    {
        if (null == str)
        {
            return "";
        }
        else
        {
            return str.toString();
        }
    }

    /**
     * 判断字符串是否为空或者不为空字符
     * 
     * @param str
     *            允许为NULL
     * @return
     */
    public static boolean isNullOrBlank(Object o)
    {
        return !isNotNullOrBlank(o);
    }

    /**
     * 判断字符串是否不为空或者不为空字符
     * 
     * @param str
     *            允许为NULL
     * @return
     */
    public static boolean isNotNullOrBlank(Object o)
    {
        if (null == o)
        {
            return false;
        }
        else
        {
            String str = o.toString();
            if ("".equals(str.trim()))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * 删除input字符串中的html格式
     * 
     * @param input
     * @param length
     * @return
     */
    public static String splitAndFilterString(String input, int length)
    {
        if (input == null || input.trim().equals(""))
        {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length)
        {
            return str;
        }
        else
        {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }

    /**
     * 去掉所有HTML元素
     * @param input 原始输入的字符串
     * @return 去掉HTML元素的字符串
     */
    public static String filterString(String input)
    {
        if (input == null || input.trim().equals(""))
        {
            return "";
        }
        try
        {
            // 去掉所有html元素,
            String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
            str = str.replaceAll("[(/>)<]", "");
            return str;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * 判断是不是中文
     * @param str 要判断的字符串
     * @return boolean 如果是中文返回true 否则返回false
     */
    public static boolean isChinese(String str)
    {

        if (str == null || str.trim().length() < 1)
            return false;

        char[] ch = str.toCharArray();
        for (char c : ch)
        {

            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

            if (ub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

            && ub != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

            && ub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
            {

                return false;

            }
        }
        return true;
    }

    /**
     * 生产一个随机数和时间
     * 
     * @param len
     *            长度
     * @return 最长29位 最短18位
     */
    public static String getMath(int len)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(System.currentTimeMillis());
        buf.append((Math.random() + "").replace("0.", ""));
        if (len < 18)
            len = 18;
        if (len > buf.length())
            len = buf.length();
        return buf.substring(0, len);
    }

    public static String UrlEncode(String str)
    {
        return UrlEncode(str, "utf-8");
    }
    
    public static String[] split(String str, String token)
    {
        str = getNullStr(str).trim();
        if (str.equals(""))
            return null;
        return str.split(token);
    }

    public static String getURL(HttpServletRequest request)
    {
        return request.getRequestURL() + "?" + request.getQueryString();
    }

    public static boolean isImage(String ext)
    {
        return ((ext.equalsIgnoreCase("jpg")) || (ext.equalsIgnoreCase("gif")) || (ext.equalsIgnoreCase("png")) || (ext.equalsIgnoreCase("bmp")));
    }

    public static String getUrl(HttpServletRequest request)
    {
        String str = "";
        str = request.getRequestURL() + "?" + UrlEncode(request.getQueryString(), "utf-8");

        return str;
    }

    public static String getIp(HttpServletRequest request)
    {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null)
            ip = getNullStr(request.getRemoteAddr());

        return getNullString(ip);
    }

    public static String getNullString(String str)
    {
        return ((str == null) ? "" : str);
    }

    public static String getNullStr(String str)
    {
        return ((str == null) ? "" : str);
    }

    public static String replace(String strSource, String strFrom, String strTo)
    {
        if ((strSource.equals("")) || (strSource == null))
            return strSource;
        String strDest = "";
        int intFromLen = strFrom.length();

        if ((strSource == null) || (strSource.trim().equals("")))
            return strSource;
        int intPos;
        while ((intPos = strSource.indexOf(strFrom)) != -1)
        {
            strDest = strDest + strSource.substring(0, intPos);
            strDest = strDest + strTo;
            strSource = strSource.substring(intPos + intFromLen);
        }
        strDest = strDest + strSource;

        return strDest;
    }

    public static String sqlstr(String str)
    {
        if ((str == null) || (str.trim().equals("")))
        {
            str = "''";
            return str;
        }
        str = "'" + replace(str, "'", "''") + "'";
        return str;
    }

    /**
     * @deprecated
     */
    public static String UnicodeToGB(String strIn)
    {
        return UnicodeToUTF8(strIn);
    }

    public static int UTF8Len(String str)
    {
        int k = 0;
        for (int i = 0; i < str.length(); ++i)
            if (str.charAt(i) > 255)
                k += 2;
            else
                ++k;

        return k;
    }

    public static String Unicode2GB(String strIn)
    {
        String strOut = null;
        if ((strIn == null) || (strIn.trim().equals("")))
            return strIn;
        try
        {
            byte[] b = strIn.getBytes("ISO8859_1");
            strOut = new String(b, "GB2312");
        }
        catch (Exception e)
        {
        }
        return strOut;
    }

    public static String UnicodeToUTF8(String strIn)
    {
        String strOut = null;
        if ((strIn == null) || (strIn.trim().equals("")))
            return strIn;
        try
        {
            byte[] b = strIn.getBytes("ISO8859_1");

            strOut = new String(b, "utf-8");
        }
        catch (Exception e)
        {
        }
        return strOut;
    }

    public static String GBToUnicode(String strIn)
    {
        String strOut = null;
        if ((strIn == null) || (strIn.trim().equals("")))
            return strIn;
        try
        {
            byte[] b = strIn.getBytes("GB2312");
            strOut = new String(b, "ISO8859_1");
        }
        catch (UnsupportedEncodingException e)
        {
        }
        return strOut;
    }

    public static String UTF8ToUnicode(String strIn)
    {
        String strOut = null;
        if ((strIn == null) || (strIn.trim().equals("")))
            return strIn;
        try
        {
            byte[] b = strIn.getBytes("utf-8");
            strOut = new String(b, "ISO8859_1");
        }
        catch (UnsupportedEncodingException e)
        {
        }
        return strOut;
    }

    public static String p_center(String str)
    {
        return "<p align=center>" + str + "</p>";
    }

    public static String p_center(String str, String color)
    {
        return "<p align=center><font color='" + color + "'>" + str + "</font></p>";
    }

    public static String makeErrMsg(String msg)
    {
        String str = "<BR><BR><BR>";
        str = str + "<table width='70%' height='50' border='0' align='center' cellpadding='0' cellspacing='1' bgcolor='blue'>";
        str = str + "<tr>";
        str = str + "<td bgcolor='#FFFFFF' align='center'><b><font color=red>" + msg + "</font></b></td>";

        str = str + "</tr>";
        str = str + "</table>";
        return str;
    }

    public static String makeErrMsg(String msg, String textclr, String bgclr)
    {
        String str = "<BR><BR><BR>";
        str = str + "<table width='70%' height='50' border='0' align='center' cellpadding='0' cellspacing='1' bgcolor='" + bgclr + "'>";

        str = str + "<tr>";
        str = str + "<td bgcolor='#FFFFFF' align='center'><font color='" + textclr + "'><b>" + msg + "</b></font></td>";

        str = str + "</tr>";
        str = str + "</table>";
        return str;
    }

    public static String waitJump(String msg, int t, String url)
    {
        String str = "";
        String spanid = "id" + System.currentTimeMillis();
        str = "\n<ol><b><span id=" + spanid + "> 3 </span>";
        str = str + "秒钟后系统将自动返回... </b></ol>";
        str = str + "<ol>" + msg + "</ol>";
        str = str + "<script language=javascript>\n";
        str = str + "<!--\n";
        str = str + "function tickout(secs) {\n";
        str = str + spanid + ".innerText = secs;\n";
        str = str + "if (--secs > 0) {\n";
        str = str + "  setTimeout('tickout(' +secs + ')', 1000);\n";
        str = str + "}\n";
        str = str + "}\n";
        str = str + "tickout(" + t + ");\n";
        str = str + "-->\n";
        str = str + "</script>\n";
        str = str + "<meta http-equiv=refresh content=" + t + ";url=" + url + ">\n";
        return str;
    }

    public static String Alert_Redirect(String msg, String toUrl)
    {
        String str = "";
        str = "<script language=javascript>\n";
        str = str + "<!--\n";
        str = str + "alert(\"" + msg + "\")\n";
        if (!(toUrl.equals("")))
            str = str + "location.href=\"" + toUrl + "\"\n";
        str = str + "-->\n";
        str = str + "</script>\n";
        return str;
    }

    public static String Alert_Back(String msg)
    {
        String str = "";
        str = "<script language=javascript>\n";
        str = str + "<!--\n";
        str = str + "alert(\"" + msg + "\")\n";
        str = str + "history.back()\n";
        str = str + "-->\n";
        str = str + "</script>\n";
        return str;
    }

    public static String Alert(String msg)
    {
        String str = "";
        str = "<script language=javascript>\n";
        str = str + "<!--\n";
        str = str + "alert(\"" + msg + "\")\n";
        str = str + "-->\n";
        str = str + "</script>\n";
        return str;
    }

    public static float toFloat(String str, float defaultValue)
    {
        float d = defaultValue;
        try
        {
            d = Float.parseFloat(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return d;
    }

    public static float toFloat(String str)
    {
        return toFloat(str, 0F);
    }

    public static double toDouble(String str, double defaultValue)
    {
        double d = defaultValue;
        try
        {
            d = Double.parseDouble(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return d;
    }

    public static double toDouble(String str)
    {
        return toDouble(str, 0D);
    }

    public static int toInt(String str, int defaultValue)
    {
        int d = defaultValue;
        try
        {
            d = Integer.parseInt(str);
        }
        catch (Exception e)
        {
        }
        return d;
    }

    public static int toInt(String str)
    {
        return toInt(str, -1);
    }

    public static long toLong(String str, long defaultValue)
    {
        long d = defaultValue;
        try
        {
            d = Long.parseLong(str);
        }
        catch (Exception e)
        {
        }
        return d;
    }

    public static Date toDate(String time, String format)
    {
        Date d = null;
        try
        {
            d = parse(time, format, Locale.CHINA);
        }
        catch (Exception e)
        {
        }
        return d;
    }

    public static Date parse(String time, String format, Locale locale) throws Exception
    {
        if (time == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);

        Date d = null;
        d = sdf.parse(time);
        return d;
    }

    public static long toLong(String str)
    {
        return toLong(str, -1L);
    }

    public static boolean isNumeric(String s)
    {
        if (s == null)
            return false;
        boolean flag = true;
        char[] numbers = s.toCharArray();
        if (numbers.length == 0)
            return false;
        for (int i = 0; i < numbers.length; ++i)
            if (!(Character.isDigit(numbers[i])))
            {
                flag = false;
                break;
            }

        return flag;
    }

    public static boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException nfe)
        {
        }
        return false;
    }

    public static boolean isCharOrNum(String s)
    {
        int len = s.length();
        for (int i = 0; i < len; ++i)
        {
            char ch = s.charAt(i);
            if ((((ch < 'a') || (ch > 'z'))) && (((ch < 'A') || (ch > 'Z'))) && (((ch < '0') || (ch > '9'))))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isSimpleCode(String s)
    {
        int len = s.length();
        for (int i = 0; i < len; ++i)
        {
            char ch = s.charAt(i);
            if ((((ch < 'a') || (ch > 'z'))) && (((ch < 'A') || (ch > 'Z'))) && (((ch < '0') || (ch > '9'))) && (ch != '-') && (ch != '_'))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isChars(String s)
    {
        int len = s.length();
        for (int i = 0; i < len; ++i)
        {
            char ch = s.charAt(i);
            if ((((ch < 'a') || (ch > 'z'))) && (((ch < 'A') || (ch > 'Z'))))
                return false;
        }

        return true;
    }

    public static boolean isNotCN(String str)
    {
        Pattern pa = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = pa.matcher(str);

        return (!(m.find()));
    }

    public static String FormatDate(Date d, String format)
    {
        if (d == null)
            return "";
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        return myFormatter.format(d);
    }

    public static String FormatPrice(double value)
    {
        String subval = "0.00";
        if (value > 0D)
        {
            subval = Double.toString(value);
            int decimal_len = subval.length() - subval.lastIndexOf(46) + 1;
            if (decimal_len > 1)
                subval = subval.substring(0, subval.lastIndexOf(46) + 3);
            else
                subval = subval + "0";
        }
        return subval;
    }

    public static String FormatPrice(String value)
    {
        if (value == null)
            return null;
        String subval = "0.00";
        if (Double.parseDouble(value) > 0D)
        {
            subval = value;
            int decimal_len = subval.length() - subval.lastIndexOf(46) + 1;
            if (decimal_len > 1)
                subval = subval.substring(0, subval.lastIndexOf(46) + 3);
            else
                subval = subval + "0";
        }
        return subval;
    }

    public static String HtmlEncode(String str)
    {
        if ((str == null) || (str.equals("")))
            return "";
        StringBuffer buf = new StringBuffer(str.length() + 6);

        char ch = ' ';
        for (int i = 0; i < str.length(); ++i)
        {
            ch = str.charAt(i);
            if (ch == '<')
                buf.append("&lt;");
            else if (ch == '>')
                buf.append("&gt;");
            else if (ch == '\'')
                buf.append("&#039;");
            else if (ch == '"')
                buf.append("&quot;");
            else if (ch == '&')
                buf.append("&amp;");
            else
                buf.append(ch);
        }
        str = buf.toString();
        return str;
    }

    public static String toHtml(String str)
    {
        if ((str == null) || (str.equals("")))
            return "";
        StringBuffer buf = new StringBuffer(str.length() + 6);

        char ch = ' ';
        for (int i = 0; i < str.length(); ++i)
        {
            ch = str.charAt(i);
            if (ch == '<')
            {
                buf.append("&lt;");
            }
            else if (ch == '>')
            {
                buf.append("&gt;");
            }
            else if (ch == ' ')
            {
                buf.append("&nbsp;");
            }
            else if (ch == '\n')
            {
                buf.append("<br>");
            }
            else if (ch == '\'')
            {
                buf.append("&#039;");
            }
            else if (ch == '"')
                buf.append("&quot;");
            else
            {
                buf.append(ch);
            }

        }

        str = buf.toString();
        return str;
    }

    public static boolean isValidIP(String ip)
    {
        Pattern p = Pattern.compile("[0-9\\*]{1,3}\\.[0-9\\*]{1,3}\\.[0-9\\*]{1,3}\\.[0-9\\*]{1,3}");

        Matcher m = p.matcher(ip);
        boolean result = m.find();

        return (result);
    }

    public static boolean IsValidEmail(String email)
    {
        String input = email;

        Pattern p = Pattern.compile("^\\.|^\\@");
        Matcher m = p.matcher(input);
        if (m.find())
        {
            return false;
        }

        p = Pattern.compile("^www\\.");
        m = p.matcher(input);
        if (m.find())
        {
            return false;
        }

        p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
        m = p.matcher(input);
        boolean result = m.find();
        if (result)
        {
            return false;
        }

        return (email.indexOf("@") != -1);
    }

    public static String ShowStatus(String msg)
    {
        String str = "";
        str = "<script language=javascript>\n";
        str = str + "<!--\n";
        str = str + "window.status=(\"" + msg + "\")\n";
        str = str + "-->\n";
        str = str + "</script>\n";
        return str;
    }

    public static String left(String str, int length)
    {
        if (str.length() >= length)
            return str.substring(0, length);

        return str.substring(0);
    }

    public static String getLeft(String str, int length)
    {
        if (str == null)
            return "";
        int k = 0;
        int len = str.length();
        for (int i = 0; i < len; ++i)
        {
            if (str.charAt(i) > 255)
                k += 2;
            else
                ++k;

            if (k >= length)
                return str.substring(0, i + 1);
        }
        return str;
    }

    public static String HTMLEncode(String text)
    {
        if (text == null)
            return "";

        StringBuffer results = null;
        char[] orig = null;
        int beg = 0;
        int len = text.length();
        for (int i = 0; i < len; ++i)
        {
            char c = text.charAt(i);
            switch (c)
            {
                case '\0':
                case '"':
                case '&':
                case '<':
                case '>':
                    if (results == null)
                    {
                        orig = text.toCharArray();
                        results = new StringBuffer(len + 10);
                    }
                    if (i > beg)
                        results.append(orig, beg, i - beg);

                    beg = i + 1;
                    switch (c)
                    {
                        default:
                            break;
                        case '&':
                            results.append("&amp;");
                            break;
                        case '<':
                            results.append("&lt;");
                            break;
                        case '>':
                            results.append("&gt;");
                            break;
                        case '"':
                            results.append("&quot;");
                    }
            }

        }

        if (results == null)
            return text;

        results.append(orig, beg, len - beg);
        return results.toString();
    }

    public static String getFileExt(String fileName)
    {
        if (fileName == null)
        {
            return "";
        }

        int dotindex = fileName.lastIndexOf(".");
        String extName = fileName.substring(dotindex + 1, fileName.length());
        extName = extName.toLowerCase();
        return extName;
    }

    public static String PadString(String str, char padChar, int length, boolean isLeft)
    {
        int strLen = str.length();
        if (strLen >= length)
            return str;
        int len = length - strLen;
        String pStr = "";
        for (int i = 0; i < len; ++i)
            pStr = pStr + padChar;
        if (isLeft)
            return pStr + str;

        return str + pStr;
    }

    public static String format(String format, Object[] args)
    {
        int len = args.length;
        for (int i = 0; i < len; ++i)
        {
            if (args[i] == null)
                args[i] = "null";
            format = format.replaceFirst("%s", args[i].toString());
        }
        return format;
    }

    public static String trace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        String result = sw.toString();
        return result;
    }

    public static String toSBC(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; ++i)
            if (c[i] == ' ')
            {
                c[i] = 12288;
            }
            else if (c[i] < '')
                c[i] = (char) (c[i] + 65248);

        return new String(c);
    }

    public static String toDBC(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; ++i)
            if (c[i] == 12288)
            {
                c[i] = ' ';
            }
            else if ((c[i] > 65280) && (c[i] < 65375))
                c[i] = (char) (c[i] - 65248);

        return new String(c);
    }

    public static String escape(String src)
    {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (int i = 0; i < src.length(); ++i)
        {
            char j = src.charAt(i);
            if ((Character.isDigit(j)) || (Character.isLowerCase(j)) || (Character.isUpperCase(j)))
            {
                tmp.append(j);
            }
            else if (j < 256)
            {
                tmp.append("%");
                if (j < '\16')
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            }
            else
            {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src)
    {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        int pos = 0;
        do
        {
            if (lastPos >= src.length())
                break;
            pos = src.indexOf("%", lastPos);
            if (pos != lastPos)
                if (src.charAt(pos + 1) != 'u')
                    break;
            char ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);

            tmp.append(ch);
            lastPos = pos + 6;
            ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);

            tmp.append(ch);
            lastPos = pos + 3;

            if (pos != -1)
                break;
            tmp.append(src.substring(lastPos));
            lastPos = src.length();
            tmp.append(src.substring(lastPos, pos));
            lastPos = pos;
        }
        while (true);

        return tmp.toString();
    }
    
    public static String replaceDouhaoWithFenhao(String str){
    	if(isNotNullOrBlank(str)&&str.indexOf(", ")!=-1){
    		return str.replace(", ",";");
    	}else{
    		return str;
    	}
    }
}