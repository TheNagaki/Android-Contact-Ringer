package thenagaki.projects.myandroidutils.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMessage(message: MessageSubscribed)

    @Update(entity = MessageSubscribed::class)
    fun updateMessage(message: MessageSubscribed)

    @Delete(entity = MessageSubscribed::class)
    fun deleteMessage(message: MessageSubscribed)

    @Query("SELECT * FROM message_subscribed ORDER BY id ASC")
    fun getAllMessages(): LiveData<List<MessageSubscribed>>?

    @Query("SELECT * FROM message_subscribed WHERE id = :i")
    fun getMessage(i: Int): LiveData<MessageSubscribed>?
}