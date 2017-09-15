package com.zhenquan.encryption.消息摘要

import java.security.MessageDigest

fun main(args: Array<String>) {
    val command = "aaaaaaadawdaduawukd"
    println( MessageDigetUtil.md5(command))
    println(MessageDigetUtil.sha1(command))
    println(MessageDigetUtil.sha256(command))

}

/**
 * MD5加密是不可逆的  因为：
 * MD5加密之后的长度为16位
 * 加密之后再转为16进制则为32位
 */
object MessageDigetUtil {
    fun md5(input: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val result = digest.digest(input.toByteArray())

        //转成16进制
        /*var sb = StringBuffer()
        result.forEach {
            val hex = it.toInt() and (0xff)
            val hexStr = hex.toString()
            if (hexStr.length == 1) {
                sb.append("0").append(hexStr)
            } else {
                sb.append(hexStr)
            }
        }
        return sb.toString()*/


        return tohex(result)

    }

    /**
     * sha1加密后20位
     * 加密转为16进制后变成40位
     */
    fun sha1(input:String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(input.toByteArray())
        return tohex(result)
    }

    /**
     * sha1加密后32位
     * 加密转为16进制后变成64位
     */
    fun sha256(input:String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val result = digest.digest(input.toByteArray())
        return tohex(result)
    }
    /**
     * 转为16进制
     */
    private fun tohex(result: ByteArray): String {
        return with(StringBuilder()) {
            result.forEach {
                val hex = it.toInt() and (0xff)
                val hexStr = hex.toString()
                if (hexStr.length == 1) {
                    append("0").append(hexStr)
                } else {
                    append(hexStr)
                }
            }
            toString()
        }
    }
}