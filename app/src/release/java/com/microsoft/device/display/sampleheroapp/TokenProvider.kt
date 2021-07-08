/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(
    @ApplicationContext private val appContext: Context,
) : ITokenProvider {

    private var firebaseApp: FirebaseApp? = null

    init {
        firebaseApp = FirebaseApp.initializeApp(
            appContext,
            FirebaseOptions.Builder()
                .setApiKey(BuildConfig.FIREBASE_KEY)
                .setApplicationId(BuildConfig.APP_ID)
                .setProjectId(BuildConfig.PROJECT_ID)
                .build(),
            javaClass.simpleName
        )
    }

    override suspend fun getMapToken(): String {
        var token = ""
        firebaseApp?.let {
            try {
                token = FirebaseFirestore.getInstance(it)
                    .collection(TOKEN_COLLECTION_NAME)
                    .document(MAP_TOKEN_NAME)
                    .get()
                    .await()
                    .getString(MAP_KEY) ?: ""
            } catch (e: FirebaseFirestoreException) {
                ""
            }
        }
        return token
    }

    companion object {
        private const val TOKEN_COLLECTION_NAME = "tokens"
        private const val MAP_TOKEN_NAME = "mapToken"
        private const val MAP_KEY = "map"
    }
}
