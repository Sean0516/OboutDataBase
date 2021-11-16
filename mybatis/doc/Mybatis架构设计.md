### Mybatis 架构设计

![image-20211103165641735](https://gitee.com/Sean0516/image/raw/master/img/image-20211103165641735.png)



### mybatis  操作数据库流程

1. 获取配置文件数据流
2. 通过SqlSessionFactoryBuilder 的 build 方法创建一个SqlSessionFactory 实例。 
3. 调用openSession 方法创建SqlSession 实例
4. 通过SqlSession 实例获取Mapper 代理对象 （动态代理对象）
5. 执行Mapper 方法 ，获取执行结果

