package city.sixrealms.wheelworld.system.service;

import city.sixrealms.wheelworld.system.domain.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog (LoginLog loginLog);
}
