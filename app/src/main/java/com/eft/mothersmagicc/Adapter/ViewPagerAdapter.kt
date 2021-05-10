package com.eft.mothersmagicc.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.eft.mothersmagicc.R


class ViewPagerAdapter(var context: Context) : PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater


    var slide_images = intArrayOf(
            R.drawable.onemumma,
            R.drawable.secondmumma,
            R.drawable.deliveryboy
    )

    var textOne_images = intArrayOf(
            R.drawable.foodforyou,
            R.drawable.maakehaathkabana,
            R.drawable.getfastestdelivery
    )

    var textTwo_images = intArrayOf(
            R.drawable.gethomecooked,
            R.drawable.healthyandfullytasty,
            R.drawable.orderfoodandgetdelivery
    )


    override fun getCount(): Int {
        return slide_images.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide_pager, container, false)

        val slide_imageView = view.findViewById<ImageView>(R.id.imageView_mumma)
        val slideTextView = view.findViewById<ImageView>(R.id.imageView_firstslogan_login)
        val slideTextTwoView = view.findViewById<ImageView>(R.id.imageView_secondslogan_login)
        slide_imageView.setImageResource(slide_images[position])
        slideTextView.setImageResource(textOne_images[position])
        slideTextTwoView.setImageResource(textTwo_images[position])
        Log.d("newValue",position.toString())
        container.addView(view)

        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }


}
