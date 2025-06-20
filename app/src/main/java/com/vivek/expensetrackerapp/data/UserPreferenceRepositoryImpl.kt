
package com.vivek.expensetrackerapp.data

import androidx.datastore.preferences.core.edit
import com.vivek.expensetrackerapp.core.datastore.preferences.UserPreferences
import com.vivek.expensetrackerapp.core.util.Constants
import com.vivek.expensetrackerapp.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val preferences: UserPreferences
):UserPreferencesRepository {


    override suspend fun setTheme(themeValue: String) {
        preferences.setTheme(themeValue = themeValue)
    }

    override fun getTheme(): Flow<String> {
        return preferences.getTheme()
    }

    override suspend fun setShouldShowOnboarding(){
        preferences.setShouldShowOnboarding()
    }

    override fun getShouldShowOnBoarding(): Flow<Boolean> {
        return preferences.getShouldShowOnBoarding()
    }
}