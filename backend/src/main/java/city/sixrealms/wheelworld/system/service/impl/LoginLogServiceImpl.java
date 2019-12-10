package city.sixrealms.wheelworld.system.service.impl;

import city.sixrealms.wheelworld.common.utils.AddressUtil;
import city.sixrealms.wheelworld.common.utils.HttpContextUtil;
import city.sixrealms.wheelworld.common.utils.IPUtil;
import city.sixrealms.wheelworld.system.dao.LoginLogMapper;
import city.sixrealms.wheelworld.system.domain.LoginLog;
import city.sixrealms.wheelworld.system.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service("loginLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    @Transactional
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }
}
