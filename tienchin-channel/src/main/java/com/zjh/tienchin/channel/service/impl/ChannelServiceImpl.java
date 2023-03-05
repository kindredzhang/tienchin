package com.zjh.tienchin.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjh.tienchin.channel.domain.Channel;
import com.zjh.tienchin.channel.domain.vo.ChannelVO;
import com.zjh.tienchin.channel.mapper.ChannelMapper;
import com.zjh.tienchin.channel.service.IChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.core.domain.entity.SysUser;
import com.zjh.tienchin.common.exception.ServiceException;
import com.zjh.tienchin.common.utils.SecurityUtils;
import com.zjh.tienchin.common.utils.StringUtils;
import com.zjh.tienchin.common.utils.bean.BeanValidators;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-13
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    protected Validator validator;

    @Override
    public List<Channel> selectChannelList(ChannelVO channelVO) {
        return channelMapper.selectChannelList(channelVO);
    }

    @Override
    public AjaxResult addChannel(ChannelVO channelVO) {
        QueryWrapper<Channel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Channel::getChannelName,channelVO.getChannelName()).eq(Channel::getDelFlag,0);
        Channel c = getOne(wrapper);
        if (c != null) {
            //说明存在一个同名的渠道并且没有删除
            return AjaxResult.error("存在同名渠道，添加失败");
        }
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelVO, channel);
        channel.setCreateBy(SecurityUtils.getUsername());
        channel.setCreateTime(LocalDateTime.now());
        channel.setDelFlag(0);
        return save(channel)?AjaxResult.success("添加成功"): AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateChannel(ChannelVO channelVO) {
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelVO, channel);
        channel.setUpdateBy(SecurityUtils.getUsername());
        channel.setUpdateTime(LocalDateTime.now());
        //防止前端修改这三个属性
        channel.setCreateTime(null);
        channel.setCreateBy(null);
        channel.setDelFlag(null);
        return updateById(channel)?AjaxResult.success("更新成功"): AjaxResult.error("更新失败");
    }

    @Override
    public Boolean deleteChannelByIds(Long[] channelIds) {
        UpdateWrapper<Channel> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(Channel::getDelFlag,1).in(Channel::getChannelId,channelIds);
        return update(wrapper);
    }

    @Override
    public boolean importChannel(List<Channel> channelList, boolean updateSupport) {
        if (updateSupport){
            List<Channel> collects = channelList.stream().map(c -> {
                c.setUpdateTime(LocalDateTime.now());
                c.setUpdateBy(SecurityUtils.getUsername());
                return c;
            }).collect(Collectors.toList());
            return updateBatchById(collects);
        } else {
            List<Channel> channels = channelList.stream()
                .map(item -> {
                item.setCreateTime(LocalDateTime.now());
                item.setCreateBy(SecurityUtils.getUsername());
                item.setChannelId(null);
                item.setDelFlag(0);
                return item;
            }).collect(Collectors.toList());
            return saveBatch(channels);
        }
    }

    @Override
    public AjaxResult getAllChannels() {
        QueryWrapper<Channel> wrapper = new QueryWrapper<>();
        return AjaxResult.success(list(wrapper.lambda().eq(Channel::getDelFlag,0)));
    }


}
