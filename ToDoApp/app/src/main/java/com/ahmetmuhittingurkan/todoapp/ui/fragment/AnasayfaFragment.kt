package com.ahmetmuhittingurkan.todoapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ahmetmuhittingurkan.todoapp.R
import com.ahmetmuhittingurkan.todoapp.databinding.FragmentAnasayfaBinding
import com.ahmetmuhittingurkan.todoapp.ui.adapter.NotlarAdapter
import com.ahmetmuhittingurkan.todoapp.ui.viewmodel.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {

    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_anasayfa,container,false)

        binding.toolbarAnasayfaBaslik="Yapılacaklar"
        binding.anasayfaFragment=this


        viewModel.notlarListesi.observe(viewLifecycleOwner){
            val notlarAdapter= NotlarAdapter(requireContext(),it,viewModel)
            binding.notlarAdapter=notlarAdapter
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.ara(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.ara(newText)
                return true
            }

        })

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
        }

        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaViewModel by viewModels()
        viewModel=tempViewModel
    }

    fun fabTikla(it:View){
        Navigation.findNavController(it).navigate(R.id.kayitGecis)
    }

    override fun onResume() {
        super.onResume()
        viewModel.notlariYukle()
    }

}
