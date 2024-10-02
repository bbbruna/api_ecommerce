package ecproducts.com.products.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "product")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private String urlImage;
	private String technicalInfo;

	// price
	private BigDecimal price;
	private BigDecimal dailyRental;
	private BigDecimal weeklyRental;
	private BigDecimal biweeklyRental;
	private BigDecimal monthlyRental;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getTechnicalInfo() {
		return technicalInfo;
	}

	public void setTechnicalInfo(String technicalInfo) {
		this.technicalInfo = technicalInfo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDailyRental() {
		return dailyRental;
	}

	public void setDailyRental(BigDecimal dailyRental) {
		this.dailyRental = dailyRental;
	}

	public BigDecimal getWeeklyRental() {
		return weeklyRental;
	}

	public void setWeeklyRental(BigDecimal weeklyRental) {
		this.weeklyRental = weeklyRental;
	}

	public BigDecimal getBiweeklyRental() {
		return biweeklyRental;
	}

	public void setBiweeklyRental(BigDecimal biweeklyRental) {
		this.biweeklyRental = biweeklyRental;
	}

	public BigDecimal getMonthlyRental() {
		return monthlyRental;
	}

	public void setMonthlyRental(BigDecimal monthlyRental) {
		this.monthlyRental = monthlyRental;
	}
}