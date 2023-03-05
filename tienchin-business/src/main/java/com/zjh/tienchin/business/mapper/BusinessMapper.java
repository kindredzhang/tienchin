package com.zjh.tienchin.business.mapper;

import com.zjh.tienchin.business.domain.Business;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.tienchin.business.domain.vo.BusinessSummary;
import com.zjh.tienchin.business.domain.vo.BusinessVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kindredzhang
 * @since 2023-03-03
 */
public interface BusinessMapper extends BaseMapper<Business> {

    List<BusinessSummary> selectBusinessList(BusinessVO businessVO);

}
