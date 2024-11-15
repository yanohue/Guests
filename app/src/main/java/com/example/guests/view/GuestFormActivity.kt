package com.example.guests.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guests.R
import com.example.guests.constants.DataBaseConstants
import com.example.guests.databinding.ActivityGuestFormBinding
import com.example.guests.model.GuestModel
import com.example.guests.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        binding.buttonConfirm.setOnClickListener(this)
        binding.radioPresenceYes.isChecked = true

        observe()
        loadData()
    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_confirm) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresenceYes.isChecked

            val model = GuestModel(guestId, name, presence)
            viewModel.save(model)
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun observe() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if(it.presence) {
                binding.radioPresenceYes.isChecked = true
            } else {
                binding.radioPresenceNo.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if(it.success) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}



















