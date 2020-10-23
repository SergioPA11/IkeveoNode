package com.sergio.ikeveo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.sergio.ikeveo.models.Gafa
import com.sergio.ikeveo.service.IkeveoServiceImpl
import com.sergio.ikeveo.service.IkeveoSingleton
import com.squareup.picasso.Picasso


class IkeveoDetailActivity : AppCompatActivity() {
    private lateinit var state: String
    private lateinit var textInputEditTextBrand: EditText
    private lateinit var textInputEditTextModel: EditText
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gafa_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        state = this.intent.getStringExtra("state").toString()

        val gafaId = this.intent.getIntExtra("gafaId", 1)

        textInputEditTextBrand = findViewById(R.id.TextInputEditTextBrand)
        textInputEditTextModel = findViewById(R.id.TextInputEditTextModel)

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteGafa(gafaId)
        }

        if(state == "Showing") getGafa(gafaId)

        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            when(state){
                "Showing" -> {
                    changeButtonsToEditing()
                }
                "Editing" -> {
                    val gafa = Gafa(gafaId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    updateGafa(gafa)
                }
                "Adding" -> {
                    val gafa = Gafa(gafaId, textInputEditTextBrand.text.toString(), textInputEditTextModel.text.toString())
                    createGafa(gafa)
                }
            }
        }
        if(state == "Adding") changeButtonsToAdding()
    }

    private fun updateGafa(gafa: Gafa) {
        val ikeveoServiceImpl = IkeveoServiceImpl()
        ikeveoServiceImpl.updateGafa(this, gafa) { ->
            run {
                changeButtonsToShowing(gafa.id)
                val intent = Intent(this, IkeveoListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createGafa(gafa: Gafa) {
        val ikeveoServiceImpl = IkeveoServiceImpl()
        ikeveoServiceImpl.createGafa(this, gafa) { ->
            run {
                changeButtonsToShowing(gafa.id)
                val intent = Intent(this, IkeveoListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeButtonsToAdding() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Añadir Gafa")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Adding"
    }

    private fun changeButtonsToShowing(gafaId: Int){
        buttonDelete.visibility = View.VISIBLE
        buttonDelete.isEnabled = true
        buttonEdit.setText("Editar Gafa")
        textInputEditTextBrand.isEnabled = false
        textInputEditTextModel.isEnabled = false
        state = "Showing"
    }

    private fun changeButtonsToEditing() {
        buttonDelete.visibility = View.GONE
        buttonDelete.isEnabled = false
        buttonEdit.setText("Aplicar Cambios")
        textInputEditTextBrand.isEnabled = true
        textInputEditTextModel.isEnabled = true
        state = "Editing"
    }

    private fun getGafa(gafaId: Int) {
        val ikeveoServiceImpl = IkeveoServiceImpl()
        ikeveoServiceImpl.getById(this, gafaId) { response ->
            run {

                val txt_brand: TextInputEditText = findViewById(R.id.TextInputEditTextBrand)
                val txt_model: TextInputEditText = findViewById(R.id.TextInputEditTextModel)
                val img: ImageView = findViewById(R.id.imageViewGafaDetail)

                txt_brand.setText(response?.brand ?: "")
                txt_model.setText(response?.model ?: "")

                val url = IkeveoSingleton.getInstance(this).baseUrl + "/img/gafas-"
                val imageUrl = url + (response?.id.toString() ?: "0" ) + ".jpg"
                Picasso.with(this).load(imageUrl).into(img);
            }
        }
    }

    private fun deleteGafa(gafaId: Int) {
        val ikeveoServiceImpl = IkeveoServiceImpl()
        ikeveoServiceImpl.deleteById(this, gafaId) { ->
            run {
                val intent = Intent(this, IkeveoListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}