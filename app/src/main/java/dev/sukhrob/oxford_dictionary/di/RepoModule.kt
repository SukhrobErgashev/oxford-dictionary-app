package dev.sukhrob.oxford_dictionary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.oxford_dictionary.domen.repository.DicRepository
import dev.sukhrob.oxford_dictionary.domen.repository.DicRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun bindDicRepo(dicRepoImpl: DicRepositoryImpl): DicRepository

}