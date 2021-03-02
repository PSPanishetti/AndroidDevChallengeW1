package com.example.androiddevchallenge.ui.models

import com.example.androiddevchallenge.R

data class ModelPet(val image:Int, val name:String, val age:Int, val gender:String = "Boy"){
    companion object{
        fun getDummyPets() : ArrayList<ModelPet>{
            return  arrayListOf(
                ModelPet(R.drawable.d1,"Charlie",5),
                        ModelPet(R.drawable.d2,"Max",5),
                ModelPet(R.drawable.d3,"Oscar",5),
                ModelPet(R.drawable.d4,"Milo",5),
                ModelPet(R.drawable.d5,"Olie",5),
                ModelPet(R.drawable.d6,"Jack",5),
                ModelPet(R.drawable.d7,"Teddy",5),
            )

        }
    }
}
