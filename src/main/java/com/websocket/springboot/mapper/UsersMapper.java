package com.websocket.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.websocket.springboot.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UsersMapper extends BaseMapper<Users> {
}
