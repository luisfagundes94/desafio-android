package com.picpay.desafio.android.core.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatchers: PicPayDispatchers)

enum class PicPayDispatchers {
    Default,
    IO
}