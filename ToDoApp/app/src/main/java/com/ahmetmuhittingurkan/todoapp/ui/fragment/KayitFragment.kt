package com.ahmetmuhittingurkan.todoapp.ui.fragment

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ahmetmuhittingurkan.todoapp.R
import com.ahmetmuhittingurkan.todoapp.databinding.FragmentKayitBinding
import com.ahmetmuhittingurkan.todoapp.entity.ReminderReceiver
import com.ahmetmuhittingurkan.todoapp.ui.viewmodel.DetayViewModel
import com.ahmetmuhittingurkan.todoapp.ui.viewmodel.KayitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class KayitFragment : Fragment() {

    private lateinit var binding: FragmentKayitBinding
    private lateinit var viewModel: KayitViewModel
    private var reminderTime: Long? = null // Hatırlatıcı zamanı saklamak için

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_kayit, container, false)

        binding.kayitFragment=this
        binding.kayitToolbarBaslik="Not Kaydet"


        binding.btnSelectReminderTime.setOnClickListener {
            showDateTimePicker()
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : KayitViewModel by viewModels()
        viewModel=tempViewModel
    }

    fun buttonKaydet(notBaslik: String, notIcerik: String) {
        if (notBaslik.isNotBlank() && notIcerik.isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                val notId = viewModel.kaydet(notBaslik, notIcerik, reminderTime)

                if (notId != null) {
                    reminderTime?.let { time ->
                        // Hatırlatıcıyı planla
                        scheduleReminder(requireContext(), notId.toInt(), time, notBaslik)
                    }
                } else {
                    Toast.makeText(requireContext(), "Hata: Not ID alınamadı.", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(requireContext(), "Not başarıyla kaydedildi.", Toast.LENGTH_SHORT).show()

                val action = KayitFragmentDirections.actionKayitFragmentToAnasayfaFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        } else {
            Toast.makeText(requireContext(), "Lütfen boş olan alanları doldurunuz.", Toast.LENGTH_SHORT).show()
        }
    }

    fun scheduleReminder(context: Context, notId: Int, reminderTime: Long, notBaslik: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("notId", notId)
            putExtra("notBaslik", notBaslik)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // Tarih Seçimi
        DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Saat Seçimi
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        calendar.set(Calendar.SECOND, 0)

                        // Geçmiş zaman kontrolü
                        if (calendar.timeInMillis >= Calendar.getInstance().timeInMillis) {
                            reminderTime = calendar.timeInMillis
                            binding.tvSelectedReminderTime.text = "Seçilen Zaman: $dayOfMonth/${monthOfYear + 1}/$year ${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
                            Toast.makeText(requireContext(), "Hatırlatıcı zamanı seçildi.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Geçmiş bir saat seçemezsiniz.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // Minimum tarih bugün
            datePicker.minDate = Calendar.getInstance().timeInMillis
        }.show()
    }


}


