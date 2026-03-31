package com.example.admin_panel_hhire_mobile.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.admin_panel_hhire_mobile.data.repository.PostRepository
import com.example.admin_panel_hhire_mobile.databinding.FragmentSettingsBinding
import com.google.gson.Gson
import java.io.File

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExportData.setOnClickListener {
            exportDataToJson()
        }

        binding.btnClearData.setOnClickListener {
            showClearDataDialog()
        }

        binding.btnAbout.setOnClickListener {
            showAboutDialog()
        }
    }
    private fun exportDataToJson() {
        val users = PostRepository.getAllUsers() // або будь-які дані
        val posts = PostRepository.getAllPosts()

        val exportMap = mapOf("users" to users, "posts" to posts)
        val jsonString = Gson().toJson(exportMap)

        val fileName = "backup_${System.currentTimeMillis()}.json"
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        file.writeText(jsonString)

        Toast.makeText(requireContext(), "Дані збережено у ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
    private fun showClearDataDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Підтвердьте дію")
            .setMessage("Ви впевнені, що хочете видалити всіх користувачів?")
            .setPositiveButton("Так") { _, _ ->
                PostRepository.getAllUsers()
                Toast.makeText(requireContext(), "Усі користувачі видалені", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Ні", null)
            .show()
    }
    private fun showAboutDialog() {
        val versionName = try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            pInfo.versionName ?: "1.0"
        } catch (e: Exception) {
            "1.0"
        }
        AlertDialog.Builder(requireContext())
            .setTitle("About app")
            .setMessage("Version: $versionName\nDeveloper: N.V.S. \nShort description: admin panel for moderating users and posts.")
            .setPositiveButton("Ок", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
