package com.sameh.task.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sameh.task.data.Company
import com.sameh.task.databinding.ItemCompanyBinding

class CompaniesAdapter : ListAdapter<Company, CompaniesAdapter.ViewHolder>(CompanyDiffUtil()) {

    private var onItemClickListener: ((Company) -> Unit)? = null

    fun setOnItemClickListener(listener: (Company) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(company: Company) {
            binding.apply {
                imgCompany.setImageResource(company.logo)
                tvCompanyDes.text = company.subTitle

                root.setOnClickListener {
                    onItemClickListener?.invoke(company)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCompanyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

class CompanyDiffUtil : DiffUtil.ItemCallback<Company>() {

    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem == newItem
    }
}