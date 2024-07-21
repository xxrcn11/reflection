package com.bt.reflection.vo;

import com.bt.reflection.interfaces.OriginalInterface;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class S1 implements OriginalInterface {

	private String name;
	private int age;
	private String id;
	
	@Override
	public String getId() {
		return "S1:" + id;
	}
	
}
