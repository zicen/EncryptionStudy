package com.zhenquan.encryption.对称加密

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * DES 加密 解密
 * * 1、创建cipher对象
 * 2、初始化cipher对象
 * 3、加密/解密
 *
 * DES加密
 * DES/CBC/NoPadding(56) ->56:8个字节，8*8 = 64位；DES只有前7位参与加密运算，最后一位作为校验码
 *
 */
fun main(args: Array<String>) {
    //注意DES加密秘钥的长度在8位及8位以上
    val command = "你好啊啊啊啊啊啊啊啊啊"
    val key = "123456789"
    val encrypt = DES.encrypt(command, key)
    println("加密："+encrypt)

    val decode = DES.decode(encrypt, key)
    println("解密："+String(decode))

    val encrypt2 = DES.encrypt2(command, key)
    println("加密2："+encrypt2)

    val decode2 = DES.decode2(encrypt2, key)
    println("解密2："+decode2)
}
object DES{
    /**
     * 加密
     */
    fun encrypt(input:String,key :String):ByteArray{
        //1、创建cipher对象
        val cipher = Cipher.getInstance("DES")
        //2、初始化cipher对象
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val key = secretKeyFactory.generateSecret(desKeySpec)
        //3、第一个参数->加密的模式  第二个参数-> key
        cipher.init(Cipher.ENCRYPT_MODE, key)
        //4、加密
        return cipher.doFinal(input.toByteArray())

    }
    /**
     * 解密
     */
    fun decode(input:ByteArray,key :String):ByteArray{
        //1、创建cipher对象
        val cipher = Cipher.getInstance("DES")
        //2、初始化cipher对象
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val key = secretKeyFactory.generateSecret(desKeySpec)
        //3、加密与解密的区别就只在于第一个参数的区别
        cipher.init(Cipher.DECRYPT_MODE, key)
        //4、解密
        return cipher.doFinal(input)

    }
    /**
     * 加密返回String的方式
     *
     *
     */
    fun encrypt2(input:String,key :String):String{
        //1、创建cipher对象
        val cipher = Cipher.getInstance("DES")
        //2、初始化cipher对象
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val key = secretKeyFactory.generateSecret(desKeySpec)
        //3、第一个参数->加密的模式  第二个参数-> key
        cipher.init(Cipher.ENCRYPT_MODE, key)
        //4、加密
        return String(cipher.doFinal(input.toByteArray()))

    }
    /**
     * 解密（传入string返回string）
     * 加密正常，但是解密的时候出现了下面的Exception
     * Exception in thread "main" javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher
     * 解决办法：Base64编码解码
     */
    fun decode2(input:String,key :String):String{
        //1、创建cipher对象
        val cipher = Cipher.getInstance("DES")
        //2、初始化cipher对象
        val secretKeyFactory = SecretKeyFactory.getInstance("DES")
        val desKeySpec = DESKeySpec(key.toByteArray())
        val key = secretKeyFactory.generateSecret(desKeySpec)
        //3、加密与解密的区别就只在于第一个参数的区别
        cipher.init(Cipher.DECRYPT_MODE, key)
        //4、解密
        return String(cipher.doFinal(input.toByteArray()))

    }

}
