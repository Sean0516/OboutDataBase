MyBatis中的SqlSession实例使用工厂模式创建，所以在创建SqlSession实例之前需要先创建SqlSessionFactory工厂对象，然后调用SqlSessionFactory对象的openSession()方法

```java
final String resource = "org/apache/ibatis/builder/MapperConfig.xml";
final Reader reader = Resources.getResourceAsReader(resource);
SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
```

### 创建sqlsession 的代码

```java
private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
  Transaction tx = null;
  try {
    // 获取mybatis 主配置文件的环境变量
    final Environment environment = configuration.getEnvironment();
    // 创建事务管理器工厂
    final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
    // 创建事务管理器
    tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
    // 根据mybatis 主配置文件中指定的 executor 类型创建对应的 executor 实例
    final Executor executor = configuration.newExecutor(tx, execType);
    // 创建DefaultSqlSession 实例
    return new DefaultSqlSession(configuration, executor, autoCommit);
  } catch (Exception e) {
    closeTransaction(tx); // may have fetched a connection so lets call close()
    throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
  } finally {
    ErrorContext.instance().reset();
  }
}
```

