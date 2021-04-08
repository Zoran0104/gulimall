package com.zoran.gulimallproduct.web;

import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.zoran.gulimallproduct.service.CategoryService;
import com.zoran.gulimallproduct.vo.Catalog2Vo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/5 16:29
 * @description：
 * @modified By：
 * @version:
 */
@Controller
@AllArgsConstructor
@Slf4j
public class IndexController {
    private final CategoryService categoryService;


    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        return categoryService.getCatalogJson();
    }


    @GetMapping("/hello")
    @ResponseBody
    public String getHello() {
        return "hello";
    }
}
