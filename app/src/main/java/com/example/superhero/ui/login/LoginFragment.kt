package com.example.superhero.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.superhero.R
import com.example.superhero.base.BaseFragment
import com.example.superhero.base.hideKeyboard
import com.example.superhero.databinding.LoginScreenBinding
import com.example.superhero.ui.registration.RegistrationFragment
import com.example.superhero.utils.observeEvent

//ფრაგმენტში ბაინდინგი ჯობია იყოს ნალი,
// ფრაგმენტის ვიუს სიცოცხლის ციკლის გამო,
// იმ მომენტისთვის როცა ვიუ აღარ იქნება ცოცხალი, დაცული იქნება


class LoginFragment : BaseFragment(), View.OnClickListener {

    private var binding: LoginScreenBinding? = null

    private val viewModel: LoginViewModel by activityViewModels()

    override fun getViewModelInstance() = viewModel

    //ფრაგმენტიდან დასაბრუნებლად ვიყენებთ setFragmentResultListener(ვაწვდით:Fragment.key){}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(RegistrationFragment.KEY_DATA) { _, bundle ->
            binding?.userNameInput?.setText(bundle.getString(RegistrationFragment.KEY_USERNAME))
        }
    }

    //ფრაგმენტის სიციცოხლის ციკლის პირვლი ეტაპი
    //inflater <-- ფარენთი
    //container აქ ჯდევა ის ვიუ რომელსაც ფრაგმენტი გააკეთებს
    //attachToParent <-- ჩასვას თუ არა დაინფლეიტებული ვიუ. FragmentManager მიხედავს
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.registrationButton?.setOnClickListener(this)
        binding?.logInButton?.setOnClickListener(this)
        binding?.backButton?.setOnClickListener(this)
        viewModel.inputError.observe(viewLifecycleOwner) {
            binding?.passwordInput?.error = getString(it)
        }
        viewModel.loginSuccess.observeEvent(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.loginFragmentStarted()
    }

    override fun onClick(v: View?) {
        hideKeyboard()
        when (v) {
            binding?.registrationButton -> {
                startRegistration()
            }
            binding?.backButton -> {
                findNavController().popBackStack()
            }
            binding?.logInButton -> {
                viewModel.login(
                    username = binding?.userNameInput?.text,
                    password = binding?.passwordInput?.text
                )
            }
        }
    }

    override fun onDestroy() {
        viewModel.loginFragmentDestroyed()
        super.onDestroy()
    }


    private fun startRegistration() {
        findNavController().navigate(R.id.form_login_to_registration)
    }


    companion object {
        const val KEY_LOGIN_RESULT = "key_login_result"
        const val KEY_LOGIN_RESULT_SUCCESS = "key_login_result_success"
    }
}