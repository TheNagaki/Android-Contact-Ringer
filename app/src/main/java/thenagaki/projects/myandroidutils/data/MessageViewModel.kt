package thenagaki.projects.myandroidutils.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    var readAllData: LiveData<List<MessageSubscribed>>?
    private val messageRepository = MessageRepository.getInstance()

    init {
        readAllData = messageRepository.getAllMessages()
    }

    fun add(name: String, message: String, duration: Int, bell: Long) {
        messageRepository.addMessage(MessageSubscribed(0, name, message, duration, bell, true))
    }

    fun update(message: MessageSubscribed) {
        messageRepository.updateMessage(message)
    }

    fun delete(message: MessageSubscribed) {
        messageRepository.deleteMessage(message)
    }

    fun getMessage(i: Int): LiveData<MessageSubscribed>? {
        return messageRepository.getMessage(i)
    }
}