package thenagaki.projects.myandroidutils.activities.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import thenagaki.projects.myandroidutils.R
import thenagaki.projects.myandroidutils.activities.EditListenerActivity
import thenagaki.projects.myandroidutils.databinding.MessageViewBinding
import thenagaki.projects.myandroidutils.data.MessageSubscribed

class MessageListAdapter(
    private val onDeleteCallback: (MessageSubscribed) -> Unit,
    private val onEditCallback: (MessageSubscribed) -> Unit
) :
    RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    private lateinit var binding: MessageViewBinding
    private var messageList = emptyList<MessageSubscribed>()

    class ViewHolder(binding: MessageViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
        val enabledImage: ImageView = binding.imageview
        val duration: TextView = binding.duration
        val bell: TextView = binding.bellChosen
        val message: TextView = binding.message
        val menuBtn: LinearLayout = binding.menuBtn
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.message_view,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = messageList[position]
        holder.name.text = currentItem.name
        holder.duration.text = currentItem.duration.toString()
        holder.bell.text = currentItem.bell.toString()
        holder.message.text = currentItem.message
        holder.enabledImage.setImageResource(if (currentItem.isSubscribed) R.drawable.check_true else R.drawable.check_false)
        menuBtnHandler(holder, currentItem)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    // Set la liste des données et notifie un changement
    @SuppressLint("NotifyDataSetChanged")
    fun setData(messages: List<MessageSubscribed>) {
        this.messageList = messages
        notifyDataSetChanged()
    }

    private fun menuBtnHandler(holder: ViewHolder, message: MessageSubscribed) {
        holder.menuBtn.setOnClickListener {
            val popup = PopupMenu(holder.menuBtn.context, holder.menuBtn)
            popup.menuInflater.inflate(R.menu.collection_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> editAction(message)
                    R.id.action_delete -> deleteAction(message)
                    R.id.action_toggle -> toggleSubscription(message)
                }
                true
            }
            popup.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleSubscription(message: MessageSubscribed) {
        message.isSubscribed = !message.isSubscribed
        onEditCallback(message)
    }

    private fun deleteAction(message: MessageSubscribed) {
        onDeleteCallback(message)
    }

    private fun editAction(message: MessageSubscribed) {
        val editMessageSubscribedIntent =
            Intent(binding.root.context, EditListenerActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("id", message.id)
        editMessageSubscribedIntent.putExtras(bundle)
        startActivity(binding.root.context, editMessageSubscribedIntent, bundle)
    }

}
