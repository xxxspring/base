package com.lf.ec.common.base.util.jwt

/**
 * Description: TODO
 * Author: jim
 * Date: 2018/10/5
 * Time: 下午12:56
 * Copyright (C) 2018 QiaoQiao Inc. All rights reserved.
 */
interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    var uniqueName: String?

    /**
     * 获取用户ID
     * @return
     */
    var uid: String

    var tenantId: String
    /**
     * 获取名称
     * @return
     */
    var username: String

    var nonce: Long
}