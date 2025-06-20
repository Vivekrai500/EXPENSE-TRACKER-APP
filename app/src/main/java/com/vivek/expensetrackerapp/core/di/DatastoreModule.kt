
package com.vivek.expensetrackerapp.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.vivek.expensetrackerapp.core.datastore.preferences.UserPreferences
import com.vivek.expensetrackerapp.core.util.Constants
import com.vivek.expensetrackerapp.data.UserPreferenceRepositoryImpl
import com.vivek.expensetrackerapp.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context):
            DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(name = Constants.USER_PREFERENCES)
            }
        )
    }




    @Provides
    @Singleton
    fun provideUserPreferencesRepository(userPreferences: UserPreferences):
            UserPreferencesRepository {
        return UserPreferenceRepositoryImpl(
            preferences = userPreferences
        )
    }
}