package com.example.s8144281assignment2

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.* //imports everything from runtime
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen() {

    var username by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(text = "Login", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(8.dp))

        //username text field
        OutlinedTextField(value = username, onValueChange = {username = it}, label = {
            Text(text = "Username") })

        Spacer(modifier = Modifier.padding(8.dp))

        //password text field and setter
        OutlinedTextField(value = password, onValueChange = {password = it}, label = {
            Text(text = "Password")},visualTransformation = PasswordVisualTransformation()) //hides password

        Spacer(modifier = Modifier.padding(8.dp))

        //Login Button
        Button(onClick = {
            Log.i("Credential", "Username: $username Password: $password")
        }) {
            Text(text = "Login")
        }
    }



}