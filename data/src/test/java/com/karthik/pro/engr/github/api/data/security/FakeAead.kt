package com.karthik.pro.engr.github.api.data.security

import com.google.crypto.tink.Aead

class FakeAead : Aead {
    private val aad = "FAKE:".toByteArray()

    override fun encrypt(
        plaintext: ByteArray,
        associatedData: ByteArray?
    ): ByteArray {
        return aad + plaintext

    }

    override fun decrypt(
        ciphertext: ByteArray,
        associatedData: ByteArray?
    ): ByteArray {
        require(ciphertext.take(aad.size).toByteArray().contentEquals(aad)) { "Invalid ciphertext" }
        return ciphertext.drop(aad.size).toByteArray()
    }
}