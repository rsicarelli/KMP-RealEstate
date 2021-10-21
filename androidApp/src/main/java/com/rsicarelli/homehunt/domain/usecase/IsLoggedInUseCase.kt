package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.repository.UserRepository
import com.rsicarelli.homehunt.domain.usecase.IsLoggedInUseCase.Outcome
import kotlinx.coroutines.flow.flow

class IsLoggedInUseCase(
    private val userRepository: UserRepository
) : UseCase<Unit, Outcome> {
    override fun invoke(request: Unit) = flow {
        userRepository.isLoggedIn()
            .takeIf { it }
            ?.let { emit(Outcome.LoggedIn) }
            ?: emit(Outcome.LoggedOut)
    }

    sealed class Outcome {
        object LoggedIn : Outcome()
        object LoggedOut : Outcome()
    }

}