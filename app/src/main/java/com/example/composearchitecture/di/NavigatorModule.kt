package com.example.composearchitecture.di

import com.example.composearchitecture.navigation.base.Navigator
import com.example.composearchitecture.navigation.base.NavigatorDirections
import com.example.composearchitecture.navigation.base.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jetbrains.annotations.NotNull

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigatorModule {

    @NotNull
    @Binds
    abstract fun bindNavigator(navigator: NavigatorImpl): Navigator

    @Binds
    @NotNull
    abstract fun bindNavigatorDestination(navigatorImpl: NavigatorImpl): NavigatorDirections
}