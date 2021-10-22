package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.IsLoggedInUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class IsLoggedInUseCase(
    private val userRepository: UserRepository
) : UseCase<Unit, Outcome> {
    override fun invoke(request: Unit) = flow {
        userRepository.getUser()
            .takeIf { it != null }
            ?.let { emit(Outcome.LoggedIn) }
            ?: emit(Outcome.LoggedOut)
    }

    sealed class Outcome {
        object LoggedIn : Outcome()
        object LoggedOut : Outcome()
    }

}