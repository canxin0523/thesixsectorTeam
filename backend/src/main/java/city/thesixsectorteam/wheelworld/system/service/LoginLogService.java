package city.thesixsectorteam.wheelworld.system.service;

import city.thesixsectorteam.wheelworld.system.domain.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog (LoginLog loginLog);
}
