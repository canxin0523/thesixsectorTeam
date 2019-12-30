package city.thesixsectorteam.wheelworld.lifeAndDie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 类/方法名称：LifeAndDie
 * 类/方法描述：
 * 创建人：zhangmf
 * 创建时间： 2019/12/24
 * 修改备注：
 */
@Data
@TableName("t_mingjie_LifeAndDie")
@Excel("生死簿记录表")
public class LifeAndDie {
    @TableId(value = "id",type = IdType.AUTO)
    private Long    id;         //主键id

    private String  userId;     //用户id

    private Date createTime; //创建时间

    private Integer age;        //当前

    private Integer totalAge;   //总

    private Integer overAge;    //剩余

    private Integer status;  //如果状态为1 则age totalAge overAge代表的是生者的  当前  总   剩余
                                //如果状态为2 则代表的为  当前  总  剩余     （服役）
}
