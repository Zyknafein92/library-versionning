package com.library.borrowmicroservice.repository;

import com.library.borrowmicroservice.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Long> {

    @Query("select b from Borrow b where current_date > b.dateEnd and b.isExtend = FALSE or b.isExtend = TRUE and current_date > b.dateExtend")
    List<Borrow> findAllBorrowOutDated();

    List<Borrow> findAllByUserID(String userID);

}
