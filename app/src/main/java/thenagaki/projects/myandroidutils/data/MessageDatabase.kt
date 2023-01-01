package thenagaki.projects.myandroidutils.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [MessageSubscribed::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        private var INSTANCE: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    "message_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun getInstance(): MessageDatabase? {
            if (INSTANCE == null) {
                throw IllegalStateException("Database must be initialized")
            }
            return INSTANCE
        }

        fun disconnectDatabase() {
            INSTANCE = null
        }
    }
}