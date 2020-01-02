package city.thesixsectorteam.wheelworld.eighteen.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：Eighteen
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/31
 * 修改备注：
 */
@Data
@TableName("t_mingjie_eighteen")
public class Eighteen implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;             //主键id

    private String  eightName;         //18名称

    private String  info;           //18介绍

    private Integer level;          //层级

    private Date createTime;     //时间

}
