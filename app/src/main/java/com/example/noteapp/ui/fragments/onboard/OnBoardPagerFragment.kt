package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.airbnb.lottie.LottieCompositionFactory
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagerBinding
import com.example.noteapp.utils.PreferenceHelper

class OnBoardPagerFragment : Fragment() {

private lateinit var binding: FragmentOnBoardPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardPagerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupListeners()
    }

    private fun setupListeners()= with(binding) {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        buttonStart.setOnClickListener{
            sharedPreferences.onBoardShown = true
            findNavController().navigate(R.id.noteFragment)
            Toast.makeText(requireContext(), "Добро пожаловать", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                title.text = "Удобства"
                desc.text = "Создавайте заметки в два клика!\n Записывайте мысли, идеи и \n важные задачи мгновенно."
                val composition = LottieCompositionFactory.fromRawResSync(requireContext(), R.raw.anim_onboard_1).value!!
                lottie.setComposition(composition)
            }
            1->{
                title.text = "Организация"
                desc.text = "Организуйте заметки по папкам \nи тегам. Легко находите нужную \nинформацию в любое время."
                val composition = LottieCompositionFactory.fromRawResSync(requireContext(), R.raw.anim_onboard_2).value!!
                lottie.setComposition(composition)
            }
            2->{
                title.text = "Синхронизация"
                desc.text = "Синхронизация на всех устройствах\n Доступ к записям в любое время \nи в любом месте."
                val composition = LottieCompositionFactory.fromRawResSync(requireContext(), R.raw.anim_onboard_3).value!!
                lottie.setComposition(composition)
                binding.buttonStart.visibility = View.VISIBLE
            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}