package com.karthik.pro.engr.github.api.data.security

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreKeyValueStore @Inject constructor(private val secureDataStore: DataStore<Preferences>) :
    KeyValueStore {
    override suspend fun getString(key: String): String? {
        return try {
            val first = secureDataStore.data.first()
            val prefKey = stringPreferencesKey(key)
            first[prefKey]
        } catch (t: Throwable) {
            return null
        }
    }

    override suspend fun putString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        secureDataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    override suspend fun remove(key: String) {
        val prefKey = stringPreferencesKey(key)
        secureDataStore.edit { preferences ->
            preferences.remove(prefKey)
        }
    }


    override fun observeString(key: String): Flow<String?> =
        secureDataStore.data.map { preferences ->
            val prefKey = stringPreferencesKey(key)
            preferences[prefKey]
        }
}