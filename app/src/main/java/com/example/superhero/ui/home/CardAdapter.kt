package com.example.superhero.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superhero.R
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.databinding.LoadingItemBinding
import com.example.superhero.databinding.SuperheroCardItemBinding


/** ადაპტერი აკავშირებს ვიუს და ვიუჰოლდერს დატასთან*/
//onItemClick ლამბდა ფუნქცია, აბრუნებს კლიკებს
class CardAdapter(
    private val onItemClick: (superheroCard: SuperheroCard) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cardList: List<SuperheroCard> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var loadingMore = false
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    private val onClickListener = View.OnClickListener { v ->
        val card = v?.tag as SuperheroCard
        onItemClick.invoke(card)
    }

    /**განსაზღვრავს რომელ პოზიციაზე რომელი ვიუჰოლდერი გამოჩნდება*/
    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) VIEW_TYPE_LOADER else VIEW_TYPE_CARD
    }

    //ამ მეთოდში ქმნება ვიუ
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            VIEW_TYPE_LOADER -> LoadingViewHolder(
                LoadingItemBinding.inflate(LayoutInflater.from(parent.context))
            )
            VIEW_TYPE_CARD -> CardViewHolder(
                binding = SuperheroCardItemBinding.inflate(LayoutInflater.from(parent.context)),
                onClickListener = onClickListener
            )
            else -> throw RuntimeException("unknown ViewType")
        }

    //გადმოეწოდება ვიუ იმპლემენტაციისთვის
    //holder უჭირავს ვიუები
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val item = cardList[position]
                holder.binding.superheroNameFromCard.text = item.name
                Glide.with(holder.itemView)
                    .load(item.image?.url)
                    .into(holder.binding.superheroImageFromCard)
                holder.binding.root.tag = item //tag <-- ობიექტი, რომელიც ნებისმიერ ობიექტში შეიძლება იყოს და შეგვიძლია დავუსეტოთ ნებიმიერ ვიუს
            }
            is LoadingViewHolder -> {
                holder.binding.loader.visibility = if (loadingMore) View.VISIBLE else View.GONE
            }
        }
    }

    /**აწვდის ადაპტერს ინფორმაციას ელემენტების რაოდენობის შესახებ*/
    override fun getItemCount() = cardList.size + 1

    /**გვაძლევს ეკრანზე გამოტანილ ობიექტებზე კლიკის შესაძლებლობას*/
    class CardViewHolder(
        val binding: SuperheroCardItemBinding,
        onClickListener: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(onClickListener)
        }
    }

    /**გვატყობინებს იუზერის მიერ ბოლომდე ჩასქროლვას*/
    class LoadingViewHolder(
        val binding: LoadingItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class LoaderSpanSizeLookup(val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
        GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.itemCount - 1 == position) 2 else 1
        }
    }


    companion object {
        const val VIEW_TYPE_CARD = 1
        const val VIEW_TYPE_LOADER = 2
    }

}


