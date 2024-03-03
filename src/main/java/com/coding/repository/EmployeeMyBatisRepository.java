//package com.coding.repository;
//
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface EmployeeMyBatisRepository {
//    @Select("select * from employees")
//    public List<Employees> findAll();
//
//
//    @Select("SELECT first_name as firstName, last_name as lastName, email_address as emailId FROM employees WHERE id = #{id}")
//    public Employees findById(long id);
//
//    @Delete("DELETE FROM employees WHERE id = #{id}")
//    public int deleteById(long id);
//
//    @Insert("INSERT INTO employees(id, first_name, last_name,email_address) " +
//            " VALUES (#{id}, #{firstName}, #{lastName}, #{emailId})")
//    public int insert(Employees employees);
//
//    @Update("Update employees set first_name=#{firstName}, " +
//            " last_name=#{lastName}, email_address=#{emailId} where id=#{id}")
//    public int update(Employees employees);
//}
