package com.tts.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageEntity {
	
	@NotEmpty(message = "Message cannot be empty")
	@Length(max = 280, message = "Message cannot have more than 280 characters")
	private String content;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserEntity user;
	
	@CreationTimestamp
	private Date createdAt;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "message_tag", joinColumns = @JoinColumn(name = "message_id"),
	    inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;
	
	public UserEntity getUser() {
		return user;
	}
	public String getContent() {
		return content;
	}
	public Long getId() {
		return id;
	}
	
	public void setUser(UserEntity u) {
		user = u;
	}
	public void setContent(String c) {
		content = c;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	

}
