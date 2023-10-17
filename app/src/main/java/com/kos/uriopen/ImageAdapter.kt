package com.kos.uriopen

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    val imageList = listOf<Pair<Boolean, String>>(
        true to "https://github.com/square/picasso/raw/master/website/static/sample.png",
        false to "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-vp9-360.webm",
        true to "https://avatars.mds.yandex.net/i?id=2d485750bbd493edb1d158099177c179a97ce001-8185245-images-thumbs&n=13",
        true to "https://avatars.mds.yandex.net/i?id=3e2880fb45dbdcb09c4d01049a907c6d-4800903-images-thumbs&n=13",
    //    false to "https://dl.espressif.com/dl/audio/ff-16b-2c-44100hz.m4a",
    //    false to "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-vp9-360.webm",
   //     false to "http://www.storiesinflight.com/js_videosub/jellies.mp4",
        true to "https://yandex-images.clstorage.net/1Pk003a19/f820c5c7/vmkrYvS4-OlpSoWQWfAmFnXUVnlrAXOuZJUNgjmxWb4HNHi7Va9PYZM1zupi7I-y2b0HFwtMcAu0oulNMFgW2mv9WzbAfIzozJ_hgaywpiYl9YOtWhC2DiFEqaf5RTwrt8T96JYIyBalUkitsP59ZnwtxxJ_XbS_csR9CFW-Mk4t6PnTJIl4nam6RCf7clTWtBSR6olyVfwTlD2FCpVJ62IjSFs2qpkmBLHavaJ-PoZ-X4VzvovH_0HXKSl2TXP9jFp5IPXq254YuPZn-NLk13d1osqYAAYv0kVOxrtFKzuDsMlbJXgIVbRw6bywPM11fU1noH6qde8DF0uKhb5RPd_5C4NnuOtPm0rVBprSlGYVtfJZyRF3j6NXrOaZdXiq8HMKu3a6m6XEEFgf4q7P5f9sZUOtWhQ_sZS5m1SMAT9siikjJvi5jNj7pFTpAPX3lNWyaAtxFz3jNo3XOPdJu6IwumlXWstHxuIIr2EurQTejzXjXpnnPWCW2mtnf7OOTyhpw5TqyO8o6DTWSzLGRNamUsub4yR_ceT9hTm3GpuiIfroVqr7RlWC6W8D3v7kbv4GQE35h41xFViqxG_w_D652wKVm1p-urm2legBR2ektYBam8O13EOEHwaYpikr44KruGb76DQ3ofoMQn6-BS0cNaGuKHfs0Be5-LdNUI8_eOuBVHubDorb1wZ7c5WkFISj-KvAhf_zdsyFq8a6maFxGnklKBjkJhGa_II-_IWsr4cAvEoVDtBn6WnGngFOf_sbg2WJSbwbyWfWGAAFZSb0UZlqA1eOc0UMlIsEmTohQMooBFoLdpaQCe5Cv6z2TuwHw32bBdyBd-t45lxyz55o6QKmGlq-mDnXNmhCl6V15uDZWeBWfBHXD5bZ9Bs4YwCJSncKKkRkI7rNAsz_BV4fpyIOSTbtMRXoaXc9gF1emrmBJpioXVqIVkQIIWa3B5WyW9pzRj5SdI2XKeVrE",
        true to "https://community.atlassian.com/html/@970A6E3EA6BE96059615B0B70CDA6961/assets/img/hot-issue-balloon.png",
        true to "https://avatars.mds.yandex.net/i?id=c6ba2e36b11268917fa2133e8898056ad940f261-9181181-images-thumbs&n=13",
        true to "https://github.com/square/picasso/raw/master/website/static/sample.png",
        true to "https://avatars.mds.yandex.net/i?id=2d485750bbd493edb1d158099177c179a97ce001-8185245-images-thumbs&n=13",
        true to "https://avatars.mds.yandex.net/i?id=8343335d8fb1d23cf24dabc6c5358878ff9330e6-6377202-images-thumbs&n=13",
   //     false to "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.item_image, parent, false)
            return ImageViewHolder(view)

    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int {
        return imageList.count()
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val (b, v) = imageList[position]

        when (getItemViewType(position)) {
            1 -> {
                Picasso.get()
                    .load(v)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(holder.image)
            }
            else -> {
                holder.video?.suspend()
                holder.video?.setVideoURI(Uri.parse(v))
                holder.video?.start()
            }
        }

    }

    class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
        val image: ImageView? = itemView.findViewById(R.id.img)
        val video: VideoView? = itemView.findViewById(R.id.video)
    }
}