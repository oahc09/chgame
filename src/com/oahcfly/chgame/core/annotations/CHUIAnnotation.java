
package com.oahcfly.chgame.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE
// 作用范围类
})
@Inherited
public @interface CHUIAnnotation {

    // Json路径
    String jsonPath();
}
