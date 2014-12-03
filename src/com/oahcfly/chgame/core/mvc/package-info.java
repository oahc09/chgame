/**
 * Project Name:CHGame-core
 * Copyright (c) 2014 OahcFly All Rights Reserved.
 */
/**
 * <pre>
 * MVC框架：
 * CHGame    控制层
 * CHModel   模型层
 * CHScreen 视图层
 *
 * CHGame负责切换CHScreen以及一些全局性的信息处理
 * 
 * CHModel数据变化更新，通知CHScreen相应的UI进行刷新
 * 一个CHScreen对应一个CHModel，也可以多个CHScreen对应1个CHModel
 * 
 * CHScreen肩负逻辑处理和ui更新操作，UI相关又可以分给CHUI具体来处理了。
 *
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 */

package com.oahcfly.chgame.core.mvc;