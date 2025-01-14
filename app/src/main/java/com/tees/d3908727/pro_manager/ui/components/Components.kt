package com.tees.d3908727.pro_manager.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonColor: Color = Color.Blue,
    textColor: Color = Color.White,
    loadingState: MutableState<Boolean> = mutableStateOf(false),
    handleClick: () -> Unit )
{

    Button(
        onClick = handleClick,
        modifier = modifier
            .fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        if (loadingState.value){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
        }else{
            Text(
                text = text,
                fontSize = 18.sp,
                color = textColor,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomField(
    onFocused: () -> Unit = {},
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    label: String,
    maxLine : Int = 1,
    leadingIcon:  ImageVector?,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Go,
    emitFinalValue: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = state.value,
        maxLines = maxLine,
        onValueChange = { newValue ->
            state.value = newValue
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        label = {
            Text(
                text = label,
                color = Color.DarkGray.copy(0.75f),
                fontSize = if (isFocused || state.value.isNotEmpty()) 11.sp else 16.sp
            )
        },

        leadingIcon = if (leadingIcon != null) {
            { Icon(imageVector = leadingIcon, contentDescription = null, tint = Color.Blue) }
        } else null,

        singleLine = true,
        textStyle =  TextStyle(fontSize = 18.sp, color = Color.Black, lineHeight = 5.sp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onGo = {
            focusManager.clearFocus()
            keyboardController?.hide()
            emitFinalValue(state.value.trim())
        }),
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Gray,
            focusedContainerColor = Color.Gray.copy(0.25f),
            unfocusedContainerColor = Color.Gray.copy(0.25f),
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}