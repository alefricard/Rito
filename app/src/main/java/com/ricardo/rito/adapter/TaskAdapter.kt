package com.ricardo.rito.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ricardo.rito.R
import com.ricardo.rito.databinding.ResItemTaksBinding
import com.ricardo.rito.model.Task


class TaskAdapter(

    private val onDeleteClick: (Task) -> Unit,
    private val onClick: (Task) -> Unit

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf <Task>()

    inner class TaskViewHolder(itemView : ResItemTaksBinding) :
        RecyclerView.ViewHolder(itemView.root){

            private val tvTitleTask : TextView
            private val imgBtnDeleteTask : ImageButton
            private val clTask : ConstraintLayout

            init {
                tvTitleTask = itemView.tvTitleTask
                imgBtnDeleteTask = itemView.imgbDelete
                clTask = itemView.clTask
            }

        fun bind(
            task : Task,
            onDeleteClick: (Task) -> Unit,
            onClick: (Task) -> Unit
        ){
            tvTitleTask.text = task.title

            imgBtnDeleteTask.setOnClickListener {
                onDeleteClick(task)
            }

            clTask.setOnClickListener{
                onClick(task)
            }

            if(task.done){

                tvTitleTask.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )
                imgBtnDeleteTask.setImageResource(R.drawable.delete)

                clTask.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context, R.color.green
                    )
                )

            }else{

                tvTitleTask.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.black)
                )
                imgBtnDeleteTask.setImageResource(R.drawable.deletebk)

                clTask.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context, R.color.gray
                    )
                )

            }
        }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ResItemTaksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(
            tasks[position],
            onDeleteClick,
            onClick
        )
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(task: Task){

        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteTask(task: Task){

        val deletedPosition = tasks.indexOf(task)

        tasks.remove(task)
        notifyItemRemoved(deletedPosition)

    }

    fun updateTask(task: Task) {

        val updatePosition = tasks.indexOf(task)

        tasks[updatePosition] = task
        notifyItemChanged(updatePosition)

    }

    fun isEmpty() = tasks.isEmpty()

}