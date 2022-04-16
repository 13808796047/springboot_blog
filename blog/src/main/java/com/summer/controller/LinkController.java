package com.summer.controller;

import com.summer.service.LinkService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Summer
 * @since 2022/4/16 11:29
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    LinkService linkService;

    @GetMapping
    public R index() {

        return linkService.getLinkList();
    }
}
