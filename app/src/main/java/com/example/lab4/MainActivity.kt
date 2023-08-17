package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.lab4.ui.theme.Lab4Theme
import androidx.compose.material3.ExperimentalMaterial3Api


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeListScreen()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen() {
    var itemName by remember { mutableStateOf(TextFieldValue()) }
    var imageUrl by remember { mutableStateOf(TextFieldValue()) }
    val itemList = remember { mutableStateListOf<RecipeItem>() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Receta") }
            )
            TextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("URL de la imagen") }
            )
            Button(
                onClick = {
                    if (itemName.text.isNotEmpty() && imageUrl.text.isNotEmpty()) {
                        itemList.add(RecipeItem(itemName.text, imageUrl.text))
                        itemName = TextFieldValue()
                        imageUrl = TextFieldValue()
                    }
                }
            ) {
                Text("Agregar")
            }
        }
//LAzy Column: Reusa elementos/celdas y bota elementos repetidos en vez de Column
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(itemList) { recipeItem -> //itera por cada elemento del itemlist
                RecipeCard(recipeItem)
            }
        }
    }
}

@Composable
fun RecipeCard(recipeItem: RecipeItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        //verticalArrangement = Arrangement.Center) { this: ColumnSCope}
    ) {
        Image(
            painter = rememberImagePainter(recipeItem.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = recipeItem.name)
    }
}

data class RecipeItem(val name: String, val imageUrl: String)

@Preview(showBackground = true)
@Composable
fun RecipeListPreview() {
    Lab4Theme {
        RecipeListScreen()
    }
}