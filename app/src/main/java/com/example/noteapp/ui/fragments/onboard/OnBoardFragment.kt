package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.OnBoardAdapter

class OnBoardFragment : Fragment() {

private lateinit var binding: FragmentOnBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentOnBoardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setUpListeners()
    }

    private fun init() {
        binding.vp2.adapter = OnBoardAdapter(this)
        //Инициализация Tab layout
        binding.dots.attachTo(binding.vp2)
        }

    private fun setUpListeners()= with(binding.vp2) {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==2){
                    binding.skip.visibility = View.INVISIBLE
                }else {
                    binding.skip.visibility = View.VISIBLE
                    binding.skip.setOnClickListener {
                        if (currentItem < 3) {
                            setCurrentItem(3, true)
                            binding.skip.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        })
    }
}