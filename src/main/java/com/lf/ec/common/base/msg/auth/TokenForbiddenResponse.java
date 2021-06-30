package com.lf.ec.common.base.msg.auth;

import com.lf.ec.common.base.constant.RestCodeConstants;
import com.lf.ec.common.base.msg.BaseResponse;

/**
 * Created by ace on 2017/8/25.
 */
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
