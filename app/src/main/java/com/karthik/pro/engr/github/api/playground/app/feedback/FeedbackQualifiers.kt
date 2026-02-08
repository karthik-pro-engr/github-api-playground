package com.karthik.pro.engr.github.api.playground.app.feedback

import javax.inject.Qualifier

// Make retention RUNTIME and target the places Dagger/Hilt expects.
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultFeedback

@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BetaFeedback
