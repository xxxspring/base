package io.github.xxxspring.base.msg.auth;

import io.github.xxxspring.base.constant.RestCodeConstants;
import io.github.xxxspring.base.msg.BaseResponse;

/**
 * Created by ace on 2017/8/23.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
