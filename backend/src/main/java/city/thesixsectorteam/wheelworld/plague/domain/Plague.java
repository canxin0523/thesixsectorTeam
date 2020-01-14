package city.thesixsectorteam.wheelworld.plague.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 类/方法名称：Plague
 * 类/方法描述：
 * 创建人：mango
 * 创建时间： 2019/12/28
 * 修改备注：
 */
@Data
@TableName("t_plague")
public class Plague {
    @TableId(value = "PLAGUE_ID",type = IdType.AUTO)
    private Long plagueId; //瘟疫ID
    private String plagueName; //瘟疫名称
    private Integer plagueStatus; //瘟疫状态（1.可用，2不可用）
    private Date createTime; //创建时间
}
