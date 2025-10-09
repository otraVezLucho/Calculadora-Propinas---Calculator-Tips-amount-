package com.example.tiptime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

class TipUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {

        /*Aquí usamos el primer superpoder de nuestra regla. composeTestRule.setContent { ... } es el equivalente en pruebas a setContent { ... } de una Activity.
        Le estamos diciendo: "Carga esta interfaz de Compose en la pantalla del dispositivo/emulador". Dentro colocamos exactamente el mismo Composable que usamos en MainActivity.*/
        composeTestRule.setContent {
            TipTimeTheme {
                Surface (modifier = Modifier.fillMaxSize()){
                    TipTimeLayout()
                }
            }
        }

        /* 1. composeTestRule.onNodeWithText("Bill Amount"): "En la pantalla, encuentra un elemento (Nodo) que tenga el texto 'Bill Amount'".
           Esto se refiere al OutlinedTextField que tiene esa etiqueta.

           2..performTextInput("10"): "Una vez que lo encuentres, realiza la acción de escribir el texto '10' en él".
           */

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")

        /*ASSERT (Verificar que la UI cambió correctamente)

        Ahora, como un buen inspector, verificamos que la acción anterior tuvo el resultado esperado.
        1.val expectedTip = ...: Primero, calculamos cuál debería ser el texto exacto que esperamos ver en pantalla. Ya sabemos que el 20%
        de 10 es 2, y formateado como moneda es $2.00 (o el equivalente en tu región).

        2.composeTestRule.onNodeWithText("Tip Amount: $expectedTip"): Volvemos a usar nuestro buscador de elementos.
        Esta vez, le decimos: "En la pantalla, encuentra un elemento (Nodo) que tenga el texto EXACTO 'Tip Amount: $2.00'".
        3..assertExists(...): Esta es la afirmación. Le decimos: "Afirmo que un elemento con ese texto debe existir".
        •Si el robot encuentra el texto "Tip Amount: $2.00" en la pantalla, la prueba PASA (verde).•Si no lo encuentra (quizás porque el cálculo
        fue incorrecto y muestra "$1.50", o no se actualizó y sigue mostrando "$0.00"), la prueba FALLA (rojo).

        */
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)

        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found."
        )
    }




}