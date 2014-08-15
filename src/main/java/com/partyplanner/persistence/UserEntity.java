/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.persistence;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gustavo
 */
@Entity
@Table( name = "user" )
@NamedQueries({
	@NamedQuery(name="getUserByEmail",query="SELECT u FROM UserEntity u WHERE u.email = :email"),
	@NamedQuery(name="getUserByNickname",query="SELECT u FROM UserEntity u WHERE u.nickname = :nickname")
})
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column( name = "user_id", insertable = false )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column( name = "user_created", insertable = false, updatable = false )
	private Date created;
	
	@Column( name = "user_updated", insertable = false )
	private Date updated;
	
	@Column( name = "user_status_id", insertable = false )
	private int statusId;
	
	@Column( name = "user_nickname" )
	private String nickname;
	
	@Column( name = "user_first_name" )
	private String firstName;
	
	@Column( name = "user_last_name" )
	private String lastName;
	
	@Column( name = "user_email" )
	private String email;
	
	@Column( name = "user_email_confirmation", insertable = false )
	private boolean emailConfirmation;
	
	@Column( name = "user_password" )
	private String password;
	
	@Column( name = "user_last_login_attempt_time", insertable = false )
	private Date lastLoginAttemptTime;
	
	@Column( name = "user_last_login_attempt_ip" )
	private String lastLoginAttemptIp;
	
	@Column( name = "user_bad_login_attempt_count", insertable = false )
	private int badLoginAttemptCount;
	
	@Column( name = "user_birth_date" )
	private Date birthDate;
	
	@Column( name = "user_communication_optin" )
	private boolean communicationOptin;
	
	@Column( name = "user_gender" )
	private boolean gender;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailConfirmation() {
		return emailConfirmation;
	}

	public void setEmailConfirmation(boolean emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginAttemptTime() {
		return lastLoginAttemptTime;
	}

	public void setLastLoginAttemptTime(Date lastLoginAttemptTime) {
		this.lastLoginAttemptTime = lastLoginAttemptTime;
	}

	public String getLastLoginAttemptIp() {
		return lastLoginAttemptIp;
	}

	public void setLastLoginAttemptIp(String lastLoginAttemptIp) {
		this.lastLoginAttemptIp = lastLoginAttemptIp;
	}

	public int getBadLoginAttemptCount() {
		return badLoginAttemptCount;
	}

	public void setBadLoginAttemptCount(int badLoginAttemptCount) {
		this.badLoginAttemptCount = badLoginAttemptCount;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isCommunicationOptin() {
		return communicationOptin;
	}

	public void setCommunicationOptin(boolean communicationOptin) {
		this.communicationOptin = communicationOptin;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) id;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof UserEntity)) {
			return false;
		}
		UserEntity other = (UserEntity) object;
		return this.id == other.id;
	}

	@Override
	public String toString() {
		return "com.partyplanner.persistence.UserEntity[ id=" + id + " ]";
	}
	
}
