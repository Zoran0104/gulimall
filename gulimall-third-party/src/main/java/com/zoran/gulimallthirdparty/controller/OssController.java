package com.zoran.gulimallthirdparty.controller;

import com.zoran.gulimallthirdparty.service.OssService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oss")
@AllArgsConstructor
public class OssController {
    private final OssService ossService;

    @GetMapping("/policy")
    public Map<String, String> getPolicy() {
        return ossService.getPolicy();
    }
}
