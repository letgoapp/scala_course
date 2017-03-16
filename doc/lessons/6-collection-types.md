# Scala Collections

## Introduction
Scala collections are a hierarchy of classes. All these classes are divided in three packages:

* scala.collection (generic collections)
* scala.collection.immutable
* scala.collection.mutable

You have to try to use the classes of the highest level possible. Then your program remains more agnostic about detail implementations.

To deal with concurrency is recommendable always user immutable collections.In fact, Scala instantiate an immutable version of the connection requested by default.

## Exercise

* Add a test case for the `com.letgo.scala_course.domain.service.MessageAnalyticsService.countMessagesOfUser` function. You have the test boilerplate already prepared in the `com.letgo.scala_course.MessageAnalyticsServiceTest` class.
* Implement the `com.letgo.scala_course.domain.service.MessageAnalyticsService.groupByUserName` method.

## How to instantiate a collection

```scala
val mySeq = Seq(1, 2, 3)

val mySet = Set(1, 2, 3)

val myMap = Map("pata" -> "negra")

val myMap2 = Map("pata" -> Seq("negra", "negra"))
```

## Java <-> Scala Collections

```scala
import collection.JavaConverters._

val myScalaCollection = Seq(1, 2)

myScalaCollection.asJava
```

## Tips and tricks

### Seqs
[Working with Seqs.](http://docs.scala-lang.org/overviews/collections/seqs.html)

### Sets
[Working with Sets.](http://docs.scala-lang.org/overviews/collections/sets.html)

### Maps
[Working with Maps](http://docs.scala-lang.org/overviews/collections/sets.html)
