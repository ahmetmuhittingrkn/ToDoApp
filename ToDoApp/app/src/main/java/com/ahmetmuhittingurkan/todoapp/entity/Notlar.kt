package com.ahmetmuhittingurkan.todoapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity("Notlar")
data class Notlar(
                  @PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "notId") @NotNull var notId:Int,
                  @ColumnInfo(name="notBaslik") @NotNull var notBaslik:String,
                  @ColumnInfo(name="notIcerik") @NotNull var notIcerik:String,
                  @ColumnInfo(name="reminderTime") var reminderTime: Long? = null
): Serializable{
}