package com.mahmoudbashir.androidunittestingapplying

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{
    private lateinit var comparer:ResourceComparer

    @Before
    fun initialization(){
        comparer = ResourceComparer()
    }

    @After
    fun teatdown(){

    }

    @Test
    fun stringResourceSameAsGivenStringReturnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =  comparer.isEqual(context,R.string.app_name,"UnitTestingApp")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentFromGivenStringReturnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =  comparer.isEqual(context,R.string.app_name,"UnitTestingApp")

        assertThat(result).isFalse()
    }
}