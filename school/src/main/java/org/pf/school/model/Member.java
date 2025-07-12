package org.pf.school.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.pf.school.common.BaseObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_member")
public class Member extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6443657914032291379L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_uid", length=50, nullable=false, unique=true)
	private String uid;
	
	@Column(name="f_password", length=500, nullable=false)
	private String password;
	
	@Column(name="f_name", length=100, nullable=true)
	private String name;
	
	@Transient
	private String retypePassword;
	
	@Column(name="f_account_non_expired", nullable=false)
	private Boolean accountNonExpired;
	
	@Column(name="f_account_non_locked", nullable=false)
	private Boolean accountNonLocked;
	
	@Column(name="f_credentials_non_expired", nullable=false)
	private Boolean credentialsNonExpired;
	
	@Column(name="f_enabled", nullable=false)
	private Boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(
			name = "tab_member_role",
			joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Transient
	private String searchFor;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Member [" + (id != null ? "id=" + id + ", " : "") + (uid != null ? "uid=" + uid + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (retypePassword != null ? "retypePassword=" + retypePassword + ", " : "")
				+ (accountNonExpired != null ? "accountNonExpired=" + accountNonExpired + ", " : "")
				+ (accountNonLocked != null ? "accountNonLocked=" + accountNonLocked + ", " : "")
				+ (credentialsNonExpired != null ? "credentialsNonExpired=" + credentialsNonExpired + ", " : "")
				+ (enabled != null ? "enabled=" + enabled + ", " : "") + (roles != null ? "roles=" + roles + ", " : "")
				+ (searchFor != null ? "searchFor=" + searchFor : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((uid != null ? uid + ", " : "")
				+ (name != null ? name + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((uid != null ? uid + ", " : "")
				+ (name != null ? name + ", " : ""));
	}
	
}