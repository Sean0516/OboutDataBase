![image-20211116094032175](https://gitee.com/Sean0516/image/raw/master/img/image-20211116094032175.png)

### Configuration

用于描述mybatis  的主配置信息，其他组件需要获取配置信息时，直接通过configuration 对象获取，除此志位，mybatis  在应用启动时，将mapper 配置信息，类型别名，typeHandler 等注册到 configuration 组件中



### MappedStatement

MappedStatement 用于描述mapper 中的sql  配置信息，是对 mapper xml  配置文件中  <select delete update insert > 等标签或者 @Select 等注解配置信息的封装



### SqlSession 

Sql Session 是mybatis  提供的面向用户的API ，表示和数据库交互时的会话对象，用于完成数据库的增删改查功能， SqlSession 是Excecutor 组件的外观， 母的是对外提供易于理解和使用的数据库操作接口

### Excecutor 

Excecutor 是mybatis  的SQL 执行器。 mybatis 中对数据库所有的操作都是由Excecutor 组件完成的

mybatis 提供了三种不同的 Executor  

#### SimpleExecutor

最基础的Executor ,完成简单的增删改查方法

#### ResueExecutor （享元模式）

对jdbc 的statement 对象做缓存，当执行相同的SQL 语句时，直接从缓存中获取statement 对象进行复用，避免了频繁创建和销毁statement 对象。 从而提升系统性能

#### BatchExecutor

对调用同一个mapper 执行的 update  insert  delete 操作。 调用statement 对象的批量操作功能。

#### CachingExecutor  (装饰器模式)

当mybatis  开启二级缓存的时候，会使用 CachingExecutor 对 SimpleExecutor ResueExecutor BatchExecutor 进行装饰， 为查询操作增加二级缓存

### StatementHandler  

StatementHandler 封装了对JDBC statement 对象的操作。 比如对statement 对象设置参数，调用statement 接口提供方法与数据库交互。

SimpleStatementHandler继承至BaseStatementHandler，封装了对JDBCStatement对象的操作，PreparedStatementHandler封装了对JDBC PreparedStatement对象的操作，而CallableStatementHandler则封装了对JDBCCallableStatement对象的操作。RoutingStatementHandler会根据Mapper配置中的statementType属性（取值为STATEMENT、PREPARED或CALLABLE）创建对应的StatementHandler实现。

### ParameterHandler 

用于statement 对象参数占位符设置值

ParameterHandler的作用是在PreparedStatementHandler和CallableStatementHandler操作对应的Statement执行数据库交互之前为参数占位符设置值

MyBatis通过ParameterMapping描述参数映射的信息。在DefaultParameterHandler类的setParameters()方法中，首先获取Mapper配置中的参数映射，然后对所有参数映射信息进行遍历，接着根据参数名称获取对应的参数值，调用对应的TypeHandler对象的setParameter()方法为Statement对象中的参数占位符设置值

### ResultSetHandler 

ResultSetHandler 封装了对JDBC ResultSet 对象操作，当执行SQL 类型为select 语句时， ResultSetHandler 用于将查询结果转换为Java 对象



### TypeHandler 

TypeHandler 是mybatis  中的类型处理器 ，用于处理Java 类型与 jdbc 类型之间的映射。 他的主要作用体现在能根据Java 类型调用 preparestatement 或者 callable statement 对象对应的set 方法为statement 对象设置值， 而且能够根据 Java类型调用 result set 对象对应的get 方法获取sql  执行 结果

涉及Java类型和JDBC类型转换的两种情况如下：

1. PreparedStatement对象为参数占位符设置值时，需要调用PreparedStatement接口中提供的一系列的setXXX()方法，将Java类型转换为对应的JDBC类型并为参数占位符赋值
2. 执行SQL语句获取ResultSet对象后，需要调用ResultSet对象的getXXX()方法获取字段值，此时会将JDBC类型转换为Java类型



MyBatis通过TypeHandlerRegistry建立JDBC类型、Java类型与TypeHandler之间的映射关系







简单来说，SqlSession是Executor组件的外观，目的是为用户提供更友好的数据库操作接口，真正执行SQL操作的是Executor组件，Executor可以理解为SQL执行器，它会使用StatementHandler组件对JDBC的Statement对象进行操作。当Statement类型为CallableStatement和PreparedStatement时，会通过ParameterHandler组件为参数占位符赋值。ParameterHandler组件中会根据Java类型找到对应的TypeHandler对象，TypeHandler中会通过Statement对象提供的setXXX()方法（例如setString()方法）为Statement对象中的参数占位符设置值。StatementHandler组件使用JDBC中的Statement对象与数据库完成交互后，当SQL语句类型为SELECT时，MyBatis通过ResultSetHandler组件从Statement对象中获取ResultSet对象，然后将ResultSet对象转换为Java对象



