package com.lf.ec.common.base.msg.auth;

import com.lf.ec.common.base.constant.RestCodeConstants;
import com.lf.ec.common.base.msg.BaseResponse;

/**
 * Created by ace on 2017/8/23.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
