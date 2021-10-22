package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.User.Companion.MAX_PASSWORD_LENGTH
import com.rsicarelli.homehunt_kmm.domain.model.User.Companion.MAX_USERNAME_LENGTH
import com.rsicarelli.homehunt_kmm.domain.model.User.Companion.MIN_PASSWORD_LENGTH
import com.rsicarelli.homehunt_kmm.domain.model.User.Companion.MIN_USERNAME_LENGTH
import com.rsicarelli.homehunt_kmm.domain.usecase.VerifyUserCredentialsUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.usecase.VerifyUserCredentialsUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VerifyUserCredentialsUseCase : UseCase<Request, Outcome> {

    override fun invoke(request: Request): Flow<Outcome> = flow {
        val (userName, password) = request

        val userNameReason = userName.invalidReasonOrNull(MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH)
        val passwordReason = password.invalidReasonOrNull(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)

        if (userNameReason == null && passwordReason == null) {
            emit(Outcome.Valid)
        } else {
            emit(Outcome.Invalid(userName = userNameReason, password = passwordReason))
        }
    }

    private fun String.invalidReasonOrNull(minSize: Int, maxSize: Int): InvalidReason? =
        when {
            this.isEmpty() || this.isBlank() -> InvalidReason.Empty
            this.length < minSize -> InvalidReason.BelowMinLength
            this.length > maxSize -> InvalidReason.AboveMaxLength
            else -> null
        }

    data class Request(
        val userName: String,
        val password: String
    )

    sealed class Outcome {
        object Valid : Outcome()
        data class Invalid(
            val userName: InvalidReason?,
            val password: InvalidReason?
        ) : Outcome()
    }

    sealed class InvalidReason {
        object Empty : InvalidReason()
        object BelowMinLength : InvalidReason()
        object AboveMaxLength : InvalidReason()
    }

}