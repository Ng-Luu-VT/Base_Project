package com.teachenglish.techenglish.extension

import android.util.Patterns

fun <T> T.validate(validator: Validator<T>): Boolean = validator.validate(this)

interface Validator<in T> {
    fun validate(any: T?): Boolean
}

/**
 * Validate Email
 */
private class EmailValidator : Validator<CharSequence> {
    override fun validate(any: CharSequence?): Boolean =
        !any.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(any).matches()
}

val EMAIL_VALIDATOR: Validator<CharSequence> = EmailValidator()

/**
 * Validate password
 */
private class PasswordValidator : Validator<CharSequence> {
    override fun validate(any: CharSequence?): Boolean = !any.isNullOrBlank() && any.length >= 4
}

val PASSWORD_VALIDATOR: Validator<CharSequence> = PasswordValidator()

/**
 * Validate user name
 */
private class UsernameValidator : Validator<CharSequence> {
    override fun validate(any: CharSequence?): Boolean = !any.isNullOrBlank() && any.length >= 4
}

val USERNAME_VALIDATOR: Validator<CharSequence> = UsernameValidator()

