package com.devhassan.dotpay

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UtilsUnitTest {

    @Test
    fun `assert that formatCurrency() returns correct currency format`() {
        assertEquals(
            "Returned format is incorrect",
            "72,988,930.00",
            Utils().formatCurrency(72988930)
        )
    }
}