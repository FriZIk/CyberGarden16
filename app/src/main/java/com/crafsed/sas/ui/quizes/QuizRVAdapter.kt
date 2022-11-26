package com.crafsed.sas.ui.quizes

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crafsed.sas.R

class QuizRVAdapter : RecyclerView.Adapter<QuizRVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizRVViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: QuizRVViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class QuizRVViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem){
    val name: TextView = viewItem.findViewById(R.id.itemTestName)
    val bage: TextView = viewItem.findViewById(R.id.itemTestBage)
    val layout: TextView = viewItem.findViewById(R.id.itemTest)
}