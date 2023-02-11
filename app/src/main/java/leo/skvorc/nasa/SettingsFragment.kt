package leo.skvorc.nasa

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import leo.skvorc.nasa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val prefs = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        if (prefs != null) {
            val state = prefs.getInt("theme", 1)
            binding.theme.isChecked = state != 1
        }

        binding.theme.setOnClickListener {
            val theme: Int
            if (binding.theme.isChecked) { AppCompatDelegate.MODE_NIGHT_YES
                theme = AppCompatDelegate.MODE_NIGHT_YES
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                theme = AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            prefs?.edit()?.putInt("theme", theme)?.commit()
        }

        return binding.root
    }
}