package leo.skvorc.nasa.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import leo.skvorc.nasa.ItemPagerActivity
import leo.skvorc.nasa.NASA_PROVIDER_CONTENT_URI
import leo.skvorc.nasa.POSITION
import leo.skvorc.nasa.R
import leo.skvorc.nasa.framework.startActivity
import leo.skvorc.nasa.model.Item
import java.io.File

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        public val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvExplanation = itemView.findViewById<TextView>(R.id.tvExplanation)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvTitle.text = item.title
            tvExplanation.text = item.explanation
            tvDate.text = item.date
            ivRead.setImageResource(if (item.read) R.drawable.green_flag else R.drawable.red_flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivRead.setOnClickListener {
            updateItem(position)
        }
        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read
        context.contentResolver.update(
            ContentUris.withAppendedId(NASA_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null, null
        )
        notifyItemChanged(position)
    }

    override fun getItemCount() = items.size

}