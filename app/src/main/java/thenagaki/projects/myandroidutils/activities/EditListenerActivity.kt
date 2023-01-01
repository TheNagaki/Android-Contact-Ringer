package thenagaki.projects.myandroidutils.activities

import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import thenagaki.projects.myandroidutils.data.MessageSubscribed
import thenagaki.projects.myandroidutils.data.MessageViewModel
import thenagaki.projects.myandroidutils.databinding.ActivityAddListenerBinding

class EditListenerActivity : AppCompatActivity() {
    private var baseM: MessageSubscribed? = null
    private lateinit var viewModel: MessageViewModel
    private val ringtonesMap = mutableMapOf<Long, String>()
    private lateinit var binding: ActivityAddListenerBinding
    private var durationNum = 0
    private lateinit var bellSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddListenerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNumPicker()
        initViewModel()
        initSpinner()

        val b = intent.extras
        if (b != null) {
            viewModel.getMessage(b.get("id") as Int)?.observe(this@EditListenerActivity) { m ->
                baseM = m
                binding.txtNumbers.text = m.duration.toString()
                val index = ringtonesMap.keys.indexOf(m.bell)
                binding.bellSpinner.setSelection(if (index <= -1) 0 else index)
                binding.listenerName.setText(m.name)
                binding.listenerMessage.setText(m.message)
            }
        }
        initCancelBtn()
        initSaveBtn()
    }

    private fun initSaveBtn() {
        binding.addListenerBtn.setOnClickListener {
            val m = baseM
            val name = binding.listenerName.text.toString()
            val message = binding.listenerMessage.text.toString()
            val duration = durationNum
            val bell = ringtonesMap.keys.elementAt(ringtonesMap.values.indexOf(bellSelected))
            if (m != null) {
                m.name = if (checkString(name) && name != m.name) name else m.name
                m.message = if (checkString(message) && message != m.message) message else m.message
                m.duration = if (duration != m.duration) duration else m.duration
                m.bell = if (bell != m.bell) bell else m.bell
                viewModel.update(m)
                Toast.makeText(this, "Modification effectuée", Toast.LENGTH_SHORT).show()
            } else {
                if (checkString(name) && checkString(message)) {

                    viewModel.add(
                        binding.listenerName.text.toString(),
                        binding.listenerMessage.text.toString(),
                        durationNum,
                        bell
                    )
                    Toast.makeText(this, "Ajout effectué", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show()
                }
            }
//            finish()
        }
    }

    private fun checkString(s: String): Boolean {
        return s.isNotEmpty()
    }

    private fun initCancelBtn() {
        binding.cancelListenerBtn.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MessageViewModel::class.java]
    }

    private fun initNumPicker() {
        setNumText()
        binding.imgMinus.setOnClickListener {
            if (durationNum > 0) {
                durationNum--
                setNumText()
            }
        }
        binding.imgPlus.setOnClickListener {
            durationNum++
            setNumText()
        }
    }

    private fun setNumText() {
        binding.txtNumbers.text = durationNum.toString()
    }

    private fun initSpinner() {
        initRingtones()
        binding.bellSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ringtonesMap.values.toTypedArray()
        )
        binding.bellSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bellSelected = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (p0 != null && p0.count > 0) {
                    bellSelected = p0.getItemAtPosition(0).toString()
                }
            }
        }
    }

    private fun initRingtones() {
        val ringtoneManager = RingtoneManager(this)
        ringtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION)
        val cursor = ringtoneManager.cursor
        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val id = cursor.getLong(RingtoneManager.ID_COLUMN_INDEX)
            ringtonesMap[id] = title
        }
    }
}