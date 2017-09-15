package com.zhenquan.encryption.非对称加密

import java.io.*
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

fun main(args: Array<String>) {
    val command = "你好"
    //程序第一次启动的时候生成秘钥对，然后保存起来使用
    val pairGenerator = KeyPairGenerator.getInstance("RSA")
    val keyPair = pairGenerator.genKeyPair()
    val publicKey = keyPair.public
    val privateKey = keyPair.private

//秘钥没必要每次都生成一个新的，所以可以保存密钥对
    val objectOutputStream = ObjectOutputStream(FileOutputStream("privateKey"))
    objectOutputStream.writeObject(privateKey)
    objectOutputStream.close()

    val objectOutputStream2 = ObjectOutputStream(FileOutputStream("publicKey"))
    objectOutputStream2.writeObject(publicKey)
    objectOutputStream2.close()

    //读取密钥对
    val objectInputStream = ObjectInputStream(FileInputStream("privateKey"))
    val readObject = objectInputStream.readObject()
    readObject as PrivateKey
    objectInputStream.close()

    val objectInputStream2 = ObjectInputStream(FileInputStream("publicKey"))
    val readObject2 = objectInputStream2.readObject()
    readObject2 as PublicKey
    objectInputStream2.close()



    //测试
    val encryptByPrivateKey = RSA.encryptByPrivateKey(command, readObject)
    println("私钥加密：" + encryptByPrivateKey)
    val encryptByPublicKey = RSA.encryptByPublicKey(command, readObject2)
    println("公钥加密：" + encryptByPublicKey)

    /**
     * 分段加密  每次加密不能超过117个字节
     */
    val command2 = "你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好"
    val encryptByPrivateKey2 = RSA.encryptByPrivateKey(command2, readObject)
    println("私钥分段加密：" + encryptByPrivateKey2)

    val deEncryptByPrivateKey = RSA.deEncryptByPublicKey(encryptByPrivateKey2, readObject2)
    println("公钥分段解密：" + deEncryptByPrivateKey)


    val encryptByPublicKey1 = RSA.encryptByPublicKey(command2, readObject2)
    println("公钥分段加密：" + encryptByPublicKey1)
    val deEncryptByPrivateKey1 = RSA.deEncryptByPrivateKey(encryptByPublicKey1, readObject)
    println("私钥分段解密：" + deEncryptByPrivateKey1)
}

object RSA {
    private val tranformation = "RSA"
    /**
     * 生成公钥和私钥
     */
    fun getKeyPair(): KeyPair{
        val pairGenerator = KeyPairGenerator.getInstance("RSA")
        val keyPair = pairGenerator.genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private
        println("publicKey:" + String(Base64.getEncoder().encode(publicKey.encoded)))
        println("privateKey:" + String(Base64.getEncoder().encode(privateKey.encoded)))
        return keyPair
    }

    private val ENCRYPT_MAX_SIZE = 117 //分段加密每次最多117个字节
    private val DECRYPT_MAX_SIZE = 128 //分段解密每次最多128个字节
    /**
     * 私钥加密
     * @param 原文
     * @param 私钥
     */
    fun encryptByPrivateKey(input: String, privateKey: PrivateKey): String {
        val cipher = Cipher.getInstance(tranformation)
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        // val doFinal = cipher.doFinal(input.toByteArray())
        // return String(Base64.getEncoder().encode(doFinal))

        //分段加密
        val outputStream = ByteArrayOutputStream()
        var offset = 0
        var temp: ByteArray? = null
        val array = input.toByteArray()
        while (array.size - offset > 0) {
            if (array.size - offset >= ENCRYPT_MAX_SIZE) {
                temp = cipher.doFinal(array, offset, ENCRYPT_MAX_SIZE)
                offset += ENCRYPT_MAX_SIZE
            } else {
                //加密最后一块
                temp = cipher.doFinal(array, offset, array.size - offset)
                offset = array.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(Base64.getEncoder().encode(outputStream.toByteArray()))
    }

    /**
     * 私钥加密，公钥解密
     * @param 密文
     * @param 公钥
     */
    fun deEncryptByPublicKey(input: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance(tranformation)
        cipher.init(Cipher.DECRYPT_MODE, publicKey)

        //分段解密
        val outputStream = ByteArrayOutputStream()
        var offset = 0
        var temp: ByteArray? = null
        val array = Base64.getDecoder().decode(input)
        while (array.size - offset > 0) {
            if (array.size - offset >= DECRYPT_MAX_SIZE) {
                temp = cipher.doFinal(array, offset, DECRYPT_MAX_SIZE)
                offset += DECRYPT_MAX_SIZE
            } else {
                //加密最后一块
                temp = cipher.doFinal(array, offset, array.size - offset)
                offset = array.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(outputStream.toByteArray())
    }

    /**
     * 公钥加密
     * @param 原文
     * @param 公钥
     */
    fun encryptByPublicKey(input: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance(tranformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        /* val doFinal = cipher.doFinal(input.toByteArray())
         return String(Base64.getEncoder().encode(doFinal))*/
        val outputStream = ByteArrayOutputStream()
        var offset = 0
        var temp: ByteArray? = null
        val array = input.toByteArray()
        while (array.size - offset > 0) {
            if (array.size - offset > ENCRYPT_MAX_SIZE) {
                temp = cipher.doFinal(array, offset, ENCRYPT_MAX_SIZE)
                offset += ENCRYPT_MAX_SIZE
            } else {
                temp = cipher.doFinal(array, offset, array.size - offset)
                offset = array.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(Base64.getEncoder().encode(outputStream.toByteArray()))

    }

    /**
     * 公钥加密，私钥解密
     * @param 原文
     * @param 私钥
     */
    fun deEncryptByPrivateKey(input: String, privateKey: PrivateKey): String {
        val cipher = Cipher.getInstance(tranformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val outputStream = ByteArrayOutputStream()
        var offset = 0
        var temp: ByteArray? = null
        val array = Base64.getDecoder().decode(input)
        while (array.size - offset > 0) {
            if (array.size - offset > DECRYPT_MAX_SIZE) {
                temp = cipher.doFinal(array, offset, DECRYPT_MAX_SIZE)
                offset += DECRYPT_MAX_SIZE
            } else {
                temp = cipher.doFinal(array, offset, array.size - offset)
                offset = array.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(outputStream.toByteArray())

    }
}