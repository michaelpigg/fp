!SLIDE

#Functional Programming in Scala#

Michael Pigg
Chariot Day 2013

!SLIDE left

# Outline #

Define Functional Programming

Scala Function Definitions

Explore FP with Scala Collections

More General FP

!SLIDE bullets left incremental

# Functional Programming #

* First class functions
  * functions can be used anywhere

* Higher order functions
  * functions that take or return functions

!SLIDE bullets left

# Pure Functions #
.notes Altering memory includes reassigning a variable, modifying a data structure in place, setting a field on an object

* Lack of side effects
    * Does not alter memory or perform I/O
* Input obtained via arguments to function
* Output is given through return value

!SLIDE bullets left

# Immutable Data #

* Pure functions can not modify data in place
* Use of immutable data structures
* Improves function in concurrent scenarios

!SLIDE bullets left
# Modularity #

* Modularity is delivered at the function level
  * functions are reusable and composable

!SLIDE bullets left incremental

# Scala and FP #

* Scala is a hybrid language that supports
  * functional paradigm
  * object-oriented paradigm

* Functions are first-class in Scala
  * Actually implemented as objects

* Immutable data structures by default

!SLIDE
# Writing Functions as Methods #

    @@@ scala
    def addOne(x: Int) = x + 1

!SLIDE
# Verbose Function Literals #

    @@@ scala
    val addOne = (x: Int) => x + 1

Defines a function Int => Int

Assigns it to the variable addOne

!SLIDE
# Less Verbose Function Literals #

    @@@ scala
    x => x + 1

This form is used with higher-order functions where the compiler can infer the type

The variable x represents is bound to the single input argument

!SLIDE
# Terse Function Literals #

    @@@ scala
    _ + 1

This form is also used with higher-order functions

The underscore symbol is bound to an input argument

!SLIDE bullets

# Methods vs Functions #

* Methods are defined as part of a class

* Functions are a complete object
    * Function0, Function1, etc.

!SLIDE code

# Function chaining #

    @@@ scala
    val multTwo: (Int => Int) = x => x * 2

    val chain = addOne.andThen(multTwo)

    chain(1)
    4

!SLIDE
# Scala Collections #

Scala has a rich collections library

The collections make extensive use of higher-order functions

We will work with the Vector collection to start

!SLIDE bullets
# map Function #

* The map function
    * takes a function as an argument
    * applies the function to each element of the collection
    * produces a collection containing the results

!SLIDE commandline incremental
# map example #

    $ val numbers = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    $ numbers.map(x => x + 1)
    Vector(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

!SLIDE commandline incremental
# terse map #

    $ numbers.map(_ + 1)
    Vector(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

!SLIDE  commandline incremental
# map with a function #

    $ val addOne = (x: Int) => x + 1

    $ numbers.map(addOne)
    Vector(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

!SLIDE

oh, the power

!SLIDE

    @@@ java
    List<Int> numbers2 = new ArrayList();
    for (number : numbers) {
        numbers2.add(number + 1);
    }

!SLIDE bullets incremental

# What's Different? #

.notes This is the difference between internal and external iteration. Java collections = external, Scala = internal

* We didn't
    * Explicitly code a loop
    * Tell the system how to iterate over the collection

* We did
    * Say what should be done to each element

!SLIDE bullets incremental

# So What? #

* Less boilerplate coding

* Flexibility
    * Easier to change underlying collection
    * Easier to go parallel

!SLIDE bullets

# Predicates #

* Many collections functions take a predicate
    * p:A => Boolean
    * element p does/does not pass a test

!SLIDE bullets incremental

# Predicate Functions #

dropWhile - drop elements until predicate is not satisfied

find - find first element that satisfies predicate

filter - return all elements that satisfy predicate

forAll - returns true if predicate holds for all elements

exists - return true if at least one element satisfies predicate

!SLIDE commandline incremental

# find #

.notes find returns an Option, which is Some if find returns something, None otherwise

    $ numbers.find(a => a > 3)
    Option[Int] = Some(4)

    $ numbers.find(a => a % 2 == 0)
    Option[Int] = Some(2)

!SLIDE commandline incremental

# even predicate #

    $ val even: (Int => Boolean) = a => a % 2 == 0
    even: Int => Boolean = <function1>

    $ even(3) 
    false
    $ even(4)
    true

!SLIDE commandline incremental

# even predicate with functions #

.notes We can pass a function reference instead of a function literal to a HOF

    $ numbers.find(even)
    Some(2)

    $ numbers.filter(even)
    Vector(2, 4, 6, 8, 10)

!SLIDE commandline incremental

# what about odd? #

.notes We could define a method that calls our even function and negates the result

    $ val odd: Int => Boolean = a => !(even(a))
    odd: Int => Boolean = <function1>

    $ odd(2)
    false
    $ odd(3)
    true

    $ numbers.filter(odd)
    Vector(1, 3, 5, 7, 9)

!SLIDE

# groupBy #

takes a function that returns a value for each element in a collection

returns a map that has the values returned as keys and a collection of elements mapping to that key for the value

!SLIDE commandline incremental

# groupBy example #

    $ numbers.groupBy(even)
    Map(false -> Vector(1, 3, 5, 7, 9), 
     true -> Vector(2, 4, 6, 8, 10))

    $ numbers.groupBy(a => a % 2)
    Map(1 -> Vector(1, 3, 5, 7, 9), 0 -> Vector(2, 4, 6, 8, 10))

!SLIDE commandline 

# groupBy more than two #

    $ Vector("apple", "apricot", "bannana", "grape", "orange" )
      .groupBy(_.charAt(0))
    Map(a -> Vector(apple, apricot), o -> Vector(orange),
      b -> Vector(bannana), g -> Vector(grape))

!SLIDE commandline incremental

# Parallel Collection #

    $ val numbers = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).par
    $ numbers.map(addOne)
    Vector(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

!SLIDE

# Exploring parallel collections #

    @@@ scala
    val fib: (Int => Long) = a => {
        a match {
            case 0 => 0
            case 1 => 1
            case _ => fib(a-2) + fib(a-1)
        }
    }

!SLIDE

# Timing a function #

    @@@ scala
    def now = System.currentTimeMillis

    def elapsed(f: => Unit): Long = {
      val start = now
      f
      now - start
    }

!SLIDE commandline incremental

# Gather some data #

    $ elapsed(fib(5))
    0
    $ elapsed(fib(30))
    10
    $ def tfx(x: Int): Long = elapsed(fib(x))
    tfx: (x: Int)Long
    $ tfx(40)
    1223
    $ tfx(42)
    3190
    $ tfx(44)
    8383

!SLIDE commandline incremental

# Fake work queue #

    $ elapsed(Vector(44,44,44,44).map(fib))
    44165
    $ elapsed(Vector(44,44,44,44).par.map(fib))
    21463

Timings from 2.4 Ghz Core 2 Duo and Scala 2.9.1 (2.10.0 is similar)

!SLIDE

# References #

Functional Programming in Scala, Chiusano and Bjarnason, Manning

State of the Lambda: Libraries Edition, Goetz, http://cr.openjdk.java.net/~briangoetz/lambda/sotc3.html
