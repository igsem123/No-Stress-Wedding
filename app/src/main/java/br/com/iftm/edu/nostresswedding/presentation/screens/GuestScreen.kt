package br.com.iftm.edu.nostresswedding.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.sample.sampleGuestList
import java.util.UUID

@Composable
fun GuestScreen() {
    var guests by remember { mutableStateOf(sampleGuestList) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            GuestForm(
                onGuestAdded = { newGuest ->
                    guests = guests + newGuest
                }
            )
        }

        items(guests.size) { index ->
            val guest = guests[index]
            GuestItem(
                guest = guest,
                onConfirmedChange = { isChecked ->
                    guests = guests.toMutableList().also {
                        it[index] = it[index].copy(isConfirmed = isChecked)
                    }
                }
            )
        }
    }
}

@Composable
fun GuestItem(
    guest: GuestDto,
    onConfirmedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))

            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(guest.name, fontWeight = FontWeight.Bold)
            Text(guest.email, style = MaterialTheme.typography.bodySmall)
            Text(guest.phone, style = MaterialTheme.typography.bodySmall)
        }

        Checkbox(
            checked = guest.isConfirmed,
            onCheckedChange = onConfirmedChange
        )
    }
}

@Composable
fun GuestForm(
    onGuestAdded: (GuestDto) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isExpanded) {
            Text(
                text = "Adicionar Convidado",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Button(
                onClick = { isExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Convidado")
            }
        } else {
            Text(
                text = "Novo Convidado",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                singleLine = true
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefone") },
                singleLine = true
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (name.isNotBlank() && email.isNotBlank()) {
                            onGuestAdded(
                                GuestDto(
                                    id = UUID.randomUUID().toString(),
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    isConfirmed = false
                                )
                            )
                            name = ""
                            email = ""
                            phone = ""
                            isExpanded = false
                        }
                    },
                    enabled = name.isNotBlank() && email.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Adicionar")
                }

                OutlinedButton(
                    onClick = { isExpanded = false },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

data class GuestDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val isConfirmed: Boolean
)