package dev.sukhrob.oxford_dictionary.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.oxford_dictionary.databinding.ItemExampleBinding

class ExampleAdapter : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    private val examplesList = ArrayList<String>()
    fun submitList(list: List<String>) {
        examplesList.clear()
        examplesList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ExampleViewHolder(private val binding: ItemExampleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                tvSeqNum.text = (adapterPosition + 1).toString()
                tvExample.text = examplesList[adapterPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        return ExampleViewHolder(
            ItemExampleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = examplesList.size

}