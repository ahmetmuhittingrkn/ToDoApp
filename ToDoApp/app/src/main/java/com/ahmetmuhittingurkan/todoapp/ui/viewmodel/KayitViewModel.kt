package com.ahmetmuhittingurkan.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmetmuhittingurkan.todoapp.data.repo.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class KayitViewModel @Inject constructor (var tdr:ToDoRepository) : ViewModel() {

    suspend fun kaydet(notBaslik: String, notIcerik: String, reminderTime: Long?): Long {
        return withContext(Dispatchers.IO) {
            tdr.kaydet(notBaslik, notIcerik, reminderTime)
        }
    }

}