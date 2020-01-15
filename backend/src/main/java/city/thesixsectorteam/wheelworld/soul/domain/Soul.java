package city.thesixsectorteam.wheelworld.soul.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：Soul
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Data
@TableName("t_mingjie_soul")
public class Soul implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;             //主键id

    private Long    userId;         //用户id

    private Long  executorId;     //执行人id   (内部用户列表中选择)（未分配就是执行人为null，分配但是未勾魂的就是有执行人但是执行状态为1）

    private String  executorInfo;   //执行原因

    private Date executorTime;   //执行时间

    private Integer executorStatus; //执行状态   1.未成功 2.成功
}
