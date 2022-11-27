package com.crafsed.sas.ui.lectures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.crafsed.sas.R
import com.crafsed.sas.data.ListData
import com.crafsed.sas.data.PairDescription
import com.google.android.material.textview.MaterialTextView

class ListAdapter(val clickCallback: () -> Unit ) : RecyclerView.Adapter<ListViewHolder>() {
    var data: List<PairDescription> = ArrayList()
        set(newData) {
            field = newData
            this.notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val datum = data[position]
        holder.lector.text = datum.lector
        holder.room.text = datum.place
        holder.time.text = "${datum.timeStart} - ${datum.timeEnd}"
        holder.obj.text = datum.obj
        holder.status.text = ":)"
//        holder.layout.setBackgroundResource(if (!datum.isOnline) R.drawable.list_gradient_1 else R.drawable.list_gradient_2)
        holder.time.setTextColor( holder.time.resources.getColor(if (!datum.place.contains("LMS", true)) R.color.main_green_a else R.color.secondary_blue_a))
        holder.room.setBackgroundColor( holder.time.resources.getColor(if (!datum.place.contains("LMS", true)) R.color.main_green_a else R.color.secondary_blue_a))
        holder.obj.isSelected = true;

        holder.layout.setOnClickListener {
            clickCallback()
        }
    }
}

@Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING")
class ListViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
    val room: MaterialTextView = viewItem.findViewById(R.id.roomTV)
    val lector: MaterialTextView = viewItem.findViewById(R.id.lectorTV)
    val time: MaterialTextView = viewItem.findViewById(R.id.timeTV)
    val obj: MaterialTextView = viewItem.findViewById(R.id.objectTV)
    val status: MaterialTextView = viewItem.findViewById(R.id.statusTV)
    val layout: ConstraintLayout = viewItem.findViewById(R.id.cardLayout)
}