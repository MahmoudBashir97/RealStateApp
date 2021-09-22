package com.mahmoudbashir.androidunittestingapplying.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mahmoudbashir.androidunittestingapplying.getOrAwaitValue
import com.mahmoudbashir.androidunittestingapplying.local.data.ShoppingDao
import com.mahmoudbashir.androidunittestingapplying.local.data.ShoppingItem
import com.mahmoudbashir.androidunittestingapplying.local.data.ShoppingItemDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest //to tell that is an Unit Test
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var database:ShoppingItemDb
    private lateinit var dao:ShoppingDao

    @Before
    fun setUp(){
        // this is not a real database
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ShoppingItemDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }


    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val item = ShoppingItem("Iphone",1,1f,"url",id = 1)

        dao.insertShoppingItem(item)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val item = ShoppingItem("Iphone",1,1f,"url",id = 1)

        dao.insertShoppingItem(item)
        dao.deleteShoppingItem(item)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(item)
    }

    @Test
    fun observeTotalPriceSum()= runBlockingTest {
        val item1 = ShoppingItem("Iphone",2,1f,"url",id = 1)
        val item2 = ShoppingItem("nokia",5,5.5f,"url",id = 2)
        val item3 = ShoppingItem("Xiaomi",0,20f,"url",id = 3)

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val totalSumPrice = dao.obsereveTotalPrice().getOrAwaitValue()

        assertThat(totalSumPrice).isEqualTo(2 * 1f + 5 * 5.5f )
    }
}