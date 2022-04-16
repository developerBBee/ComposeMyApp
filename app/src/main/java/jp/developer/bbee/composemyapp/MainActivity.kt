package jp.developer.bbee.composemyapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
data class Item (val itemId: Int, val itemName: String, val itemPrice: Int, var itemPurchase: Int)

@Composable
fun ItemListView(itemList: List<Item>) {
    val resourcesImage = listOf<Int>(
        R.drawable.boxdesign_item001,
        R.drawable.boxdesign_item002,
        R.drawable.boxdesign_item003,
        R.drawable.boxdesign_item004,
        R.drawable.boxdesign_item005,
    )

    LazyColumn (
        Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(10.dp)
    ) { // scroll list vertical
        items(itemList) { item ->
            ItemView(item, resourcesImage)
        }
    }
}

@Composable
fun ItemView(item: Item, resourcesImage: List<Int>) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
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
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = item.itemName,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "¥ " + String.format("%,d", item.itemPrice),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight().padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                Text(
                    text = "購入数 ",
                    fontSize = 16.sp
                )
                DropListView(item)
                Spacer(modifier = Modifier.width(20.dp))
                TextButton(
                    modifier = Modifier.height(24.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        /* TODO: Delete item in shopping cart */
                    }) {
                    Text(
                        modifier = Modifier.wrapContentHeight(),
                        text = "削除",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
    Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp))
}

@Composable
fun DropListView(item: Item) {
    var expandedDropdownMenu by remember { mutableStateOf(false) }
    val itemsDropdownMenu = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8 ,9)
    val disabledValue = 9
    var selectedIndex by remember { mutableStateOf(item.itemPurchase) }
    Box(modifier = Modifier
//        .fillMaxSize()
//        .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            text = itemsDropdownMenu[selectedIndex].toString(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentWidth()
                .width(50.dp)
                .clickable(onClick = { expandedDropdownMenu = true })
                .background(
                    color = MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 1.5.dp,
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.medium
                )
        )
        DropdownMenu(
            expanded = expandedDropdownMenu,
            onDismissRequest = { expandedDropdownMenu = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            itemsDropdownMenu.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expandedDropdownMenu = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s.toString() + disabledText)
                }
            }
        }
    }
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
        Surface {
            ItemListView(ItemListData.itemsShoppingCart)
        }
    }
}

@Preview
@Composable
fun DropListPreview() {
    ComposeMyAppTheme {
        Surface {
            DropListView(ItemListData.itemsShoppingCart[0])
        }
    }
}
