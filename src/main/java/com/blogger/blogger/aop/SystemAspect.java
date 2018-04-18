package com.blogger.blogger.aop;

import com.blogger.blogger.service.ILogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @author sucl
 */
@Aspect
@Component
public class SystemAspect {

    private final Logger logger = LoggerFactory.getLogger(SystemAspect.class);

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.blogger.blogger.aop.SystemControllerAnnotation)")
    public void controllerPoint() {
    }

    @Before("controllerPoint()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("SystemControllerAnnotation :" + getServiceMthodDescription(joinPoint));
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        StringBuffer parameters = new StringBuffer();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                parameters.append(paramName);
                parameters.append("=");
                parameters.append(paramValues[0]);
                parameters.append(";");
            }
        }
        logger.info("request parameters: " + parameters.toString());
    }


    @AfterReturning(returning = "ret", pointcut = "controllerPoint()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint
     *        切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerAnnotation.class)
                            .description();
                    break;
                }
            }
        }
        return description;
    }

    @AfterThrowing(pointcut = "controllerPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {

    }

}
