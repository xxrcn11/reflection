package com.bt.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.bt.reflection.annotations.StnAPI;
import com.bt.reflection.annotations.StnController;
import com.bt.reflection.interfaces.OriginalInterface;
import com.bt.reflection.vo.BeanInfo;
import com.bt.reflection.vo.S1;
import com.bt.reflection.vo.S2;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		/**
		 * 1. 등록된 beans 중에서 특정 annotation이 선언된 클래스 추출
		 * 2. 1에서 추출한 bean 들 중에서 특정 annotation을 가진 메소드 선택
		 * 3. 2의 애노테이션 값을 key로 하고 메소드 정보(메소드 이름, 파라미터 정보....)를 value로 하는 Map 완성
		 * 4. 3의 정보를 바탕으로 특정 클래스(S, S2)가 파라미터로 전돨되면 해당 파라미터를 처리할 수 있는 메소드를 invoke 호출 
		 */
		
		
		Map<String, Object>  beansMap = getClassesWithStnController(context);
		
		
		
		// key : parameter[0].type, value : Method 인 Map을 생성해서 key로 처리할 메소드 찾아서 호출할 것!
		
		List<BeanInfo> callMethodList = new ArrayList<>();
		beansMap.forEach((k, v) -> {
			log.info("key: {}, value : {}", k, v);
			callMethodList.addAll(filterMethod(v));
			
		});
		
		callMethodList.forEach(beanInfo -> {
			log.info("name : {}, api : {}, parameter[0].type : {}", 
					beanInfo.getMethod().getName(), 
					beanInfo.getMethod().getDeclaredAnnotation(StnAPI.class).api(),
					beanInfo.getMethod()
			);
		});
		
		
		List<Object> dataList = getDataList();
		dataList.forEach( x -> {
			BeanInfo beanInfo = findBeanInfo(callMethodList, x);
			
			try {
				beanInfo.getMethod().invoke(beanInfo.getObj(), x);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
	}
	
	private static BeanInfo findBeanInfo(List<BeanInfo> callMethodList, Object parameter) {
		
		
		for (BeanInfo beanInfo : callMethodList) {
			if( beanInfo.getParameterClass().equals(parameter.getClass()) ) {
				log.info("데이터 : {}, 호출할 메소드 : {}", parameter, beanInfo.getMethod());
				
				return beanInfo;
			}
		}
		
		return null;
	}

	
	private static List<Object> getDataList() {
		return List.of(
				S1.builder().name("budnamu").age(22).id("ㅋㅋㅋ").build(),
				S2.builder().name("S2").age(55).id("ㅋㅋㅋ").build()
				);
	}


	/**
	 * 메소드 필터링
	 * 1. 메소드에 StnAPI annotation이 선언되어 있어야 함
	 * 2. StnAPI annotation에 인자 api에 값이 있어야 함 
	 * 3. 메소드의 파라미터가 특정 인터페이스를 구현하고 있는 것만 필터링
	 * 
	 * @param bean
	 * @return
	 */
	private static List<BeanInfo> filterMethod(Object bean) {
		
		
		return List.of(bean.getClass().getMethods()).stream()
			.filter(m -> m.isAnnotationPresent(StnAPI.class))
			.filter(m -> !"".equals(m.getDeclaredAnnotation(StnAPI.class).api()) )
			
			.filter(m -> {
				Class cls = m.getParameters()[0].getType();
				log.info("{} : {} : {} : {}", 
						m.getName(),
						cls,
						cls.getInterfaces(),
						OriginalInterface.class.isAssignableFrom(cls)
						);
				return true;
			})
			
			.filter(m -> OriginalInterface.class.isAssignableFrom(m.getParameters()[0].getType()))
			.map(m -> new BeanInfo( bean, m, m.getParameters()[0].getType()))
			.collect(Collectors.toUnmodifiableList());
		
	}
	
	/**
	 * StnController annotation이 선언된 클래스 조회
	 * @param context
	 * @return
	 */
	private static Map<String, Object> getClassesWithStnController(ConfigurableApplicationContext context) {
		return context.getBeansWithAnnotation(StnController.class);
	}

}
