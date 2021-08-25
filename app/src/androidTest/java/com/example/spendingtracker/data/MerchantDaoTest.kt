package com.example.spendingtracker.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.spendingtracker.data.entities.Merchant
import com.example.spendingtracker.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
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
    fun canInsertAMerchant() = runBlockingTest {
        val merchant = Merchant(merchantName = "John Lewis")
        appDatabase.merchantDao().insertMerchant(merchant)

        val retrievedMerchants = appDatabase.merchantDao().getAllMerchants().getOrAwaitValue()

        assertThat(retrievedMerchants, `is`(notNullValue()))
        assertThat(retrievedMerchants.size, `is`(1))
        assertThat(retrievedMerchants, hasItem(merchant))
    }

    @Test
    fun canRetrieveAMerchantById() = runBlockingTest {
        val merchant1 = Merchant(merchantName = "Waitrose")
        appDatabase.merchantDao().insertMerchant(merchant1)

        val merchant2 = Merchant(merchantName = "Amazon")
        appDatabase.merchantDao().insertMerchant(merchant2)

        val retrievedMerchant =
            appDatabase.merchantDao().getMerchantById(merchant1.id).getOrAwaitValue()

        assertThat(retrievedMerchant, `is`(notNullValue()))
        assertThat(retrievedMerchant.merchantName, `is`(merchant1.merchantName))
    }

    @Test
    fun canInsertMultipleMerchants() = runBlockingTest {
        val merchant1 = Merchant(merchantName = "Bed, Bath and Beyond")
        val merchant2 = Merchant(merchantName = "Target")
        val merchant3 = Merchant(merchantName = "Pottery Barn")
        appDatabase.merchantDao().insertAllMerchants(merchant1, merchant2, merchant3)

        val retrievedMerchants = appDatabase.merchantDao().getAllMerchants().getOrAwaitValue()

        assertThat(retrievedMerchants, `is`(notNullValue()))
        assertThat(retrievedMerchants.size, `is`(3))
        assertThat(retrievedMerchants, hasItem(merchant1))
        assertThat(retrievedMerchants, hasItem(merchant2))
        assertThat(retrievedMerchants, hasItem(merchant3))
    }

    @Test
    fun canUpdateAMerchant() = runBlockingTest {
        val merchant = Merchant(merchantName = "Waterstones")
        appDatabase.merchantDao().insertMerchant(merchant)

        val updatedMerchant = Merchant(merchantName = "Borders", id = merchant.id)
        appDatabase.merchantDao().updateMerchant(updatedMerchant)

        val retrievedMerchant =
            appDatabase.merchantDao().getMerchantById(merchant.id).getOrAwaitValue()

        assertThat(retrievedMerchant, `is`(notNullValue()))
        assertThat(retrievedMerchant.merchantName, `is`(updatedMerchant.merchantName))
        assertThat(retrievedMerchant.merchantName, `is`(not(merchant.merchantName)))
    }

    @Test
    fun canDeleteAMerchant() = runBlockingTest {
        val merchant1 = Merchant(merchantName = "Greggs")
        val merchant2 = Merchant(merchantName = "Tesco")
        appDatabase.merchantDao().insertAllMerchants(merchant1, merchant2)

        appDatabase.merchantDao().deleteMerchant(merchant1)

        val retrievedMerchants = appDatabase.merchantDao().getAllMerchants().getOrAwaitValue()

        assertThat(retrievedMerchants, `is`(notNullValue()))
        assertThat(retrievedMerchants.size, `is`(1))
        assertThat(retrievedMerchants, hasItem(merchant2))
        assertThat(retrievedMerchants, not(hasItem(merchant1)))
    }

    @Test
    fun canDeleteAllMerchants() = runBlockingTest {
        val merchant1 = Merchant(merchantName = "Harrods")
        val merchant2 = Merchant(merchantName = "Selfridges")
        appDatabase.merchantDao().insertAllMerchants(merchant1, merchant2)

        appDatabase.merchantDao().deleteAllMerchants()

        val retrievedMerchants = appDatabase.merchantDao().getAllMerchants().getOrAwaitValue()

        assertThat(retrievedMerchants, `is`(emptyList()))
        assertThat(retrievedMerchants, not(hasItem(merchant1)))
        assertThat(retrievedMerchants, not(hasItem(merchant2)))
    }

}