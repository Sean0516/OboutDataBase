package org.apache.ibatis.sean;

import org.apache.ibatis.common.User;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;

/**
 * @Description DemoTest
 * @Author Sean
 * @Date 2021/11/10 18:36
 * @Version 1.0
 */
public class MybatisUtilsTest {
  @Test
  public void testSql(){
    String s = new SQL().SELECT("*").FROM("T_USER").WHERE("ID = #{id}").toString();
    System.out.println(s);
  }
  @Test
  public void metaObjectTest(){
    MetaObject metaObject = SystemMetaObject.forObject(new User("sean", "21", "男"));

    String[] getterNames = metaObject.getGetterNames();
    for (String getterName : getterNames) {
      System.out.println("getterName = " + getterName);
    }

  }

}
