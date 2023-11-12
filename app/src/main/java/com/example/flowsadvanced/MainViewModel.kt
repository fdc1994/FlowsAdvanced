package com.example.flowsadvanced

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countdownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)

        while(currentValue > 0 ){
            delay(100L)
            currentValue--
            emit(currentValue)
        }
    }

    private val simpleFirstIntFlow = flow<Int> {
        emit(1)
        delay(500)
        emit(2)
    }

    private val exampleFlow = flow {
        delay(250L)
        emit("Appetizer")
        delay(1000L)
        emit("Main Dish")
        delay(100L)
        emit("Dessert")
    }
    init {
//        collectFlow()
//        collectLatestFlow()
//        collectFlowWithFilter()
//        collectFlowWithMap()
//        collectFlowWithOnEach()
//        countFlow()
//        reduceFlow()
//        foldFlow()
//        flatMapConcatFlow()
//        flatMapMergeFlow()
//        flatMapLatest()
        collectFlowExample()
    }
    private fun collectFlow() {
        /**
         * Collect will always get all the states regardless of whether the flow has already emitted
         * and started a new emission.
         */
        viewModelScope.launch {
            countdownFlow.collect {time ->
                delay(1200)
                println("Collect : The current time is $time")

            }
        }
    }

    private fun collectLatestFlow() {
        /**
         * Collect Latest will only collect current emissions.
         *
         * In this case, since we are emitting a new value every second, but delaying this collection by 1.5
         * seconds, only the last value will be printed since no new value will be emitted after it
         * and we can collect it after 1.5 seconds
         */
        viewModelScope.launch {
            countdownFlow.collectLatest {time ->
                delay(1500)
                println("Collect latest : The current time is $time")

            }
        }
    }

    private fun collectFlowWithFilter() {
        viewModelScope.launch {
            /**
             * Collect with Filter operator
             */
            countdownFlow
                .filter {time ->
                    time % 2 == 0
                }
                .collect {time ->
                println("Collect With Filter : The current time is $time")

            }
        }
    }

    private fun collectFlowWithMap() {
        viewModelScope.launch {
            /**
             * Collect with Map operator
             */
            countdownFlow
                .map {time ->
                    time * time
                }
                .collect {time ->
                    println("Collect With Map : The current time is $time")

                }
        }
    }

    private fun collectFlowWithOnEach() {
        viewModelScope.launch {
            /**
             * Collect with OnEach
             */
            countdownFlow
                .onEach {time ->
                    println("On Each print : $time")
                }
                .collect {time ->
                    println("Collect With OnEach : The current time is $time")
                }
        }
    }

    private fun countFlow() {
        /**
         * Count operator
         */
        viewModelScope.launch {
            val count = countdownFlow
                .filter {
                    it % 2 == 0
                }
                .map {
                    it * it
                }
                .onEach {
                    println(it.toString())
                }
                .count {
                    it % 2 == 0
                }
            println("The Count is $count")
        }
    }

    private fun reduceFlow() {
        /**
         * Reduce operator
         */
        viewModelScope.launch {
            val reduceResult = countdownFlow
                .reduce { accumulator, value ->
                    accumulator + value
                }
            println("The accumulated value is $reduceResult")

        }
    }

    private fun foldFlow() {
        /**
         * Fold operator
         */
        viewModelScope.launch {
            val foldResult = countdownFlow
                .fold(initial = 100) { accumulator, value ->
                    accumulator + value
                }
            println("The accumulated value is $foldResult")

        }
    }

    private fun flatMapConcatFlow () {
        /**
         * FlatMap Concat
         */
        viewModelScope.launch {
            simpleFirstIntFlow.flatMapConcat {value ->
                flow {
                    emit(value+1)
                    delay(100L)
                    emit(value +2)
                }
            }.collect {
                println("The Value is $it")
            }
        }
    }

    private fun flatMapMergeFlow () {
        /**
         * FlatMap Merge
         */
        viewModelScope.launch {
            simpleFirstIntFlow.flatMapMerge {value ->
                flow {
                    emit(value+1)
                    delay(1500)
                    emit(value +2)
                }
            }.collect {
                println("The Value is $it")
            }
        }
    }


    private fun flatMapLatest () {
        /**
         * FlatMap Latest
         *
         * Will only emmit the latest value
         */
        viewModelScope.launch {
            simpleFirstIntFlow.flatMapLatest {value ->
                flow {
                    emit(value+1)
                    delay(1500)
                    emit(value +2)
                }
            }.collect {
                println("The Value is $it")
            }
        }
    }

    private fun collectFlowExample () {
        viewModelScope.launch {
            exampleFlow.onEach {
                println("FLOW: $it is delivered")
            }
                .collect{
                println("FLOW: Now eating $it")
                delay(1500L)
                println("FLOW: Finished eating $it")
            }
        }
    }


}