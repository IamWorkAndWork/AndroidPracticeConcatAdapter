package app.practice.practiceconcatadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.practice.practiceconcatadapter.databinding.ActivityMainBinding
import app.practice.practiceconcatadapter.model.UserModel

class MainActivity : AppCompatActivity() {

    private lateinit var horizontalWrapperAdapter: HorizontalWrapperAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemOneColumnList = mutableListOf<UserModel>()
        for (i in 0..10) {
            itemOneColumnList.add(
                UserModel(i.toString(), "item one column $i", "")
            )
        }

        val itemTwoColumnList = mutableListOf<UserModel>()
        for (i in 0..4) {
            itemTwoColumnList.add(
                UserModel(i.toString(), "item two column $i", "")
            )
        }

        val itemHozizontalColumnList = mutableListOf<UserModel>()
        for (i in 0..50) {
            itemHozizontalColumnList.add(
                UserModel(i.toString(), "item horizontal column $i", "")
            )
        }

        val oneColumnAdapter = OneColumnAdapter(onOneColumnClicked).apply {
            submitList(itemOneColumnList)
        }

        val twoColumnAdapter = TwoColumnAdapter(onTwoColumnClicked).apply {
            submitList(itemTwoColumnList)
        }

        val horizontalAdapter = HorizontalAdapter(onHorizontalClicked).apply {
            submitList(itemHozizontalColumnList)
        }

        horizontalWrapperAdapter = HorizontalWrapperAdapter(horizontalAdapter)

        val concatAdapter =
            concatAdapter(horizontalWrapperAdapter, oneColumnAdapter, twoColumnAdapter)

        val gridLayoutManager = GridLayoutManager(this, 12)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (concatAdapter.getItemViewType(position)) {
                    OneColumnAdapter.VIEW_TYPE -> 12
                    TwoColumnAdapter.VIEW_TYPE -> 6
                    HorizontalWrapperAdapter.VIEW_TYPE -> 12
                    else -> 12
                }
            }
        }

        binding.DataReyclerView.apply {
            adapter = concatAdapter
            layoutManager = gridLayoutManager
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        horizontalWrapperAdapter.onSaveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        horizontalWrapperAdapter.onRestoreState(savedInstanceState)
    }

    private fun concatAdapter(
        horizontalWrapperAdapter: HorizontalWrapperAdapter,
        oneColumnAdapter: OneColumnAdapter,
        twoColumnAdapter: TwoColumnAdapter
    ): ConcatAdapter {
        val config = ConcatAdapter.Config.Builder().apply {
            setIsolateViewTypes(false)
        }.build()

        val concatAdapter = ConcatAdapter(
            config, twoColumnAdapter, horizontalWrapperAdapter, oneColumnAdapter
        )
        return concatAdapter
    }

    val onOneColumnClicked: (UserModel) -> Unit = { model ->
        Toast.makeText(
            this,
            "${model.name} from one column adapter was clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    val onTwoColumnClicked: (UserModel) -> Unit = { model ->
        Toast.makeText(
            this,
            "${model.name} from two column adapter was clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    val onHorizontalClicked: (UserModel) -> Unit = { model ->
        Toast.makeText(
            this,
            "${model.name} from horizontal adapter was clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

}