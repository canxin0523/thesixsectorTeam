package city.thesixsectorteam.wheelworld.system.service.impl;

import city.thesixsectorteam.wheelworld.common.utils.AddressUtil;
import city.thesixsectorteam.wheelworld.common.utils.HttpContextUtil;
import city.thesixsectorteam.wheelworld.common.utils.IPUtil;
import city.thesixsectorteam.wheelworld.system.dao.LoginLogMapper;
import city.thesixsectorteam.wheelworld.system.domain.LoginLog;
import city.thesixsectorteam.wheelworld.system.service.LoginLogService;
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
