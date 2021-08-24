package com.example.spendingtracker.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spendingtracker.data.entities.Merchant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MerchantDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = appDatabase.close()

    @Test
    fun canInsertAndRetrieveMerchants() = runBlockingTest {
        val merchant = Merchant(merchantName = "John Lewis")
        appDatabase.merchantDao().insertMerchant(merchant)

        val retrievedMerchants = appDatabase.merchantDao().getAllMerchants()

        assertThat(retrievedMerchants, `is`(notNullValue()))
    }


}