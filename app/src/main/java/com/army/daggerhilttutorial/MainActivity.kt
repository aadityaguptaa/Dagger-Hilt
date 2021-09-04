package com.army.daggerhilttutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
        println(someClass.doAnotherThing())


    }
}

/**
 * This is a dependency. we inject this into the onCreate method using field injection
 * someOtherClass is a constructor injection
 *
 * Singleton makes the dependency alive as long as the Application is alive
 */
@Singleton
class SomeClass
@Inject
constructor(
    @Impl1 private val someInterfaceImpl1: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface //constructor injection
){

    fun doAThing(): String{
        return someInterfaceImpl1.getAThing()
    }

    fun doAnotherThing(): String{
        return someInterfaceImpl2.getAThing()
    }

}

/**
 * We can't directly inject a dependency, if we don't own the code.
 * For example, in this case, we are injecting an interface, so hilt would think we don't own the code, so this won't work
 */
class SomeInterfaceImpl1
@Inject
constructor(): SomeInterface{

    override fun getAThing(): String {
        return "A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor(): SomeInterface{

    override fun getAThing(): String {
        return "A Thing2"
    }
}

interface  SomeInterface{

    fun getAThing(): String
}

@InstallIn(SingletonComponent::class)
@Module
abstract class MyModule{

    /*@Singleton
    @Binds
    abstract fun bindSomeDependency(
        someImpl: SomeInterfaceImpl
    ): SomeInterface*/

    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface{
        return SomeInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface{
        return SomeInterfaceImpl2()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2
