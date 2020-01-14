package city.thesixsectorteam.wheelworld.trial.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：Trial
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Data
@TableName("t_mingjie_trial")
public class Trial implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;             //主键id

    private Long    userId;         //被执行人

    private Long    trialUserId;    //执行人

    private Date    trialTime;      //审判时间

    private String  info;           //审判内容

    private Integer type;           //类型 1待审 2已审

    private Integer types;          //类型 1是回 2dy
}
