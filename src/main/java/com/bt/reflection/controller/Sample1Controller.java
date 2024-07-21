package com.bt.reflection.controller;

import org.springframework.stereotype.Component;

import com.bt.reflection.annotations.StnAPI;
import com.bt.reflection.annotations.StnController;
import com.bt.reflection.vo.S1;
import com.bt.reflection.vo.S2;
import com.bt.reflection.vo.S3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@StnController
public class Sample1Controller {

	@StnAPI(api = "01")
	public void method1(S1 s) {
		log.info("method1이 처리합니다. data: {}", s);
	}
	
	@StnAPI(api = "02")
	public void method2(S2 s) {
		log.info("method2이 처리합니다. data: {}", s);
	}
	
	@StnAPI(api = "03")
	public void method3(S3 s) {
		log.info("method3이 처리합니다. data: {}", s);
	}
	
	
}
