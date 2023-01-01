package thenagaki.projects.myandroidutils.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_subscribed")
data class MessageSubscribed(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "message")
    var message: String,
    @ColumnInfo(name = "duration")
    var duration: Int,
    @ColumnInfo(name = "bell")
    var bell: Long,
    @ColumnInfo(name = "is_subscribed")
    var isSubscribed: Boolean
) {
}
