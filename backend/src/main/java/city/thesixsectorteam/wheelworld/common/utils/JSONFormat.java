package com.brez.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JSON 日期格式处理（java转化为JSON）
 *
 * @author zxd
 */
@SuppressWarnings("unchecked")
public class JSONFormat implements JsonValueProcessor {

    /**
     * datePattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * set集合预留字段
     */
    private String[] properties;


    /**
     * 需要做处理的复杂属性类型
     */
    private Class<?> clazz;

    /**
     * JsonObjectValueProcessor
     */
    public JSONFormat() {
        super();
    }

    /**
     * @param format
     */
    public JSONFormat(String format) {
        super();
        this.datePattern = format;
    }

    /**
     * @param properties
     * @param clazz
     */
    public JSONFormat(String[] properties, Class<?> clazz) {
        super();
        this.properties = properties;
        this.clazz = clazz;
    }

    /**
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * @param key
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processObjectValue(String key, Object value,
                                     JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * process
     *
     * @param value
     * @return
     */
    private Object process(Object value) {
        try {
            if (value instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                return sdf.format((Date) value);
            } else if (value instanceof Set) {
                PropertyDescriptor propertyDescriptor = null;
                Method method = null;
                Set<Object> set = (Set<Object>) value;
                StringBuffer jsonReturn = new StringBuffer("");
                for (Object obj : set) {
                    for (String pp : properties) {
                        propertyDescriptor = new PropertyDescriptor(pp, clazz);
                        method = propertyDescriptor.getReadMethod();
                        jsonReturn.append(method.invoke(obj) + ",");
                    }
                }
                return jsonReturn.delete(jsonReturn.length() - 1, jsonReturn.length()).toString();
            }
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }

    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String pDatePattern) {
        datePattern = pDatePattern;
    }

    /**
     * 格式化含时间json数组
     *
     * @param list                   数据列表
     * @param formatType             时间格式 如yyyy/MM/dd HH:mm:ss
     * @param clazz                  时间类型 如Timestamp
     * @param filterStr可过滤的字段列表(可为空)
     * @return
     * @作者: 张晓东
     * @创建日期： 2015年12月8日
     * @返回值： JSONArray
     * @修改记录（修改时间、作者、原因）：
     */
    public static JSONArray formatDate(List list, String formatType, Class<?> clazz, final List<String> filterStr) {
        return formatDate(list, formatType, clazz, filterStr, true);
    }

    public static JSONArray formatDate(List list, String formatType, Class<?> clazz, final List<String> filterStr, final boolean exClude) {
        JSONArray resultArray = null;
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(clazz, new JSONFormat(formatType));
        jsonConfig.registerJsonValueProcessor(HashMap.class, new JsonValueProcessor() {//去空配置
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                return null;
            }

            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                Map map = (HashMap) value;
                for (Object key : map.keySet()) {
                    if (map.get(key) == null) {
                        map.put(key, "");
                    }
                }
                return JSONObject.fromObject(value, jsonConfig);
            }
        });
        //过滤字符串
        if (filterStr != null && filterStr.size() > 0) {
            jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                // 重写内部的允许字段通过的方法
                public boolean apply(Object source, String name, Object value) {
                    // 排除的字段名字（属性名）
                    if (exClude) {
                        return filterStr.contains(name);
                    } else {
                        return !filterStr.contains(name);
                    }
                }
            });
        }
        resultArray = JSONArray.fromObject(list, jsonConfig);
        return resultArray;
    }

    /**
     * 格式化含时间json对象
     *
     * @param map               数据对象
     * @param formatType        时间格式 如yyyy/MM/dd HH:mm:ss
     * @param clazz             时间类型 如Timestamp
     * @param filterStr可过滤的字段列表
     * @return
     * @作者: 张晓东
     * @创建日期： 2015年12月8日
     * @返回值： JSONArray
     * @修改记录（修改时间、作者、原因）：
     */
    public static JSONObject formatDate(Map map, String formatType, Class<?> clazz, final List<String> filterStr) {
        JSONObject resultObject = null;
        for (Object key : map.keySet()) {
            if (map.get(key) == null) {
                map.put(key, "");
            }
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(clazz, new JSONFormat(formatType));
        //过滤字符串
        if (filterStr != null && filterStr.size() > 0) {
            jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                // 重写内部的允许字段通过的方法
                public boolean apply(Object source, String name, Object value) {
                    // 排除的字段名字（属性名）
                    if (filterStr.contains(name)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
        resultObject = JSONObject.fromObject(map, jsonConfig);
        return resultObject;
    }

    public static void main(String[] args) {
        List<String> filterStr = Arrays.asList(new String[]{"date", "endRow", "firstPage", "hasNextPage", "hasPreviousPage", "isFirstPage", "isLastPage", "lastPage", "navigatepageNums", "nextPage", "size", "prePage", "startRow", "navigatePages"});
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", "张三");
        map1.put("address", null);
        map1.put("date", new Timestamp(System.currentTimeMillis()));
        list.add(map1);
        System.out.println(JSONFormat.formatDate(map1, "yyyy/MM/dd HH:mm:ss", Timestamp.class, null));
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "李四");
        map2.put("address", null);
        map2.put("date", new Timestamp(System.currentTimeMillis()));
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map21 = new HashMap<String, Object>();
        map21.put("name", "张三");
        map21.put("address", null);
        map21.put("date", new Timestamp(System.currentTimeMillis()));
        list2.add(map21);
        map2.put("brothers", list2);
        list.add(map2);
        System.out.println(JSONFormat.formatDate(list, "yyyy/MM/dd HH:mm:ss", Timestamp.class, filterStr));

    }

    public static JSONObject formatPage(Object map, String formatType, Class<?> clazz, final List<String> filterStr) {
        JSONObject resultObject = null;
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(clazz, new JSONFormat(formatType));
        jsonConfig.registerJsonValueProcessor(HashMap.class, new JsonValueProcessor() {//去空配置
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                return null;
            }

            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                Map map = (HashMap) value;
                for (Object key : map.keySet()) {
                    if (map.get(key) == null) {
                        map.put(key, "");
                    }
                }
                return JSONObject.fromObject(value, jsonConfig);
            }
        });
        //过滤字符串
        //默认过滤mybatis产生的分页字段
        final List<String> defultStr = new ArrayList<>(Arrays.asList("endRow", "firstPage", "hasNextPage",
                "hasPreviousPage", "isFirstPage", "isLastPage", "lastPage", "navigatepageNums",
                "nextPage", "size", "prePage", "startRow", "navigatePages"));
        if (filterStr != null && filterStr.size() > 0) {
            defultStr.addAll(filterStr);
        }
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            // 重写内部的允许字段通过的方法
            public boolean apply(Object source, String name, Object value) {
                // 排除的字段名字（属性名）
                if (defultStr.contains(name)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        JSONObject mapObj = JSONObject.fromObject(map, jsonConfig);
        for (Object key : mapObj.keySet()) {
            if (mapObj.get(key) == null || "null".equals(mapObj.get(key))) {
                mapObj.put(key, "");
            }
        }
        resultObject = JSONObject.fromObject(mapObj, jsonConfig);
        return resultObject;
    }
}
