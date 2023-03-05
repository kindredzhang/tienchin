package com.zjh.tienchin.channel.service;

import com.zjh.tienchin.channel.domain.Channel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.channel.domain.vo.ChannelVO;
import com.zjh.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-13
 */
public interface IChannelService extends IService<Channel> {

    List<Channel> selectChannelList(ChannelVO channelVO);

    AjaxResult addChannel(ChannelVO channelVO);

    AjaxResult updateChannel(ChannelVO channelVO);

    Boolean deleteChannelByIds(Long[] channelIds);


    boolean importChannel(List<Channel> channelList, boolean updateSupport);

    AjaxResult getAllChannels();

}
