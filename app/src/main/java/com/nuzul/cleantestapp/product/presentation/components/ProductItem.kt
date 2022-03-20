package com.nuzul.cleantestapp.product.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.presentation.ProductViewModel

@Composable
fun ProductItem(
    product: Product,
    viewModel: ProductViewModel = hiltViewModel(),
    onItemClick: (Product) -> Unit
) {
    Row(
        modifier = Modifier
        .padding(20.dp)
            .clickable {
                onItemClick(product)
            }
        .fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .weight(0.9F)) {
            Text(
                modifier = Modifier. align(alignment = Alignment.Start),
                text = "${product.sku} ${product.name}",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier. align(alignment = Alignment.Start),
                text = "Rp " +product.price.toString(),
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            modifier = Modifier.weight(0.1F),
            onClick = { viewModel.deleteProduct(product.sku) }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }

    }


}