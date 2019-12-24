package city.thesixsectorteam.wheelworld.area.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：Area
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/23
 * 修改备注：
 */
@Data
@TableName("t_area")
public class Area implements Serializable{

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;             //主键id

    private Long code;             //地区code

    private String name;            //地区名称

    private String info;           //描述

    private Date createTime;     //时间

    private Long fatherId;       //父级id
}
