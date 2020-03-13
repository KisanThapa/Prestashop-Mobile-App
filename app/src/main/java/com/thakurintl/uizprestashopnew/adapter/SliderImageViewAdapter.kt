package com.thakurintl.uizprestashopnew.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import com.thakurintl.uizprestashopnew.R
import com.thakurintl.uizprestashopnew.objects.UtilityObjects

class SliderImageViewAdapter(
    val context: Context,
    private val imageIds: List<String>,
    private val productId: Int
) :
    SliderViewAdapter<SliderImageViewAdapter.SliderAdapterVH>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(
        viewHolder: SliderAdapterVH,
        position: Int
    ) {

        val imgUrl1 =
            "http://t.uiz.eu/api/images/products/" + productId + "/" + imageIds[position] + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"
        val imgUrl2 =
            "http://t.uiz.eu/api/images/products/" + productId + "/" + imageIds[position] + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"
        val imgUrl3 =
            "http://t.uiz.eu/api/images/products/" + productId + "/" + imageIds[position] + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"
        val imgUrl4 =
            "http://t.uiz.eu/api/images/products/" + productId + "/" + imageIds[position] + "/?ws_key=" + UtilityObjects.API_KEY + "&display=full&output_format=JSON"


        when (position) {
            0 -> Picasso.get().load(imgUrl1)
                .into(viewHolder.imageViewBackground)


            1 -> Picasso.get().load(imgUrl2)
                .into(viewHolder.imageViewBackground)


            2 -> Picasso.get().load(imgUrl3)
                .into(viewHolder.imageViewBackground)


            else -> Picasso.get().load(imgUrl4)
                .into(viewHolder.imageViewBackground)

        }
    }

    override fun getCount(): Int { //slider view count could be dynamic size
        return imageIds.size
    }

    inner class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.slider_image)

    }

}
