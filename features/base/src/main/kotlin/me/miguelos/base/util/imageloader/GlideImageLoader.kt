package me.miguelos.base.util.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GlideImageLoader
@Inject constructor(val context: Context) : ImageLoader {
    private val requestManager: RequestManager = Glide.with(context)

    override fun <T : ImageView> loadImage(imageView: T, resId: Int) {
        requestManager
            .load(resId)
            .into(imageView)
    }

    override fun <T : ImageView> loadImage(imageView: T, url: String?) {
        requestManager
            .load(url)
            .into(imageView)
    }

    override fun <T : ImageView> loadImage(imageView: T, url: String?, placeholder: Int?) {
        requestManager
            .load(url)
            .apply {
                placeholder?.let { placeholder(it) }
            }
            .into(imageView)
    }

    override fun <T : ImageView> loadImage(imageView: T, drawable: Drawable) {
        requestManager
            .load(drawable)
            .into(imageView)
    }

    override fun <T : ImageView> loadImage(imageView: T, bitmap: Bitmap) {
        requestManager
            .load(bitmap)
            .into(imageView)
    }

    override fun <T : ImageView> loadCircleImage(imageView: T, uri: String) {
        requestManager
            .load(uri)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

}
