package com.rsicarelli.homehunt.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.core.util.await
import com.rsicarelli.homehunt.domain.usecase.SignInUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.SignInUseCase.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignInUseCase(
    private val firebaseAuth: FirebaseAuth
) : UseCase<Request, Outcome> {
    override operator fun invoke(request: Request) = flow {
        try {
            firebaseAuth.signInWithCredential(request.authCredential).await()
                ?.run { if (user == null) emit(Outcome.Error) else emit(Outcome.Success) }
                ?: emit(Outcome.Error)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Outcome.Error)
        }
    }.flowOn(Dispatchers.IO)

    data class Request(val authCredential: AuthCredential)
    sealed class Outcome {
        object Success : Outcome()
        object Error : Outcome()
    }
}