package com.zoran.gulimallproduct;

import com.zoran.gulimallproduct.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GulimallProductApplicationTests {

	@Resource
	private CategoryService categoryService;
	@Test
	void contextLoads() {
		System.out.println(categoryService.count());
	}

}
