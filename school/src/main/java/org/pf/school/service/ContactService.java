package org.pf.school.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Contact;
import org.pf.school.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepo contactRepo;	

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Contact> oe = this.contactRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addContact(Contact contact, String updateBy) throws IOException {
		
		Contact obj = new Contact();
		
		obj.setName(contact.getName());
		obj.setPost(contact.getPost());
		obj.setPhone(contact.getPhone());
		obj.setEmail(contact.getEmail());
		
		if (!contact.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(contact.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(contact.getFile().getContentType());
			obj.setFileData(contact.getFile().getBytes());
		}
		
		obj.setAddDefaults(updateBy);
		
		obj = contactRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteContact(UUID id, String updateBy) {

		Optional<Contact> oe = this.contactRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Contact obj = oe.get();
		
		contactRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateContact(Contact contact, String updateBy) throws IOException  {
		
		Optional<Contact> oe = this.contactRepo.findById(contact.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Contact obj = oe.get();
		
		obj.setName(contact.getName());
		obj.setPost(contact.getPost());
		obj.setPhone(contact.getPhone());
		obj.setEmail(contact.getEmail());
		
		if (!contact.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(contact.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(contact.getFile().getContentType());
			obj.setFileData(contact.getFile().getBytes());
		}

		obj.setUpdateDefaults(updateBy);
		
		obj = contactRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}
}