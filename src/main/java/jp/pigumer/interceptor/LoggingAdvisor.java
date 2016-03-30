package jp.pigumer.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class LoggingAdvisor extends AbstractPointcutAdvisor {

	private static final long serialVersionUID = 1L;

	@Autowired
	LoggingInterceptor interceptor;
	
	@Override
	public Pointcut getPointcut() {
		return new Pointcut() {

			@Override
			public ClassFilter getClassFilter() {
				return ClassFilter.TRUE;
			}

			@Override
			public MethodMatcher getMethodMatcher() {
				return new MethodMatcher() {

					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						return AnnotationUtils.isAnnotationDeclaredLocally(Controller.class, targetClass) ||
								AnnotationUtils.isAnnotationDeclaredLocally(RestController.class, targetClass);
					}

					@Override
					public boolean isRuntime() {
						return false;
					}

					@Override
					public boolean matches(Method method, Class<?> targetClass, Object... args) {
						return matches(method, targetClass);
					}
					
				};
			}
			
		};
	}

	@Override
	public Advice getAdvice() {
		return interceptor;
	}

}
