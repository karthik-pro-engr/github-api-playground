package com.karthik.pro.engr.github.api.data.security.config

internal object TinkConfig {
    internal const val KEYSET_PREF_FILE = "crypto_keyset_store"
    internal const val KEYSET_PREF_NAME = "github_auth_keyset"
    internal const val MASTER_KEY_URI = "android-keystore://github_master_key"
}