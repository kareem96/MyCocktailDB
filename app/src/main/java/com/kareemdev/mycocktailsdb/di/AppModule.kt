package com.kareemdev.mycocktailsdb.di

import android.content.Context
import androidx.room.Room
import com.kareemdev.mycocktailsdb.data.local.CocktailDao
import com.kareemdev.mycocktailsdb.data.local.CocktailDatabase
import com.kareemdev.mycocktailsdb.data.remote.Api
import com.kareemdev.mycocktailsdb.data.repositories.IRepository
import com.kareemdev.mycocktailsdb.data.repositories.Repository
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.BASE_URL
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.DATABASE_NAME
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCocktailDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CocktailDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCocktailDao(
        database: CocktailDatabase
    ) = database.getCocktailDao()

    @Singleton
    @Provides
    fun provideCocktailApi(): Api {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideDefaultCocktailRepository(
        api: Api,
        dao: CocktailDao
    ): IRepository = Repository(dao, api)


}