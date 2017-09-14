package com.zhenquan.encryption.加密基础


/**
 * 凯撒加密，解密，以及破解凯撒加密（频度分析法）
 */
fun main(args: Array<String>) {
    //1、加密
    val encrypt = KAISA.encrypt("I love you", 1)
    println(encrypt)


    //2、解密
    val commant = "J!mpwf!zpv"
    val decode = KAISA.decode(commant, 1)
    println(decode)

    //3、频度分析法破解
    /** 源文件 -》 密文文件
     * 1、首先假设源文件包含出现最多的字符是e
     * 2、读取密文文件，列出出现次数最多的字符，将此字符与e进行比对，则两者之间的差值就是key
     * 3、重复进行2步骤，直到得到结果
     */
}
object KAISA{
    /**
     * 加密
     * input  输入的要加密字符
     * key  秘钥
     */
    fun encrypt(input: String, key: Int): String {
        val array = input.toCharArray()
        return with(StringBuilder()) {
            array.map { it.toInt() + key }
                    .forEach { this.append(it.toChar()) }
            toString()
        }

    }

    /**
     * 解密
     */
    fun decode(input: String, key: Int): String {
        val array = input.toCharArray()
        return with(StringBuilder()) {
            array.map { it.toInt() - key }
                    .forEach { this.append(it.toChar()) }
            toString()
        }
    }
}
