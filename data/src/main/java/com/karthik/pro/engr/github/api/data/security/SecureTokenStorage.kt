package com.karthik.pro.engr.github.api.data.security

import android.util.Base64
import android.util.Base64.decode
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.Aead
import com.karthik.pro.engr.github.api.data.security.config.PreferenceKeys.ENCRYPTED_GITHUB_TOKEN
import com.karthik.pro.engr.github.api.domain.security.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
import javax.inject.Inject


class SecureTokenStorage @Inject constructor(
    private val secureDataStore: DataStore<Preferences>,
    private val aead: Aead
) :
    TokenStorage {

    private val charset: Charset = Charsets.UTF_8
    private val tokenKey = stringPreferencesKey(ENCRYPTED_GITHUB_TOKEN)
    override suspend fun save(token: String) {
        withContext(Dispatchers.IO) {
            val encrypt = aead.encrypt(token.toByteArray(), null)
            val encoded = Base64.encodeToString(encrypt, Base64.NO_WRAP)
            secureDataStore.edit { preferences ->
                preferences[tokenKey] = encoded
            }
        }
    }

    override suspend fun read(): String? = withContext(Dispatchers.IO) {
        val preferences = try {
            secureDataStore.data.first()
        } catch (t: Throwable) {
            return@withContext null
        }
        val encoded =
            preferences[tokenKey] ?: return@withContext null
        return@withContext try {
            val encrypted = decode(encoded, Base64.NO_WRAP)
            val decrypt = aead.decrypt(encrypted, null)
            String(decrypt, charset)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            secureDataStore.edit { prefs ->
                prefs.remove(tokenKey)
            }
        }

    }

    override fun observe(): Flow<String?> {
        return secureDataStore.data.map { prefs ->
            val encoded = prefs[tokenKey]?: return@map null
            try {
                val decoded = decode(encoded, Base64.NO_WRAP)
                val decrypt = aead.decrypt(decoded, null)
                String(decrypt, charset)
            } catch (t: Throwable) {
                null
            }

        }
    }

}