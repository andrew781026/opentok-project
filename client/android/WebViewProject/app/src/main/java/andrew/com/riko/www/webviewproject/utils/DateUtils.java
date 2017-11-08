package andrew.com.riko.www.webviewproject.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
	public static SimpleDateFormat sdDate = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sdDate2 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat sdTime = new SimpleDateFormat("HHmmssSS");
	public static SimpleDateFormat sdTime2= new SimpleDateFormat("HHmmss");
	public static SimpleDateFormat sdDateTime = new SimpleDateFormat("yyyyMMddHHmmssSS");
	public static SimpleDateFormat sdTimeHHmm = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat sdDateTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdDateTime3 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static Map<String,SimpleDateFormat> sdfMap = new HashMap<String, SimpleDateFormat>();

	public static SimpleDateFormat getSimpaleDateFormat(String format){
		
		SimpleDateFormat sd = sdfMap.get(format);
		if (sdfMap.get(format) == null){
			
			sd = new SimpleDateFormat(format);
			sdfMap.put("format", sd);
		}
		return sd;
	}
	
	public static String getDateTimeString() {
		return getDateTimeString(new Date());
	}
	
	public static String getDateTimeString(Date date) {
		return sdDateTime.format(date);
	}
	
	/**
	 *  default length = 8
	 */
	public static String getTime(Date date){
		return getTime(date, 8);
	}
	
	public static String getTime(Date date, int len){
		return sdTime.format(date).substring(0, len);
	}
	
	/**
	 *  default length = 16
	 */
	public static String getDateTime(Date date){
		return getDateTime(date, 16);
	}
	public static String getDateTime(Date date, int len){
		return sdDateTime.format(date).substring(0, len);
	}

	public static Date getDateAddOneDay(Date date) {
		
		return getDateAddDay(date, 1);
	}

	public static Date getDateAddDay(Date date,int day) {
		Calendar cals = Calendar.getInstance();
		cals.setTime(date);
		cals.add(Calendar.DAY_OF_MONTH, day);
		
		return cals.getTime();
	}


	
	
	public static String getDate(Date date,String dataFormat) {

		if ( date == null ) return null;

		SimpleDateFormat sdf = sdfMap.get( dataFormat );
		if ( sdf == null ){
			sdf = new SimpleDateFormat(dataFormat);
			sdfMap.put(dataFormat,sdf);
		}

		return sdf.format(date);
	}


	/**
	 * check Date format YYYYMMDD 
	 * 	and YYYY >= 1800
	 * 	and check Date is correct
	 */
	public static boolean isYYYYMMDD(String inDate) {

		try {
			Date date = sdDate.parse(inDate);
			
			Calendar cals = Calendar.getInstance();
			
			cals.setTime(date);
			
			int y4 = cals.get(Calendar.YEAR);
			
			if( y4 < 1800){
				throw new Exception("year(" + y4 +") < 1800");
			}
			
			if (!inDate.equals(sdDate.format(date))){
				throw new Exception("Date(" + inDate +") is not correct.");
			}
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	/**
	 * @param inDate yyyymmdd
	 * 
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public static Date getDateAddOneDay(String inDate) throws ParseException {
		
		return getDateAddOneDay(sdDateTime.parse(inDate + "000000000"));
	}

	/**
	 * 
	 * @param time1 HH:mm
	 * @param time2 HH:mm
	 * @return
	 * the value 0 if the argument time1 is equal to this time2; 
	 * a value less than 0 if this time1 is before the time2 ; 
	 * and a value greater than 0 if this time1 is after the time2 .
	 * @throws ParseException 
	 */
	public static int compareTime(String time1, String time2) throws ParseException {
		
		Date d1 = sdTimeHHmm.parse(time1);
		Date d2 = sdTimeHHmm.parse(time2);
		
		return d1.compareTo(d2);
		
	}

	public static int getDeviationDay(Date date1, Date date2) throws ParseException {
		
		Date newdate1 = sdDateTime3.parse(sdDate.format(date1) + "000000");
		Date newdate2 = sdDateTime3.parse(sdDate.format(date2) + "000000");
		
		long datetime1 = newdate1.getTime() / (1000 * 60 * 60 * 24);
		long datetime2 = newdate2.getTime() / (1000 * 60 * 60 * 24);
		
		return (int) Math.abs(datetime1 - datetime2);
		
		
	}

	public static Date getDateAddYear(Date date, int addYear) {
		Calendar cals = Calendar.getInstance();
		cals.setTime(date);
		cals.add(Calendar.YEAR, addYear);
		
		return cals.getTime();
	}

	public static boolean isHHmmss(String time) {
		try {
			Date dtime = sdTime2.parse(time);
			if (!sdTime2.format(dtime).equals(time)){
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * 特別處理yyy(民國年)
	 * @param format
	 * @param value
	 * @return
	 */
	public static Date dateFormatS2D(String value, String format) throws Exception {

		if (StringUtils.isEmpty(value) || StringUtils.isEmpty(value.trim())){
			return null;
		}
		
		if (format.indexOf("yyyy") < 0){
		
			int yyyIdx = format.indexOf("yyy");
			
			if (yyyIdx >= 0){
				
				String yyy = value.substring(yyyIdx, yyyIdx + 3);
				
				format = format.replace("yyy", "yyyy");
				
				value = value.substring(0, yyyIdx) + yyy2yyyy(Integer.parseInt(yyy)) + value.substring(yyyIdx + 3, value.length());
			}
		}
		//System.out.println(format);
		
		return getSimpaleDateFormat(format).parse(value);
	}
	
	/**
	 * 特別處理yyy(民國年)
	 * @param format
	 * @param date
	 * @return
	 */
	public static String dateFormatD2S(Date date, String format){
		
		if ( date == null ) return null;
		
		if (format.indexOf("yyyy") < 0){
		
			if (format.indexOf("yyy") >= 0){
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				
				int yyy = yyyy2yyy(calendar.get( Calendar.YEAR ));
				
				format = format.replace("yyy", yyy + "");
			}
		}

		return getSimpaleDateFormat(format).format(date);
	}
	
	public static int yyy2yyyy(int yyy){
		return yyy + 1911;
	}
	
	public static int yyyy2yyy(int yyyy){
		return yyyy - 1911;
	}

	public static Date getLastDate(Timestamp date1, Timestamp date2) {

		if (date1 == null && date2 == null){
			return null;
		}else if (date1 == null){
			return date2;
		}else if (date2 == null){
			return date1;
		}else{
			return date1.after(date2)? date1: date2;
		}
		
	}

	
	
}
