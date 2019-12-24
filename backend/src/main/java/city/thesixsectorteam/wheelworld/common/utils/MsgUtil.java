package com.brez.util;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MsgUtil {
    public static final String OPERATE_KEY_STATUS = "status";
    public static final String OPERATE_KEY_MSG = "msg";
    public static final String OPERATE_SUCCESS = "200"; // 默认成功
    public static final String OPERATE_FAILED = "500"; // 失败码

    /**
     * 返回成功
     *
     * @param msg 提示消息
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success() {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, "操作成功");
        return result.toString();
    }

    /**
     * 返回成功
     *
     * @param msg 提示消息
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(String msg) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 返回成功
     *
     * @param msg 提示消息
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(JSONObject data) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, "操作成功");
        result.put("data", data);
        return result.toString();
    }

    /**
     * 自定义状态码返回成功
     *
     * @param msg  提示消息
     * @param code 自定义状态吗
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(String msg, Integer code) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, code);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 返回分页查询数据
     *
     * @param msg  提示消息
     * @param list 返回对象
     * @param cls  日期格式化Class
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String successPage(String msg, List<?> list, Class<?> cls) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.putAll(JSONFormat.formatPage(new PageInfo(list), "yyyy-MM-dd HH:mm:ss", cls, null));
        return result.toString();
    }

    /**
     * 返回分页查询数据
     *
     * @param msg  提示消息
     * @param list 返回对象
     * @param cls  日期格式化Class
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String successPage(String msg, List<?> list, Class<?> cls, List<String> filterStr) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.putAll(JSONFormat.formatPage(new PageInfo(list), "yyyy-MM-dd HH:mm:ss", cls, filterStr));
        return result.toString();
    }

    /**
     * 自定义key value返回数据
     *
     * @param msg
     * @param key
     * @param value
     * @return
     * @作者: hxl
     */
    public static String success(String msg, List<?> list, Class<?> cls) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.put("list", JSONFormat.formatDate(list, "yyyy-MM-dd HH:mm:ss", cls, null));
        return result.toString();
    }

    public static String success(List<?> list, Class<?> cls, String dateFormat) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put("list", JSONFormat.formatDate(list, dateFormat, cls, null));
        return result.toString();
    }

    public static String success(List<?> list, Class<?> cls) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put("list", JSONFormat.formatDate(list, "yyyy-MM-dd HH:mm:ss", cls, null));
        return result.toString();
    }

    public static String success(String msg, List<?> list, Class<?> cls, List<String> filterStr) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.put("list", JSONFormat.formatDate(list, "yyyy-MM-dd HH:mm:ss", cls, filterStr));
        return result.toString();
    }

    public static String success(String msg, List<?> list, Class<?> cls, String removeItem) {
        String[] rms = removeItem.split(",");
        List<String> removeList = Arrays.asList(rms);
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.put("list", JSONFormat.formatDate(list, "yyyy-MM-dd HH:mm:ss", cls, removeList));
        return result.toString();
    }

    public static String success(String msg, List<?> list) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        result.put("list", list);
        return result.toString();
    }

    public static String success(List<?> list) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put("list", list);
        return result.toString();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String successPage(List<?> list, Class<?> cls) {
        JSONObject result = JSONFormat.formatPage(new PageInfo(list), "yyyy-MM-dd HH:mm:ss", cls, null);
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        return result.toString();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String successPage(List<?> list, Class<?> cls, String dateFormat) {
        JSONObject result = JSONFormat.formatPage(new PageInfo(list), dateFormat, cls, null);
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        return result.toString();
    }

    /**
     * 带参数返回成功
     *
     * @param msg    提示消息
     * @param result 返回对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(String msg, JSONObject result) {
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 带参数返回成功
     *
     * @param msg    提示消息
     * @param object 返回对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(String msg, Object object) {
        return success(msg, object, Date.class);
    }

    /**
     * 带参数返回成功
     *
     * @param msg    提示消息
     * @param object 返回对象
     * @param cls    时间类型类对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    @SuppressWarnings("rawtypes")
    public static String success(String msg, Object object, Class cls) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(cls, new JSONFormat("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = JSONObject.fromObject(object, jsonConfig);
        result.put(OPERATE_KEY_STATUS, OPERATE_SUCCESS);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 自定义状态码带参数返回成功
     *
     * @param msg    提示消息
     * @param code   自定义状态码
     * @param result 返回对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String success(String msg, Integer code, JSONObject result) {
        result.put(OPERATE_KEY_STATUS, code);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 返回失败
     *
     * @param msg 提示消息
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String fail() {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_FAILED);
        result.put(OPERATE_KEY_MSG, "操作失败");
        return result.toString();
    }

    /**
     * 返回失败
     *
     * @param msg 提示消息
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String fail(String msg) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, OPERATE_FAILED);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 自定义状态码返回失败
     *
     * @param msg  提示消息
     * @param code 自定义状态码
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String fail(String msg, Integer code) {
        JSONObject result = new JSONObject();
        result.put(OPERATE_KEY_STATUS, code);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 带参数返回失败
     *
     * @param msg    提示消息
     * @param result 返回对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String fail(String msg, JSONObject result) {
        result.put(OPERATE_KEY_STATUS, OPERATE_FAILED);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    /**
     * 自定义状态码带参数返回失败
     *
     * @param msg    提示消息
     * @param code   自定义状态码
     * @param result 返回对象
     * @return
     * @作者: 张晓东
     * @创建日期： 2016年11月2日
     */
    public static String fail(String msg, Integer code, JSONObject result) {
        result.put(OPERATE_KEY_STATUS, code);
        result.put(OPERATE_KEY_MSG, msg);
        return result.toString();
    }

    public static void main(String[] args) {

    }
}
