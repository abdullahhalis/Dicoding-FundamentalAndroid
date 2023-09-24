package com.dicoding.myreadwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.myreadwritefile.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonNew.setOnClickListener(this@MainActivity)
            buttonOpen.setOnClickListener(this@MainActivity)
            buttonSave.setOnClickListener(this@MainActivity)
        }

    }

    private fun newFile() {
        binding.apply {
            editTitle.setText("")
            editFile.setText("")
            Toast.makeText(this@MainActivity, "Clearing file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showList() {
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.apply {
            editTitle.setText(fileModel.filename)
            editFile.setText(fileModel.data)
            Toast.makeText(
                this@MainActivity, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun saveFile() {
        binding.apply {
            when{
                editTitle.text.toString().isEmpty() -> Toast.makeText(this@MainActivity, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
                editFile.text.toString().isEmpty() -> Toast.makeText(this@MainActivity, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
                else -> {
                    val title = editTitle.text.toString()
                    val text = editFile.text.toString()
                    val fileModel = FileModel()
                    fileModel.filename = title
                    fileModel.data = text
                    FileHelper.writeToFile(fileModel, this@MainActivity)
                    Toast.makeText(this@MainActivity, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}