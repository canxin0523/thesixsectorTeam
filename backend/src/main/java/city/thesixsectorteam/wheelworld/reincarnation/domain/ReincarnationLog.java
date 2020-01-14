package city.thesixsectorteam.wheelworld.reincarnation.domain;

import city.thesixsectorteam.wheelworld.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 类/方法名称：ReincarnationLog
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/19
 * 修改备注：
 */
@Data
@TableName("t_mingjie_reincarnation_log")
@Excel("转生记录信息")
public class ReincarnationLog  implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)
    private Long    id;             //主键id

    private String  userId;         //用户id

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date    createTime;     //创建时间

    @ExcelField(value = "转生描述")
    private String  info;           //描述

    private Long    fatherId;       //上一世id

    private Long    roundId;        //zpid
}
