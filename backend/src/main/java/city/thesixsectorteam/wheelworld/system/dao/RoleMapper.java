package city.thesixsectorteam.wheelworld.system.dao;

import city.thesixsectorteam.wheelworld.system.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
	
	List<Role> findUserRole(String userName);
	
}