package com.example.hamburgueriaz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private var amountValueTextView: TextView? = null
    private var baconCheckBox: CheckBox? = null
    private var cheeseCheckBox: CheckBox? = null
    private var onionRingCheckBox: CheckBox? = null
    private var totalOrderValueTextView: TextView? = null
    private var orderButton: Button? = null
    private var addValue: Button? = null
    private var reduceValue: Button? = null
    private var quantity = 0
    private var totalOrderValue = 0.00
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar as views
        amountValueTextView = findViewById(R.id.amountValue)
        baconCheckBox = findViewById(R.id.baconCheckBox)
        cheeseCheckBox = findViewById(R.id.cheeseCheckbox)
        onionRingCheckBox = findViewById(R.id.onionRingCheckBox)
        totalOrderValueTextView = findViewById(R.id.totalOrderValue)
        orderButton = findViewById(R.id.orderButton)
        addValue = findViewById(R.id.addValue)
        reduceValue = findViewById(R.id.reduceValue)
        setupListeners()
    }

    private fun setupListeners() {
        orderButton!!.setOnClickListener { view: View? -> sendOrder() }
        addValue!!.setOnClickListener { view: View? -> addQuantity() }
        reduceValue!!.setOnClickListener { view: View? -> reduceQuantity() }
    }

    // Função para somar a quantidade
    private fun addQuantity() {
        if (quantity >= 0) {
            quantity++
            updateQuantity(quantity)
        }
    }

    // Função para subtrair a quantidade
    private fun reduceQuantity() {
        if (quantity > 0) {
            quantity--
            updateQuantity(quantity)
        }
    }

    // Função para atualizar a quantidade na view
    private fun updateQuantity(quantity: Int) {
        amountValueTextView!!.text = quantity.toString()
    }

    // Função para calcular o valor total do pedido
    private fun calculeTotalOrder() {
        totalOrderValue = quantity * 20.00 +
                (if (baconCheckBox!!.isChecked) 2.00 else 0.00) +
                (if (cheeseCheckBox!!.isChecked) 2.00 else 0.00) + if (onionRingCheckBox!!.isChecked) 3.00 else 0.00
    }

    // Função para enviar o pedido
    private fun sendOrder() {
        calculeTotalOrder()
        val userName: TextInputEditText = findViewById(R.id.inputText)
        val resumeOrder = StringBuilder()
        resumeOrder.append("Nome: ").append(userName.getText()).append("\n")
        resumeOrder.append("Quantidade: ").append(quantity).append("\n")
        if (baconCheckBox!!.isChecked) {
            resumeOrder.append("Adicional: Bacon\n")
        }
        if (cheeseCheckBox!!.isChecked) {
            resumeOrder.append("Adicional: Queijo\n")
        }
        if (onionRingCheckBox!!.isChecked) {
            resumeOrder.append("Adicional: Onion Rings\n")
        }
        resumeOrder.append("Valor Total: R$ ").append(totalOrderValue)
        totalOrderValueTextView!!.text = resumeOrder.toString()
        composeEmail(
            "hamburgueriaZ@email.com.br",
            "Pedido de " + userName.getText(),
            resumeOrder.toString()
        )
    }

    private fun composeEmail(email: String, subject: String, message: String) {
        val result = Intent(Intent.ACTION_SEND)
        result.type = "plain/text"
        result.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        result.putExtra(Intent.EXTRA_SUBJECT, subject)
        result.putExtra(Intent.EXTRA_TEXT, message)
        ContextCompat.startActivity(Intent.createChooser(result, "Send mail..."))
    }
}