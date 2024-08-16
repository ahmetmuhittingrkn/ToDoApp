package com.ahmetmuhittingurkan.todoapp.data.repo

import com.ahmetmuhittingurkan.todoapp.data.datasource.ToDoDataSource
import com.ahmetmuhittingurkan.todoapp.entity.Notlar

class ToDoRepository (var tds:ToDoDataSource){

    suspend fun kaydet(notBaslik:String,notIcerik:String,reminderTime:Long?):Long=tds.kaydet(notBaslik,notIcerik,reminderTime)

    suspend fun guncelle(notId:Int,notBaslik:String,notIcerik: String,reminderTime: Long?)=tds.guncelle(notId,notBaslik,notIcerik,reminderTime)

    suspend fun sil(notId:Int)=tds.sil(notId)

    suspend fun notlariYukle(): List<Notlar> = tds.notlariYukle()

    suspend fun ara(aramaKelimesi:String) : List<Notlar> = tds.ara(aramaKelimesi)

}