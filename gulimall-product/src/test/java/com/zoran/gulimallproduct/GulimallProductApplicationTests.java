package com.zoran.gulimallproduct;

import com.aliyun.oss.OSSClient;
import com.zoran.gulimallproduct.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallProductApplicationTests {

	@Resource
	private OSSClient ossClient;

	/**
	 * 用于测试OSS
	 */
	@Test
	public void testOSS() throws FileNotFoundException {
		InputStream stream = new FileInputStream("/Users/zorantaylor/Downloads/2560x1600-4569418-white-hair-red-eyes-tokyo-ghoul-kaneki-ken-anime-boys-mask-anime.jpg");
		System.out.println("hello");
		ossClient.putObject("gulimall-zoran", "neki-ken-anime-boys-mask-anime.jpg", stream);
		ossClient.shutdown();
		System.out.println("success");
	}

}
