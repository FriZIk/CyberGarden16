package com.crafsed.sas.ui.quizes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.crafsed.sas.R
import com.crafsed.sas.data.QuizesData

class QuizRVAdapter(val data: List<QuizesData>, val onClickCallback: (QuizesData) -> Unit, val isLector: Boolean) :
    RecyclerView.Adapter<QuizRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizRVViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)
        return QuizRVViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: QuizRVViewHolder, position: Int) {
        holder.name.text = data[position].quizName
        holder.layout.setOnClickListener {
            onClickCallback(data[position])
        }
        if (isLector) {
            holder.bage.text = "Открыть"
        }
    }
}

class QuizRVViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    val name: TextView = viewItem.findViewById(R.id.itemTestName)
    val bage: TextView = viewItem.findViewById(R.id.itemTestBage)
    val layout: CardView = viewItem.findViewById(R.id.itemTest)
}