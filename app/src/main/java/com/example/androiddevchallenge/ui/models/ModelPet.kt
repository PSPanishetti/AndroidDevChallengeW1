/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.models

import com.example.androiddevchallenge.R

data class ModelPet(val image: Int, val name: String, val age: Int, val gender: String = "Boy") {
    companion object {
        fun getDummyPets(): ArrayList<ModelPet> {
            return arrayListOf(
                ModelPet(R.drawable.d1, "Charlie", 5),
                ModelPet(R.drawable.d2, "Max", 5),
                ModelPet(R.drawable.d3, "Oscar", 5),
                ModelPet(R.drawable.d4, "Milo", 5),
                ModelPet(R.drawable.d5, "Olie", 5),
                ModelPet(R.drawable.d6, "Jack", 5),
                ModelPet(R.drawable.d7, "Teddy", 5),
            )
        }
    }
}
