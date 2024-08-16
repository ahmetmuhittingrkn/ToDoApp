package com.ahmetmuhittingurkan.todoapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmetmuhittingurkan.todoapp.R
import com.ahmetmuhittingurkan.todoapp.databinding.CardTasarimBinding
import com.ahmetmuhittingurkan.todoapp.databinding.FragmentAnasayfaBinding
import com.ahmetmuhittingurkan.todoapp.entity.Notlar
import com.ahmetmuhittingurkan.todoapp.ui.fragment.AnasayfaFragmentDirections
import com.ahmetmuhittingurkan.todoapp.ui.viewmodel.AnasayfaViewModel
import com.google.android.material.snackbar.Snackbar

class NotlarAdapter(var mContext: Context,var notlarListesi:List<Notlar>,var viewModel:AnasayfaViewModel): RecyclerView.Adapter<NotlarAdapter.CardTasarimHolder>() {

    inner class CardTasarimHolder(var tasarim:CardTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimHolder {
        val binding : CardTasarimBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.card_tasarim,parent, false)
        return CardTasarimHolder(binding)
    }

    override fun onBindViewHolder(holder: CardTasarimHolder, position: Int) {
        val not=notlarListesi.get(position)
        val t= holder.tasarim

        t.notNesnesi=not

        t.cardViewSatir.setOnClickListener(){
            val gecis=AnasayfaFragmentDirections.detayGecis(Not=not)
            Navigation.findNavController(it).navigate(gecis)
        }

        t.imageViewSil.setOnClickListener(){
            Snackbar.make(it,"${not.notBaslik} silinsin mi ? ",Snackbar.LENGTH_SHORT)
                .setAction("EVET"){
                    viewModel.sil(not.notId)
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
       return notlarListesi.size
    }

}