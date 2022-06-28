package com.aunanant.SpringGCpSecurity.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="detail")

public class Detail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@NotNull
    @Column(name = "detail_name")
    private String detailName;

	@Column(name = "imageDetail_url")
    private String imageDetailUrl;

	@NotNull
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	 private Product product;

	public Long getId() {
		return id;
	}

	public String getDetailName() {
		return detailName;
	}

	public String getImageDetailUrl() {
		return imageDetailUrl;
	}

	public Product getProduct() {
		return product;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public void setImageDetailUrl(String imageDetailUrl) {
		this.imageDetailUrl = imageDetailUrl;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	 
}
