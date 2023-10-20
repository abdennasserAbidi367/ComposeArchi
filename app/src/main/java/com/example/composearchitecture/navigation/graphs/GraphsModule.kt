package com.example.composearchitecture.navigation.graphs

import com.example.composearchitecture.navigation.base.NavigationGraph
import com.example.composearchitecture.navigation.destinations.home.graph.HomeGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GraphsModule {

    @Binds
    @IntoMap
    @ClassKey(HomeGraph::class)
    abstract fun homeGraph(graph: HomeGraph): NavigationGraph

    /*@Binds
    @IntoMap
    @ClassKey(DialogsGraph::class)
    abstract fun dialogsGraph(graph: DialogsGraph): NavigationGraph*/

}