package com.rsicarelli.homehunt.core.model

import kotlinx.coroutines.flow.Flow

interface UseCase<R, O> {
    operator fun invoke(request: R): Flow<O>
}

