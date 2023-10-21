package com.example.composearchitecture.feature.login

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearchitecture.feature.toolbar.CustomTopAppBar
import com.example.composearchitecture.ui.theme.ComposedLibThemeSurface
import com.example.composearchitecture.ui.theme.Purple500
import com.example.composearchitecture.ui.theme.Purple700


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun LoginScreen(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            CustomTopAppBar("Signup", true)
        }, content = {
            Surface(modifier = Modifier.padding(it.calculateTopPadding())) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    var textState by remember { mutableStateOf("") }
                    val username = remember { mutableStateOf(TextFieldValue()) }
                    val password = remember { mutableStateOf(TextFieldValue()) }

                    Text(text = "Login", modifier = Modifier.fillMaxSize(0.15f), style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.border(BorderStroke(width = 2.dp, color = Purple500), shape = RoundedCornerShape(20.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.Blue,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (textState.isNotEmpty()) {
                                IconButton(onClick = { textState = "" }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        label = { Text(text = "Username") },
                        value = username.value,
                        onValueChange = { username.value = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        label = { Text(text = "Password") },
                        value = password.value,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = { password.value = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(50.dp)
                        ) {
                            Text(text = "Login")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableText(
                        text = AnnotatedString("Forgot password?"),
                        onClick = { },
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default
                        )
                    )

                    ClickableText(
                        text = AnnotatedString("Sign up here"),
                        modifier = Modifier.padding(20.dp),
                        onClick = {onClick},
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline,
                            color = Purple700
                        )
                    )
                }
            }
        })
    }



    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = 0.65f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onClick) {
            Text(text = "Go back")
        }
    }*/
}

@Composable
//@Preview("PIXEL_XL", PIXEL_XL)
//@Preview
@Preview(widthDp = 750, heightDp = 750, showBackground = true)
private fun LoginScreenPreview(){
    ComposedLibThemeSurface {
        LoginScreen {

        }
    }
}