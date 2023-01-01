package thenagaki.projects.myandroidutils

import android.app.Application
import thenagaki.projects.myandroidutils.data.MessageDatabase

class Launcher : Application() {
    override fun onCreate() {
        super.onCreate()
        MessageDatabase.getDatabase(baseContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        MessageDatabase.disconnectDatabase()
    }
}