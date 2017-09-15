package com.zhenquan.encryption.对称加密

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

/**
 * 本例子举的是QQ保存本地联系人到数据库的操作，然后需要对联系人数据进行加密的一个需求
 */
fun main(args: Array<String>) {
    val key = "1234567812345678"
    //1、获取联系人数据
    val json = "我是联系人数据"

    //2、将联系人数据缓存到本地，加密
    val br = BufferedWriter(FileWriter("UserInfo.db"))
    val encrypt = AES.encrypt(json, key)
    br.write(encrypt)
    br.close()


    //3、显示，解密
    val bufferedReader = BufferedReader(FileReader("UserInfo.db"))
    val readLine = bufferedReader.readLine()
    val decode = AES.decode(readLine, key)
    println("解密："+decode)

}