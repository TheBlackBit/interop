package com.theblackbit.blog.interop.fragments.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _isLoadingState: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingState: State<Boolean> = _isLoadingState

    private val _loginStatusStateFlow: MutableStateFlow<LoginStatus> =
        MutableStateFlow(LoginStatus.IDLE)
    val loginStatusStateFlow: StateFlow<LoginStatus> = _loginStatusStateFlow

    fun doLogin(userName: String?, password: String?) {
        viewModelScope.launch {
            _isLoadingState.value = true
            _loginStatusStateFlow.value = LoginStatus.IDLE
            delay(2000)
            if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()) {
                _loginStatusStateFlow.value = LoginStatus.SUCCESS
            } else {
                _loginStatusStateFlow.value = LoginStatus.ERROR
            }
            _isLoadingState.value = false
        }
    }
}
