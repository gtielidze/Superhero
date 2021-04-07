package com.example.superhero.ui.registration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.superhero.R
import com.example.superhero.base.BaseFragment
import com.example.superhero.databinding.RegistrationScreenBinding

//parentFragmentManager - ვიყენებთ იმ შემთხევაში როცა იმ ფრაგმენტზე გვინდა ზემოქმედება სად ეს კლასი ჯდება
//ChildFragmentManager - ვიყენებთ იმ შემთხვევაში თუ გვჭირდება ფრაგმენტი ფრაგმენტში რომ ჩავსვათ


class RegistrationFragment : BaseFragment() {

    private var binding: RegistrationScreenBinding? = null

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backButton?.setOnClickListener { activity?.onBackPressed() }
        binding?.signUpBtn?.setOnClickListener {
            viewModel.onRegister(
                name = binding?.registrationActivityName?.text,
                username = binding?.userNameInput?.text,
                password = binding?.passwordInput?.text,
                repeatPassword = binding?.registrationScreenRepeatPassword?.text
            )
        }
        viewModel.validationError.observe(viewLifecycleOwner, this::showValidationError)
        viewModel.registrationComplete.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.loginFragment, true)
        }
    }

    private fun showValidationError(error: RegistrationViewModel.ValidationError) {
        binding?.apply {
            when (error) {
                RegistrationViewModel.ValidationError.EmptyUsername -> {
                    userNameInput.error = getString(R.string.registration_error_empty_username)
                }
                RegistrationViewModel.ValidationError.EmptyName -> {
                    registrationActivityName.error = getString(R.string.registration_error_empty_name)
                }
                RegistrationViewModel.ValidationError.EmptyPassword -> {
                    passwordInput.error = getString(R.string.registration_error_empty_password)
                }
                RegistrationViewModel.ValidationError.PasswordsNotMatching -> {
                    registrationScreenRepeatPassword.error =
                        getString(R.string.registration_error_passwords_not_matching)
                }
                RegistrationViewModel.ValidationError.None -> {
                    userNameInput.error = null
                    registrationActivityName.error = null
                    passwordInput.error = null
                    registrationScreenRepeatPassword.error = null
                }
            }
        }

    }


    companion object {
        const val KEY_USERNAME = "key_username"
        const val KEY_DATA = "key_data"
    }

}