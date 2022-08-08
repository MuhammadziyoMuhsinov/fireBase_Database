package uz.muhammadziyo.firebasedatabase.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import uz.muhammadziyo.firebasedatabase.databinding.ItemRvBinding
import uz.muhammadziyo.firebasedatabase.models.MyUser

class RvAdapter : ListAdapter<MyUser,RvAdapter.Vh>( RvAdapter.MyDiffUtil()) {

    lateinit var itemClick : ((MyUser) -> Unit)

    inner class Vh(val itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
    fun onBind(user:MyUser){
        itemRv.tvAge.text = user.age.toString()
        itemRv.tvName.text = user.name.toString()
        itemRv.root.setOnClickListener {
            itemClick.invoke(user)
        }
    }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<MyUser>() {
        @SuppressLint("RestrictedApi")
        override fun areItemsTheSame(oldItem: MyUser, newItem: MyUser): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("RestrictedApi")
        override fun areContentsTheSame(oldItem: MyUser, newItem: MyUser): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
      holder.onBind(getItem(position))
    }


}