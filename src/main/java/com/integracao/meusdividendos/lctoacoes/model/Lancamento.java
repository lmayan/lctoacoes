package com.integracao.meusdividendos.lctoacoes.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//@NotBlank(message = "O campo USUARIO é obrigatório.")
	@Size(min = 4, max = 20, message = "O campo Usuário não pode conter menos de 4 nem mais de 20 caracteres")
	private String usuario;

	@NotBlank(message = "O campo NOME AÇÃO/FII é obrigatório.")
	@Size(min = 5, max = 6, message = "O campo Nome da Ação não pode conter menos de 5 caracteres")
	private String nomeAcao;

	@NotNull(message = "O campo DATA é obrigatório.")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataCompraVenda;

	@NotNull(message = "O campo QUANTIDADE é obrigatório.")
	@DecimalMin(message = "VALOR minimo é de 1", value = "1")
	private int qtdade = 1;

	@NotBlank(message = "O campo TIPO VENDA/COMPRA é obrigatório.")
	@Size(min = 4, max = 10, message = "O campo Tipo Venda/Compra não pode conter menos de 4 ou mais de 10 caracteres")
	private String tipo_vc;

	@Enumerated(EnumType.STRING)
	private Tipo_AcFi tipo_acfi;

	@NotNull(message = "O campo VALOR UNITARIO é obrigatório.")
	@DecimalMin(message = "VALOR minimo é de 0,01", value = "0.01")
	@DecimalMax(message = "VALOR maximo é de 99.999,99", value = "9999999")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal vlrUnit;

	@NumberFormat(pattern = "#,##0.00")
	@DecimalMax(message = "VALOR maximo é de 99.999,99", value = "9999999")
	private BigDecimal vlrDesp = new BigDecimal(0);

	@NumberFormat(pattern = "#,##0.00")
	@DecimalMax(message = "VALOR maximo é de 999.999,99", value = "99999999")
	private BigDecimal totLcto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao.toUpperCase();
	}

	public Date getDataCompraVenda() {
		return dataCompraVenda;
	}

	public void setDataCompraVenda(Date dataCompraVenda) {
		this.dataCompraVenda = dataCompraVenda;
	}

	public int getQtdade() {
		return qtdade;
	}

	public void setQtdade(int qtdade) {
		this.qtdade = qtdade;
	}

	public String getTipo_vc() {
		return tipo_vc;
	}

	public void setTipo_vc(String tipo_vc) {
		this.tipo_vc = tipo_vc;
	}

	public Tipo_AcFi getTipo_acfi() {
		return tipo_acfi;
	}

	public void setTipo_acfi(Tipo_AcFi tipo_acfi) {
		this.tipo_acfi = tipo_acfi;
	}

	public BigDecimal getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(BigDecimal vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public BigDecimal getVlrDesp() {
		return vlrDesp;
	}

	public void setVlrDesp(BigDecimal vlrDesp) {
		this.vlrDesp = vlrDesp;
	}

	public BigDecimal getTotLcto() {
		return totLcto;
	}

	public void setTotLcto(BigDecimal totLcto) {
		this.totLcto = totLcto;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", usuario=" + usuario + ", nomeAcao=" + nomeAcao + ", dataCompraVenda="
				+ dataCompraVenda + ", qtdade=" + qtdade + ", tipo_vc=" + tipo_vc + ", tipo_acfi=" + tipo_acfi
				+ ", vlrUnit=" + vlrUnit + ", vlrDesp=" + vlrDesp + ", totLcto=" + totLcto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
