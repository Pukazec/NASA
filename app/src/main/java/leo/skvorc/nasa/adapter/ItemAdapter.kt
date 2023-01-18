package leo.skvorc.nasa.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {

            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete_item)
                setIcon(R.drawable.delete)
                setMessage(R.string.are_you_sure)
                setCancelable(false)
                setPositiveButton(R.string.dialog_yes) { _, _ -> deleteItem(position)}
                setNegativeButton(R.string.dialog_no, null)
                show()
            }
            true
        }
        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(POSITION, position)
        }

        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(NASA_PROVIDER_CONTENT_URI, item._id!!),
            null, null
        )
        File(item.picturePath).delete()
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

}