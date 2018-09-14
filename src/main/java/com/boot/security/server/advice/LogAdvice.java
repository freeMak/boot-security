package com.boot.security.server.advice;

import com.boot.security.server.utils.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.boot.security.server.annotation.LogAnnotation;
import com.boot.security.server.model.SysLogs;
import com.boot.security.server.service.SysLogService;

import io.swagger.annotations.ApiOperation;

/**
 * 统一日志处理
 *
 * @author 小威老师 xiaoweijiagou@163.com
 *
 *         2017年8月19日
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private SysLogService logService;

	@Around(value = "@annotation(com.boot.security.server.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
        sysLogs.setUser(UserUtil.getLoginUser()); // 设置当前登录用户
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
		if (StringUtils.isEmpty(module)) {
			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
			if (apiOperation != null) {
				module = apiOperation.value();
			}
		}

		if (StringUtils.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setModule(module);

		try {
			Object object = joinPoint.proceed();
			sysLogs.setFlag(true);

			return object;
		} catch (Exception e) {
			sysLogs.setFlag(false);
			sysLogs.setRemark(e.getMessage());
			throw e;
        } finally {
            if (sysLogs.getUser() != null) {
                logService.save(sysLogs);
            }
        }

	}
}
