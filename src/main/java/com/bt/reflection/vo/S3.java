package com.bt.reflection.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class S3 {
	
	private String name;
	private int age;
	private String id;
	
	
	public String getId() {
		return "S3:" + id;
	}

	public String getCode() {
		return "DEF02";
	}

}
