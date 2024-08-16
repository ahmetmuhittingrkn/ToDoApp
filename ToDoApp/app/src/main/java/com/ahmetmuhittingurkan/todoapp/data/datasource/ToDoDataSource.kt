package com.ahmetmuhittingurkan.todoapp.data.datasource

import android.util.Log
import com.ahmetmuhittingurkan.todoapp.entity.Notlar
import com.ahmetmuhittingurkan.todoapp.room.NotlarDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoDataSource(var ndao:NotlarDao) {

    suspend fun kaydet(notBaslik: String, notIcerik: String, reminderTime: Long?): Long {
        return withContext(Dispatchers.IO) {
            val yeniNot = Notlar(0, notBaslik, notIcerik, reminderTime)
            ndao.kaydet(yeniNot) // Bu değeri döndürüyoruz
        }
    }

    suspend fun guncelle(notId:Int,notBaslik:String,notIcerik:String,reminderTime:Long?){
        withContext(Dispatchers.IO){
            val guncellenenNot=Notlar(notId,notBaslik,notIcerik,reminderTime)
            ndao.guncelle(guncellenenNot)
        }
    }

    suspend fun sil(notId:Int){
        withContext(Dispatchers.IO){
            val silinenKisi=Notlar(notId,"","")
            ndao.sil(silinenKisi)
        }
    }

    suspend fun notlariYukle(): List<Notlar> =
        withContext(Dispatchers.IO){
            return@withContext ndao.notlariYukle()
        }


    suspend fun ara(aramaKelimesi:String): List<Notlar> =
        withContext(Dispatchers.IO){
            return@withContext ndao.ara(aramaKelimesi)
        }

    }
