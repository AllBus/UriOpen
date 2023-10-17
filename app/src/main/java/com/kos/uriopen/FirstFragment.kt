package com.kos.uriopen

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kos.uriopen.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val REQUEST_CODE = 123

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            requireContext().startActionViewIntent(binding.link.text.toString())
        }
        binding.buttonActivity.setOnClickListener {
            val cn =
                ComponentName("com.demetra.osnova", "com.demetra.osnova.main.LauncherActivity")
            val intent = Intent(Intent.ACTION_MAIN)
            intent.setComponent(cn);
            //  intent.setClassName("com.demetra.osnova", "com.demetra.osnova.mai.LauncherActivity");
            requireContext().startActivity(intent)
        }

        val btnLinkPast = view.findViewById<View>(R.id.btn_link_past)
        val btnLinkDelete = view.findViewById<View>(R.id.btn_link_delete)

        btnLinkPast.setOnClickListener {
            binding.link.setText(Utils.getClipboard(requireContext()))
        }

        btnLinkDelete.setOnClickListener {
            binding.link.setText("")
        }

        binding.btnPick.setOnClickListener {
            chooseConfigFile()
        }

        Picasso.get().setIndicatorsEnabled(true)


        binding.viewPager.adapter = ImageAdapter()
        binding.viewPager.offscreenPageLimit = 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != REQUEST_CODE || resultCode != Activity.RESULT_OK || data == null)
            return

        val uri = data.data

        setConfigPath(uri)
    }

    private fun setConfigPath(uri: Uri?) {
        binding.link.setText(uri?.toString().orEmpty())
        if (uri!= null) {
           // val p = File(URI.create(uri.toString().orEmpty())).absolutePath
          //  binding.linkLabel.setText(p)
        }else{
            binding.linkLabel.setText("")
        }

    }

    private fun chooseConfigFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "text/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(intent, REQUEST_CODE)
        }
        catch (ex: Exception) {
            Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}