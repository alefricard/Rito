package com.ricardo.rito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ricardo.rito.databinding.ActivityMainBinding
import com.ricardo.rito.databinding.ActivityNewTaskBinding
import com.ricardo.rito.model.Constants.EXTRA_NEW_TASK
import com.ricardo.rito.model.Task

class NewTaskActivity : AppCompatActivity() {

    //outra  forma de iniciar o binding. sera chamado só de necessário
    private val binding by lazy {ActivityNewTaskBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {

            onSubmit()

        }

    }

    private fun onSubmit() {

        if (binding.editTaskTitle.text.isEmpty()){
            binding.editTaskTitle.error = getString(R.string.erroCampoVazio)
            binding.editTaskTitle.requestFocus()
            return
        }

        if (binding.editDescriptionTask.text.isEmpty()){
            binding.editDescriptionTask.error = getString(R.string.erroCampoVazio)
            binding.editDescriptionTask.requestFocus()
            return
        }

        val newTask = Task(
            binding.editTaskTitle.text.toString(),
            binding.editDescriptionTask.text.toString()
        )

        val intentResult = Intent()
        intentResult.putExtra(EXTRA_NEW_TASK, newTask)
        setResult(RESULT_OK, intentResult)
        finish()

    }



}