package com.ashu.filepdf.filemytax.repository;

import com.ashu.filepdf.filemytax.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

}
