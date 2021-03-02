package com.example.androiddevchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.ui.models.ModelPet

class MainViewModel : ViewModel() {

    private var infoPet: MutableLiveData<ModelPet?> = MutableLiveData(null)
    val infoPetObj: LiveData<ModelPet?> = infoPet

    fun showPet(pet: ModelPet?) {
        infoPet.value = pet
    }

}