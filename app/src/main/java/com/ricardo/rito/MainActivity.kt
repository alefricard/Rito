package com.ricardo.rito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.ricardo.rito.adapter.TaskAdapter
import com.ricardo.rito.databinding.ActivityMainBinding
import com.ricardo.rito.model.Constants.EXTRA_NEW_TASK
import com.ricardo.rito.model.Task


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupLayout()


    }

    fun onDataUpdate() = if (adapter.isEmpty()){
        binding.rvTasks.visibility = View.GONE
        binding.tvNoTask.visibility = View.VISIBLE
    }else{
        binding.rvTasks.visibility = View.VISIBLE
        binding.tvNoTask.visibility = View.GONE
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->

        if (result.resultCode != RESULT_OK)
            return@registerForActivityResult

        val task = result.data?.extras?.getSerializable(EXTRA_NEW_TASK) as Task
        adapter.addTask(task)
        onDataUpdate()

    }

    private fun setupLayout() {
        binding.fabAdd.setOnClickListener {

            resultLauncher.launch(Intent(this,NewTaskActivity::class.java))

        }
    }

    private fun setupAdapter() {

        adapter = TaskAdapter(
            onDeleteClick =  {taskToConfirmDeletion ->

            showDeleteConfirmation(taskToConfirmDeletion){taskToBeDeleted ->
                adapter.deleteTask(taskToBeDeleted)
                onDataUpdate()
            }
            },

            onClick = {taskToBeShowed ->
                showTaskDetails(taskToBeShowed){taskToBeUpdate ->
                    adapter.updateTask(taskToBeUpdate)
                }
            }

        )
        binding.rvTasks.adapter = adapter
        onDataUpdate()

    }

    private fun showTaskDetails(task: Task, onTaskStatusChanged: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)

        builder.apply {
            this.setTitle("Detalhes da tarefa")
            setMessage("""
                Titulo: ${task.title}
                Descrição: ${task.description}
                Concluida:${
                    if (task.done){
                        "Sim"
                    }else{
                        "Não"
                    }
                }
            """.trimIndent())

            setPositiveButton(
                if (task.done){
                    "Não concluida"
                }else
                    "Concluida"
            ){_, _ ->
                task.done = !task.done
                onTaskStatusChanged(task)
            }
            setNegativeButton("Não"){dialog,_ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }

    private fun showDeleteConfirmation(task: Task, onConfirm: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)

        builder.apply {
            this.setTitle("Confirmação")
            setMessage("Deseja excluir a tarefa \"${task.title}\"?")
            setPositiveButton("Sim"){_, _ ->
                onConfirm(task)
            }
            setNegativeButton("Não"){dialog,_ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }
}