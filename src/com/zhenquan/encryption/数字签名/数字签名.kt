package com.zhenquan.encryption.数字签名

import java.security.PrivateKey
import java.security.Signature
import java.util.*

fun main(args: Array<String>) {
}

object Sign{
    fun sign(input:String,key:PrivateKey):String {
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(key)
        val sign = signature.sign()
        return String(Base64.getEncoder().encode(sign))
    }
}