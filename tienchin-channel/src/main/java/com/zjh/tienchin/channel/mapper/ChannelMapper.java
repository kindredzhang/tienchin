package com.zjh.tienchin.channel.mapper;

import com.zjh.tienchin.channel.domain.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.tienchin.channel.domain.vo.ChannelVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-13
 */
public interface ChannelMapper extends BaseMapper<Channel> {

    List<Channel> selectChannelList(ChannelVO channelVO);
}
