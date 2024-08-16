package com.ahmetmuhittingurkan.todoapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetmuhittingurkan.todoapp.data.repo.ToDoRepository
import com.ahmetmuhittingurkan.todoapp.entity.Notlar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var tdr:ToDoRepository):ViewModel() {

    var notlarListesi=MutableLiveData<List<Notlar>>()

    init{
        notlariYukle()
    }

    fun sil(notId:Int){
        CoroutineScope(Dispatchers.Main).launch {
            tdr.sil(notId)
            notlariYukle()
        }
    }

    fun notlariYukle(){
        CoroutineScope(Dispatchers.Main).launch{
            notlarListesi.value=tdr.notlariYukle()
        }
    }

    fun ara(aramaKelimesi:String){
        CoroutineScope(Dispatchers.Main).launch{
            notlarListesi.value=tdr.ara(aramaKelimesi)
        }
    }


}