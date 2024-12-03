package org.libertas;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class MarcasDao {
	//private static LinkedList<Pessoa> lista = new LinkedList<Pessoa>();
	
	public void salvar(Marcas m) {
		if (m.getId()>0) {
			alterar(m);
		}else {
			inserir(m);
		}
	}
	
	public Retorno inserir(Marcas m) {
		//lista.add(p);
		//abre a conexao com o bd
		Conexao con = new Conexao();
		
		RetornoDao retornoDao = new RetornoDao();
		try {
			String sql = "INSERT INTO marca (nome, descricao, pais_origem, ano_fundacao, website)"
					+ " VALUES (?,?,?,?,?)";
			PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setString(1,  m.getNome());
			prep.setString(2,  m.getDescricao());
			prep.setString(3,  m.getPais_origem());
			prep.setString(4,  m.getAno_fundacao());
			prep.setString(5,  m.getWebsite());
			prep.execute();
			
			String mensagem = "Inserido com sucesso!";
			Boolean resposta = true;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			//fecha a conexao com o banco de dados 
			con.desconecta();
			return retorno;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			//fecha a conexao com o banco de dados 
			con.desconecta();
			String mensagem = "Falha ao inserir!";
			Boolean resposta = false;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			return retorno;
		}
	}
	public LinkedList<Marcas> listar(String pesquisa) {
		//return lista;
		LinkedList<Marcas> lista = new LinkedList<Marcas>();
		Conexao con = new Conexao();
		try {
			if (pesquisa == null){
				pesquisa="";
			}
			String sql = "SELECT * FROM marca "
					+ "WHERE nome like ? "
					+ "ORDER BY nome";
			PreparedStatement sta = con.getConnection().prepareStatement(sql);
			sta.setString(1,  "%" + pesquisa + "%");
			System.out.println(sta.toString());
			ResultSet res = sta.executeQuery();
			while (res.next()) {
				Marcas m = new Marcas();
				m.setId(res.getInt("id"));
				m.setNome(res.getString("nome"));
				m.setDescricao(res.getString("descricao"));
				m.setPais_origem(res.getString("pais_origem"));
				m.setAno_fundacao(res.getString("ano_fundacao"));
				m.setWebsite(res.getString("website"));
				lista.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		return lista;
	}
	
	public Retorno alterar(Marcas m) {
		Conexao con = new Conexao();
		
		RetornoDao retornoDao = new RetornoDao();
		
		try {
			String sql = "UPDATE marca SET"
					+" nome = ?, descricao = ?," 
					+ "pais_origem = ? , ano_fundacao = ?,"
					+ "website = ? "
					+ "WHERE id = ?";
			PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setString(1, m.getNome());
			prep.setString(2, m.getDescricao());
			prep.setString(3, m.getPais_origem());
			prep.setString(4, m.getAno_fundacao());
			prep.setString(5, m.getWebsite());
			prep.setInt(6, m.getId());
			prep.execute();
			
			String mensagem = "Alterado com sucesso!";
			Boolean resposta = true;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			//fecha a conexao com o banco de dados 
			con.desconecta();
			return retorno;
			
		} catch (Exception e) {
			e.printStackTrace();
			String mensagem = "Falha ao alterar!";
			Boolean resposta = false;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			//fecha a conexao com o banco de dados 
			con.desconecta();
			
			return retorno;
		}
		
	}
	public Retorno excluir(Marcas m) {
		Conexao con = new Conexao();
		
		RetornoDao retornoDao = new RetornoDao();
		
		try {
			String sql = "DELETE FROM marca"
					+ " WHERE id = ?";
			PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setInt(1, m.getId());
			prep.execute();
			
			String mensagem = "Excluido com sucesso!";
			Boolean resposta = true;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			//fecha a conexao com o banco de dados 
			con.desconecta();
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			
			String mensagem = "Falha ao excluir!";
			Boolean resposta = false;
			
			Retorno retorno = retornoDao.RetornoJson(resposta, mensagem);
			
			//fecha a conexao com o banco de dados 
			con.desconecta();
			
			return retorno;
		}
		
	}
	
	public Marcas consultar(int id) {
		Marcas m = new Marcas();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM marca WHERE id = "+ id;
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			if (res.next()) {
				m.setId(res.getInt("id"));
				m.setNome(res.getString("nome"));
				m.setDescricao(res.getString("descricao"));
				m.setPais_origem(res.getString("pais_origem"));
				m.setAno_fundacao(res.getString("ano_fundacao"));
				m.setWebsite(res.getString("website"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconecta();
		return m;
	}
}