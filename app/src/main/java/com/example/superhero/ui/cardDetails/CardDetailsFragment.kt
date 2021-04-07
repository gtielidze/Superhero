package com.example.superhero.ui.cardDetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.superhero.R
import com.example.superhero.base.BaseFragment
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.databinding.CardDetailsScreenBinding
import com.example.superhero.ui.cardDetails.CardDetailsViewModel.CardSavedState.*
import com.example.superhero.ui.login.LoginViewModel
import com.example.superhero.utils.observeEvent


class CardDetailsFragment : BaseFragment() {

    private var binding: CardDetailsScreenBinding? = null

    private val cardDetailArg by navArgs<CardDetailsFragmentArgs>()

    private val viewModel by viewModels<CardDetailsViewModel> {
        CardDetailsViewModel.CardDetailsViewModelFactory(cardDetailArg.data)
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun getViewModelInstance() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CardDetailsScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cardModel.observe(viewLifecycleOwner) {
           showCardData(it)
        }

        binding?.backButtonDetailCard?.setOnClickListener {
            findNavController().popBackStack()
        }
        binding?.addRemoveBtn?.setOnClickListener {
            viewModel.buttonClicked()
        }
        viewModel.cardSaved.observe(viewLifecycleOwner) {
            when (it) {
                NotSaved -> binding?.addRemoveBtn?.setText(R.string.card_details_add)
                Saved -> binding?.addRemoveBtn?.setText(R.string.card_details_remove)
                else -> binding?.addRemoveBtn?.setText(R.string.card_details_log_in)
            }
        }

        viewModel.loginRequired.observeEvent(viewLifecycleOwner) {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.login)
        }

        loginViewModel.loginFlowFinished.observeEvent(viewLifecycleOwner) {
            if (it) viewModel.determineCardSavedState()
        }
        viewModel.showConfirmDialog.observeEvent(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext()).setMessage(R.string.are_you_sure_delete)
                .setPositiveButton(
                    R.string.common_yes
                ) { dialog, which ->
                    dialog.dismiss()
                    viewModel.deleteConfirmed()
                }
                .setNeutralButton(R.string.common_no) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }.show()
        }


    }

    private fun showCardData(card: SuperheroCard) {
        binding?.apply {
            heroName.text = card.name
            fullNameTxt.text = card.biography?.fullName
            aliasesTxt.text = card.biography?.aliases.toString()
            Glide.with(heroImg).load(card.image?.url).placeholder(R.drawable.item_loader)
                .into(heroImg)
            eyeColor.text = card.appearance?.eyeColor
            gender.text = card.appearance?.gender
            hairColor.text = card.appearance?.hairColor
            height.text = card.appearance?.height.toString()
            race.text = card.appearance?.race
            weight.text = card.appearance?.weight.toString()

            val combatProgress = card.powerstats?.combat?.toIntOrNull()
            if (combatProgress != null) {
                combatPrb.progress = combatProgress
            }
            combatProgressTxt.text = "$combatProgress"

            val durabilityProgress = card.powerstats?.durability?.toIntOrNull()
            if (durabilityProgress != null) {
                durabilityPrb.progress = durabilityProgress
            }
            durabilityProgressTxt.text = "$durabilityProgress"

            val intelligenceProgress = card.powerstats?.intelligence?.toIntOrNull()
            if (intelligenceProgress != null) {
                intelligencePrb.progress = intelligenceProgress
            }
            intelligenceProgressTxt.text = "$intelligenceProgress"

            val powerProgress = card.powerstats?.power?.toIntOrNull()
            if (powerProgress != null) {
                powerPrb.progress = powerProgress
            }
            powerProgressTxt.text = "$powerProgress"

            val speedProgress = card.powerstats?.speed?.toIntOrNull()
            if (speedProgress != null) {
                speedPrb.progress = speedProgress
            }
            speedProgressTxt.text = "$speedProgress"

            val strengthProgress = card.powerstats?.strength?.toIntOrNull()
            if (strengthProgress != null) {
                strengthPrb.progress = strengthProgress
            }
            strengthProgressTxt.text = "$strengthProgress"

            baseTxt.text = card.work?.base
            occupationTxt.text = card.work?.occupation
            groupAffiliationTxt.text = card.connections?.groupAffiliation
            relativesTxt.text = card.connections?.relatives


        }
    }

}





