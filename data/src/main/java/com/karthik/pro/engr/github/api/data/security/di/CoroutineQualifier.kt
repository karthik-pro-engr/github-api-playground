package com.karthik.pro.engr.github.api.data.security.di

import javax.inject.Qualifier

@Qualifier
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope