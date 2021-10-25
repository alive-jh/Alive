package com.wechat.util;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



public final class StringUtil {
	public static final String EMPTY_STRING = "";
	
	public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); 

	
	private StringUtil() {
	
	}

	 /**
     * 将普通字符串格式化成数据库认可的字符串格式
     *
     * @param str 要格式化的字符串
     * @return 合法的数据库字符串
     */
    public static String toSql(String str) {
      String sql = new String(str);
      return Replace(sql, "'", "''");
    }
    
    /**
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source 源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     * 用于输入的表单字符串转化成HTML格式的文本
     */
    public static String Replace(String source, String oldString,
                                 String newString) {
      StringBuffer output = new StringBuffer();

      int lengthOfSource = source.length(); // 源字符串长度
      int lengthOfOld = oldString.length(); // 老字符串长度

      int posStart = 0; // 开始搜索位置
      int pos; // 搜索到老字符串的位置

      while ( (pos = source.indexOf(oldString, posStart)) >= 0) {
        output.append(source.substring(posStart, pos));

        output.append(newString);
        posStart = pos + lengthOfOld;
      }

      if (posStart < lengthOfSource) {
        output.append(source.substring(posStart));
      }

      return output.toString();
    }

	  
	
	/**
	 * 得到当前日期(文本格式)
	 * @return
	 */
	  public static String getToday()
      {
              return (new Date(System.currentTimeMillis())).toString();
      }

	public static String getGbcode(String str)
	{
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("ISO-8859-1");
			return new String(bytesStr, "GBK");
		} catch (Exception ex) {
			return str;
		}
	}
	
	
	
	/**
	 *  对页面中的标题或需要定长显示的字符串进行格式化，使其适合于在给定的长度内显示，
	 *  长度超过时，显示为"ssssss..."
	 * */
	public static String htmlTitleFilter(String srcTitle,int outputLength){
		String result = srcTitle;
		try{
			while (result.getBytes().length > outputLength){
				result = result.substring(0,result.length()-1);
			}
			if (srcTitle.length() > result.length())
				result = result + "...";
		}catch(Exception e){
		}
		return result;
	}

	public static boolean getBoolean(String property) {
		return Boolean.valueOf(property).booleanValue();
	}

	public static boolean getBoolean(String property, boolean defaultValue) {
		return (property == null) ? defaultValue : Boolean.valueOf(property)
				.booleanValue();
	}

	public static int getInt(String property, int defaultValue) {
		return (property == null) ? defaultValue : Integer.parseInt(property);
	}

	public static int getInt(String property) {
		return Integer.parseInt(property);
	}

	public static String getString(String property, String defaultValue) {
		return (property == null) ? defaultValue : property;
	}

	public static Integer getInteger(String property) {
		return (property == null) ? null : Integer.valueOf(property);
	}

	public static Integer getInteger(String property, Integer defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getInteger(property);
	}

	public static long getLong(String property) {
		return Long.parseLong(property);
	}

	public static long getLong(String property, long defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getLong(property);
	}

	public static double getDouble(String property) {
		return Double.parseDouble(property);
	}

	public static double getDouble(String property, double defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getDouble(property);
	}

	public static float getFloat(String property) {
		return Float.parseFloat(property);
	}

	public static float getFloat(String property, float defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getFloat(property);
	}

	public static Date getDate(String str) {
		return str == null ? null : Date.valueOf(str);
	}

	public static java.sql.Time getTime(String str) {
		return str == null ? null : java.sql.Time.valueOf(str);
	}

	public static java.sql.Timestamp getTimeStamp(String str) {
		return str == null ? null : java.sql.Timestamp.valueOf(str);
	}

	/**
	 * 获得类对象名字
	 * @param className String
	 * @return String
	 */
	public static String getObjectName(String className) {
		String result = new String(className);
		if (className.indexOf(" ") != -1) {
			result = className.substring(className.lastIndexOf(" ") + 1);
		}
		if (className.indexOf(".") != -1) {
			result = className.substring(className.lastIndexOf(".") + 1);
		}
		return result.toUpperCase();
	}

	/**
	 *
	 * @param property String
	 * @param delim String
	 * @return Map
	 */
	public static Map toMap(String property, String delim) {
		Map map = new HashMap();
		if (property != null) {
			StringTokenizer tokens = new StringTokenizer(property, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(), tokens.hasMoreElements() ? tokens
						.nextToken() : EMPTY_STRING);
			}
		}
		return map;
	}

	public static String[] toStringArray(String propValue, String delim) {
		if (propValue != null) {
			return propValue.split(delim);
		} else {
			return null;
		}
	}

	public static String fieldValue2String(Object object) {
		if (object != null) {
			return object.toString();
		} else {
			return null;
		}
	}

	public static String getValue(Object object) {
		String result = "";
		if (object != null) {
			result = String.valueOf(object);
			result = result.equals("null") ? "" : result;
		}
		return result;
	}

	/**
	 * 返回指定对象的字符串值
	 * @param object Object
	 * @return String
	 */
	
	
	 public static final String replace(String line, String oldString, String newString)
     {
             if (line == null)
             {
                     return null;
             }
             int i=0;
             if ((i=line.indexOf( oldString, i )) >= 0)
             {
                     char [] line2 = line.toCharArray();
                     char [] newString2 = newString.toCharArray();
                     int oLength = oldString.length();
                     StringBuffer buf = new StringBuffer(line2.length);
                     buf.append(line2, 0, i).append(newString2);
                     i += oLength;
                     int j = i;
                     while((i=line.indexOf( oldString, i )) > 0)
                     {
                             buf.append(line2, j, i-j).append(newString2);
                             i += oLength;
                             j = i;
                     }
                     buf.append(line2, j, line2.length - j);
                     return buf.toString();
             }
             return line;
     }

	public static String returnValue(Object object) {
		String result = "";
		if (object != null) {
			result = String.valueOf(object);
			result = result.equals("null") ? "" : result;
		}
		return result;
	}

	/**
	 * 将字符串参数格式成sql格式
	 * @param paramStr String   源参数
	 * @param splitStr String   分割字符：如","
	 * @return String
	 */
	public static String returnParam(String paramStr, String splitStr) {
		StringBuffer result = new StringBuffer();
		String[] params = paramStr.split(splitStr);
		for (int i = 0; i < params.length; i++) {
			if (i == 0) {
				result.append("'" + params[i] + "'");
			} else {
				result.append("," + "'" + params[i] + "'");
			}
		}
		return result.toString();
	}

	/**
	 * 判断是否是数字
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean isValidNumber(String param) {
		try {
			int i = Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 截取标题为指定长度的标题...
	 * @param str String
	 * @param len int
	 * @return String
	 */
	public static String replaceTitle(String str, int len, String taget) {
		StringBuffer buf2 = new StringBuffer();
		if (str.length() > len) {
			buf2.append(str.substring(0, len));
			buf2.append(taget);
		} else {
			buf2.append(str);
		}
		return buf2.toString();
	}

	/**
	 * 判断字符是否为空
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("")) ? true : false;
	}

	/**
	 * 判断map对象是否为空
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlankByMap(Object param) {
		return (param != null || !param.equals("")) ? true : false;
	}
	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static float round(float v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 显示定长的数据
	 * @param str String
	 * @param length int
	 * @return String
	 */
	public static String subString(String str, int length) {
		int len = str.length();
		String strnew = "";
		if (len >= length) {
			strnew = str.substring(0, length - 2) + "....";
		} else {
			strnew = str;
		}
		return strnew;
	}

	/**
	 * 根据提供的文字连续生成该内容
	 * @param str String
	 * @param length int
	 * @return String
	 */
	public static String makeString(String str, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param str String
	 * @param num int
	 * @param tag String
	 * @return String
	 */
	public static String newString(String str, int num, String tag) {
		StringBuffer sb = new StringBuffer();

		int len = str.length();
		int t = len / num;
		if (t > 0) {
			for (int i = 1; i <= t; i++) {
				if (i == 1) {
					sb.append(str.substring(0, num));
				} else {
					sb.append(tag + str.substring(num * (i - 1), num * (i)));
				}
			}
			sb.append(tag + str.substring(num * t));
		} else {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * html过滤
	 * @param str
	 * @return
	 */
	public static final String htmlFilter(String str) {
		if (str == null)
			return "&nbsp;";
		char toCompare;
		StringBuffer replaceChar = new StringBuffer(str.length() + 256);
		int maxLength = str.length();
		try {
			for (int i = 0; i < maxLength; i++) {
				toCompare = str.charAt(i);
				// 所有的 " 替换成 : &quot;
				if (toCompare == '"')
					replaceChar.append("&quot;");
				// 所有的 < 替换成： &lt;
				else if (toCompare == '<')
					replaceChar.append("&lt;");
				// 所有的 > 替换成： &gt;
				else if (toCompare == '>')
					replaceChar.append("&gt;");
				// 所有的 & 替换嘏: &amp;
				else if (toCompare == '&') {
					if (i < maxLength - 1)
						if (str.charAt(i + 1) == '#') {
							replaceChar.append("&#");
							i++;
						} else
							replaceChar.append("&amp;");
				} else if (toCompare == ' ')
					replaceChar.append("&nbsp;");
				// 所有的 \r\n （using System.getProperty("line.separator") to get it ） 替换成　<br>lihjk
				else if (toCompare == '\r')
					;//replaceChar.append("<br>");
				else if (toCompare == '\n')
					replaceChar.append("<br>");
				else
					replaceChar.append(toCompare);
			}//end for
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		} finally {

			return replaceChar.toString();
		}

	}
	

	public static String UTF82GBK(String str){
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("UTF-8");
			return new String(bytesStr, "GBK");
		} catch (Exception ex) {
			return str;
		}
	}
	
	public static String UTF82GB2312(String str){
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("UTF-8");
			return new String(bytesStr, "GB2312");
		} catch (Exception ex) {
			return str;
		}
	}
	
	public static String GBK2UTF8(String str){
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("GBK");
			return new String(bytesStr, "UTF-8");
		} catch (Exception ex) {
			return str;
		}
	}
	
	public static String GB23122UTF8(String str){
		if (str == null)
			return "";

		try {
			byte[] bytesStr = str.getBytes("GB2312");
			return new String(bytesStr, "UTF-8");
		} catch (Exception ex) {
			return str;
		}
	}
	
	//UTF-8编码
	public static String UTF8Encode(String str) {
    	String result="";
		if (str == null)
			return result;

		try {
			result = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//UTF-8解码
	public static String UTF8Decode(String str) {
    	String result="";
		if (str == null)
			return result;

		try {
			result = java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//GB2312编码
	public static String GB2312Encode(String str) {
    	String result="";
		if (str == null)
			return result;

		try {
			result = java.net.URLEncoder.encode(str, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//GB2312解码
	public static String GB2312Decode(String str) {
    	String result="";
		if (str == null)
			return result;

		try {
			result = java.net.URLDecoder.decode(str, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
     * 返回两个日期间隔多少天
     * @param beginDate
     * @param endDate
     * @return Date
     * @throws ParseException 
     */
    public static int getDayCount(String beginDate, String endDate) throws ParseException {
 
	    long l=format.parse(beginDate).getTime()-format.parse(endDate).getTime(); 
	    long day=l/(24*60*60*1000);
	    //long hour=(l/(60*60*1000)-day*24);
	    //long min=((l/(60*1000))-day*24*60-hour*60);
	    //long s=(l/1000-day*24*60*60-hour*60*60-min*60);
      
	    int dayCount=(int) day;
	    
		return dayCount;
    }
    
    /** 
    * 取得两个时间段的时间间隔 
    * return t2 与t1的间隔年数
    * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
    */ 
    public static int getBetweenYears(String beginDate,String endDate) throws ParseException{ 
    	int betweenYears = 0; 
    	Date d1 = (Date) format.parse(beginDate); 
    	Date d2 = (Date) format.parse(endDate); 
    	Calendar c1 = Calendar.getInstance(); 
    	Calendar c2 = Calendar.getInstance(); 
    	c1.setTime(d1); 
    	c2.setTime(d2); 
    	// 保证第二个时间一定大于第一个时间 
    	if(c1.after(c2)){ 
	    	c1 = c2; 
	    	c2.setTime(d1); 
    	} 
    	betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
    	return betweenYears; 
    } 
    
    public static String getDateFormat(java.util.Date date, String s)
    {
        if(date == null) return "";
        if(s == null)
            s = "yyyy-MM-dd HH:mm:ss";
        String s1 = "";
        try
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
            s1 = simpledateformat.format(date);
        }
        catch(Exception exception)
        {
            return "";
        }
        return s1;
    }
    public static String getDateFormat(String s, String s1)
    {
        if(s.equals(""))
            return getDateFormat(new java.util.Date(), s1);
        String s3;
        try
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
            java.util.Date date = simpledateformat.parse(s);
            s3 = getDateFormat(date, s1);
        }
        catch(Exception exception)
        {
            return "";
        }
        return s3;
    }
    
    public static String getNow(String s)
    {
        return getDateFormat(new java.util.Date(), s);
    }

    public static String getNow()
    {
        return getDateFormat(new java.util.Date(), "yyyy-MM-dd");
    }

    public static String getNows()
    {
        return getDateFormat(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getNowYear()
    {
        String s = getDateFormat(new java.util.Date(), "yyyy-MM-dd");
        if(s.length() >= 4)
            s = s.substring(0, 4);
        return s;
    }

    public static String getNowMonth()
    {
        String s = getDateFormat(new java.util.Date(), "yyyy-MM-dd");
        if(s.length() >= 7)
            s = s.substring(5, 7);
        return s;
    }

    public static String getNowDay()
    {
        String s = getDateFormat(new java.util.Date(), "yyyy-MM-dd");
        if(s.length() >= 10)
            s = s.substring(8, 10);
        return s;
    }

    public static String getNowHms()
    {
        String s = getDateFormat(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
        if(s.length() >= 10)
            s = s.substring(11, s.length());
        return s;
    }
    
    public static java.util.Date getDateByString(String s){
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date d = new java.util.Date();
    	try {
    		d = sf.parse(s);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return d;
    }
    
	/**
	 * 将map转换为sql条件语句
	 * @param conditions
	 * @return
	 */
	public static String sqlConditionMapToString(Map conditions){
		StringBuffer conditionStr = new StringBuffer();
		if(conditions != null){
			Set<String> keys = conditions.keySet();
			for (String key : keys) {
				String val = (String)conditions.get(key);
				conditionStr.append(" AND "+key+"="+val);
			}
		}
		return conditionStr.toString();
	}
	
	/**
	 * 将字符串转换为sql查询字符条件
	 * @param str
	 * @return
	 */
	public static String toSqlStringCondition(String str){
		return (str == null || "".equals(str)) ? "" : "'"+str+"'";
	}
	
	public static String toString(String s) {
        return (s == null) ? "" : s;
    }
	
	/**
	 * 获取两时间差值
	 */
	public static String getTimeDifferent(java.util.Date firstTime,java.util.Date secondTime){
		String  res = "";
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime= (Date) new java.util.Date();
		//将截取到的时间字符串转化为时间格式的字符串
		Date beginTime = null;
		try {
			beginTime = (Date) sdf.parse("2011-09-14 12:53:30");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//默认为毫秒，除以1000是为了转换成秒
		long interval=(firstTime.getTime()-secondTime.getTime())/1000;//秒
		long day=interval/(24*3600);//天
		String lastModifyTime = "";
		//		long hour=interval%(24*3600)/3600;//小时
//		long minute=interval%3600/60;//分钟
//		long second=interval%60;//秒
		//res = "两个时间相差："+day+"天"+hour+"小时"+minute+"分"+second+"秒";
		lastModifyTime  = getDateFormat(secondTime, "yyyy-MM-dd HH:mm:ss");
		res = day >0 ? day+"天前已更新("+lastModifyTime+")" : "今天已更新("+lastModifyTime+")";
		return res;

	}
	
	public static String getNextDay(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		String nextDay = getDateFormat(date, "yyyy-MM-dd HH:mm:ss");
		return nextDay;
	}
	
	public static String getNextDay(String s) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
		try {
			date = simpledateformat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		String nextDay = getDateFormat(date, "yyyy-MM-dd");
		return nextDay;
	}
	
	public static boolean isNumber(String str){
		if(str == null || "".equals(str))
			return false;
		 String number = "0123456789.";
         for (int i = 0; i < str.length(); i++) {
             if (number.indexOf(str.charAt(i)) == -1) {
                 return false;
             }
         }
         return true;
	}
	
//	public static String parsAesString(String str) throws Exception{
//		if("".equals(StringUtil.toString(str)))
//			return "";
//		else if(str.indexOf("@") == -1){
//			return AESUtil.Decrypt(StringUtil.toString(str));
//		}
//		String res = "";
//		String eachAes = "";
//		String[] array = str.split("@");
//		for(int i=0; i<array.length; i++){
//			eachAes = AESUtil.Decrypt(StringUtil.toString(array[i].toString()));
//			if(!"".equals(eachAes))
//				res += i == 0 ? eachAes : "@"+eachAes;
//		}
//		return res;
//	}
	
	public static boolean isEmptyStr(String str){
		if("".equals(StringUtil.toString(str)))
			return true;
		else if("0".equals(str))
			return true;
		return false;
	}
	
	public static int totalPageCount(String totalCount,String pageSize){
		int res = 0;
		try{
			res = (int)Math.ceil(StringUtil.getDouble(totalCount) / StringUtil.getDouble(pageSize));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public static String getJsonString(Object obj,String[] array){
		JsonConfig config = new JsonConfig();
		if(array != null && array.length > 0)
			config.setExcludes(array);
       /* config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
        		if (name.equals("agentUserAgent"))
                    return true;
                else
                    return false;
            }
        });*/
        String result = JSONArray.fromObject(obj, config).toString();
        return result;
	}
	
//	public static String getCustDetailListJsonString(List<CustomerDetailInfo> detailInfoList){
//		JsonConfig config = new JsonConfig();
//		config.registerJsonValueProcessor(AgentUserAgent.class,   
//	            new ObjectJsonValueProcessor(new String[]{"name"},AgentUserAgent.class));  
//        String result = JSONArray.fromObject(detailInfoList, config).toString();
//        return result;
//	}
	
	//字符串转数值保留小数点后两位
	public static String formateStringToMoney(String strMoney){
		String result = "";
		if(StringUtil.isEmptyStr(strMoney))
			result = "0.00";
		else
			result = new DecimalFormat("#.00").format(Double.parseDouble(strMoney));
		return result;
	}
	
	
	public static Double formateDouble(Double money){
		if(money == null)
			return null;
		DecimalFormat df=new DecimalFormat(".##");
		String st=df.format(money);
		return Double.valueOf(st);
	}
	
	/*private final static String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
		"9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s",
		"d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n",
	"m" };*/
	
	private final static String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8","9"};

	public static String getRandomStr(Integer length) {
		String s = "";
		for (int i = 0; i < length; i++) {
			int a=(int)(Math.random()*10);
			s+=str[a];
		}
		return s;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	public static int compare_date(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date d1 = df.parse(date1);
			java.util.Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {
				return 1;
			} else if (d1.getTime() < d2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	public static int compare_date1(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date d1 = df.parse(date1);
			java.util.Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {
				return -1;
			} else if (d1.getTime() < d2.getTime()) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	//判断日期格式
	public static boolean isDateStringValid(String date)   
	  {   
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD"); 

	     //输入对象不为空
	   
	    try {
	  sdf.parse(date);
	   return true;
	 } catch (ParseException e) {
	  // TODO Auto-generated catch block
	  return false;
	 } 
	 
	    }
	
	public void Test()
	{
		FastDateFormat fd =	FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Date date= new Date(0);
	    //System.out.println(date);
		////System.out.println(DateUtils.addMonths(date, -5));
		
		//pro.setCreateDate(fd.format(dateUtils.addMonths(date, 1)));
	
	}
	
	public static void main(String args[])
	{
		
		String s=",";
	}

}
