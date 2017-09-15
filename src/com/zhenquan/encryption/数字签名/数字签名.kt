package com.zhenquan.encryption.数字签名

import com.zhenquan.encryption.非对称加密.RSA
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.util.*

fun main(args: Array<String>) {
   val input =  "name=iphont&price=78888"
    val keyPair = RSA.getKeyPair()
    val sign = Sign.sign(input,keyPair.private!!)
    println("sign:" + sign)
    val verify = Sign.verify(input, keyPair.public!!, sign)
    println("verify:"+verify)
}

/**
 * 数字签名：是对非对称加密和消息摘要的组合应用 SHA256withRSA
 * 应用场景：
 * 1、校验用户身份（使用私钥签名，公钥校验，只要用公钥能校验通过，则该信息一定是私钥持有者发布的）
 * 2、校验数据完整性（用解密后的消息摘要跟原文的消息摘要进行比对）
 *
 *
 * 数字签名流程
 * 1、发件人对原文进行“数字摘要加密”->RSA加密  然后将明文+密文一起发送出去（为了接收方的校验）   这个步骤就是签名的操作
 * 2、收件人对密文进行“RSA解密”->  将解密后的内容得到其HASH值与明文的HASH值进行比对，如果不同则真实性有问题
 *
 *
 *
 *
 *
 */
object Sign {
    fun sign(input: String, key: PrivateKey): String {
        //获取数据签名实例
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(key)
        //设置数据源
        signature.update(input.toByteArray())
        val sign = signature.sign()
        return String(Base64.getEncoder().encode(sign))
    }

    /**
     * 校验
     */
    fun verify(input: String, publicKey: PublicKey, sign: String): Boolean {
        val signature = Signature.getInstance("SHA256withRSA")
        //初始化签名
        signature.initVerify(publicKey)
        //传入数据源
        signature.update(input.toByteArray())
        //校验签名信息

        return signature.verify(Base64.getDecoder().decode(sign))

    }
}