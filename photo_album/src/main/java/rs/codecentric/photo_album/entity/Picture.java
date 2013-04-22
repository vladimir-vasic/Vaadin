package rs.codecentric.photo_album.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author vladimir.vasic@codecentric.de
 * 
 */
@Entity
@Table(name = "picture")
@NamedQueries({ 
	@NamedQuery(name = "Picture.findAll", query = "SELECT p FROM Picture p ORDER BY p.pictureId"), 
	@NamedQuery(name = "Picture.getByName", query = "SELECT p FROM Picture p WHERE p.name = :name ORDER BY p.pictureId"),
	@NamedQuery(name = "Picture.getById", query = "SELECT p FROM Picture p WHERE p.pictureId = :pictureId"),
	@NamedQuery(name = "Picture.getAll4User", query = "SELECT p FROM Picture p JOIN p.album a WHERE a.albumOwner.userId = :userId ORDER BY p.pictureId")
	})
public class Picture implements Serializable {

	private static final long serialVersionUID = 6822988104117835241L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "picture_id", unique = true, nullable = false)
	private Long pictureId;

	@Column(name = "name", length = 256, nullable = false)
	@NotNull
	private String name;

	@Column(name = "content")
	@Lob
	private byte[] content;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	private Album album;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_datetime")
	private Date createDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_datetime")
	private Date updateDateTime;

	@Transient
	private String contentString;
	
	// getters setters
	public Long getPictureId() {
		return pictureId;
	}

	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
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

	public String getContentString()
	{
	   return new String(this.content);
	}
	
	@Override
	public String toString() {
		return "Picture [pictureId=" + pictureId + ", name=" + name + ", album=" + album.getAlbumName() + "]";
	}

}
