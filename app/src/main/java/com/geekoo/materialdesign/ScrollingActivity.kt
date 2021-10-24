package com.geekoo.materialdesign

import android.graphics.Color
import android.os.Bundle
import android.service.autofill.Sanitizer
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.geekoo.materialdesign.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            if(binding.btnAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.btnAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            } else {
                binding.btnAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.btnAppBar.setNavigationOnClickListener(){
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }

        binding.content.btnSkip?.setOnClickListener{
            binding.content.cvAd?.visibility = View.GONE
        }

        binding.content.btnBuy?.setOnClickListener{
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        binding.content.imgCover?.let {
            loadImage();
        }

        binding.content.cbEnabledPass?.setOnClickListener{
            binding.content.tilpassword?.isEnabled = !binding.content.tilpassword?.isEnabled!!
        }

        binding.content.etUrl?.onFocusChangeListener = View.OnFocusChangeListener {
                _, focused ->
            val url = binding.content.etUrl?.text.toString()
            var errorStr: String? = null
            if(!focused){
                if(url.isEmpty()){
                  errorStr = getString(R.string.card_required)
                } else if(URLUtil.isValidUrl(url)){
                    loadImage(url)
                } else {
                    errorStr = getString(R.string.card_invalid_url)
                }
            }
            binding.content.tilurl?.error = errorStr;
        }
        
        binding.content.toogleButtom?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                when(checkedId){
                    R.id.btnRed -> Color.RED
                    R.id.btnBlue -> Color.BLUE
                    else -> Color.GREEN

                }
            )
        }
    }

    private fun loadImage(url: String = "https://images.milledcdn.com/2021-03-04/thZit3Ccb_fYVBqE/K5-U67H_f4no.jpeg"){

            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.content.imgCover!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}