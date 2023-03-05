package com.zjh.tienchin.clue.mapper;

import com.zjh.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.tienchin.clue.domain.vo.ClueDetails;
import com.zjh.tienchin.clue.domain.vo.ClueSummary;
import com.zjh.tienchin.clue.domain.vo.ClueVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
public interface ClueMapper extends BaseMapper<Clue> {

    List<ClueSummary> selectClueList(ClueVo clueVO);

    ClueSummary getClueSummaryById(Long clueId);

    ClueDetails getClueDetailsByClueId(Long clueId);
}
