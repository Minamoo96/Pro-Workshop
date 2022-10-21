package com.fury.labs.professionalworkshop.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SliderPref(private val context: Context) {
    companion object{
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("SliderScroll")
        val SROLL_KEY = stringPreferencesKey("Scroll")
    }
    val getScrolled: Flow<String> = context.datastore.data
        .map { preference ->
            preference[SROLL_KEY]?: ""
        }
    suspend fun setScroll(scroll: String){
        context.datastore.edit{ preference ->
            preference[SROLL_KEY] = scroll
        }
    }
}