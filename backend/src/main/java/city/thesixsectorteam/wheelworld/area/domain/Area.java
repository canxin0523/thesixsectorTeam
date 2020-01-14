package city.thesixsectorteam.wheelworld.area.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

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
@Excel("地区信息表")
public class Area implements Serializable{

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;             //主键id
    @ExcelField(value = "地区code")
    private Long code;             //地区code
    @ExcelField(value = "地区名称")
    private String name;            //地区名称
    @ExcelField(value = "地区描述")
    private String info;           //描述

    private Date createTime;     //时间

    private Long fatherId;       //父级id
}
