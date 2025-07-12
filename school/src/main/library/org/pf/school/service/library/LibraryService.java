package org.pf.school.service.library;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.library.Library;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.library.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class LibraryService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private LibraryRepo libraryRepo;
	
	private Audit audit;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Library> oe = this.libraryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addLibrary(Library obj, String updateBy) {
		
		obj = libraryRepo.save(obj);
		
		audit = new Audit(updateBy, "Library", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteLibrary(UUID id, String updateBy) {

		Optional<Library> oe = this.libraryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Library obj = oe.get();
		
		audit = new Audit(updateBy, "Library", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		libraryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateLibrary(Library library, String updateBy) {
		
		Optional<Library> oe = this.libraryRepo.findById(library.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Library obj = oe.get();
		
		obj.setShortName(library.getShortName());
		obj.setName(library.getName());
		obj.setAddress(library.getAddress());
		obj.setCity(library.getCity());
		obj.setPin(library.getPin());
		obj.setState(library.getState());
		obj.setCountry(library.getCountry());
		obj.setMobile(library.getMobile());
		obj.setPhone(library.getPhone());
		obj.setEmail(library.getEmail());
		
		obj = libraryRepo.save(obj);
		
		audit = new Audit(updateBy, "Library", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
