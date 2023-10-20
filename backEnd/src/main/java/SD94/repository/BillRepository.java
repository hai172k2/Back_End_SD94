
package SD94.repository;

import SD94.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}

//package SD94.repository;
//
//import SD94.entity.Staff;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface UserRepository extends JpaRepository<Staff, Long> {
//    Staff finByUsername( String username);
//}

