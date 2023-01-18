package leo.skvorc.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import leo.skvorc.nasa.adapter.ItemPagerAdapter
import leo.skvorc.nasa.databinding.ActivityItemPagerBinding
import leo.skvorc.nasa.framework.fetchItems
import leo.skvorc.nasa.model.Item

const val POSITION = "leo.skvorc.nasa.position"
class ItemPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemPagerBinding
    private lateinit var items: MutableList<Item>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        items = fetchItems()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = position
    }
}