package com.ahmetmuhittingurkan.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmetmuhittingurkan.todoapp.data.repo.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(var tdr:ToDoRepository):ViewModel() {

    fun guncelle(notId:Int,notBaslik:String,notIcerik:String,reminderTime:Long?){
        CoroutineScope(Dispatchers.Main).launch {
            tdr.guncelle(notId,notBaslik,notIcerik,reminderTime)
        }
    }
}