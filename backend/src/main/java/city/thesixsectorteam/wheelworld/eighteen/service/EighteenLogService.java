package city.thesixsectorteam.wheelworld.eighteen.service;

import city.thesixsectorteam.wheelworld.common.utils.MsgUtil;
import city.thesixsectorteam.wheelworld.common.utils.Util;
import city.thesixsectorteam.wheelworld.eighteen.dao.EighteenLogDao;
import city.thesixsectorteam.wheelworld.eighteen.domain.EighteenLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类/方法名称：EighteenLogService
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2020/1/2
 * 修改备注：
 */
@Service
@Transactional
public class EighteenLogService {

    @Autowired
    private EighteenLogDao eighteenLogDao;

    /**
     * @Author zmf
     * @Description //TODO 保存日志数据 主表
     * @Date 9:27 2020/1/2
     * @Param [eighteenLog] userId eighteen主键  status = 1  info 入狱描述
     * @return java.lang.String
    **/
    public String saveLog(EighteenLog eighteenLog) {
        eighteenLog.setInOrOutTime(new Date());
        boolean bo = eighteenLogDao.saveLog(eighteenLog);
        if (bo) return MsgUtil.success();
        return MsgUtil.fail();
    }

    /**
     * @Author zmf
     * @Description //TODO 数据参数   用户名称  和  狱名  进行模糊查询使用
     * @Date 9:59 2020/1/2
     * @Param [userName, eightName]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    public List<Map<String, Object>> logList(String userName, String eightName) {
        List<Map<String, Object>> logList = eighteenLogDao.logList(userName,eightName);
        return Util.mapRtnFmt(logList,"inOrOutTime");
    }
}
