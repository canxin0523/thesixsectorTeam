package city.thesixsectorteam.wheelworld.plague.service;

import city.thesixsectorteam.wheelworld.plague.domain.PlagueInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 类/方法名称：PlagueInfoService
 * 类/方法描述： 瘟疫详细描述
 * 创建人：mango
 * 创建时间： 2019/12/28
 * 修改备注：
 */
public interface PlagueInfoService extends IService<PlagueInfo> {
    /**
     * 更加瘟疫Id 查询瘟疫详细
     */
    Map findPlagueInfoByPlagueId(String plagueId);

    /**
     * 添加瘟疫详细
     */
    void addPlagueInfo(PlagueInfo plagueInfo);
    /**
     * 更新瘟疫详细
     */
    void updatePlagueInfo(PlagueInfo plagueInfo);
}
