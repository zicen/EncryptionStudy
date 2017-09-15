package com.zhenquan.encryption.对称加密

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun main(args: Array<String>) {

}

/**
 * AES(128)  128=8*16  所以需要16位的秘钥
 */
object AES{
    /**
     * 加密
     * 这种加密方式有两种限制。1、秘钥必须是16位的
     * 2、待加密内容的长度必须是16的倍数，否则出现如下异常：
     * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
     * 解决办法：通过补全传入加密内容等方式进行避免  或者直接使用base64进行编解码
     * （关于AES的博客地址：http://blog.csdn.net/hbcui1984/article/details/5201247）
     */
    fun encrypt(input:String,key:String): ByteArray? {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec)
        val result = cipher.doFinal(input.toByteArray())
        return result
    }

    fun encode(input:String,key:String): ByteArray? {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
        val result = cipher.doFinal(input.toByteArray())
        return result
    }
}