package city.thesixsectorteam.wheelworld.job.dao;


import city.thesixsectorteam.wheelworld.job.domain.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface JobMapper extends BaseMapper<Job> {
	
	List<Job> queryList();
}