package dev.sukhrob.oxford_dictionary.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.oxford_dictionary.R
import dev.sukhrob.oxford_dictionary.databinding.ItemSenseBinding
import dev.sukhrob.oxford_dictionary.domen.model.Sense

class SenseAdapter : RecyclerView.Adapter<SenseAdapter.SenseViewHolder>() {

    private val dataList = ArrayList<Sense>()
    fun submitList(list: List<Sense>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SenseViewHolder(private val binding: ItemSenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val exampleAdapter: ExampleAdapter by lazy { ExampleAdapter() }

        init {

        }

        fun bind() {
            with(binding) {
                tvDef.text = dataList[adapterPosition].def
//                tvLevel.text = dataList[adapterPosition].level?.substring(
//                    dataList[adapterPosition].level!!.length - 3,
//                    dataList[adapterPosition].level!!.length - 1
//                )
            }
            binding.expandableLayout.visibility =
                if (dataList[adapterPosition].isExpanded) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            if (dataList[adapterPosition].isExpanded) {
                binding.btnArrow.setImageResource(R.drawable.ic_arrow_up)
            } else {
                binding.btnArrow.setImageResource(R.drawable.ic_arrow_down)
            }

            exampleAdapter.submitList(dataList[adapterPosition].examples)
            setupRv()

            binding.linearLayout.setOnClickListener {
                dataList[adapterPosition].isExpanded = !dataList[adapterPosition].isExpanded
                notifyItemChanged(adapterPosition)
            }

        }

        private fun setupRv() {
            with(binding.childRv) {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = this@SenseViewHolder.exampleAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SenseViewHolder {
        return SenseViewHolder(
            ItemSenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SenseViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = dataList.size

}