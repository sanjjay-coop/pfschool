package org.pf.school.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Member;
import org.pf.school.model.SchoolMessage;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.SchoolMessageRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SchoolMessageService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SchoolMessageRepo messageRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private StaffRepo staffRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SchoolMessage> oe = this.messageRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMessage(SchoolMessage obj, String updateBy) {
		
		obj = messageRepo.save(obj);
		
		audit = new Audit(updateBy, "Message", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMessage(UUID id, String updateBy) {

		Optional<SchoolMessage> oe = this.messageRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SchoolMessage obj = oe.get();
		
		audit = new Audit(updateBy, "Message", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		messageRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMessage(SchoolMessage message, String updateBy) {
		
		Optional<SchoolMessage> oe = this.messageRepo.findById(message.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SchoolMessage obj = oe.get();
		
		
		obj = messageRepo.save(obj);
		
		audit = new Audit(updateBy, "Message", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult sendMessage(SchoolMessage message, String updateBy) {
		
		Member member = this.memberRepo.findByUidIgnoreCase(updateBy);
		
		List<SchoolMessage> messages = new ArrayList<SchoolMessage>();
		
		switch(message.getTargetAudience()) {
		
		case "Student":
			
			List<Student> listStudent = this.studentRepo.findByMember_Enabled(true);
			
			for (Student student : listStudent) {
				SchoolMessage sm = new SchoolMessage();
				sm.setSubject(message.getSubject());
				sm.setContent(message.getContent());
				sm.setMessageTo(student.getMember());
				sm.setMessageFrom(member);
				sm.setAddDefaults(updateBy);
				messages.add(sm);
			}
			
			break;
			
		case "Staff":
			
			List<Staff> listStaff = this.staffRepo.findByMember_Enabled(true);
			
			for (Staff staff: listStaff) {
				SchoolMessage sm = new SchoolMessage();
				sm.setSubject(message.getSubject());
				sm.setContent(message.getContent());
				sm.setMessageTo(staff.getMember());
				sm.setMessageFrom(member);
				sm.setAddDefaults(updateBy);
				messages.add(sm);
			}
			
			break;
			
		default:
			
			List<Member> listMember = this.memberRepo.findByEnabled(true);
			
			for (Member mem: listMember) {
				SchoolMessage sm = new SchoolMessage();
				sm.setSubject(message.getSubject());
				sm.setContent(message.getContent());
				sm.setMessageTo(mem);
				sm.setMessageFrom(member);
				sm.setAddDefaults(updateBy);
				messages.add(sm);
			}
			
			break;
		}
		
		this.messageRepo.saveAll(messages);
		
		return new TransactionResult(null, true);
	}
	
}

