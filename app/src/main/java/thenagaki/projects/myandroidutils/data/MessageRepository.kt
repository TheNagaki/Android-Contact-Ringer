package thenagaki.projects.myandroidutils.data

import androidx.lifecycle.LiveData

class MessageRepository {
    companion object {
        private var INSTANCE: MessageRepository? = null

        fun getInstance(): MessageRepository {
            if (INSTANCE == null) {
                INSTANCE = MessageRepository()
            }
            return INSTANCE!!
        }
    }

    private val messageDao = MessageDatabase.getInstance()?.messageDao()

    fun addMessage(message: MessageSubscribed) {
        Thread {
            messageDao?.addMessage(message)
        }.start()
    }

    fun updateMessage(message: MessageSubscribed) {
        Thread {
            messageDao?.updateMessage(message)
        }.start()
    }

    fun deleteMessage(message: MessageSubscribed) {
        Thread {
            messageDao?.deleteMessage(message)
        }.start()
    }

    fun getAllMessages(): LiveData<List<MessageSubscribed>>? {
        return messageDao?.getAllMessages()
    }

    fun getMessage(i: Int): LiveData<MessageSubscribed>? {
        return messageDao?.getMessage(i)
    }
}