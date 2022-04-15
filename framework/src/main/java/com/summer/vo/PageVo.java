package com.summer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Summer
 * @since 2022/4/16 1:59
 */
@Data
@AllArgsConstructor
public class PageVo {
    private List rows;
    private Long total;
}
