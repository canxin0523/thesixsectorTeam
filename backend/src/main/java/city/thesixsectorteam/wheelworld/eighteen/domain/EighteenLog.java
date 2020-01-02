package city.thesixsectorteam.wheelworld.eighteen.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：EighteenLog
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2020/1/2
 * 修改备注：
 */
@Data
@TableName("t_mingjie_eighteen_log")
public class EighteenLog implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;             //主键id

    private Long    userId;         //用户id

    private Long    eighteenId;           //18主键

    private Date inOrOutTime;         //进入时间/出去时间

    private Integer status;         //状态    1.进入时间  2.出去时间

    private String  info;           //描述
}
