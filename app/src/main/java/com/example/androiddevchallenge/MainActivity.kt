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
package com.example.androiddevchallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.ui.MainViewModel
import com.example.androiddevchallenge.ui.models.ModelPet
import com.example.androiddevchallenge.ui.theme.*

var infoPet: MutableLiveData<ModelPet?> = MutableLiveData(null)
lateinit var lifecycleOwner: LifecycleOwner
lateinit var mainViewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java);

        lifecycleOwner = this
        setContent {
            MyApp {
                MyScreenContent()
            }


        }

    }

    override fun onBackPressed() {
        if(mainViewModel.infoPetObj.value!=null){
            mainViewModel.showPet(null)
        }else{
            super.onBackPressed()
        }

    }

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeDevChallengeTheme {
        Surface(color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(1f)) {
            content()
        }
    }
}




@Composable
fun MyScreenContent() {

    val petInfo:ModelPet? by mainViewModel.infoPetObj.observeAsState()
    Log.d("PetInfo","Pet info  is : $petInfo")

    Column {
        Row{
            TitleHeader("Love to adopt and\nLove to live")
        }
        SearchToolBar()
        PetsListContainer(ModelPet.getDummyPets())
    }

    if(petInfo!=null){
        ShowPetInfoViaComposabel(pet = petInfo!!)
    }


}


@Composable
fun PetsListContainer(pets : ArrayList<ModelPet>,
                      modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    Column(modifier = androidx.compose.ui.Modifier.padding(LargePadding)) {
        Text(text = "Our Recommendations",
            style = MaterialTheme.typography.subtitle2.copy(
                fontSize = 18.sp
            ),)
        Divider(
            color=colors.primary,
            modifier = modifier
                .fillMaxWidth(0.4f)
                .padding(top = StandardPadding),
            thickness = 5.dp
        )
        PetsGrid(pets = pets)
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetsGrid(pets: ArrayList<ModelPet>){

    LazyVerticalGrid(cells = GridCells.Fixed(2)){
      items(pets){pet->
          PetCard(pet = pet)
      }
    }
}

@Composable
fun TitleHeader(title: String,modifier: Modifier = Modifier) {

    val colors = lightColors()

    Text(
        text = title,
        modifier = modifier.padding(LargePadding),
        style = MaterialTheme.typography.h5.copy(
            lineHeight = 35.sp,
            fontFamily = CustomTitleFont
        ),
        color = colors.onSurface
    )

}

@Preview(showBackground = true,name="DefaultPreview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}

@Composable
fun PetCard(pet:ModelPet, modifier: Modifier = Modifier){

    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth(1f)
            .wrapContentHeight()
            .padding(16.dp)
        ,
        elevation = 10.dp,
        /*border = BorderStroke(0.dp,MaterialTheme.colors.primary.copy(alpha = 0.3f))*/
    ) {

        Column( modifier = modifier
            .padding(8.dp)
            .clip(
                RoundedCornerShape(StandardPadding)
            )
            .clickable {
                mainViewModel.showPet(pet)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(painter = painterResource(id = pet.image ),
                contentDescription = null,
                modifier = modifier
                    .height(100.dp)
                    .fillMaxWidth(1f),
                contentScale = ContentScale.FillBounds)

            Text(text = pet.name,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier.padding(top= StandardPadding,bottom = 2.dp))

        }

    }

}

@Preview
@Composable
fun ShowPetInfo() {
    ShowPetInfoViaComposabel(ModelPet(R.drawable.d2,"Dexter",-2))
}


@Composable
fun ShowPetInfoViaComposabel(pet: ModelPet, modifier: Modifier = Modifier) {
    var colors = MaterialTheme.colors
    Surface(modifier = Modifier
        .fillMaxHeight(1f)
        .fillMaxWidth(1f),
        color = MaterialTheme.colors.background
    ) {

        Card(
            modifier
                .padding(LargePadding)
                .clip(RoundedCornerShape(5))
                .background(colors.surface)
        )
        {
            Box {
                Image(painter = painterResource(id = pet.image),
                    contentDescription = null,
                    modifier = modifier
                        .clip(RoundedCornerShape(topStartPercent = 5, topEndPercent = 5))
                        .height(300.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                LazyColumn() {

                    item {
                        Column(verticalArrangement = Arrangement.Top,
                            modifier = modifier
                                .padding(top = 300.dp)
                                .background(colors.surface)) {

                            Text(
                                text = pet.name,
                                style = MaterialTheme.typography.h5.copy(
                                    color = colors.onSurface,
                                    fontFamily = CustomTitleFont
                                ),
                                modifier = modifier.padding(
                                    start = LargePadding,
                                    top = LargePadding
                                ),
                            )

                            Surface(
                                shape = RoundedCornerShape(10),
                                modifier = modifier
                                    .padding(StandardPadding)
                                    .wrapContentHeight(Alignment.Top)
                                    .fillMaxWidth(1f)
                            ) {
                                Row(
                                    modifier = modifier
                                        .padding(StandardPadding)
                                        .fillMaxWidth(1f),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Feature(
                                        modifier = modifier,
                                        pet.age.toString() + " years",
                                        R.drawable.ic_baseline_cake_24,
                                        colors.primary
                                    )
                                    Feature(
                                        modifier = modifier,
                                        pet.gender.toString() + "",
                                        R.drawable.male_icon,
                                        colors.error
                                    )
                                    Feature(
                                        modifier = modifier,
                                        (100..200).random().toString() + " views",
                                        R.drawable.ic_baseline_remove_red_eye_24,
                                        colorResource(id = R.color.green)
                                    )
                                }
                            }

                            Text(
                                text = "More about ${pet.name}",
                                modifier = modifier.padding(
                                    start = LargePadding,
                                    top = StandardPadding
                                ),
                                style = MaterialTheme.typography.subtitle2
                            )

                            Card(
                                backgroundColor = colors.primary.copy(alpha = 0.03f),
                                modifier = modifier.padding(8.dp),
                                elevation = 0.dp,
                                shape = RoundedCornerShape(5)
                            ) {
                                Column {
                                    Text(
                                        text = "${pet.name} is a nice dog." +
                                                " I would like to keep him forever, But I am Jhon Cena and" +
                                                " you know no one can see me. I want ${pet.name} to have a visible/physical owner so that he don't " +
                                                "feel like he is living with an invisible man\n\nHe Got These Awesome Skills\n" +
                                                "\n1. He is creative" +
                                                "\n2. He loves people / kids" +
                                                "\n3. He is brave" +
                                                "\n4. He can save your life if you are in danger" +
                                                "\n\nThe Cons About Him" +
                                                "\nHe don't have any cons" +
                                                "\n\nAdopt him and be a visible owner for him",
                                        modifier = modifier.padding(LargePadding),
                                        style = MaterialTheme.typography.body2.copy(lineHeight = 25.sp)
                                    )

                                    Row(Modifier.padding(start = StandardPadding)) {

                                        Image(
                                            painter = painterResource(id = R.drawable.jhon),
                                            contentDescription = null,
                                            modifier = modifier
                                                .width(65.dp)
                                                .height(65.dp)
                                                .padding(StandardPadding)
                                                .clip(RoundedCornerShape(50)),
                                            contentScale = ContentScale.FillBounds
                                        )

                                        Column {
                                            Text(
                                                text = "Jhon Cena",
                                                modifier = modifier.padding(
                                                    start = LargePadding,
                                                    top = StandardPadding
                                                ),
                                                style = MaterialTheme.typography.subtitle2
                                            )
                                            Text(
                                                text = "Seller",
                                                modifier = modifier.padding(
                                                    start = LargePadding,
                                                    top = StandardPadding
                                                ),
                                                style = MaterialTheme.typography.caption
                                            )
                                        }
                                    }

                                    Button(onClick = {},
                                        border = BorderStroke(2.dp,colors.primary),
                                        shape = RoundedCornerShape(5),
                                        modifier = modifier
                                            .fillMaxWidth(1f)
                                            .padding(LargePadding)
                                            .height(60.dp)) {
                                        Text(text = "ADOPT ${pet.name.toUpperCase()}")
                                    }

                                    OutlinedButton(onClick = {},
                                        border = BorderStroke(2.dp,colors.primary),
                                        shape = RoundedCornerShape(5),
                                        modifier = modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = LargePadding,
                                                end = LargePadding,
                                                bottom = LargePadding
                                            )
                                            .height(60.dp)) {
                                        Row {
                                            Image(painter = painterResource(id = R.drawable.ic_baseline_phone_24),
                                                contentDescription = null,
                                                modifier=modifier.padding(end = LargePadding),
                                                colorFilter = ColorFilter.tint(colors.primary))
                                            Text(text = "CALL JHON CENA",modifier = modifier)
                                        }

                                    }



                                }

                            }

                        }
                    }
                }
            }

        }
    }
}

@Composable
fun Feature(modifier: Modifier = Modifier, featureText :String, featureIcon:Int, boxColor: Color) {
    Surface(shape = RoundedCornerShape(5),
        modifier = modifier
            .wrapContentHeight(Alignment.Top)
            .wrapContentWidth(Alignment.Start),
        border = BorderStroke(
            1.dp,
            boxColor.copy(alpha = 0.5f)),
        color = boxColor.copy(0.03f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(LargePadding)
                .defaultMinSize(50.dp)) {
            Image(painter = painterResource(featureIcon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(boxColor.copy(alpha = 1f)),
                modifier=modifier.size(30.dp)
            )
            Text(text = featureText,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier.padding(top= StandardPadding),
                color = boxColor
            )
        }
    }

}


@Composable
fun SearchToolBar(modifier: Modifier = Modifier){

    val colors = MaterialTheme.colors

    Card(
        shape = RoundedCornerShape(percent = 50),
        modifier = modifier
            .padding(LargePadding)
            .fillMaxWidth(1f)
            .wrapContentHeight(align = Alignment.CenterVertically)
        /*.clip(RoundedCornerShape(50))*/,

        border = BorderStroke(2.dp,colors.primary.copy(alpha = 0.3f)),
        backgroundColor = colors.surface,
        elevation = 10.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(
                id = R.drawable.ic_baseline_search_24),
                contentDescription = "Who Cares",
                colorFilter = ColorFilter.tint(colors.onSurface, BlendMode.SrcAtop),
                alpha = 1f,
                modifier = modifier
                    .defaultMinSize(48.dp, 48.dp)
                    .wrapContentHeight()
                    .padding(start = StandardPadding)
            )
            Text(text = "Search",
                color = lightColors().onSurface.copy(alpha = 1f),
                modifier = modifier.padding(top=LargePadding,bottom = LargePadding,start= StandardPadding))
        }

    }
}





