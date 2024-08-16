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
import androidx.navigation.fragment.navArgs
import com.ahmetmuhittingurkan.todoapp.R
import com.ahmetmuhittingurkan.todoapp.databinding.FragmentDetayBinding
import com.ahmetmuhittingurkan.todoapp.entity.Notlar
import com.ahmetmuhittingurkan.todoapp.entity.ReminderReceiver
import com.ahmetmuhittingurkan.todoapp.ui.viewmodel.DetayViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class DetayFragment : Fragment() {

    private lateinit var binding: FragmentDetayBinding
    private lateinit var viewModel: DetayViewModel
    private var reminderTime: Long? = null
    private val gelenNot: Notlar by lazy {
        val args: DetayFragmentArgs by navArgs()
        args.Not
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_detay, container, false)

        binding.detayFragment=this
        binding.detayToolbarBaslik="Notlar"

        val bundle:DetayFragmentArgs by navArgs()
        val gelenNot=bundle.Not

        binding.notNesnesi=gelenNot

        binding.btnUpdateReminderTime.setOnClickListener {
            showDateTimePicker()
        }

        gelenNot.reminderTime?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it

            val formattedTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
            val formattedDate = String.format("%d/%d/%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR))

            binding.tvUpdatedReminderTime.text = "Seçilen Zaman: $formattedDate $formattedTime"
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : DetayViewModel by viewModels()
        viewModel=tempViewModel
    }

    fun buttonGuncelle(notId: Int, notBaslik: String, notIcerik: String) {
        if (notBaslik.isNotBlank() && notIcerik.isNotBlank()) {
            val finalReminderTime = reminderTime ?: gelenNot.reminderTime
            viewModel.guncelle(notId, notBaslik, notIcerik, finalReminderTime)
            Toast.makeText(requireContext(), "Not başarıyla güncellendi.", Toast.LENGTH_SHORT).show()
            val action = DetayFragmentDirections.actionDetayFragmentToAnasayfaFragment()
            Navigation.findNavController(requireView()).navigate(action)
        } else {
            Toast.makeText(requireContext(), "Lütfen boş olan alanları doldurunuz.", Toast.LENGTH_SHORT).show()
        }
    }

    fun scheduleReminder(context: Context, notId: Int, reminderTime: Long, notBaslik: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra("notId", notId)
        intent.putExtra("notBaslik", notBaslik)

        val pendingIntent = PendingIntent.getBroadcast(context, notId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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
                            binding.tvUpdatedReminderTime.text = "Seçilen Zaman: $dayOfMonth/${monthOfYear + 1}/$year ${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
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
