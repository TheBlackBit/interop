package com.theblackbit.blog.interop.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.theblackbit.blog.interop.R
import com.theblackbit.blog.interop.composable.LoadingButtonComposable
import com.theblackbit.blog.interop.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        initCompose()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.loginStatusStateFlow.collect {
                if (it == LoginStatus.SUCCESS) {
                    navigateToHome()
                } else if (it == LoginStatus.ERROR) {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Snackbar
            .make(
                binding.buttonComposeView,
                getString(R.string.wrong_username_or_password),
                LENGTH_LONG,
            )
            .show()
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun initCompose() {
        binding.buttonComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Mdc3Theme {
                    val isLoading = viewModel.isLoadingState
                    LoadingButtonComposable(
                        modifier = Modifier.fillMaxWidth(),
                        title = getString(R.string.login),
                        isLoading = isLoading,
                        onButtonClick = { doLogin() },
                    )
                }
            }
        }
    }

    private fun doLogin() {
        viewModel.doLogin(
            binding.userNameTextInputEditText.text?.toString(),
            binding.passwordTextInputEditText.text?.toString(),
        )
    }
}
