package com.bt.reflection.vo;

import com.bt.reflection.interfaces.ChildInterface;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class S2 implements ChildInterface {
	
	private String name;
	private int age;
	private String id;
	
	@Override
	public String getId() {
		return "S2:" + id;
	}
	@Override
	public String getCode() {
		return "DEF01";
	}

}
