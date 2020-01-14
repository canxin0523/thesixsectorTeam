package city.thesixsectorteam.wheelworld.hadesCurrency.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：HadesCurrencyLog
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Data
@TableName("t_mingjie_hadescurrency_log")
public class HadesCurrencyLog implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long    id;         //主键id
    private Long    userId;     //用户id
    private String  info;       //明细
    private Date    createTime; //创建时间
    private Integer status;     //状态 1.充值 2.消费
}
