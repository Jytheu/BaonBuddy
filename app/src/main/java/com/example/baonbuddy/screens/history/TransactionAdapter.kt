package com.example.baonbuddy.screens.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.baonbuddy.R
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLabel: TextView = view.findViewById(R.id.tvTransactionLabel)
        val tvDate: TextView = view.findViewById(R.id.tvTransactionDate)
        val tvAmount: TextView = view.findViewById(R.id.tvTransactionAmount)
        val vIndicator: View = view.findViewById(R.id.vCategoryIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        
        holder.tvLabel.text = if (transaction.type == "BAON") "Allowance" else transaction.category
        holder.tvDate.text = dateFormat.format(Date(transaction.timestamp))
        
        if (transaction.type == "EXPENSE") {
            holder.tvAmount.text = "-₱${transaction.amount}"
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#E53935"))
            holder.vIndicator.setBackgroundColor(android.graphics.Color.parseColor("#FFEBEE"))
        } else {
            holder.tvAmount.text = "+₱${transaction.amount}"
            holder.tvAmount.setTextColor(android.graphics.Color.parseColor("#00B58B"))
            holder.vIndicator.setBackgroundColor(android.graphics.Color.parseColor("#E8F5E9"))
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle(R.string.delete_confirm)
                .setPositiveButton(R.string.delete) { _, _ ->
                    onDelete(position)
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
            true
        }
    }

    override fun getItemCount() = transactions.size
}