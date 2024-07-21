package com.bt.reflection.vo;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 나중에 호출할 bean과 method에 대한 참조
 */
@AllArgsConstructor
@Data
public class BeanInfo {
	private Object obj; 	// 메소드가 포함된 클래스의 인스턴스
	private Method method;	// 메소드 참조
	private Class<?> parameterClass; // 메소드의 파라미터 객체 참조
}
