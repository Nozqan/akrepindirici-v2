package com.nebiozkan.akrepindirici

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point. Annotated for Hilt dependency injection so that
 * the DI graph (database, repositories, security manager) is available
 * application-wide.
 */
@HiltAndroidApp
class AkrepIndiriciApp : Application()
