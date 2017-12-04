package com.lemon.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称:  [ecp]
 * 包:        [com.cndtour.ecp.crs.annotation]
 * 类描述:    [配置返回值类型]
 * 创建人:    [xflu]
 * 创建时间:  [2015/7/31 16:03]
 * 修改人:    [xflu]
 * 修改时间:  [2015/7/31 16:03]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodName {
    String name() default "";
}
