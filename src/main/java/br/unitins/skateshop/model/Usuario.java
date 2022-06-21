package br.unitins.skateshop.model;

import java.util.Objects;

import br.unitins.skateshop.application.Util;

public class Usuario implements Cloneable {
	private Integer id;
	private String nome;
	private String nomeUsuario;
	private String senha;
	private String email;
	private String cpf;
	private String contato;
	private NivelAdm nivelAdm;
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", nomeUsuario=" + nomeUsuario + ", senha=" + senha + ", email="
				+ email + ", cpf=" + cpf + ", contato=" + contato + "]";
	}

	public Usuario getClone() {
		try {
			return (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getContato() {
		return contato;
	}
	
	public void setContato(String contato) {
		this.contato = contato;
	}

	public NivelAdm getNivelAdm() {
		return nivelAdm;
	}

	public void setNivelAdm(NivelAdm nivelAdm) {
		this.nivelAdm = nivelAdm;
	}
}
