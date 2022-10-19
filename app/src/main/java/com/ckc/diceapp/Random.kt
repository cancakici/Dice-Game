package com.ckc.diceapp

class Random(var random : Int){

    fun randomGet():Int{
        return (1..random).random()
    }
}