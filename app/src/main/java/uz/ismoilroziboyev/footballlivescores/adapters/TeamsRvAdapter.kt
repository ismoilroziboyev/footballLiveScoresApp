package uz.ismoilroziboyev.footballlivescores.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.ismoilroziboyev.footballlivescores.databinding.ItemTeamsRvBinding
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.Standing
import uz.ismoilroziboyev.footballlivescores.utils.setImageWithUrl

class TeamsRvAdapter : ListAdapter<Standing, TeamsRvAdapter.MyViewHolder>(MyDiffUtill()) {


    class MyDiffUtill : DiffUtil.ItemCallback<Standing>() {

        override fun areItemsTheSame(oldItem: Standing, newItem: Standing): Boolean {
            return oldItem.team.id == newItem.team.id
        }


        override fun areContentsTheSame(oldItem: Standing, newItem: Standing): Boolean {
            return oldItem == newItem
        }

    }


    inner class MyViewHolder(val itemTeamsRvBinding: ItemTeamsRvBinding) :
        RecyclerView.ViewHolder(itemTeamsRvBinding.root) {

        fun onBind(item: Standing) {
            itemTeamsRvBinding.apply {

                try {
                    imageView.setImageWithUrl(item.team.logos[0].href)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                rankTv.text = item.stats[8].displayValue
                playedTv.text = item.stats[3].displayValue
                drawsTv.text = item.stats[2].displayValue
                losessTv.text = item.stats[1].displayValue
                teamNameTv.text = item.team.displayName
                goalDifferenceTv.text = item.stats[9].displayValue
                ptsTv.text = item.stats[6].displayValue

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTeamsRvBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}