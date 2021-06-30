package com.lf.ec.common.base.exception

class ExtFieldTypeErrorException(type: String) : BaseException(ErrorCode.EXT_FIELD_TYPE_ERROR, type) {
}