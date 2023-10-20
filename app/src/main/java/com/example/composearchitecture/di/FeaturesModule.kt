package com.example.composearchitecture.di

import com.example.composearchitecture.feature.home.HomeScreenGraphEntry
import com.example.composearchitecture.feature.login.LoginScreenGraphEntry
import com.example.composearchitecture.navigation.base.NavigationGraphEntry
import com.example.composearchitecture.navigation.destinations.home.graph.HomeGraph
import com.example.composearchitecture.navigation.destinations.login.LoginGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
internal object FeaturesModule {

    @Provides
    @IntoMap
    @ClassKey(HomeGraph::class)
    fun homeEntries(homeScreenGraphEntry: HomeScreenGraphEntry): Set<NavigationGraphEntry> = setOf(homeScreenGraphEntry)

    @Provides
    @IntoMap
    @ClassKey(LoginGraph::class)
    fun loginEntries(loginScreenGraphEntry: LoginScreenGraphEntry): Set<NavigationGraphEntry> = setOf(loginScreenGraphEntry)


}