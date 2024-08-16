package com.ahmetmuhittingurkan.todoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahmetmuhittingurkan.todoapp.entity.Notlar

@Dao
interface NotlarDao {
    @Query("SELECT * FROM Notlar")
    fun notlariYukle(): List<Notlar>

    @Insert
    fun kaydet(notlar: Notlar):Long

    @Delete
    fun sil(notlar: Notlar)

    @Update
    fun guncelle(notlar: Notlar)

    @Query("SELECT * FROM Notlar WHERE notBaslik LIKE :aramaKelimesi")
    fun ara(aramaKelimesi: String): List<Notlar>


}