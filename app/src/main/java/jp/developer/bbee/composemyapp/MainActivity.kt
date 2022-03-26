package jp.developer.bbee.composemyapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.bbee.composemyapp.ui.theme.ComposeMyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ItemListView(ItemListData.itemsShoppingCart)
                }
            }
        }
    }
}

// Shopping cart for electronic commerce UI
data class Item (val itemId: Int, val itemName: String, val itemPrice: Int, var itemBuyNum: Int)

@Composable
fun ItemListView(itemList: List<Item>) {
    val resourcesImage = listOf<Int>(
        R.drawable.boxdesign_item001,
        R.drawable.boxdesign_item002,
        R.drawable.boxdesign_item003,
        R.drawable.boxdesign_item004,
        R.drawable.boxdesign_item005,
    )

    LazyColumn (Modifier.background(color = MaterialTheme.colors.background)
        .fillMaxWidth().padding(10.dp)
    ) { // scroll list vertical
        items(itemList) { item ->
            ItemView(item, resourcesImage)
        }
    }
}

@Composable
fun ItemView(item: Item, resourcesImage: List<Int>) {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(5.dp)
    ) {
        Image(
            painter = painterResource(id = resourcesImage[item.itemId - 1]),
            contentDescription = "item image",
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.itemName,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "¥ " + String.format("%,d", item.itemPrice),
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp))
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!" ,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(10.dp),
        fontStyle = FontStyle.Italic
    )
}

@Preview(
    showBackground = true,
    name = "light"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "dark"
)
@Composable
fun ItemListPreview() {
    ComposeMyAppTheme {
        ItemListView(ItemListData.itemsShoppingCart)
    }
}