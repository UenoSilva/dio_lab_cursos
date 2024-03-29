package com.ueno.todolistapp.ui

import android.app.Activity
import android.os.Bundle
//import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ueno.todolistapp.databinding.ActivityAddTaskBinding
import com.ueno.todolistapp.data.local.TaskRepository
import com.ueno.todolistapp.domain.Task
import com.ueno.todolistapp.extentions.format
import com.ueno.todolistapp.extentions.text
import java.util.Date
import java.util.TimeZone

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var repository: TaskRepository

    private var taskId: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = TaskRepository(this@AddTaskActivity)


        if (intent.hasExtra(TASK_ID)) {
            //Log.e("INTENT", TASK_ID)
            taskId = intent.getStringExtra(TASK_ID).toString()
            //Log.e("INTENT", "$taskId")
            repository.findTaskBYId(taskId).let {
                binding.tilTitle.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }


        setupListeners()

    }


    private fun setupListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val dataPicker = MaterialDatePicker.Builder.datePicker().build()
            dataPicker.addOnPositiveButtonClickListener {
                val timezone = TimeZone.getDefault()
                val offset = timezone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            dataPicker.show(supportFragmentManager, "DATA_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timerPicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
            timerPicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timerPicker.minute in 0..9) "0${timerPicker.minute}" else "${timerPicker.minute}"
                val hour =
                    if (timerPicker.hour in 0..9) "0${timerPicker.hour}" else "${timerPicker.hour}"
                "$hour:$minute".also { binding.tilHour.text = it }
            }
            timerPicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.mbCancelar.setOnClickListener {
            finish()
        }

        binding.mbCriarTarefa.setOnClickListener {
            val task =
                Task(
                    id = taskId.toLong(),
                    title = binding.tilTitle.text,
                    description = binding.tilDescription.text,
                    date = binding.tilDate.text,
                    hour = binding.tilHour.text
                )
            repository.savaIfNotExist(task)
            //TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}

