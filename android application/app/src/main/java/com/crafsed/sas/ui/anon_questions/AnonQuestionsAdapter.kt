package com.crafsed.sas.ui.anon_questions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crafsed.sas.R
import com.crafsed.sas.data.AnonQuestionData

class AnonQuestionsAdapter : RecyclerView.Adapter<AnonQuestionViewHolder>() {
    var data: List<AnonQuestionData> = ArrayList()
    set(newData){
        field = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnonQuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anon_question, parent, false)
        return AnonQuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AnonQuestionViewHolder, position: Int) {
        data.forEach {
            println(it)
        }

        data[position].question.split("&").apply {
            if (this.size == 1){
                holder.text.text = this[0]
                holder.header.text = "Вопрос от студента"
            } else {
                holder.text.text = this[1]
                holder.header.text = this[0]
            }


        }
    }
}

class AnonQuestionViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    val header: TextView = viewItem.findViewById(R.id.themeTV)
    val text: TextView = viewItem.findViewById(R.id.questionTV)
}