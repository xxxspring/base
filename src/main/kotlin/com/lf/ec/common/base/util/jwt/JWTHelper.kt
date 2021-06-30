//package com.lf.ec.common.base.util.jwt
//
//import com.lf.ec.common.base.constant.CommonConstants
//import com.lf.ec.common.base.util.StringHelper
//import io.jsonwebtoken.Claims
//import io.jsonwebtoken.Jws
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import org.joda.time.DateTime
//
///**
// * Created by xponly on 17/11/19.
// */
//
//object JWTHelper {
//    private val rsaKeyHelper = RsaKeyHelper()
//    /**
//     * 密钥加密token
//     *
//     * @param jwtInfo
//     * @param priKeyPath
//     * @param expire
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun generateToken(jwtInfo: IJWTInfo, priKeyPath: String, expire: Int): String {
//        return Jwts.builder()
//                .setSubject(jwtInfo.uniqueName)
//                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.uid)
//                .claim(CommonConstants.JWT_KEY_USERNAME, jwtInfo.username)
//                .claim(CommonConstants.JWT_KEY_TENANT_ID, jwtInfo.tenantId)
//                .claim(CommonConstants.JWT_KEY_NONCE, jwtInfo.nonce)
//                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
//                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
//                .compact()
//    }
//
//    /**
//     * 密钥加密token
//     *
//     * @param jwtInfo
//     * @param priKey
//     * @param expire
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun generateToken(jwtInfo: IJWTInfo, priKey: ByteArray, expire: Int): String {
//        return Jwts.builder()
//                .setSubject(jwtInfo.uniqueName)
//                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.uid)
//                .claim(CommonConstants.JWT_KEY_USERNAME, jwtInfo.username)
//                .claim(CommonConstants.JWT_KEY_TENANT_ID, jwtInfo.tenantId)
//                .claim(CommonConstants.JWT_KEY_NONCE, jwtInfo.nonce)
//                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
//                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
//                .compact()
//    }
//
//    /**
//     * 密钥加密token
//     *
//     * @param jwtInfo
//     * @param priKey
//     * @param expire
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun generateTokenNoExpire(jwtInfo: IJWTInfo, priKey: ByteArray): String {
//        return Jwts.builder()
//                .setSubject(jwtInfo.uniqueName)
//                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.uid)
//                .claim(CommonConstants.JWT_KEY_USERNAME, jwtInfo.username)
//                .claim(CommonConstants.JWT_KEY_TENANT_ID, jwtInfo.tenantId)
//                .claim(CommonConstants.JWT_KEY_NONCE, jwtInfo.nonce)
//                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
//                .compact()
//    }
//
//    /**
//     * 公钥解析token
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun parserToken(token: String, pubKeyPath: String): Jws<Claims> {
//        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token)
//    }
//
//    /**
//     * 公钥解析token
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun parserToken(token: String, pubKey: ByteArray): Jws<Claims> {
//        return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token)
//    }
//
//    /**
//     * 获取token中的用户信息
//     *
//     * @param token
//     * @param pubKeyPath
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun getInfoFromToken(token: String, pubKeyPath: String): IJWTInfo {
//        val claimsJws = parserToken(token, pubKeyPath)
//        val body = claimsJws.body
//        return JWTInfo(StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_USER_ID]), StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_USERNAME]),
//                StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_TENANT_ID]), StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_NONCE]).toLong())
//    }
//
//    /**
//     * 获取token中的用户信息
//     *
//     * @param token
//     * @param pubKey
//     * @return
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun getInfoFromToken(token: String, pubKey: ByteArray): IJWTInfo {
//        val claimsJws = parserToken(token, pubKey)
//        val body = claimsJws.body
//        return JWTInfo(StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_USER_ID]), StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_USERNAME]),
//                StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_TENANT_ID]), StringHelper.getObjectValue(body[CommonConstants.JWT_KEY_NONCE]).toLong())
//    }
//}
