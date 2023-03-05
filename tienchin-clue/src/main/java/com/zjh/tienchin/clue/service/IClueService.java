package com.zjh.tienchin.clue.service;

import com.zjh.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.clue.domain.vo.ClueDetails;
import com.zjh.tienchin.clue.domain.vo.ClueSummary;
import com.zjh.tienchin.clue.domain.vo.ClueVo;
import com.zjh.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
public interface IClueService extends IService<Clue> {

    AjaxResult addClue(Clue clue);

    List<ClueSummary> selectClueList(ClueVo clueVo);

    ClueSummary getClueSummaryById(Long clueId);

    AjaxResult getClueDetailsByClueId(Long clueId);

    AjaxResult clueFollow(ClueDetails clueDetails);

    AjaxResult invalidClueFollow(ClueDetails clueDetails);

    AjaxResult getClueSummaryByClueId(Long clueId);

    AjaxResult updateClue(Clue clue);

    AjaxResult clue2Business(Integer clueId);
}
