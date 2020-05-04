package com.github.barry.core.utils;

import com.alibaba.druid.support.json.JSONUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtils
 * @Description StringUtils
 * @Author wangxuexing
 * @Date 2020/5/2 16:54
 * @Version 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static AtomicInteger randomNo = new AtomicInteger(0);
    private static Pattern pattern;
    private static Matcher matcher;
    public static String emailPattern = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$";
    public static String pswPattern = "\\w{8,}";
    //数字，字母（包括大小写），汉字及组合
    public static String REGEX = "[\\pP\\p{Punct}]";

    public static boolean isMatch(String str, String pattern){
        return str.matches(pattern);
    }
    /**
     * 所有都是UTF-8编码
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private static Map<String, String> referencesMap = new HashMap<String, String>();
    static {
        referencesMap.put("'", "\\'");
        referencesMap.put("\"", "\\\"");
        referencesMap.put("\\", "\\\\");

        referencesMap.put("\n", "\\\n");
        referencesMap.put("\0", "\\\0");
        referencesMap.put("\b", "\\\b");
        referencesMap.put("\r", "\\\r");
        referencesMap.put("\t", "\\\t");
        referencesMap.put("\f", "\\\f");
    }

    /**
     * escape sql tag with the source code.
     * @param source
     * @return
     */
    public static String escapeSqlStr(String source) {
        if (source == null){
            return "";
        }
        StringBuffer sbuffer = new StringBuffer(source.length());
        for (int i = 0; i < source.length(); i++) {
            String c = source.substring(i, i + 1);
            if (referencesMap.get(c) != null) {
                sbuffer.append(referencesMap.get(c));
            } else {
                sbuffer.append(c);
            }
        }
        return sbuffer.toString();
    }

    /**
     * ======================字符串取默认值处理start===============================
     */

    /**
     * 取Long型默认值
     * @param s 字符串
     * @param defaultValue 默认的long型数值
     * @return
     */
    public static long getLongValue(String s, long defaultValue) {
        if(s == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(s);
            } catch (Exception var4) {
                return defaultValue;
            }
        }
    }

    /**
     * 取Float型默认值
     * @param s 字符串
     * @param defaultValue 默认的float型值
     * @return
     */
    public static float getFloatValue(String s, float defaultValue) {
        if(s == null) {
            return defaultValue;
        } else {
            try {
                return Float.parseFloat(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    /**
     * 取Short型默认值
     * @param s 字符串
     * @param defaultValue 默认的short型值
     * @return
     */
    public static short getShortValue(String s, short defaultValue) {
        if(s == null) {
            return defaultValue;
        } else {
            try {
                return Short.parseShort(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    /**
     * 取Double型默认值
     * @param s 字符串
     * @param defaultValue 默认的double型值
     * @return
     */
    public static double getDoubleValue(String s, double defaultValue) {
        if(s == null) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(s);
            } catch (Exception var4) {
                return defaultValue;
            }
        }
    }

    /**
     * 取int型默认值
     * @param s 字符串
     * @param defaultValue 默认的int型值
     * @return
     */
    public static int getIntValue(String s, int defaultValue) {
        if(s != null && s.length() != 0) {
            try {
                return Integer.parseInt(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * 取boolean型默认值
     * @param s 字符串
     * @param defaultValue 默认的boolean型值
     * @return
     */
    public static boolean getBooleanValue(String s, boolean defaultValue) {
        if(s == null) {
            return defaultValue;
        } else {
            try {
                return Boolean.parseBoolean(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    /**
     * ======================字符串取默认值处理end===============================
     */

    public static boolean isElMatch(String str, String emailPattern) {
        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(str);
        if(matcher.matches()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据排序字段和排序方式生成 order by 字符串
     * @param sort
     *            排序字段(多个字段间以,分割)
     * @param order
     *            排序方式(与order字段对应,多个排序方式以,分割)
     * @return
     */
    public static String generateSqlOrderByClause(String sort, String order) {
        StringBuilder orderBuilder = new StringBuilder();
        if (isNotBlank(order) && (isNotBlank(sort))) {
            String sortArray[] = sort.split(",", -1);
            String orderArray[] = order.split(",", -1);
            for (int m = 0; m < sortArray.length; m++) {
                if (m > 0) {
                    orderBuilder.append(",");
                }
                orderBuilder.append(camelToUnderLine(sortArray[m])).append(" ").append(orderArray[m]);
            }
        }
        return orderBuilder.toString();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br/>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name
     *            转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String camelToUnderLine(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br/>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name
     *            转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String underLineToCamel(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
//        } else if (!name.contains("_")) {
//            // 不含下划线，仅将首字母小写
//            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 生成固定长度的字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return getRandomString(base, length);
    }

    /**
     * 生成固定长度的字符串
     * @param scope 在哪些字符串中随机生成
     * @param length 生成长度
     * @return
     */
    public static String getRandomString(String scope, int length) {
        if(StringUtils.isBlank(scope)){
            scope = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(scope.length());
            sb.append(scope.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() {
        /** The FieldPosition. */
        FieldPosition HELPER_POSITION = new FieldPosition(0);
        /** This Format for format the data to special format. */
        Format dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        /** This Format for format the number to special format. */
        NumberFormat numberFormat = new DecimalFormat("000");
        Calendar rightNow = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
        numberFormat.format(genRandomNum(3), sb, HELPER_POSITION);
        return sb.toString();
    }

    /**
     * 产生n位随机数
     * @return
     */
    public static long genRandomNum(int n){
        if(n<1){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }

    /**
     * 邮箱判断
     * dell 2016年1月15日
     * 修改者名字
     * 修改日期
     * 修改内容
     * @param
     * @return boolean
     * @throws
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        if (StringUtils.isBlank(email)) {
            return false;
        }
        try {
            String check =
                    "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检查是否电话号码
     * dell 2016年2月16日
     * 修改者名字
     * 修改日期
     * 修改内容
     * @param
     * @return boolean
     * @throws
     */
    public static boolean isMobile(String mobile) {
        boolean flag = false;
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        try {
            String check = "^[1][3-8]+\\d{9}";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(mobile);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static byte[] utf8Bytes(String data) {
        return data.getBytes(UTF_8);
    }

    public static String utf8String(byte[] data) {
        return new String(data, UTF_8);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Object[] array, String sep) {
        return join(array, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    @SuppressWarnings("rawtypes")
    public static String join(Collection list, String sep) {
        return join(list, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    @SuppressWarnings("rawtypes")
    public static String join(Collection list, String sep, String prefix) {
        Object[] array = list == null ? null : list.toArray();
        return join(array, sep, prefix);
    }

    /**
     * 以指定的分隔符来进行字符串元素连接
     * <p>
     * 例如有字符串数组array和连接符为逗号(,)
     * <code>
     * String[] array = new String[] { "hello", "world", "qiniu", "cloud","storage" };
     * </code>
     * 那么得到的结果是:
     * <code>
     * hello,world,qiniu,cloud,storage
     * </code>
     * </p>
     *
     * @param array  需要连接的对象数组
     * @param sep    元素连接之间的分隔符
     * @param prefix 前缀字符串
     * @return 连接好的新字符串
     */
    public static String join(Object[] array, String sep, String prefix) {
        if (array == null) {
            return "";
        }

        int arraySize = array.length;

        if (arraySize == 0) {
            return "";
        }

        if (sep == null) {
            sep = "";
        }

        if (prefix == null) {
            prefix = "";
        }

        StringBuilder buf = new StringBuilder(prefix);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            buf.append(array[i] == null ? "" : array[i]);
        }
        return buf.toString();
    }

    /**
     * 以json元素的方式连接字符串中元素
     * <p>
     * 例如有字符串数组array
     * <code>
     * String[] array = new String[] { "hello", "world", "qiniu", "cloud","storage" };
     * </code>
     * 那么得到的结果是:
     * <code>
     * "hello","world","qiniu","cloud","storage"
     * </code>
     * </p>
     *
     * @param array 需要连接的字符串数组
     * @return 以json元素方式连接好的新字符串
     */
    public static String jsonJoin(String[] array) {
        int arraySize = array.length;
        int bufSize = arraySize * (array[0].length() + 3);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(',');
            }

            buf.append('"');
            buf.append(array[i]);
            buf.append('"');
        }
        return buf.toString();
    }

    public static boolean inStringArray(String s, String[] array) {
        for (String x : array) {
            if (x.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成手机验证码
     * @return
     */
    public static String genRandomNum(){
        int[] array = {1,2,3,4,5,6,7,8,9};
        Random rand = new Random();
        for (int i = 9; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for(int i = 0; i < 6; i++){
            result = result * 10 + array[i];
        }
        return String.valueOf(result);
    }

    /**
     * 将带分隔符的字符串按照分隔符拆分成List<String>
     * @param param 参数
     * @param delimiter 分隔符
     * @return
     */
    public static List<String> changeStr2List(String param, String delimiter) {
        List<String> paramList = new ArrayList<>();
        if(StringUtils.isNotBlank(param)) {
            if(StringUtils.isNotBlank(delimiter)) {
                String[] paramArr = param.split(delimiter);
                for(String meta : paramArr) {
                    paramList.add(meta);
                }
            }else {
                paramList.add(param);
            }
        }else {
            return null;
        }
        return paramList;
    }

    /**
     * unicode 转换成 中文
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                // do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        // 到这里铁定包含
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    /**
     * 获取字符串长度，汉字长度为2
     * @param str
     * @return
     */
    public static long getStringLength(String str){
        long valueLength = 0;
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (temp.matches("[\u4e00-\u9fa5]")) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 获取两端文字的变化率
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 初始化空字符串的情况
        for(int i = 1; i <= m; i++){
            dp[i][0] = i;
        }
        for(int i = 1; i <= n; i++){
            dp[0][i] = i;
        }
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                // 增加操作：str1a变成str2后再加上b，得到str2b
                int insertion = dp[i][j-1] + 1;
                // 删除操作：str1a删除a后，再由str1变为str2b
                int deletion = dp[i-1][j] + 1;
                // 替换操作：先由str1变为str2，然后str1a的a替换为b，得到str2b
                int replace = dp[i-1][j-1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
                // 三者取最小
                dp[i][j] = Math.min(replace, Math.min(insertion, deletion));
            }
        }
        return dp[m][n];
    }

    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String formatJsonStr(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    /**
     *
     * 获取唯一字符串
     */
    public static String generateUniqueStr(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 相似度匹配支付串特殊字符替换
     * @param src
     * @return
     */
    public static String replaceAllSimilarMatch(String src){
        if(src == null){
            return "";
        }
        return src.replaceAll("\"", "“").replaceAll("\n", "<br>");
    }

    /**
     * 打印异常日志
     * @param e Throwable
     * @return
     */
    public static String getExceptionStack(Throwable e){
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }

    /**
     * 获取指定长度的随机数
     * @param size 指定长度
     * @return
     */
    public static String getRandom(int size) {
        String seqNum = String.format("%0"+size+"d", new Random().nextInt(10));
        return seqNum.substring(seqNum.length() - size,seqNum.length());
    }

    /**
     * 未解码前的参数字符串转json
     * @param param key1=value2&key2=value2
     * @return
     */
    public static String urlParam2Json(String param){
        return map2Json(convertParamsString2Map(param));
    }

    /**
     * Map 转 json字符串
     * @param map
     * @return
     */
    public static String map2Json(Map<String, Object> map){
        return JSONUtils.toJSONString(map)
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\\"\\[", "[")
                .replaceAll("\\]\\\"", "]");
    }

    /**
     * 处理URL参数串(没有解码前)，把参数名和参数值转化成键值对的形式
     */
    public static Map<String, Object> convertParamsString2Map(String param) {
        if(StringUtils.isBlank(param)) {
            return null;
        }
        //解码
        String[] params = param.split("&");
        Map<String, String> key2value = new TreeMap<String, String>();
        for(int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if(p.length == 0) {
                continue;
            }
            try {
                String keyStr = URLDecoder.decode(p[0], "UTF-8");
                if(StringUtils.isBlank(keyStr)) {
                    continue;
                }
                String valueStr;
                if(p.length == 2) {
                    valueStr = URLDecoder.decode(p[1], "UTF-8");
                } else {
                    valueStr = "";
                }
                key2value.put(keyStr, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //遍历每一行传参
        Map<String, Object> map = new HashMap<String, Object>();
        for(Map.Entry<String, String> entry : key2value.entrySet()) {
            String keyStr = entry.getKey();
            String value = entry.getValue();
            //根目录的key
            Matcher keyMatcher = Pattern.compile("^[a-zA-Z\\_]{1}[\\w]*").matcher(keyStr);
            if(!keyMatcher.find()) {
                continue;
            }
            String key = keyMatcher.group(0);
            if(!map.containsKey(key)) {
                map.put(key, new HashMap<String, Object>());
            }

            //二级以及二级目录以上的key
            String pattern = "\\[([\\w]+?)\\]";
            Matcher filterMatcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(keyStr);
            //获取所有的patternKey
            List<String> patternKeyList = new ArrayList<String>();
            while(filterMatcher.find()) {
                String patternKey = filterMatcher.group(1);
                patternKeyList.add(patternKey);
            }
            //有子元素
            if(!patternKeyList.isEmpty()) {
                //遍历并写入
                Object childMap = map.get(key);
                int patternKeyListSize = patternKeyList.size();
                for(int j = 0; j < patternKeyListSize; j++) {
                    String patternKey = patternKeyList.get(j);
                    Map<String, Object> _childMap = (HashMap<String, Object>) childMap;
                    if(!_childMap.containsKey(patternKey)) {
                        //是否是最后一个节点，是的话直接赋值
                        if(j == patternKeyListSize-1) {
                            _childMap.put(patternKey, value);
                            break;
                        }
                        _childMap.put(patternKey, new HashMap<String, Object>());
                    }
                    childMap = _childMap.get(patternKey);
                }
            }
            //只有一级元素
            else {
                map.put(key, value);
            }
        }
        map = (Map<String, Object>) map2list(map);
        return map;
    }

    private static Object map2list(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        boolean allIsNumber = true;
        for(String key : keySet) {
            //不是数字
            if(!Pattern.matches("^[0-9]+$", key)) {
                allIsNumber = false;
            }
            Object childNode = map.get(key);
            if(childNode instanceof Map) {
                childNode = map2list((Map<String, Object>) childNode);
                map.put(key, childNode);
            }
        }
        Object res;
        if(allIsNumber) {
            res = new ArrayList<>();
            for(String key : keySet) {
                Object value = map.get(key);
                ((List<Object>) res).add(value);
            }
        } else {
            res = map;
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
       /* StringBuilder express = new StringBuilder();
        String minScore = "0";
        String maxScore = "10";
        String minOperator = "<=";
        String maxOperator = "<";
        express.append(minScore);
        express.append("≤".equals(minOperator) ? "<=" : minOperator);
        express.append("162.10");
        express.append("&&");
        express.append("162.10");
        express.append("≤".equals(maxOperator) ? "<=" : maxOperator);
        express.append(maxScore);
        Boolean matchResult = Boolean.valueOf(String.valueOf(ExpressUtils.getExpressRunner().execute(express.toString(), null, null, true, false)));*/
/*//        String num1 = "5.988E-7";
        String num1 = "5.9889999999999999999";
        BigDecimal bd = new BigDecimal(num1);
        System.out.println(bd.toPlainString());*/
//        System.out.println(matchResult);
       /* String s = "2133_";
        System.out.println(isMatch(s, REGEX));*/
        /*String s = "{\"companyId\":\"1\",\"scoreModelId\":\"1\",\"createId\":\"1\",\"finLimitModelName\":\"融资额度2_2\",\"finLimitModelDetail\":\"[{\\\"gradeDetailId\\\":437,\\\"scoreRule\\\":\\\"id_56+id_59\\\"}]\",\"gradeModelId\":\"55\",\"isSameRule\":\"0\"}";
        System.out.println(s);
        System.out.println(s.replaceAll("\\\\\"", "\"").replaceAll("\\\"\\[", "[").replaceAll("\\]\\\"", "]"));*/
        String s = "finLimitModelName=融资额度2_3&scoreModelId=1&gradeModelId=55&isSameRule=0&companyId=1&createId=1&finLimitModelDetail=[{\"gradeDetailId\":437,\"scoreRule\":\"id_56&&id_59\"}]";
        System.out.println(s);
        Map<String, Object> map = convertParamsString2Map(s);
        System.out.println(map);
    }

}
