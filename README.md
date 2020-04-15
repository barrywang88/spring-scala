**1, 什么是CQRS**

CQRS最早来自于Betrand Meyer（Eiffel语言之父，开-闭原则OCP提出者）在 Object-Oriented Software Construction 这本书中提到的一种 命令查询分离 (Command Query Separation,CQS) 的概念。其基本思想在于，任何一个对象的方法可以分为两大类：

命令(Command):不返回任何结果(void)，但会改变对象的状态。
查询(Query):返回结果，但是不会改变对象的状态，对系统没有副作用。
根据CQS的思想，任何一个方法都可以拆分为命令和查询两部分。比如：

private int i = 0;
private int Increase(int value) {
i += value;
return i;
}
这个方法，我们执行了一个命令即对变量i进行相加，同时又执行了一个Query，即查询返回了i的值，如果按照CQS的思想，该方法可以拆成Command和Query两个方法，如下：

private void increaseCommand(int value) {
i += value;
}
private int queryValue() {
return i;
}
操作和查询分离使得我们能够更好的把握对象的细节，能够更好的理解哪些操作会改变系统的状态。当然CQS也有一些缺点，比如代码需要处理多线程的情况。

CQRS是对CQS模式的进一步改进成的一种简单模式。 它由Greg Young在CQRS, Task Based UIs, Event Sourcing agh! 这篇文章中提出。“CQRS只是简单的将之前只需要创建一个对象拆分成了两个对象，这种分离是基于方法是执行命令还是执行查询这一原则来定的(这个和CQS的定义一致)”。
CQRS使用分离的接口将数据查询操作(Queries)和数据修改操作(Commands)分离开来，这也意味着在查询和更新过程中使用的数据模型也是不一样的。这样读和写逻辑就隔离开来了。



使用CQRS分离了读写职责之后，可以对数据进行读写分离操作来改进性能，可扩展性和安全。

**2, 为什么要引入CQRS**

CQRS模式有一些优点：

分工明确，可以负责不同的部分
将业务上的命令和查询的职责分离能够提高系统的性能、可扩展性和安全性。并且在系统的演化中能够保持高度的灵活性，能够防止出现CRUD模式中，对查询或者修改中的某一方进行改动，导致另一方出现问题的情况。
逻辑清晰，能够看到系统中的那些行为或者操作导致了系统的状态变化。
可以从数据驱动(Data-Driven) 转到任务驱动(Task-Driven)以及事件驱动(Event-Driven).
在下场景中，可以考虑使用CQRS模式：

当在业务逻辑层有很多操作需要相同的实体或者对象进行操作的时候。CQRS使得我们可以对读和写定义不同的实体和方法，从而可以减少或者避免对某一方面的更改造成冲突
对于一些基于任务的用户交互系统，通常这类系统会引导用户通过一系列复杂的步骤和操作，通常会需要一些复杂的领域模型，并且整个团队已经熟悉领域驱动设计技术。写模型有很多和业务逻辑相关的命令操作的堆，输入验证，业务逻辑验证来保证数据的一致性。读模型没有业务逻辑以及验证堆，仅仅是返回DTO对象为视图模型提供数据。读模型最终和写模型相一致。
适用于一些需要对查询性能和写入性能分开进行优化的系统，尤其是读/写比非常高的系统，横向扩展是必须的。比如，在很多系统中读操作的请求时远大于写操作。为适应这种场景，可以考虑将写模型抽离出来单独扩展，而将写模型运行在一个或者少数几个实例上。少量的写模型实例能够减少合并冲突发生的情况
适用于一些团队中，一些有经验的开发者可以关注复杂的领域模型，这些用到写操作，而另一些经验较少的开发者可以关注用户界面上的读模型。
对于系统在将来会随着时间不段演化，有可能会包含不同版本的模型，或者业务规则经常变化的系统
需要和其他系统整合，特别是需要和事件溯源Event Sourcing进行整合的系统，这样子系统的临时异常不会影响整个系统的其他部分。

**3, 系统如何实现**

CQRS采用Spring MVC+Scala+Scala sql是实现。

1) Spring MVC及封装的Spring Boot作为一个Javaer这里就不再赘述。

2) 为什么要用Scala语言开发：

首先，Scala也是基于JVM的语言，可以直接调用Java API及强大三方库；
其次，Scala = OOP + FP；Java 8以前只有OOP。Scala 是面向对象及面向函数编程都支持都很好的语言；
最后，Scala学好后，编程效率要比Java高很多；
另外，Scala很多特性Java一直在模仿但是重来没超越。比如，Lambda，流式处理，模式匹配，隐私转换，天然的线程安全，可以作为脚本语言REPL，macro支持，强大的类型系统，大名鼎鼎的Akka框架等...。Spark,kafka等优秀开源项目都是Scala写的。
注意，业务开发简单Scala学习就够了。如果深入学习Scala学习成本要比Java高

3) 为什么用Scala sql代替MyBatis:

Scala-sql 是一个轻量级的 JDBC 库，提供了在scala中访问关系型数据库的一个简单的API，其定位是对面 scala开发者，提供一个可以替换 spring-jdbc, MyBatis 的数据访问库。相比 spring-jdbc, MyBatis, Hibernate 等库，scala-sql有一些自己独特的特点：
1. SQL语句与对象直接映射。函数式支持。scala-sql支持从ResultSet到Object的映射，在1.0版本中，是映射到JavaBean，而在2.0中，则是映射到Case Class。选择 Case Class的原因也是为了更好的支持函数式编程。（支持Case Class与函数式编程有什么关系？函数式编程的精髓就是无副作用的值变换， immutable才是函数式编程的真爱）
2. 编译期间的SQL语法检查。 这个特性可以让开发变得更加便捷一些，如果有SQL语法错误，或者存在错误拼写的字段名、表名等情况，在编译时期就能 发现错误，可以更快的暴露错误，缩短测试周期。
3. 零学习成本。只要你会写Sql脚本，了解了Scala基本语法。你就可以直接使用scala-sql对Mysql做增删改查。
4. 面向scala语言。因此，如果选择 java 或者其他编程语言，scala-sql基本上没有意义。而spring-jdbc, iBatis等显然不受这个限制。
5. 概念简单。 scala-sql 为 java.sql.Connection, javax.sql.DataSource 等对象扩展了：executeUpdate、rows、foreach等方法， 但其语义完全与 jdbc 中的概念是一致的。 熟悉jdbc的程序员，并且熟悉scala语法的程序员，scala-sql基本上没有新的概念需要学习。
6. 强类型。 scala-sql目前是2.0版本，使用了sql"sql-statement" 的插值语法来替代了Jdbc中复杂的 setParameter 操作，并且是强类型和 可扩展的。
强类型：如果你试图传入 URL、FILE 等对象时，scala编译器会检测错误，提示无效的参数类型。
可扩展：不同于JDBC，只能使用固定的一些类型，scala-sql通过Bound Context:JdbcValueAccessor来扩展，也就是说，如果你定义了 对应的扩展，URL、FILE也是可以作为传给JDBC的参数的。（当然，需要在JdbcValueAccessor中正确的进行处理）。 

4) 代码结构