package org.pf.school.repository.accounts;

import java.util.Date;
import java.util.List;

import org.pf.school.model.Member;
import org.pf.school.model.accounts.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenditureRepo extends JpaRepository<Expenditure, Long>{

	@Query("select e from Expenditure e order by e.transactionDate desc limit 50")
	public List<Expenditure> listExpenditureRecent();
	
	@Query("select e from Expenditure e "
			+ "WHERE "
			+ "e.transactionDate >=:startDate "
			+ "AND "
			+ "e.transactionDate <=:endDate "
			+ "order by e.transactionDate asc")
	public List<Expenditure> listExpenditure(Date startDate, Date endDate);
	
	public List<Expenditure> findByMemberOrderByTransactionDateDesc(Member member);
	
}

