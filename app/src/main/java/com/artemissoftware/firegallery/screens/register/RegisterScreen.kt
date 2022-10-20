package com.artemissoftware.firegallery.screens.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.button.FGButton
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.composables.textfield.FGOutlinedTextField
import com.artemissoftware.common.composables.textfield.FGTextFieldType
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.splash.composables.Logo
import com.artemissoftware.firegallery.ui.UIEvent
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    scaffoldState: FGScaffoldState,
) {

    val viewModel: RegisterViewModel = hiltViewModel()
    val state = viewModel.state.value


    //TODO: Resolver isto
    val ll = stringResource(R.string.accept)

    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UIEvent.ShowErrorDialog -> {

                    val dialogType = DialogType.Error(
                        title = event.title,
                        description = event.message,
                        dialogOptions = DialogOptions(
                            confirmationText = ll,

                        )
                    )

                    scaffoldState.showDialog(dialogType)
                }
                else ->{}
            }
        }
    }


    BuildRegisterScreen(
        navController = navController,
        state = state,
        events = viewModel::onTriggerEvent
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BuildRegisterScreen(
    navController: NavHostController,
    state: RegisterState,
    events: ((RegisterEvents) -> Unit)? = null,
) {

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val passwordConfirm = remember { mutableStateOf(TextFieldValue()) }


    if(state.registered){
        navController.popBackStack()
    }

    FGScaffold(
        isLoading = state.isLoading,
        showTopBar = true,
        title = stringResource(R.string.profile),
        onNavigationClick = {
            navController.popBackStack()
        }
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)){

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){

                Box(modifier = Modifier.fillMaxWidth()) {

                    Logo(modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterEnd))

                    Column(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FGText(
                            text = stringResource(R.string.create_new_account),
                            style = FGStyle.TextAlbertSansBold28
                        )
                        FGText(
                            text = stringResource(R.string.create_new_account_to_use_features),
                            style = FGStyle.TextAlbertSans
                        )
                    }

                }


                Spacer(modifier = Modifier.height(20.dp))

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.EMAIL,
                    text = email.value,
                    onValueChange = { text->
                        email.value = text
                        events?.invoke(
                            RegisterEvents.ValidateRegister(
                                email = email.value.text,
                                password = password.value.text,
                                passwordConfirm = passwordConfirm.value.text
                            )
                        )
                    },
                    label = stringResource(R.string.email)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = password.value,
                    onValueChange = { text->
                        password.value = text
                        events?.invoke(
                            RegisterEvents.ValidateRegister(
                                email = email.value.text,
                                password = password.value.text,
                                passwordConfirm = passwordConfirm.value.text
                            )
                        )
                    },
                    label = stringResource(R.string.password)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = passwordConfirm.value,
                    onValueChange = { text->
                        passwordConfirm.value = text
                        events?.invoke(
                            RegisterEvents.ValidateRegister(
                                email = email.value.text,
                                password = password.value.text,
                                passwordConfirm = passwordConfirm.value.text
                            )
                        )
                    },
                    label = stringResource(R.string.confirm_password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                FGButton(
                    enabled = state.isValidData,
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.register),
                    onClick = {
                        events?.invoke(
                            RegisterEvents.Register(
                                email = email.value.text,
                                password = password.value.text
                            )
                        )
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {

    val state = RegisterState()

    BuildRegisterScreen(state = state, navController = rememberNavController())
}