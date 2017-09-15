package com.zhenquan.encryption.消息摘要

/**
 * 消息摘要的常用算法就是   MD5
 * 应用场景：用户登录，用户密码以密文传输
 * 增加破解难度：
 * 1、多次加密
 * 2、加“盐”：拼接字符串
 * 3、结合数字签名
 *
 */
fun main(args: Array<String>) {
    //1、MD5加密密码
    val password = MessageDigetUtil.md5("123456")
    //2、请求登录接口,传入加密后的密码，然后服务器做校验
    //TODO  httputil.request(""+password.........)
}