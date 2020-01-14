package city.thesixsectorteam.wheelworld.hadesCurrency.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 类/方法名称：HadesCurrency
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Data
@TableName("t_mingjie_hadescurrency")
public class HadesCurrency implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long    id;         //主键id

    private Long    userId;     //用户id

    private BigDecimal total;  //总mb

    private Date    time;       //充值时间
}
