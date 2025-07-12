package org.pf.school.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarouselRepo extends JpaRepository<Carousel, UUID>{
	
	@Query("select cs from Carousel cs where cs.pubEndDate >=:today order by recordAddDate desc")
	public List<Carousel> listCarouselForPublication(Date today);
}
