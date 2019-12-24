package city.thesixsectorteam.wheelworld.common.utils;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


public class Util {
    public static SimpleDateFormat fmtAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 转化为字符串
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }
    
    /**
     * 转化为字符串（带有默认值）
     * @param obj
     * @return
     */
    public static String toStr(Object obj, Object dft) {
        if (obj == null) {
            return toStr(dft);
        } else if (obj.toString().trim().equals("")) {
            return toStr(dft);
        } else {
            return obj.toString().trim();
        }
    }
    
    /**
     * 转为int型
     * @param obj OBJECT型
     * @param defaultValue 缺省类型
     * @return 转化结果
     */
    public static int toInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }
    
    public static void main(String [] args){
		System.out.println(getMoveDate(-30));
    }
    
    public static String getMinMonthDate(){
    	SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return fm.format(calendar.getTime());
	}
    
    public static String getWeek(int type){
    	SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
        Calendar rl=Calendar.getInstance(Locale.CHINA);
        rl.setFirstDayOfWeek(Calendar.MONDAY);
        rl.setTimeInMillis(System.currentTimeMillis());
        if(type == 7){
        	rl.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }else{
        	rl.set(Calendar.DAY_OF_WEEK, type + 1);
        }
        return fm.format(rl.getTime());
    }
    
    /**
     * 百度地图坐标转化
     * @param lat经度
     * @param lon维度
     * @return
     */
    public static String map_tx2bd(double lat, double lon){  
        double bd_lat;  
        double bd_lon;  
        double x_pi=3.14159265358979324;  
        double x = lon, y = lat;  
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
        bd_lon = z * Math.cos(theta) + 0.0065;  
        bd_lat = z * Math.sin(theta) + 0.006;  
        return bd_lon+","+bd_lat;  
    }
    
    /**
     * list中的时间类型格式化
     * @param list 对象
     * @param col 格式化字段  多个时“,”分隔
     * @return
     */
    public static List<Map<String, Object>> mapRtnFmt(List<Map<String, Object>> list, String col){
        if(list == null || list.size() == 0 || col.equals("")){
            return list;
        }
        String [] cols = col.split(",");
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < cols.length; j++){
                if(toStr(list.get(i).get(cols[j])).length() > 19){
                    list.get(i).put(cols[j], toStr(list.get(i).get(cols[j])).substring(0,19));
                }else{
                    list.get(i).put(cols[j], toStr(list.get(i).get(cols[j])));
                }
            }
            Map<String, Object> map = list.get(i);
            for(Entry<String, Object> entry : map.entrySet()){
                if(entry.getValue() == null){
                    list.get(i).put(entry.getKey(), "");
                }
            }
        }
        return list;
    }

    /** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
    public static Map<String, Object> convertBean(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException { 
        Class<? extends Object> type = bean.getClass(); 
        Map<String, Object> returnMap = new HashMap<>(); 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); 

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if (!propertyName.equals("class")) { 
                Method readMethod = descriptor.getReadMethod(); 
                Object result = readMethod.invoke(bean, new Object[0]); 
                if (result != null) { 
                    returnMap.put(propertyName, result); 
                } else { 
                    returnMap.put(propertyName, ""); 
                } 
            } 
        } 
        return returnMap; 
    }

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * 
	 * @param o
	 * @return
	 */
	public static List<Map<String, Object>> getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		//String[] fieldNames = new String[fields.length];
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap<>();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			
			Object value = "";
			try {
				String firstLetter = fields[i].getName().substring(0, 1).toUpperCase();
				String getter = "get" + firstLetter + fields[i].getName().substring(1);
				Method method = o.getClass().getMethod(getter, new Class[] {});
				value = method.invoke(o, new Object[] {});
			} catch (Exception e) {
				return null;
			}
			
			infoMap.put("value", value);
			list.add(infoMap);
		}
		return list;
	}
	
	/**
	 * 获取当前日期相隔日期
	 * @param dateCount 相隔天数
	 * @return
	 */
	public static String getMoveDate(int dateCount){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DATE, dateCount);
    	Date start = calendar.getTime();
    	String startDate = sdf.format(start);
    	return startDate;
	}
}
