package com.example.superhero.ui.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.superhero.R
import com.example.superhero.base.BaseFragment
import com.example.superhero.data.models.user.UserProfile
import com.example.superhero.data.storage.DataStore
import com.example.superhero.databinding.ProfileScreenBinding
import com.example.superhero.ui.login.LoginViewModel
import com.example.superhero.utils.observeEvent


class ProfileFragment : BaseFragment() {

    private var binding: ProfileScreenBinding? = null

    private val viewModel by viewModels<ProfileViewModel>()

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imageButtonKingdom?.setOnClickListener {
            DataStore.language = "en"
            activity?.recreate()
        }
        binding?.imageButtonGeorgian?.setOnClickListener {
            DataStore.language = "ka"
            activity?.recreate()
        }

        binding?.logoutButton?.setOnClickListener {
            loginViewModel.logOut()
            findNavController().navigate(R.id.show_home)
        }

        viewModel.userProfile.observe(viewLifecycleOwner, this::showUserData)
        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            loginViewModel.logOut()
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.login)
        }
        loginViewModel.loginFlowFinished.observeEvent(viewLifecycleOwner) { loginSuccess ->
            if (loginSuccess)
                viewModel.getProfile()
            else
                findNavController().navigate(R.id.show_home)
        }
    }

    private fun showUserData(userProfile: UserProfile) {
        binding?.userNameTextView?.text = userProfile.userName
        binding?.nameTextView?.text = userProfile.name
        //Glide ჩატვირთავს იუზერის პროფილის ფოტოს
        Glide.with(this@ProfileFragment)
            .load(userProfile.imageUrl)
            .centerCrop()
            .into(binding?.profilePictureImageView!!)
    }
}