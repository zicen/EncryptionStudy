package com.zhenquan.encryption.对称加密

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

fun main(args: Array<String>) {
    val command = "你好"
    val key = "1234567812345678"
    val encrypt = AES.encrypt(command, key)
    val decode = AES.decode(encrypt, key)
    println("加密："+encrypt)
    println("解密："+decode)
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
     *
     *
     *
     * 加密需要在返回字符串的时候对其字节数组进行一个编码的操作
     */
    fun encrypt(input:String,key:String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec)
        val result = cipher.doFinal(input.toByteArray())
        return String(Base64.getEncoder().encode(result))
    }

    /**
     * 解密
     *
     *
     * 解密需要先使用base64对输入的加密后的文件进行解码
     */
    fun decode(input:String,key:String): String{
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
        val result = cipher.doFinal(Base64.getDecoder().decode(input))
        return String(result)
    }
}