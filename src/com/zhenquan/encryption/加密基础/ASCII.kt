package com.zhenquan.encryption.加密基础

/**
 * 介绍ASCII码以及with函数的用法
 * ASCII（American Standard Code for Information Interchange，美国信息交换标准代码）是基于拉丁字母的一套电脑编码系统，
 * 主要用于显示现代英语和其他西欧语言。它是现今最通用的单字节编码系统，并等同于国际标准ISO/IEC 646。
 */
fun main(args: Array<String>) {

    val s = "I love you"
    val array = s.toCharArray()
    /**
     * with函数传入一个参数StringBuilder（）然后就可以对这个对象进行操作，在函数最后还可以返回数据，当然也可以不返回数据。
     */
    val string = with(StringBuilder()) {
        array
                .map { it.toInt() }   //Char字符可以直接通过toInt（）方法转成ASCII码值，反之也可以使用toChar将Int转成Char字符
                .forEach { this.append(it.toString() + " ") }
        //返回结果
        this.toString()
    }

    println(string)

}