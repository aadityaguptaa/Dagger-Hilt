package com.army.daggerhilttutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
        println(someClass.doSomeOtherThing())

    }
}

/**
 * This is a dependency. we inject this into the onCreate method using field injection
 * someOtherClass is a constructor injection
 */
class SomeClass
@Inject
constructor(
    private val someOtherClass: SomeOtherClass //constructor injection
){

    fun doAThing(): String{
        return "Look I did a thing!"
    }

    fun doSomeOtherThing(): String{
        return someOtherClass.doSomeOtherThing()
    }
}

class SomeOtherClass
@Inject
constructor(){

    fun doSomeOtherThing(): String{
        return "Look I did some other thing!"
    }
}