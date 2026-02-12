package com.karthik.pro.engr.github.api.data.di.security

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.internal.RegistryConfiguration
import com.karthik.pro.engr.github.api.data.security.config.TinkConfig.KEYSET_PREF_FILE
import com.karthik.pro.engr.github.api.data.security.config.TinkConfig.KEYSET_PREF_NAME
import com.karthik.pro.engr.github.api.data.security.config.TinkConfig.MASTER_KEY_URI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    @Provides
    @Singleton
    fun provideKeySetHandle(@ApplicationContext context: Context): KeysetHandle {
        AeadConfig.register()
        val manager = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_PREF_NAME, KEYSET_PREF_FILE)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
        return manager.keysetHandle
    }

    @Provides
    @Singleton
    fun provideAead(handle: KeysetHandle): Aead =
        handle.getPrimitive(RegistryConfiguration.get(), Aead::class.java)
}