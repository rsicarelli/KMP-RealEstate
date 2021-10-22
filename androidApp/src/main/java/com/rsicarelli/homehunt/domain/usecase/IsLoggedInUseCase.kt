package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.repository.UserRepository_Old
import com.rsicarelli.homehunt.domain.usecase.IsLoggedInUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class IsLoggedInUseCase(
    private val userRepositoryOld: UserRepository
) : UseCase<Unit, Outcome> {
    override fun invoke(request: Unit) = flow {
        userRepositoryOld.getUser()
            .takeIf { it != null }
            ?.let { emit(Outcome.LoggedIn) }
            ?: emit(Outcome.LoggedOut)
    }

    sealed class Outcome {
        object LoggedIn : Outcome()
        object LoggedOut : Outcome()
    }

}