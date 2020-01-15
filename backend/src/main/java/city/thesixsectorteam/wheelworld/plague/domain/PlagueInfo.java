package city.thesixsectorteam.wheelworld.plague.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 类/方法名称：PlagueInfo
 * 类/方法描述：
 * 创建人：mango
 * 创建时间： 2019/12/28
 * 修改备注：
 */
@Data
@TableName("t_plague_info")
public class PlagueInfo {
    @TableId(value = "PLAGUE_INFO_ID",type = IdType.AUTO)
    private Long plagueInfoId;
    private Long plagueId;
    private Long areaId;
    private Date happenTime;
    private String info;
}
