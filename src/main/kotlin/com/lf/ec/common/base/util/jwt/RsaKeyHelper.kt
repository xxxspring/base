package com.lf.ec.common.base.util.jwt

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.io.DataInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 * Description: TODO
 * Author: jim
 * Date: 2018/10/5
 * Time: 下午1:00
 * Copyright (C) 2018 QiaoQiao Inc. All rights reserved.
 */
class RsaKeyHelper {
    /**
     * 获取公钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPublicKey(filename: String): PublicKey {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream(filename)
        val dis = DataInputStream(resourceAsStream)
        val keyBytes = ByteArray(resourceAsStream.available())
        dis.readFully(keyBytes)
        dis.close()
        val spec = X509EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }

    /**
     * 获取密钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPrivateKey(filename: String): PrivateKey {
        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream(filename)
        val dis = DataInputStream(resourceAsStream)
        val keyBytes = ByteArray(resourceAsStream.available())
        dis.readFully(keyBytes)
        dis.close()
        val spec = PKCS8EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(spec)
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPublicKey(publicKey: ByteArray): PublicKey {
        val spec = X509EncodedKeySpec(publicKey)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }

    /**
     * 获取密钥
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPrivateKey(privateKey: ByteArray): PrivateKey {
        val spec = PKCS8EncodedKeySpec(privateKey)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(spec)
    }

    /**
     * 生存rsa公钥和密钥
     *
     * @param publicKeyFilename
     * @param privateKeyFilename
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Throws(IOException::class, NoSuchAlgorithmException::class)
    fun generateKey(publicKeyFilename: String, privateKeyFilename: String, password: String) {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        val secureRandom = SecureRandom(password.toByteArray())
        keyPairGenerator.initialize(1024, secureRandom)
        val keyPair = keyPairGenerator.genKeyPair()
        val publicKeyBytes = keyPair.public.encoded
        var fos = FileOutputStream(publicKeyFilename)
        fos.write(publicKeyBytes)
        fos.close()
        val privateKeyBytes = keyPair.private.encoded
        fos = FileOutputStream(privateKeyFilename)
        fos.write(privateKeyBytes)
        fos.close()
    }

    companion object {

        /**
         * 生存rsa公钥
         *
         * @param password
         * @throws IOException
         * @throws NoSuchAlgorithmException
         */
        @Throws(IOException::class, NoSuchAlgorithmException::class)
        fun generatePublicKey(password: String): ByteArray {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            val secureRandom = SecureRandom(password.toByteArray())
            keyPairGenerator.initialize(1024, secureRandom)
            val keyPair = keyPairGenerator.genKeyPair()
            return keyPair.public.encoded
        }

        /**
         * 生存rsa公钥
         *
         * @param password
         * @throws IOException
         * @throws NoSuchAlgorithmException
         */
        @Throws(IOException::class, NoSuchAlgorithmException::class)
        fun generatePrivateKey(password: String): ByteArray {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            val secureRandom = SecureRandom(password.toByteArray())
            keyPairGenerator.initialize(1024, secureRandom)
            val keyPair = keyPairGenerator.genKeyPair()
            return keyPair.private.encoded
        }

        @Throws(IOException::class, NoSuchAlgorithmException::class)
        fun generateKey(password: String): Map<String, ByteArray> {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            val secureRandom = SecureRandom(password.toByteArray())
            keyPairGenerator.initialize(1024, secureRandom)
            val keyPair = keyPairGenerator.genKeyPair()
            val publicKeyBytes = keyPair.public.encoded
            val privateKeyBytes = keyPair.private.encoded
            val map = HashMap<String, ByteArray>()
            map.put("pub", publicKeyBytes)
            map.put("pri", privateKeyBytes)
            return map
        }

        fun toHexString(b: ByteArray): String {
            return BASE64Encoder().encodeBuffer(b)
        }

        @Throws(IOException::class)
        fun toBytes(s: String): ByteArray {
            return BASE64Decoder().decodeBuffer(s)
        }

        @Throws(NoSuchAlgorithmException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            val secureRandom = SecureRandom("123".toByteArray())
            keyPairGenerator.initialize(1024, secureRandom)
            val keyPair = keyPairGenerator.genKeyPair()
            println(keyPair.public.encoded)
        }
    }

}