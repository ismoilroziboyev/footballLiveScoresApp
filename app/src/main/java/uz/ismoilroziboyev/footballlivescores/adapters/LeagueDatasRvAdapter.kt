package uz.ismoilroziboyev.footballlivescores.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.database.entities.LeaguesEntity
import uz.ismoilroziboyev.footballlivescores.databinding.ItemRecyclerViewBinding
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.ResStandingsData
import uz.ismoilroziboyev.footballlivescores.models.responce.standins.Standing
import uz.ismoilroziboyev.footballlivescores.retrofit.ApiClient
import uz.ismoilroziboyev.footballlivescores.utils.hide
import uz.ismoilroziboyev.footballlivescores.utils.setImageWithUrl
import uz.ismoilroziboyev.footballlivescores.utils.show

class LeagueDatasRvAdapter(
    val onItemClickListener: OnItemClickListener,
    val appDatabase: AppDatabase,
) :
    ListAdapter<LeaguesEntity, LeagueDatasRvAdapter.MyViewHolder>(MyDiffUtil()) {


    private val TAG = "LeagueDatasRvAdapter"

    interface OnItemClickListener {
        fun onItemClickListener(item: LeaguesEntity)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<LeaguesEntity>() {

        override fun areItemsTheSame(oldItem: LeaguesEntity, newItem: LeaguesEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LeaguesEntity,
            newItem: LeaguesEntity,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class MyViewHolder(val itemRecyclerViewBinding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(itemRecyclerViewBinding.root) {

        fun onBind(item: LeaguesEntity) {
            itemRecyclerViewBinding.apply {
                image.setImageWithUrl(item.image)

                ligaName.text = item.name
                countryName.text = item.abbr

                nextButton.setOnClickListener { onItemClickListener.onItemClickListener(item) }

                resultLayout.hide()
                loadingAnim.show()

                ApiClient.apiService.getStandingsNoCoroutine(item.id)
                    .enqueue(object : Callback<ResStandingsData> {
                        override fun onResponse(
                            call: Call<ResStandingsData>,
                            response: Response<ResStandingsData>,
                        ) {
                            if (response.isSuccessful) {

                                appDatabase.standingsDao().insert(response.body()!!)
                                val datas = response.body()?.data?.standings!!

                                val team1 = datas[0]
                                team1Name.text = team1.team.displayName
                                team1DCount.text = team1.stats[2].displayValue
                                team1LCount.text = team1.stats[1].displayValue
                                team1PtsCount.text = team1.stats[6].displayValue
                                team1GaCount.text = team1.stats[5].displayValue
                                team1GdCount.text = team1.stats[4].displayValue

                                val team2 = datas[1]
                                team2Name.text = team2.team.displayName
                                team2DCount.text = team2.stats[2].displayValue
                                team2LCount.text = team2.stats[1].displayValue
                                team2PtsCount.text = team2.stats[6].displayValue
                                team2GaCount.text = team2.stats[5].displayValue
                                team2GdCount.text = team2.stats[4].displayValue

                                val team3 = datas[2]
                                team3Name.text = team3.team.displayName
                                team3DCount.text = team3.stats[2].displayValue
                                team3LCount.text = team3.stats[1].displayValue
                                team3PtsCount.text = team3.stats[6].displayValue
                                team3GaCount.text = team3.stats[5].displayValue
                                team3GdCount.text = team3.stats[4].displayValue


                                val team4 = datas[3]
                                team4Name.text = team4.team.displayName
                                team4DCount.text = team4.stats[2].displayValue
                                team4LCount.text = team4.stats[1].displayValue
                                team4PtsCount.text = team4.stats[6].displayValue
                                team4GaCount.text = team4.stats[5].displayValue
                                team4GdCount.text = team4.stats[4].displayValue


                                try {
                                    team1Image.setImageWithUrl(team1.team.logos[0].href ?: "")
                                    team2Image.setImageWithUrl(team2.team.logos[0].href ?: "")
                                    team3Image.setImageWithUrl(team3.team.logos[0].href ?: "")
                                    team4Image.setImageWithUrl(team4.team.logos[0].href ?: "")

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                                resultLayout.show()
                                loadingAnim.hide()
                            }
                        }

                        override fun onFailure(call: Call<ResStandingsData>, t: Throwable) {
                            resultLayout.hide()
                            loadingAnim.hide()
                        }

                    })

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}