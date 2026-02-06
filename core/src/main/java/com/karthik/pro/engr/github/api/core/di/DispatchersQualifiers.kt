package com.karthik.pro.engr.github.api.core.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@MustBeDocumented
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@MustBeDocumented
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher