package org.apache.ibatis.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description User
 * @Author Sean
 * @Date 2021/11/15 11:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class User {
  private String name;
  private String age;
  private String sex;
}
