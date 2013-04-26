package rs.codecentric.photo_album.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@NamedQueries({ 
	@NamedQuery(name = "Authority.findAll", query = "SELECT a FROM Authority a ORDER BY a.authorityId")
})
public class Authority implements Serializable {

	private static final long serialVersionUID = 8244749072904463870L;

	@Id
	@GeneratedValue
	@Column(name="id_authority")
	private Long authorityId;
	
	@Column(name="authority")
	private String authority;

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Authority [authorityId=" + authorityId + ", authority=" + authority + "]";
	}
	
}
