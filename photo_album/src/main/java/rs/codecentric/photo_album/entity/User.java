package rs.codecentric.photo_album.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author vladimir.vasic@codecentric.de
 * 
 */
@Entity
@Table(name = "users")
@NamedQueries({ 
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.userId"),
	@NamedQuery(name = "User.findPosibleFriends", query = "SELECT u FROM User u WHERE NOT u.userId = :userId AND NOT u IN (SELECT fr FROM User cur JOIN cur.friends fr WHERE cur.userId = :curUserId)")
})
public class User implements Serializable {

	private static final long serialVersionUID = 6561462504945298344L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "username", unique = true, nullable = false)
	private String userName;

	@Column(name = "password", unique = true, nullable = false)
	private String userPassword;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name = "user_authorities", joinColumns = { @JoinColumn(name = "user_id")}, inverseJoinColumns={@JoinColumn(name="id_authority")})
	private Set<Authority> authoritySet;
	
	@Column(name = "user_email", unique = true, nullable = false)
	private String userEmail;

	@Column(name = "user_lib", unique = true)
	private String userLib;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Album> userAlbums;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_datetime")
	private Date createDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_datetime")
	private Date updateDateTime;
	
	@ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="user_friend", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="friend_id")})
	@LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> friends = new HashSet<User>();
 
    @ManyToMany(mappedBy="friends")
    private Set<User> teammates = new HashSet<User>();
    
	// getters setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authority> getAuthoritySet() {
		return authoritySet;
	}

	public void setAuthoritySet(Set<Authority> authoritySet) {
		this.authoritySet = authoritySet;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<Album> getUserAlbums() {
		return userAlbums;
	}

	public void setUserAlbums(List<Album> userAlbums) {
		this.userAlbums = userAlbums;
	}

	public String getUserLib() {
		return userLib;
	}

	public void setUserLib(String userLib) {
		this.userLib = userLib;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<User> getTeammates() {
		return teammates;
	}

	public void setTeammates(Set<User> teammates) {
		this.teammates = teammates;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPassword=" + userPassword + ", enabled=" + enabled
				+ ", authoritySet has elements" + authoritySet.iterator().hasNext() + ", userEmail=" + userEmail
				+ ", userLib=" + userLib + ", userAlbums=" + userAlbums
				+ ", createDateTime=" + createDateTime + ", updateDateTime="
				+ updateDateTime + ", friends has elements" + friends.iterator().hasNext()
				+ ", teammates has elements" + teammates.iterator().hasNext() + "]";
	}
	
}

