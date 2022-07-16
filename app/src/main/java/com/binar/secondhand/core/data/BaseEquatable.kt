package com.binar.secondhand.core.data

open class BaseEquatable(private val identifier: String) : Equatable {
    override val uniqueId: String
        get() = identifier

    override val longId: Long
        get() = 0L
}